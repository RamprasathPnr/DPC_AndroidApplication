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
import com.omneagate.dpc.R;
import com.omneagate.dpc.Utility.Constants;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.NetworkConnection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by root on 26/9/16.
 */
public class OfflineTruckMemoManager extends Service implements Handler.Callback, ServiceResponseListener {
    Timer TruckMemoManager;
    private TruckMemoTimerTask TruckMemoTask;
    List<DpcTruckMemoDto> TruckMemoList;
    private ResReqController controller = new ResReqController(this);
    Context context;


    public OfflineTruckMemoManager() {
        super();
    }

    public OfflineTruckMemoManager(Context context) {
        this.context = context;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        controller = new ResReqController(com.omneagate.dpc.Service.OfflineTruckMemoManager.this);
        controller.addOutboxHandler(new Handler(Looper.getMainLooper()));
        TruckMemoManager = new Timer();
        TruckMemoTask = new TruckMemoTimerTask();
        Long timerWaitTime = Long.parseLong(getString(R.string.serviceTimeout));
        TruckMemoManager.schedule(TruckMemoTask, 18000, timerWaitTime);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }


    @Override
    public void onError(String url, HashMap<String, Object> requestParams, String errorMessage, Boolean isVolleyError) {

    }

    @Override
    public void onNetWorkFailure(String url) {

    }

    class TruckMemoTimerTask extends TimerTask {

        @Override
        public void run() {
            try {
                TruckMemoList = new ArrayList<>();

                try {
                    TruckMemoList = DBHelper.getInstance(OfflineTruckMemoManager.this).GetUnsynedTruckMemo();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.e("TruckMemoList", "" + TruckMemoList.toString());
                NetworkConnection network = new NetworkConnection(OfflineTruckMemoManager.this);
                if (network.isNetworkAvailable()) {
                    for (DpcTruckMemoDto truckMemoDto : TruckMemoList) {
                        syncTruckMemoToServer(truckMemoDto);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void syncTruckMemoToServer(DpcTruckMemoDto dpcTruckMemoDto) {
        new OfflineDataSyncTask().execute(dpcTruckMemoDto);
    }

    private class OfflineDataSyncTask extends AsyncTask<DpcTruckMemoDto, String, String> {

        @Override
        protected String doInBackground(DpcTruckMemoDto... dpcTruckMemoDtos) {

            try {
                String dpctruckmeo = new Gson().toJson(dpcTruckMemoDtos[0]);
                controller.handleMessage_(ResReqController.OFFLINE_TRUCKMEMO, dpctruckmeo, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    @Override
    public void onSuccessResponse(String url, HashMap<String, Object> requestParams, String response) {
        switch (url) {
            case Constants.OFFLINE_TRUCKMEMO_URL:
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Log.d("Check", "" + response);
                DpcTruckMemoDto truckMemoResponse = gson.fromJson(response, DpcTruckMemoDto.class);
                String truckMemoNumber = truckMemoResponse.getTruckMemoNumber();
                if (truckMemoResponse.getStatusCode().equalsIgnoreCase("0")) {
                    DBHelper.getInstance(com.omneagate.dpc.Service.OfflineTruckMemoManager.this).UpdateTruckMemoNumber(truckMemoNumber);
                }
                break;
        }

    }
}
