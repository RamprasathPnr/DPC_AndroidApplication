package com.omneagate.dpc.process;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.os.BatteryManager;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omneagate.dpc.Model.DpcPOSHealthStatisticsDto;
import com.omneagate.dpc.Service.BaseSchedulerService;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.LocationId;
import com.omneagate.dpc.Utility.NetworkConnection;
import com.omneagate.dpc.Utility.ResultUpdatable;
import com.omneagate.dpc.Utility.SessionId;
import com.omneagate.dpc.Utility.Volley_service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;

public class StatisticsProcess implements Serializable, BaseSchedulerService, ResultUpdatable {
    DpcPOSHealthStatisticsDto dpcPOSHealthStatisticsDto;
    int scale, health, level, plugged, status, temperature, voltage;
    String technology;
    boolean present;
    private int batteryLevel = 0;
    private static String serverUrl = null;
    Context globalContext;
    static boolean register = false;
    BatteryReceiver receiver = new BatteryReceiver();
//    BufferedReader in = null;
//    private String _session = "session";
//    private String sessionId;

    public StatisticsProcess() {
    }

    @Override
    public void process(Context context) {
        globalContext = context;
        if (serverUrl == null) {
            serverUrl = DBHelper.getInstance(globalContext).getMasterData("serverUrl");
        }
        // Check whether sessionId is empty or null
        startProcess();
    }

    private void startProcess() {
        registerBatteryReceiver();
        initializeValues();
        StatisticsTask();
//        unregisterBatteryReceiver();
    }

    //
    private void initializeValues() {
        dpcPOSHealthStatisticsDto = new DpcPOSHealthStatisticsDto();
        try {
            PackageInfo pInfo = globalContext.getPackageManager().getPackageInfo(globalContext.getPackageName(), 0);
            dpcPOSHealthStatisticsDto.setVersionNum(pInfo.versionCode);
            dpcPOSHealthStatisticsDto.setApkInstalledTime(pInfo.firstInstallTime);
            dpcPOSHealthStatisticsDto.setLastUpdatedTime(pInfo.lastUpdateTime);
            dpcPOSHealthStatisticsDto.setVersionName(pInfo.versionName);
            ConnectivityManager cm = (ConnectivityManager) globalContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            dpcPOSHealthStatisticsDto.setNetworkInfo(cm.getActiveNetworkInfo().getTypeName());
            long totalFreeMemory = getAvailableInternalMemorySize() + getAvailableExternalMemorySize();
            dpcPOSHealthStatisticsDto.setHardDiskSizeFree(formatSize(totalFreeMemory));
            dpcPOSHealthStatisticsDto.setUserId(String.valueOf(SessionId.getInstance().getUserId()));
            TelephonyManager telephonyManager = (TelephonyManager) globalContext.getSystemService(Context.TELEPHONY_SERVICE);
            try {
                dpcPOSHealthStatisticsDto.setSimId(telephonyManager.getSimSerialNumber());
            } catch (Exception e) {
            }
        } catch (Exception e) {
        }
    }

    //
    private void StatisticsTask() {
        NetworkConnection network = new NetworkConnection(globalContext);
        if (network.isNetworkAvailable()) {
            dpcPOSHealthStatisticsDto.setBatteryLevel(batteryLevel);
            dpcPOSHealthStatisticsDto.setLatitude(LocationId.getInstance().getLatitude());
            dpcPOSHealthStatisticsDto.setLongtitude(LocationId.getInstance().getLongitude());
            dpcPOSHealthStatisticsDto = getAllStatisticsData();
            String url = serverUrl + "/dpc/addstatistics";
            Log.e("Statistics RequestURL", url);
            String statsData = new Gson().toJson(dpcPOSHealthStatisticsDto);
            Log.e("StatisticsProcess RequestDto", "" + statsData);
            new Volley_service().execute(url, statsData, StatisticsProcess.this, "");
        }
    }

    private boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    //
    private long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = 0;
        long availableBlocks = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        }
        return availableBlocks * blockSize;
    }

    private long getAvailableExternalMemorySize() {
        if (externalMemoryAvailable()) {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = 0;
            long availableBlocks = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                blockSize = stat.getAvailableBlocksLong();
                availableBlocks = stat.getBlockSizeLong();
            }
            return availableBlocks * blockSize;
        } else {
            return 0;
        }
    }

    private String formatSize(long size) {
        String suffix = null;
        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));
        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }
        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    private DpcPOSHealthStatisticsDto getAllStatisticsData() {
        try {
            dpcPOSHealthStatisticsDto.setDeviceNum((Settings.Secure.getString(globalContext.getContentResolver(), Settings.Secure.ANDROID_ID)).toUpperCase());
            dpcPOSHealthStatisticsDto.setScale(scale);
            dpcPOSHealthStatisticsDto.setHealth(health);
            dpcPOSHealthStatisticsDto.setLevel(level);
            dpcPOSHealthStatisticsDto.setPlugged(plugged);
            dpcPOSHealthStatisticsDto.setStatus(status);
            dpcPOSHealthStatisticsDto.setTemperature(temperature);
            dpcPOSHealthStatisticsDto.setVoltage(voltage);
            dpcPOSHealthStatisticsDto.setTechnology(technology);
            dpcPOSHealthStatisticsDto.setPresent(present);
            dpcPOSHealthStatisticsDto.setCpuUtilisation(String.valueOf(readUsage()));
            DBHelper db = DBHelper.getInstance(globalContext);
            dpcPOSHealthStatisticsDto.setNoOfAssociatedFarmers(db.getTotalAssociatedFarmer());
            dpcPOSHealthStatisticsDto.setNoOfRequestedFarmers(db.getTotalFarmer());
            dpcPOSHealthStatisticsDto.setNoOfUnSyncedProcurement(db.getUnSyncedProcurementCount());
            dpcPOSHealthStatisticsDto.setNoOfUnSyncedTruckMemo(db.getUnSyncedTruckmemoCount());
            dpcPOSHealthStatisticsDto.setNoOfUnSyncedVouchers(0);
            ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
            ActivityManager activityManager = (ActivityManager) globalContext.getSystemService(globalContext.ACTIVITY_SERVICE);
            activityManager.getMemoryInfo(mi);
            long availableMegs = mi.availMem / 1048576L;
            long totalMegs = 0;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                totalMegs = mi.totalMem / 1048576L;
            }
            dpcPOSHealthStatisticsDto.setMemoryRemaining(String.valueOf(availableMegs));
            dpcPOSHealthStatisticsDto.setTotalMemory(String.valueOf(totalMegs));
            dpcPOSHealthStatisticsDto.setMemoryUsed(String.valueOf(totalMegs - availableMegs));
        } catch (Exception e) {
        }
        return dpcPOSHealthStatisticsDto;
    }

    private float readUsage() {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            String load = reader.readLine();
            String[] toks = load.split(" ");
            long idle1 = Long.parseLong(toks[5]);
            long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);
            try {
                Thread.sleep(360);
            } catch (Exception e) {
            }
            reader.seek(0);
            load = reader.readLine();
            reader.close();
            toks = load.split(" ");
            long idle2 = Long.parseLong(toks[5]);
            long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);
            return (float) (cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public class BatteryReceiver extends BroadcastReceiver implements Serializable {
        @Override
        public void onReceive(Context context, Intent intent) {

            int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            present = intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);
            status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);
            technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
            voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
            if (currentLevel >= 0 && scale > 0) {
                batteryLevel = (currentLevel * 100) / scale;
            }
            unregisterBatteryReceiver();
        }
    }

    private void registerBatteryReceiver() {
        // Registering battery receiver
        unregisterBatteryReceiver();
        try {
            if (!register) {
                IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                globalContext.registerReceiver(receiver, batteryLevelFilter);
                register = true;
            }
            Log.e("Battery register", "success");
        } catch (Exception e) {
            Log.e("Battery register", "failed");
            unregisterBatteryReceiver();
        }
    }

    //
    private void unregisterBatteryReceiver() {
        // Unregistering battery receiver
        if (register) {
            try {
                globalContext.unregisterReceiver(receiver);
                Log.e("Battery unregister", "success");
            } catch (Exception e) {
                Log.e("Battery unregister", "failed");
            }
            register = false;
        }
    }

    @Override
    public void setResult(String result, String id) {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        DpcPOSHealthStatisticsDto dpcPOSHealthStatisticsDto = gson.fromJson(result, DpcPOSHealthStatisticsDto.class);
        Log.e("Statistics ResponseDTO", "" + dpcPOSHealthStatisticsDto);
        if (dpcPOSHealthStatisticsDto.getStatusCode().equals("0"))
            Log.e("DpcPOSHealthStatistic", "success");
        else
            Log.e("DpcPOSHealthStatistic", "failed");
    }
}
