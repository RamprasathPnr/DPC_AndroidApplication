package com.omneagate.dpc.process;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.os.BatteryManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omneagate.dpc.Model.DpcHeartBeatDto;
import com.omneagate.dpc.Model.DpcHeartBeatResponseDto;
import com.omneagate.dpc.Model.ResponseDto;
import com.omneagate.dpc.Service.BaseSchedulerService;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.GPSService;
import com.omneagate.dpc.Utility.LocationId;
import com.omneagate.dpc.Utility.LoginData;
import com.omneagate.dpc.Utility.NetworkConnection;
import com.omneagate.dpc.Utility.ResultUpdatable;
import com.omneagate.dpc.Utility.SessionId;
import com.omneagate.dpc.Utility.Util;
import com.omneagate.dpc.Utility.Volley_service;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;


public class HeartBeatProcess implements BaseSchedulerService, Serializable, ResultUpdatable {

    DpcHeartBeatDto heartBeatDto;
    GPSService mGPSService;
    private int batteryLevel = 0;
    private static String serverUrl = null;
    Context globalContext;
    static boolean register = false;
    private static long dpcId;
    BatteryReceiver batteryLevelReceiver = new BatteryReceiver();

    public void process(Context context) {
        globalContext = context;
        if (serverUrl == null) {
            serverUrl = DBHelper.getInstance(globalContext).getMasterData("serverUrl");
        }
        if (dpcId == 0)
            dpcId = getDpcId();
        // Check whether sessionId is empty or null
        if ((SessionId.getInstance().getSessionId() != null) && (!SessionId.getInstance().getSessionId().equalsIgnoreCase(""))) {
            startProcess();
        }
    }

    private void startProcess() {
        registerBatteryReceiver();
        initializeValues();
        getCurrentLocation();
        getPackageInformation();
        boolean sessionInvalid = HeartBeatTask();
        unregisterBatteryReceiver();
    }

    private void initializeValues() {
        heartBeatDto = new DpcHeartBeatDto();
    }

    private long getDpcId() {
        long dpcId = 0;
        try {
            ResponseDto responsedata = LoginData.getInstance().getResponseData();
            if (responsedata != null) {
                if (responsedata.getUserDetailDto().getDpcProfileDto() != null)
                    dpcId = responsedata.getUserDetailDto().getDpcProfileDto().getId();
            }
        } catch (Exception e) {
        }
        return dpcId;
    }

    private void getCurrentLocation() {
        mGPSService = new GPSService(globalContext);
        mGPSService.getLocation();
        if (mGPSService.isLocationAvailable) {
            double latitude = mGPSService.getLatitude();
            double longitude = mGPSService.getLongitude();
            LocationId.getInstance().setLatitude(Util.latLngRoundOffFormat(latitude));
            LocationId.getInstance().setLongitude(Util.latLngRoundOffFormat(longitude));
            if (heartBeatDto != null) {
                String lat = String.valueOf(latitude);
                String lng = String.valueOf(longitude);
                if (StringUtils.isNotEmpty(lat) && StringUtils.isNotEmpty(lng)) {
                    heartBeatDto.setLatitude(lat);
                    heartBeatDto.setLongitude(lng);
                }
            }
        }
    }

    private void getPackageInformation() {
        try {
            PackageInfo pInfo = globalContext.getPackageManager().getPackageInfo(globalContext.getPackageName(), 0);
            heartBeatDto.setVersion(pInfo.versionCode);
        } catch (Exception e) {
        }
    }

    public boolean HeartBeatTask() {

        NetworkConnection network = new NetworkConnection(globalContext);
        if (network.isNetworkAvailable()) {
            if (dpcId != 0) {
                heartBeatDto.setBatteryLevel(batteryLevel);
                String url = serverUrl + "/dpc/createHeartBeat";
                heartBeatDto.setDpcProfileId(dpcId);
                String heartBeatData = new Gson().toJson(heartBeatDto);
                Log.e("HeartBeat RequestURL",url);
                new Volley_service().execute(url, heartBeatData, HeartBeatProcess.this, "heartbeat");
            }
        }
        return true;
    }


    @Override
    public void setResult(String result, String id) {
        Log.e("Heart Beat response DTO:", "@@@@@@@@@@@@@@@" + result);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        DpcHeartBeatResponseDto heatBeatResponseDto = gson.fromJson(result, DpcHeartBeatResponseDto.class);
        if (heatBeatResponseDto.getStatusCode()==0)
            Log.e("HeartBeatDto", "success");
        else
            Log.e("HeartBeatDto", "failed");
    }




    public class BatteryReceiver extends BroadcastReceiver implements Serializable {
        @Override
        public void onReceive(Context context, Intent intent) {

            int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            if (currentLevel >= 0 && scale > 0) {
                batteryLevel = (currentLevel * 100) / scale;
            }
            unregisterBatteryReceiver();
        }
    }

    /*// Broadcast receiver for battery
    private final BroadcastReceiver batteryLevelReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            // UnRegistering battery receiver
            if (register) {
                try {
                    context.unregisterReceiver(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                register = false;
            }

            int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            if (currentLevel >= 0 && scale > 0) {
                batteryLevel = (currentLevel * 100) / scale;
            }
        }
    };*/

    private void registerBatteryReceiver() {
        // Registering battery receiver
        try {
            if (!register) {
                IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                globalContext.registerReceiver(batteryLevelReceiver, batteryLevelFilter);
                register = true;
            }
        } catch (Exception e) {
        }
    }

    private void unregisterBatteryReceiver() {
        // UnRegistering battery receiver
        if (register) {
            try {
                globalContext.unregisterReceiver(batteryLevelReceiver);
            } catch (Exception e) {
            }
            register = false;
        }
    }
}
