package com.omneagate.dpc.Receiver;

/**
 * Created by user on 18/03/16.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Model.EnumModel.ApplicationType;
import com.omneagate.dpc.Model.LoginHistoryDto;
import com.omneagate.dpc.Model.ResponseDto;
import com.omneagate.dpc.Model.UserDetailDto;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Service.OffLineFarmerSyncService;
import com.omneagate.dpc.Service.OffLineProcurementSync;
import com.omneagate.dpc.Service.OfflineTruckMemoManager;
import com.omneagate.dpc.Service.ResReqController;
import com.omneagate.dpc.Service.ServiceResponseListener;
import com.omneagate.dpc.Service.UpdateDataService;
import com.omneagate.dpc.Service.Utilities;
import com.omneagate.dpc.Utility.Constants;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.LoginData;
import com.omneagate.dpc.Utility.MySharedPreference;
import com.omneagate.dpc.Utility.SessionId;
import com.omneagate.dpc.Utility.Util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ConnectivityReceiver extends BroadcastReceiver implements Handler.Callback, ServiceResponseListener {

    public static ConnectivityReceiverListener connectivityReceiverListener;
    private Context mContext;
    private ResReqController controller;

    public ConnectivityReceiver() {
        super();
    }

    public ConnectivityReceiver(Context context_) {
        this.mContext = context_;
    }

    @Override
    public void onReceive(Context context, Intent arg1) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        controller = new ResReqController(context);
        controller.addOutboxHandler(new Handler(this));
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener.onNetworkConnectionChanged(isConnected);
        }

        if (isConnected) {
            if (GlobalAppState.localLogin) {
                this.mContext = context;
                try {
                    UserDetailDto loginResponse = DBHelper.getInstance(mContext)
                            .getBackgroundLogin(SessionId.getInstance().getUserId());
                    HashMap<String, Object> inputParams = Utilities.getInstance().getRequestParams();
                    inputParams.put("userName", loginResponse.getUsername());
                    inputParams.put("password", Util.DecryptPassword(loginResponse.getEncryptedPassword()));
                    inputParams.put("deviceId", Settings.Secure.getString(mContext.getContentResolver(),
                            Settings.Secure.ANDROID_ID).toUpperCase());
                    inputParams.put("appType", String.valueOf(ApplicationType.DPC));
                    Object data = "";
                    controller.handleMessage(ResReqController.BACKGROUND_LOGIN, inputParams, data);
                } catch (Exception e) {
                    Log.e("NetworkChangeReceiver", "onReceive() ,Exception = " + e);
                }
            }

        }
    }

    public static boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) Application.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onSuccessResponse(String url, HashMap<String, Object> requestParams, String response) {
        try {
            switch (url) {
                case Constants.BACKGROUND_LOGIN:
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();

                    Log.e("Check", "" + response);
                    ResponseDto loginresponse_dto = gson.fromJson(response, ResponseDto.class);
                    LoginData.getInstance().setResponseData(loginresponse_dto);

                    if (loginresponse_dto.getStatusCode().equalsIgnoreCase("0")
                            && loginresponse_dto.getDeviceStatus().toString().equalsIgnoreCase("ACTIVE")
                            && loginresponse_dto.getUserDetailDto().getProfile().equalsIgnoreCase("DPC")
                            || loginresponse_dto.getUserDetailDto().getProfile().equalsIgnoreCase("ADMIN")) {

                        GlobalAppState.localLogin = true;
                        MySharedPreference.writeBoolean(mContext,
                                MySharedPreference.DEVICE_STATUS, true);
                        SessionId.getInstance().setSessionId(loginresponse_dto.getSessionid());
                        SessionId.getInstance().setUserId(loginresponse_dto.getUserDetailDto().getId());

                        mContext.startService(new Intent(mContext, UpdateDataService.class));
                        mContext.startService(new Intent(mContext, OffLineFarmerSyncService.class));
                        mContext.startService(new Intent(mContext, OffLineProcurementSync.class));
                        mContext.startService(new Intent(mContext, OfflineTruckMemoManager.class));
                        insertLoginHistoryDetails(loginresponse_dto);
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(String url, HashMap<String, Object> requestParams, String errorMessage, Boolean isVolleyError) {
    }

    @Override
    public void onNetWorkFailure(String url) {
    }


    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(boolean isConnected);
    }

    private void insertLoginHistoryDetails(ResponseDto loginresponse_dto) {
        LoginHistoryDto loginHistoryDto = new LoginHistoryDto();
        if (loginresponse_dto.getUserDetailDto().getDpcProfileDto() != null)
            loginHistoryDto.setDpcId(loginresponse_dto.getUserDetailDto().getDpcProfileDto().getId());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        loginHistoryDto.setLoginTime(df.format(new Date()));
        loginHistoryDto.setLoginType("BACKGROUND_LOGIN");
        loginHistoryDto.setUserId(loginresponse_dto.getUserDetailDto().getId());
        df = new SimpleDateFormat("ddMMyyHHmmss", Locale.getDefault());
        String transactionId = df.format(new Date());
        loginHistoryDto.setTransactionId(transactionId);
        SessionId.getInstance().setTransactionId(transactionId);
        DBHelper.getInstance(mContext).insertLoginHistory(loginHistoryDto);
    }

}