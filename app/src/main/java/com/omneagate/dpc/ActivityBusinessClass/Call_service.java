package com.omneagate.dpc.ActivityBusinessClass;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.omneagate.dpc.Receiver.AlarmReceiver;

/**
 * Created by user1 on 22/11/16.
 */
public class Call_service {
    Context context;

    public void startservices(Context context) {
        this.context = context;
//        scheduleHearbeatAlarm();
//        scheduleStatisticsAlarm();
    }

    public void scheduleHearbeatAlarm() {
        Log.e("Tag", "scheduleHeartBeatAlarm called...");
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("requestCode", 0);
        final PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis();
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, firstMillis, 600000, pIntent);//900000
    }

    // Setup a recurring alarm for every fifteen minutes
    public void scheduleStatisticsAlarm() {
        Log.e("Tag", "scheduleStatisticsAlarm called...");
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra("requestCode", 1);
        final PendingIntent pIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        long firstMillis = System.currentTimeMillis() + 5000;
        AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, firstMillis, 660000, pIntent);
    }
}
