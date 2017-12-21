package com.omneagate.dpc.Service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omneagate.dpc.Model.DPCSyncExceptionDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Utility.Constants;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.NetworkConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 17/8/16.
 */
public class FirstSyncExceptionService extends Service implements Handler.Callback, ServiceResponseListener {


    //    List<DPCSyncExceptionDto> dpcSyncExceptionDtoList;
    private String localId;
    private ResReqController controller;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("INSIDE SERVICE", "SERVICE");
        controller = new ResReqController(com.omneagate.dpc.Service.FirstSyncExceptionService.this);
        controller.addOutboxHandler(new Handler(Looper.getMainLooper()));

//        Util.LoggingQueue(this, "FirstSyncExceptionService", "onCreate() called");
        try {
//            Util.LoggingQueue(FirstSyncExceptionService.this, "FirstSyncExceptionService", "SyncExcTimerTask called , Started to send FirstSyncExceptionService message");
            Constants.dpcSyncExceptionDtoList = new ArrayList<>();
            Constants.dpcSyncExceptionDtoList = DBHelper.getInstance(com.omneagate.dpc.Service.FirstSyncExceptionService.this).getAllSyncExcData();

            Log.e("dpcSyncExceptionDtoList ser",Constants.dpcSyncExceptionDtoList.toString());

//            Util.LoggingQueue(FirstSyncExceptionService.this, "FirstSyncExceptionService", "SyncExcTimerTask , No of Execptions in PosSyn " + posSyncExceptionDtoList.size());
            NetworkConnection network = new NetworkConnection(com.omneagate.dpc.Service.FirstSyncExceptionService.this);
            if (network.isNetworkAvailable()) {
//                Util.LoggingQueue(FirstSyncExceptionService.this, "FirstSyncExceptionService", "SyncExcTimerTask , NetworkAvailable ");
                updateToServer();
            }
//            Util.LoggingQueue(FirstSyncExceptionService.this, "FirstSyncExceptionService", "SyncExcTimerTask , End of FirstSyncExceptionService message ");
        } catch (Exception e) {
            e.printStackTrace();
//            Util.LoggingQueue(FirstSyncExceptionService.this, "FirstSyncExceptionService", "SyncExcTimerTask , syncExc UpdateToServer Error ");
        }
    }


    private void updateToServer() {
        try {
//            Util.LoggingQueue(FirstSyncExceptionService.this, "FirstSyncExceptionService", "updateToServer() called");

            if (Constants.dpcSyncExceptionDtoList != null
                    && Constants.dpcSyncExceptionDtoList.size() > 0) {
                new excUpdateToServerAsync().execute(Constants.dpcSyncExceptionDtoList.get(0));
            } else {
                System.out.println("Shiva checking constant value else method ::::");
            }
        } catch (Exception e) {
            e.printStackTrace();
//            Util.LoggingQueue(FirstSyncExceptionService.this, "FirstSyncExceptionService", "updateToServer() Exception = " + e);
        }
    }


    class excUpdateToServerAsync extends AsyncTask<DPCSyncExceptionDto, String, String> {
        @Override
        protected String doInBackground(DPCSyncExceptionDto... syncExcData) {
            Log.e("excUpdateToServerAsync", "excUpdateToServerAsync");
            localId = "" + syncExcData[0].getLocalId();
            String syncExcStr = new Gson().toJson(syncExcData[0]);
            Log.e("syncExcStr","syncExcStr"+syncExcStr);

            controller = new ResReqController(com.omneagate.dpc.Service.FirstSyncExceptionService.this);
            controller.handleMessage_(ResReqController.DPC_POS_EXCEPTION, syncExcStr, null);
            return null;

        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }

    @Override
    public void onSuccessResponse(String url, HashMap<String, Object> requestParams, String response) {

        try {
            switch (url) {
                case Constants.DPC_POS_EXCEPTION:
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    DPCSyncExceptionDto dpcSyncExceptionDto = gson.fromJson(response, DPCSyncExceptionDto.class);

                    if ((dpcSyncExceptionDto != null) && (dpcSyncExceptionDto.getStatusCode().equalsIgnoreCase("0"))) {
//                        Util.LoggingQueue(FirstSyncExceptionService.this, "FirstSyncExceptionService", "excUpdateToServerAsync onPostExecute  localId = " + localId);
                        DBHelper.getInstance(com.omneagate.dpc.Service.FirstSyncExceptionService.this).updateSyncExcData(dpcSyncExceptionDto, localId);
                        Constants.dpcSyncExceptionDtoList.remove(0);
                        if (Constants.dpcSyncExceptionDtoList.size() > 0) {
//                            Util.LoggingQueue(FirstSyncExceptionService.this, "FirstSyncExceptionService", "excUpdateToServerAsync onPostExecute  Sending  = " + dpcSyncExceptionDtoList);
                            updateToServer();
                        } /*else {
//                            Util.LoggingQueue(FirstSyncExceptionService.this, "FirstSyncExceptionService", "excUpdateToServerAsync onPostExecute  completed............  = ");
                        }*/
                    }
                    break;
            }

        } catch (Exception e) {

        }

    }

    @Override
    public void onError(String url, HashMap<String, Object> requestParams, String errorMessage, Boolean isVolleyError) {
        try {
            switch (url) {
                case Constants.DPC_POS_EXCEPTION:
                    Toast.makeText(com.omneagate.dpc.Service.FirstSyncExceptionService.this, getString(R.string.toast_server_unreachable), Toast.LENGTH_SHORT).show();
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onNetWorkFailure(String url) {

    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
