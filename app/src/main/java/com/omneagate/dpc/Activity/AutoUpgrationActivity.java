package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.omneagate.dpc.Model.EnumModel.CommonStatuses;
import com.omneagate.dpc.Model.UpgradeDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Service.ResReqController;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.LocalDbRecoveryProcess;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by user on 4/8/16.
 */
public class AutoUpgrationActivity extends BaseActivity implements  Handler.Callback {
    String refId = "", serverRefId = "";
    Integer oldVersion, newVersion;
    String downloadApkPath;
    private ProgressBar progressBar;
    final ResReqController controller = new ResReqController(this);
    private TextView tvUploadCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_upgradation_version);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        controller.addOutboxHandler(new Handler(this));
        progressBar = (ProgressBar) findViewById(R.id.autoUpgradeprogressBar);
        progressBar.setVisibility(View.VISIBLE);
        tvUploadCount = (TextView) findViewById(R.id.tvUploadCount);
        SimpleDateFormat regDate = new SimpleDateFormat("ddMMyyhhmmss", Locale.getDefault());
        refId = regDate.format(new Date());
        downloadApkPath = getIntent().getStringExtra("downloadPath");
        Log.i("downloadApkPath", downloadApkPath);
        newVersion = getIntent().getIntExtra("newVersion", 0);
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            oldVersion = pInfo.versionCode;
            UpgradeDto upgradeDto = new UpgradeDto();
            upgradeDto.setCreatedTime(new Date().getTime());
            upgradeDto.setPreviousVersion(pInfo.versionCode);
            upgradeDto.setCurrentVersion(newVersion);
            upgradeDto.setStatus(CommonStatuses.UPDATE_START);
            upgradeDto.setDeviceNum(Settings.Secure.getString(
                    getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase());
              upgradeData(upgradeDto);
        } catch (Exception e) {
            Log.e("AutoUpgrade", e.toString(), e);
        DBHelper.getInstance(this).insertTableUpgrade(oldVersion, "Upgrade failed because:" + e.toString(), "fail", "FAILURE", newVersion, refId, serverRefId);
            showToastMessage(getString(R.string.internalError), 3000);
        }

    }

     private void upgradeData(UpgradeDto upgradeDto){
         GsonBuilder gsonBuilder = new GsonBuilder();
         Gson gson = gsonBuilder.create();
         String upgradedetails = gson.toJson(upgradeDto);
         controller.handleMessage_(ResReqController.UPGRADE_ADDDETAILS, upgradedetails, null);

      }
    private void downloadStart() throws Exception {
        PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        oldVersion = pInfo.versionCode;
        DBHelper.getInstance(this).insertTableUpgrade(oldVersion, "Upgrade Available in the paths:" + downloadApkPath, "success", "UPGRADE_START", newVersion, refId, serverRefId);
        String dbName = refId + ".db";
        DBHelper.getInstance(this).insertTableUpgrade(oldVersion, "Back up the DB file:" + dbName, "success", "BACKUP_START", newVersion, refId, serverRefId);
        LocalDbRecoveryProcess localDbRecoveryPro = new LocalDbRecoveryProcess(this);
        if (localDbRecoveryPro.backupDb()) {
         DBHelper.getInstance(this).insertTableUpgrade(oldVersion, "Back up the DB file finished:" + dbName, "success", "BACKUP_END", newVersion, refId, serverRefId);
            File file = new File(Environment.getExternalStorageDirectory(), "DPC");
            if (!file.exists()) {
                file.mkdir();
            }
            final String path = Environment.getExternalStorageDirectory() + "/DPC/DPC.apk";
           DBHelper.getInstance(this).insertTableUpgrade(pInfo.versionCode, "Download starts:" + path, "success", "DOWNLOAD_START", newVersion, refId, serverRefId);
            getFutureFile(path);
        } else {
            showToastMessage(getString(R.string.internalError), 3000);
            Toast.makeText(AutoUpgrationActivity.this,getString(R.string.internalError),Toast.LENGTH_SHORT).show();
        }
    }
    private void getFutureFile(String path) {
        Ion.with(AutoUpgrationActivity.this).load(downloadApkPath)
                .progressBar(progressBar)
                .progressHandler(new ProgressCallback() {
                    @Override
                    public void onProgress(long downloaded, long total) {
                        double ratio = downloaded / (double) total;
                        DecimalFormat percentFormat = new DecimalFormat("#.#%");
                        tvUploadCount.setText(percentFormat.format(ratio));

                    }
                })
                .write(new File(path))
                .setCallback(new FutureCallback<File>() {
                    @Override
                    public void onCompleted(Exception e, File file) {
                        if (e != null) {
                            Toast.makeText(AutoUpgrationActivity.this, "Error downloading file.Try again", Toast.LENGTH_LONG).show();
                       DBHelper.getInstance(AutoUpgrationActivity.this).insertTableUpgrade(oldVersion, "Download failed because of" + e.toString(), "failed", "DOWNLOAD_FAIL", newVersion, refId, serverRefId);
                            onBackPressed();
                            return;
                        }
                     //   upGradeComplete();
                      DBHelper.getInstance(AutoUpgrationActivity.this).insertTableUpgrade(oldVersion, "Download completed successfully in:" + file.getAbsolutePath(), "success", "DOWNLOAD_END", newVersion, refId, serverRefId);
                        Intent i = new Intent();
                        i.setAction(Intent.ACTION_VIEW);
                        i.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                        startActivity(i);
                       DBHelper.getInstance(AutoUpgrationActivity.this).insertTableUpgradeExec(oldVersion, "Download completed successfully in:" + file.getAbsolutePath(), "success", "EXECUTION", newVersion, refId, serverRefId);
                        finish();
                    }

                });
    }

    @Override
    public boolean handleMessage(Message msg) {
        try{
            switch (msg.what) {

                case ResReqController.UPGRADE_ADDDETAILS_SUCCESS:
                    try {
                        downloadStart();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return true;
                case  ResReqController.UPGRADE_ADDDETAILS_FAILED:
                    return true;
             }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
