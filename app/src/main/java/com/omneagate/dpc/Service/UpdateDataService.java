package com.omneagate.dpc.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.omneagate.dpc.Utility.DownloadDataProcessor;
import com.omneagate.dpc.Utility.NetworkConnection;
import com.omneagate.dpc.Utility.SessionId;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Service for syncing connection of server
 */
public class UpdateDataService extends Service {

    Timer syncTimer;
    SyncTimerTask syncTimerTask;

    @Override
    public void onCreate() {
        super.onCreate();
        syncTimer = new Timer();
        syncTimerTask = new SyncTimerTask();
//        Util.LoggingQueue(this, "Info", "Service DB sync manager started");
//        Long timerWaitTime = 5 * 60 * 1000l;
        Long timerWaitTime = 15 * 60 * 1000l;
        syncTimer.schedule(syncTimerTask, 0, timerWaitTime);
//        Util.LoggingQueue(UpdateDataService.this, "Info", "Service DB sync manager created");
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public void onDestroy() {
        try {
            if (syncTimer != null) {
                syncTimer.cancel();
                syncTimer = null;
            }
        } catch (Exception e) {
            Log.e("Error in UPDATE destroy", "Error in Service", e);
        }

    }

    class SyncTimerTask extends TimerTask {

        @Override
        public void run() {

           /* String session_id = loginresponse_dto.getSessionid();
            SessionId.getInstance().setSessionId(session_id);*/

            String id = SessionId.getInstance().getSessionId();
            Log.e("Session id timer task", id);
            try {
                if (SessionId.getInstance().getSessionId().length() > 0) {
                    Log.e("inside session", "session");
                    NetworkConnection network = new NetworkConnection(UpdateDataService.this);

                    if (network.isNetworkAvailable()) {
                        DownloadDataProcessor dataDownload = new DownloadDataProcessor(UpdateDataService.this);
                        dataDownload.processor(UpdateDataService.this);
                    }
                }
//                Util.LoggingQueue(UpdateDataService.this, "Info", "Service DB sync manager ended");
            } catch (Exception e) {
//                Util.LoggingQueue(UpdateDataService.this, "Error", e.getMessage());
                Log.e("Error", e.toString(), e);
            }
        }

    }

}
