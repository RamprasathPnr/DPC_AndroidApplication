package com.omneagate.dpc.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omneagate.dpc.Model.DPCResponseDto;
import com.omneagate.dpc.Model.DpcTruckMemoDto;
import com.omneagate.dpc.Model.FarmerRegistrationRequestDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Utility.Constants;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.NetworkConnection;
import com.omneagate.dpc.Utility.ResultUpdatable;
import com.omneagate.dpc.Utility.Volley_service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/*
*
 * Created by user on 26/8/16.
 */

public class OffLineFarmerSyncService extends Service implements Handler.Callback, ServiceResponseListener, ResultUpdatable {

    Timer farmerSyncTimer;
    private FarmerTimerTask farmerTimerTask;
    List<FarmerRegistrationRequestDto> syncFarmerRegistrationList;
    Context context;
    private String serverUrl;
    private ResReqController controller = new ResReqController(this);

    public OffLineFarmerSyncService() {
        super();
    }

    public OffLineFarmerSyncService(Context context) {
        this.context = context;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        controller = new ResReqController(com.omneagate.dpc.Service.OffLineFarmerSyncService.this);
        controller.addOutboxHandler(new Handler(Looper.getMainLooper()));

        Log.e("siva offline", "service start");
        serverUrl = DBHelper.getInstance(getApplicationContext()).getMasterData("serverUrl");
        farmerSyncTimer = new Timer();
        farmerTimerTask = new FarmerTimerTask();
        Long timerWaitTime = Long.parseLong(getString(R.string.serviceTimeout));
        farmerSyncTimer.schedule(farmerTimerTask, 15000, timerWaitTime);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }


    class FarmerTimerTask extends TimerTask {

        @Override
        public void run() {
            try {
                syncFarmerRegistrationList = new ArrayList<>();
                syncFarmerRegistrationList = DBHelper.getInstance(OffLineFarmerSyncService.this).getAllFarmers();
                Log.e("THE LIST IS", "syncFarmerRegistrationList++++++++" + syncFarmerRegistrationList);
                NetworkConnection network = new NetworkConnection(OffLineFarmerSyncService.this);
                if (network.isNetworkAvailable()) {
//                    Log.e("inside online", "if success");
                    for (FarmerRegistrationRequestDto farmer : syncFarmerRegistrationList) {
                        Log.e("inside for", "for");
                        syncFarmerToServer(farmer);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void syncFarmerToServer(FarmerRegistrationRequestDto farmer) {
//        Log.e("inside syncFarmerToServer", "syncFarmerToServer");
        new OfflineDataSyncTask().execute(farmer);
    }


    private class OfflineDataSyncTask extends AsyncTask<FarmerRegistrationRequestDto, String, String> {


        @Override
        protected String doInBackground(FarmerRegistrationRequestDto... farmer) {
            try {
                Log.e("inside OfflineDataSyncTask", "OfflineDataSyncTask");
                String farmer_one = new Gson().toJson(farmer[0]);
                Log.e("farmer_one json......", farmer_one);
                String url = serverUrl + Constants.FARMER_REGISTRATION;
                Log.e("FARMER RequestURL", url);

                controller.handleMessage_(ResReqController.OFFLINE_REGISTRATION, farmer_one, null);

//                new Volley_service().execute(url, farmer_one, OffLineFarmerSyncService.this, "Offline Farmer");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    @Override
    public void setResult(String result, String id) {
       /* GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        Log.d("Check", "" + result);
        DPCResponseDto reg_res_dto = gson.fromJson(result, DPCResponseDto.class);
        Log.e("RESPONSE DATA OFFLINE FARMER REQ", "" + reg_res_dto);
        String res_ref_number = reg_res_dto.getReferenceNumber();
        Log.e("RESPONSE REF NUMBER OffLineFarmerSyncService", "" + res_ref_number);
        if (res_ref_number != null) {
            DBHelper.getInstance(com.omneagate.dpc.Service.OffLineFarmerSyncService.this).UpdateRegistrationNumber(res_ref_number);
        }*/
    }


    @Override
    public void onSuccessResponse(String url, HashMap<String, Object> requestParams, String response) {

        switch (url) {
            case Constants.OFFLINE_REGISTRATION:
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Log.d("Check", "" + response);
                DPCResponseDto reg_res_dto = gson.fromJson(response, DPCResponseDto.class);
                Log.e("RESPONSE DATA OFFLINE FARMER REQ", "" + reg_res_dto);
                String res_ref_number = reg_res_dto.getReferenceNumber();
                Log.e("RESPONSE REF NUMBER OffLineFarmerSyncService", "" + res_ref_number);
                if (res_ref_number != null) {
                    DBHelper.getInstance(com.omneagate.dpc.Service.OffLineFarmerSyncService.this).UpdateRegistrationNumber(res_ref_number);
                }
                break;
        }

    }

    @Override
    public void onError(String url, HashMap<String, Object> requestParams, String errorMessage, Boolean isVolleyError) {

    }

    @Override
    public void onNetWorkFailure(String url) {

    }
}
