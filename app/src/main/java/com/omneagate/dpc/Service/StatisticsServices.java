package com.omneagate.dpc.Service;

import android.app.IntentService;
import android.content.Intent;

public class StatisticsServices extends IntentService {
    public StatisticsServices() {
        super("StatisticsServices");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Statistics task
        BaseSchedulerService bsStats = (BaseSchedulerService) intent.getSerializableExtra("value");
        bsStats.process(StatisticsServices.this);
    }
}
