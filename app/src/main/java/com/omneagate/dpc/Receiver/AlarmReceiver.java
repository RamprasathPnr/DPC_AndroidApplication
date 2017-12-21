package com.omneagate.dpc.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.omneagate.dpc.Service.HeartBeatService;
import com.omneagate.dpc.Service.StartService;
import com.omneagate.dpc.Service.StatisticsServices;
import com.omneagate.dpc.Utility.GetSessionId;
import com.omneagate.dpc.process.HeartBeatProcess;
import com.omneagate.dpc.process.StatisticsProcess;

public class AlarmReceiver extends BroadcastReceiver implements StartService {
//    private int requestCode;
    Context receiverContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.receiverContext = context;
        int requestCode = intent.getExtras().getInt("requestCode");
        Log.e("requestCode = ", "" + requestCode);
        GetSessionId.getInstance(context).callServer_forSession(this,requestCode);
    }

    @Override
    public void callservice(int requestCode) {
        if (requestCode == 0) {
            Intent i = new Intent(receiverContext, HeartBeatService.class);
            HeartBeatProcess heartBeat = new HeartBeatProcess();
            i.putExtra("value", heartBeat);
            receiverContext.startService(i);
            Log.e("AlarmReceiver", "HeartBeatService alarm service started");
        } else {
            Intent intent = new Intent(receiverContext, StatisticsServices.class);
//            Bundle bundle = new Bundle();
            StatisticsProcess statClass = new StatisticsProcess();
            intent.putExtra("value", statClass);
            receiverContext.startService(intent);
            Log.e("AlarmReceiver", "StatisticsServices alarm service started");
        }
    }
}