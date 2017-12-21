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
import com.omneagate.dpc.Model.DPCProcurementDto;

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
 * Created by user on 27/9/16.
 */
public class OffLineProcurementSync extends Service implements Handler.Callback, ServiceResponseListener {
    Timer procurementSyncTimer;
    private ProcurementTimerTask procurementTimerTask;
    private ResReqController controller = new ResReqController(this);
    List<DPCProcurementDto> procurement_list;
    Context context;

    public OffLineProcurementSync() {
        super();
    }

    public OffLineProcurementSync(Context context) {
        this.context = context;
    }
    @Override
    public void onCreate() {
        Log.e("siva offline","OffLineProcurementSync service start");
        super.onCreate();
        controller = new ResReqController(com.omneagate.dpc.Service.OffLineProcurementSync.this);
        controller.addOutboxHandler(new Handler(Looper.getMainLooper()));
        procurementSyncTimer = new Timer();
        procurementTimerTask = new ProcurementTimerTask();
//        Util.LoggingQueue(OfflineTransactionManager.this, "OfflineTransactionManager", "UserDetailsDialog() called");
//        Util.LoggingQueue(OfflineTransactionManager.this, "Info", "Service OfflineTransactionManager created ");
        Long timerWaitTime = Long.parseLong(getString(R.string.serviceTimeout));
        procurementSyncTimer.schedule(procurementTimerTask, 15000, timerWaitTime);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class ProcurementTimerTask extends TimerTask {

        @Override
        public void run() {
            try {
                procurement_list = new ArrayList<>();
                procurement_list = DBHelper.getInstance(OffLineProcurementSync.this).getProcurement();
                Log.e("procurement_list","*************************"+procurement_list.toString());
                NetworkConnection network = new NetworkConnection(OffLineProcurementSync.this);
                if (network.isNetworkAvailable()) {
                    Log.e("inside online","if success");
                    for (DPCProcurementDto procurement : procurement_list) {
                        Log.e("inside procurement_list","for");
//                        Util.LoggingQueue(OfflineTransactionManager.this, "Info", "Processing bill id" + bill.getTransactionId());
                        syncProcurementToServer(procurement);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    private void syncProcurementToServer(DPCProcurementDto procurement) {
        Log.e("inside syncProcurementToServer","syncProcurementToServer");
        new OfflineDataProcurementSyncTask().execute(procurement);
    }

    private class OfflineDataProcurementSyncTask extends AsyncTask<DPCProcurementDto, String, String> {

        @Override
        protected String doInBackground(DPCProcurementDto... procurement) {

            try {
                Log.e("inside OfflineDataSyncTask","OfflineDataSyncTask");
                String receipt_one = new Gson().toJson(procurement[0]);
                controller.handleMessage_(ResReqController.OFFLINE_PROCUREMENT, receipt_one, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
                case Constants.OFFLINE_PROCUREMENT:
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    Log.d("Check", "" + response.toString());
                    DPCProcurementDto pro_res_dto = gson.fromJson(response.toString(), DPCProcurementDto.class);
                    String ReceiptNumber = pro_res_dto.getProcurementReceiptNo();
                    if (pro_res_dto.getStatusCode().equalsIgnoreCase("0")) {
                        DBHelper.getInstance(context).UpdateProcurementReceiptNumber(ReceiptNumber);
                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onError(String url, HashMap<String, Object> requestParams, String errorMessage, Boolean isVolleyError) {
        try {
            switch (url) {
                case Constants.OFFLINE_PROCUREMENT:
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNetWorkFailure(String url) {
    }
}
