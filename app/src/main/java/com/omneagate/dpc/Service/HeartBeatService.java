package com.omneagate.dpc.Service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class HeartBeatService extends IntentService {

    public HeartBeatService() {
        super("HeartBeatService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("HeartBeatService", "started");

        BaseSchedulerService bsStats = (BaseSchedulerService) intent.getSerializableExtra("value");
        bsStats.process(HeartBeatService.this);

//        HeartBeatProcess heartBeatClass = new HeartBeatProcess();
//        BaseSchedulerService bs = (BaseSchedulerService) heartBeatClass;
//        bs.process(HeartBeatService.this);
    }
}
