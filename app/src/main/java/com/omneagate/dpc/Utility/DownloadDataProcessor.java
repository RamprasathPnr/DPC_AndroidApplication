package com.omneagate.dpc.Utility;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omneagate.dpc.Model.EnumModel.TableNames;
import com.omneagate.dpc.Model.FirstSyncRequestDto;
import com.omneagate.dpc.Model.FirstSyncResponseDto;
import com.omneagate.dpc.Model.FistSyncInputDto;

import com.omneagate.dpc.R;
import com.omneagate.dpc.Service.FirstSyncExceptionService;
import com.omneagate.dpc.Service.ResReqController;
import com.omneagate.dpc.Service.ServiceResponseListener;
import com.omneagate.dpc.Service.Utilities;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;


public class DownloadDataProcessor implements Handler.Callback, ServiceResponseListener {

    String deviceId;
    //Activity context
    private Context context = null;
    private boolean mExternalStorageAvailable = false;
    final private String databaseDatabase = "/databases/";
    private String currentDBPath = null;
    private File mSdPath, mData;
    final private String dataDbpath = "//data/";
    private boolean mExternalStorageWriteable = false;

    //private List<FistSyncInputDto> firstSync;  //FistSync items


    private String serverUrl = "";

    private ResReqController controller;

    //Constructor
    public DownloadDataProcessor(Context context) {
        this.context = context;
    }


    public void processor(Context c) {
        try {
            //this.context = c;
            controller = new ResReqController(context);
            Constants.firstSync = new ArrayList<>();
            Log.e("Inside processor", "processor");
            controller.addOutboxHandler(new Handler(Looper.getMainLooper()));
//            controller.addOutboxHandler(new Handler(c));
            new UpdateSyncTask().execute("");

        } catch (Exception e) {
            e.printStackTrace();
//            Util.LoggingQueue(context, "Download Error", "Error:" + e.toString());
        }
    }


    // After response received from server successfully in android
    private void processSyncResponseData(Map<String, Integer> tableDetails) {

//        firstSync = new ArrayList<>();

        Log.e("Regular Sync", "" + Constants.firstSync.size());

        if (tableDetails.containsKey("TABLE_DPC_DISTRICT"))
            Constants.firstSync.add(getInputDTO("TABLE_DPC_DISTRICT", TableNames.TABLE_DPC_DISTRICT));

        if (tableDetails.containsKey("TABLE_DPC_TALUK"))
            Constants.firstSync.add(getInputDTO("TABLE_DPC_TALUK", TableNames.TABLE_DPC_TALUK));

        if (tableDetails.containsKey("TABLE_DPC_VILLAGE"))
            Constants.firstSync.add(getInputDTO("TABLE_DPC_VILLAGE", TableNames.TABLE_DPC_VILLAGE));

       /* if (tableDetails.containsKey("TABLE_DPC_REVENUE_VILLAGE"))
            Constants.firstSync.add(getInputDTO("TABLE_DPC_REVENUE_VILLAGE", TableNames.TABLE_DPC_REVENUE_VILLAGE));*/

        if (tableDetails.containsKey("TABLE_BANK"))
            Constants.firstSync.add(getInputDTO("TABLE_BANK", TableNames.TABLE_BANK));

        if (tableDetails.containsKey("TABLE_LAND_TYPE"))
            Constants.firstSync.add(getInputDTO("TABLE_LAND_TYPE", TableNames.TABLE_LAND_TYPE));

        /*if (tableDetails.containsKey("TABLE_FARMER_CLASS"))
            Constants.firstSync.add(getInputDTO("TABLE_FARMER_CLASS", TableNames.TABLE_FARMER_CLASS));*/

        if (tableDetails.containsKey("TABLE_PADDY_CATEGORY"))
            Constants.firstSync.add(getInputDTO("TABLE_PADDY_CATEGORY", TableNames.TABLE_PADDY_CATEGORY));

        if (tableDetails.containsKey("TABLE_DPC_STATE"))
            Constants.firstSync.add(getInputDTO("TABLE_DPC_STATE", TableNames.TABLE_DPC_STATE));

        if (tableDetails.containsKey("TABLE_DPC_GRADE"))
            Constants.firstSync.add(getInputDTO("TABLE_DPC_GRADE", TableNames.TABLE_DPC_GRADE));


        if (tableDetails.containsKey("TABLE_DPC_FARMER_REGISTRATION"))
            Constants.firstSync.add(getInputDTO("TABLE_DPC_FARMER_REGISTRATION", TableNames.TABLE_DPC_FARMER_REGISTRATION));

        if (tableDetails.containsKey("TABLE_DPC_FARMER_APPROVED_LAND_DETAILS"))
            Constants.firstSync.add(getInputDTO("TABLE_DPC_FARMER_APPROVED_LAND_DETAILS", TableNames.TABLE_DPC_FARMER_APPROVED_LAND_DETAILS));

        if (tableDetails.containsKey("TABLE_DPC_PADDY_RATE"))
            Constants.firstSync.add(getInputDTO("TABLE_DPC_PADDY_RATE", TableNames.TABLE_DPC_PADDY_RATE));

        if (tableDetails.containsKey("TABLE_DPC_PROCUREMENT"))
            Constants.firstSync.add(getInputDTO("TABLE_DPC_PROCUREMENT", TableNames.TABLE_DPC_PROCUREMENT));

        if (tableDetails.containsKey("TABLE_DPC_TRUCK_MEMO"))
            Constants.firstSync.add(getInputDTO("TABLE_DPC_TRUCK_MEMO", TableNames.TABLE_DPC_TRUCK_MEMO));

        if (tableDetails.containsKey("TABLE_DPC_CAP"))
            Constants.firstSync.add(getInputDTO("TABLE_DPC_CAP", TableNames.TABLE_DPC_CAP));

        if (tableDetails.containsKey("TABLE_DPC_SPECIFICATION"))
            Constants.firstSync.add(getInputDTO("TABLE_DPC_SPECIFICATION", TableNames.TABLE_DPC_SPECIFICATION));

        if (Constants.firstSync.size() > 0) {
            Log.e("firstSync size", "" + Constants.firstSync.size());
            FirstSyncRequestDto dpcRequest = new FirstSyncRequestDto();
            String syncTime = DBHelper.getInstance(context).getMasterData("syncTime");
            dpcRequest.setLastSyncTime(syncTime);
            String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase();
            dpcRequest.setDeviceNumber(deviceId);
            dpcRequest.setTableName(Constants.firstSync.get(0).getTableName());
            setTableSyncCall(dpcRequest);
            Log.e("firstSync data", "" + Constants.firstSync.toString());
        } else {
            firstSyncSuccess();
        }
    }


    private void setTableSyncCall(FirstSyncRequestDto dpcRequest) {
        try {
            Log.e("setTableSyncCall", "" + Constants.firstSync.size());
            String updateData = new Gson().toJson(dpcRequest);
            Log.e("Request sync call", updateData);
//            Util.LoggingQueue(context, "Download Sync", "Req:" + updateData);
            new UpdateTablesTask(updateData).execute("");
            Log.e("After UpdateTablesTask", "" + Constants.firstSync.size());
        } catch (Exception e) {
            Log.e("Error", e.toString(), e);
        }
    }

    @Override
    public void onSuccessResponse(String url, HashMap<String, Object> requestParams, String response) {
        try {
//            Log.e("onSuccessResponse",""+firstSync.toString());
//            Utilities.getInstance().dismissProgressBar();
            Log.e("CHECK" + " REGULAR", response);
            switch (url) {
                case Constants.REGULARSYNC_GET_TABLE_DETAILS:
                    Log.e("notified", "notified");
//                    notifyOutboxHandlers(REGULARSYNC_GET_TABLE_DETAILS_SUCCESS, 0, 0, response);

                    Log.e("inside notified success", "inside notified");
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    Log.d("Check", "" + response);
                    FirstSyncResponseDto get_details_response_dto = gson.fromJson(response, FirstSyncResponseDto.class);
                    String status = get_details_response_dto.getStatusCode();
                    Log.e("status", status);

                    if (get_details_response_dto.getStatusCode().equalsIgnoreCase("0")) {
                        processSyncResponseData(get_details_response_dto.getTableDetails());
                    }
                    break;

                case Constants.REGULARSYNC_GET_TABLE_DATA:
                    GsonBuilder gsonBuilder_ = new GsonBuilder();
                    Gson gson_ = gsonBuilder_.create();
                    Log.d("Check", "" + response);
                    Log.e("Shiva After the call", "" + Constants.firstSync.toString());
                    FirstSyncResponseDto get_details_response_dto_ = gson_.fromJson(response, FirstSyncResponseDto.class);
                    if (get_details_response_dto_.getStatusCode().equalsIgnoreCase("0")) {
                        Log.e("true condition", "true" + get_details_response_dto_);
                        Log.e("GET_TABLE_DATA", "" + Constants.firstSync);
                        insertIntoDatabase(get_details_response_dto_);
                    }
                    break;

                case Constants.REGULARSYNC_COMPLETE:
                    Log.e("REGULARSYNC_COMPLETE", "REGULARSYNC_COMPLETE");
                    GsonBuilder gsonBuilder_complete = new GsonBuilder();
                    Gson gson_complete = gsonBuilder_complete.create();
                    Log.d("Check", "" + response);
                    FirstSyncResponseDto sync_success_dto = gson_complete.fromJson(response, FirstSyncResponseDto.class);
                    if (sync_success_dto.getStatusCode().equalsIgnoreCase("0") && sync_success_dto.getLastSyncTime() != null) {
//                        Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show();
                        DBHelper.getInstance(context).updateMaserData("syncTime", sync_success_dto.getLastSyncTime());
                    }
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String url, HashMap<String, Object> requestParams, String errorMessage, Boolean isVolleyError) {
        try {
            Utilities.getInstance().dismissProgressBar();
            switch (url) {
                case Constants.REGULARSYNC_GET_TABLE_DETAILS:
                    Toast.makeText(context, context.getString(R.string.toast_server_unreachable), Toast.LENGTH_SHORT).show();
                    break;


                case Constants.REGULARSYNC_GET_TABLE_DATA:
                    Toast.makeText(context, context.getString(R.string.toast_server_unreachable), Toast.LENGTH_SHORT).show();
                    break;


                case Constants.REGULARSYNC_COMPLETE:
                    Toast.makeText(context, context.getString(R.string.toast_server_unreachable), Toast.LENGTH_SHORT).show();
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNetWorkFailure(String url) {

    }

    private class UpdateTablesTask extends AsyncTask<String, String, String> {
        String dpcRequestdto;

        public UpdateTablesTask(String updateData) {
            this.dpcRequestdto = updateData;
        }


        @Override
        protected String doInBackground(String... params) {
            try {
//                Object data = "";
                Log.e("THE REQUEST DTO", dpcRequestdto);
                Log.e("before call", "" + Constants.firstSync.toString());
                controller = new ResReqController(context);
                controller.handleMessage_(ResReqController.REGULARSYNC_GET_TABLE_DATA, dpcRequestdto, null);
                Log.e("after1 call", "" + Constants.firstSync.toString());
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("DownloadDataProcessor", "UpdateTablesTask" + e.toString());
            }
            return null;
        }
    }

    private void afterDatabaseInsertion(FirstSyncResponseDto firstSynchResDto) throws Exception {
        Log.e("After insert", "After insert");
        FirstSyncRequestDto dpcRequest = new FirstSyncRequestDto();
        String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase();
        dpcRequest.setDeviceNumber(deviceId);
        String lastModifiedDate = DBHelper.getInstance(context).getMasterData("syncTime");
        dpcRequest.setLastSyncTime(lastModifiedDate);
        if (firstSynchResDto.isHasMore()) {
            Log.e("isHasMore ", "isHasMore");
            dpcRequest.setTotalCount(firstSynchResDto.getTotalCount());
            dpcRequest.setTotalSentCount(firstSynchResDto.getTotalSentCount());
            dpcRequest.setCurrentCount(firstSynchResDto.getCurrentCount());
            dpcRequest.setTableName(Constants.firstSync.get(0).getTableName());
            setTableSyncCall(dpcRequest);
        } else {
            Log.e("isHasMore else ", "isHasMore else");
            Constants.firstSync.remove(0);
            if (Constants.firstSync.size() > 0) {
                dpcRequest.setTableName(Constants.firstSync.get(0).getTableName());
                setTableSyncCall(dpcRequest);
            } else {
                Log.e("success", "success");
                firstSyncSuccess();
            }
        }
    }

    private void firstSyncSuccess() {
        try {
            Log.e("firstSyncSuccess", "firstSyncSuccess");
            new SyncSuccess().execute("");
        } catch (Exception e) {
//            Util.LoggingQueue(context, "Error", e.toString());
            Log.e("Error in First sync", e.toString(), e);
        }
    }

    private FistSyncInputDto getInputDTO(String tableName, TableNames names) {
        FistSyncInputDto inputDto = new FistSyncInputDto();
        inputDto.setTableName(tableName);
        inputDto.setTableNames(names);
        inputDto.setDynamic(true);

        Log.e("inside FistSyncInputDto", "" + inputDto);
        return inputDto;
    }

    private class SyncSuccess extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... f_url) {
            Log.e("REGULARSYNC_COMPLETE", "REGULARSYNC_COMPLETE");
            try {
                String deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase();
                HashMap<String, Object> inputParams = Utilities.getInstance().getRequestParams();
                inputParams.put("deviceNumber", deviceId);
                Object data = "";
                controller = new ResReqController(context);
                controller.handleMessage(ResReqController.REGULARSYNC_COMPLETE, inputParams, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    //Async task for Download Sync
    private class UpdateSyncTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... f_url) {
            try {
                String lastSyncTime = DBHelper.getInstance(context).getMasterData("syncTime");
                deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase();
                Log.e("RegularSyncPageActivity", "Update Sync Task");
                HashMap<String, Object> inputParams = Utilities.getInstance().getRequestParams();
                inputParams.put("deviceNumber", deviceId);
                inputParams.put("lastSyncTime", lastSyncTime);
                Object data = "";
                controller.handleMessage(ResReqController.REGULARSYNC_GET_TABLE_DETAILS, inputParams, data);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("DownloadDataProcessor", "Update Sync Exception" + e.toString());
            }
            return null;
        }
    }


    private void insertIntoDatabase(FirstSyncResponseDto table_detail_dto) {
        try {
            FistSyncInputDto fistSyncInputDto = Constants.firstSync.get(0);
            Log.e("table name inside", "" + fistSyncInputDto.getTableNames());
            boolean isExceptionThrown;
            switch (fistSyncInputDto.getTableNames()) {
                // Log.e("table name switch",""+fistSyncInputDto.getTableNames());
                case TABLE_DPC_STATE:
                    isExceptionThrown = DBHelper.getInstance(context).insertState(table_detail_dto.getDpcStateDtos(), "RegularSync");
                    if (!isExceptionThrown) {
                        backupDb(true, "", DBHelper.DATABASE_NAME, "");
                        context.startService(new Intent(context, FirstSyncExceptionService.class));
                        return;
                    }
                    break;
                case TABLE_DPC_VILLAGE:
                    isExceptionThrown = DBHelper.getInstance(context).insertVillageName(table_detail_dto.getDpcVillageDtos(), "RegularSync");
                    if (!isExceptionThrown) {
                        backupDb(true, "", DBHelper.DATABASE_NAME, "");
                        context.startService(new Intent(context, FirstSyncExceptionService.class));
                        return;
                    }
                    break;
                case TABLE_BANK:
                    isExceptionThrown = DBHelper.getInstance(context).insertBankData(table_detail_dto.getBankDto(), "RegularSync");
                    if (!isExceptionThrown) {
                        return;
                    }
                    break;
                case TABLE_DPC_TALUK:
                    isExceptionThrown = DBHelper.getInstance(context).insertTalukData(table_detail_dto.getDpcTalukDtos(), "RegularSync");
                    if (!isExceptionThrown) {
                        backupDb(true, "", DBHelper.DATABASE_NAME, "");
                        context.startService(new Intent(context, FirstSyncExceptionService.class));
                        return;
                    }
                    break;
                case TABLE_LAND_TYPE:
                    isExceptionThrown = DBHelper.getInstance(context).insertLandTypeData(table_detail_dto.getLandTypeDto(), "RegularSync");
                    Log.e("inside land type case", "" + isExceptionThrown);
                    if (!isExceptionThrown) {
                        backupDb(true, "", DBHelper.DATABASE_NAME, "");
                        context.startService(new Intent(context, FirstSyncExceptionService.class));
                        return;
                    }
                    break;

                case TABLE_DPC_DISTRICT:
                    isExceptionThrown = DBHelper.getInstance(context).insertDistrictkData(table_detail_dto.getDpcDistrictDtos(), "RegularSync");
                    if (!isExceptionThrown) {
                        backupDb(true, "", DBHelper.DATABASE_NAME, "");
                        context.startService(new Intent(context, FirstSyncExceptionService.class));
                        return;
                    }
                    break;

                case TABLE_PADDY_CATEGORY:
                    isExceptionThrown = DBHelper.getInstance(context).insertPaddyCategory(table_detail_dto.getPaddyCategoryDto(), "RegularSync");
                    if (!isExceptionThrown) {
                        backupDb(true, "", DBHelper.DATABASE_NAME, "");
                        context.startService(new Intent(context, FirstSyncExceptionService.class));
                        return;
                    }
                    break;

                case TABLE_DPC_GRADE:
                    isExceptionThrown = DBHelper.getInstance(context).insertGrade(table_detail_dto.getPaddyGradeDto(), "RegularSync");
                    if (!isExceptionThrown) {
                        backupDb(true, "", DBHelper.DATABASE_NAME, "");
                        context.startService(new Intent(context, FirstSyncExceptionService.class));
                        return;
                    }
                    break;

                case TABLE_DPC_FARMER_REGISTRATION:
                    isExceptionThrown = DBHelper.getInstance(context).InsertAssociatedFarmerDetails(table_detail_dto.getFarmerRegistrationDto(), "RegularSync");
                    if (!isExceptionThrown) {
                        backupDb(true, "", DBHelper.DATABASE_NAME, "");
                        context.startService(new Intent(context, FirstSyncExceptionService.class));
                        return;
                    }
                    break;

                case TABLE_DPC_FARMER_APPROVED_LAND_DETAILS:
                    isExceptionThrown = DBHelper.getInstance(context).InsertAssociatedFarmerLandDetails(table_detail_dto.getFarmerApprovedLandDetailsDto(), "RegularSync");
                    if (!isExceptionThrown) {
                        backupDb(true, "", DBHelper.DATABASE_NAME, "");
                        context.startService(new Intent(context, FirstSyncExceptionService.class));
                        return;
                    }
                    break;

                case TABLE_DPC_PADDY_RATE:
                    isExceptionThrown = DBHelper.getInstance(context).InsertDpcRateDetails(table_detail_dto.getDpcPaddyRateDtos(), "RegularSync");
                    if (!isExceptionThrown) {
                        backupDb(true, "", DBHelper.DATABASE_NAME, "");
                        context.startService(new Intent(context, FirstSyncExceptionService.class));
                        return;
                    }
                    break;

                case TABLE_DPC_PROCUREMENT:
                    isExceptionThrown = DBHelper.getInstance(context).InsertDpcProcurementDetails(table_detail_dto.getDpcProcurementDtos(), "RegularSync");
                    if (!isExceptionThrown) {
                        backupDb(true, "", DBHelper.DATABASE_NAME, "");
                        context.startService(new Intent(context, FirstSyncExceptionService.class));
                        return;
                    }
                    break;


                case TABLE_DPC_CAP:
                    isExceptionThrown = DBHelper.getInstance(context).InsertDpcCapDetails(table_detail_dto.getDpcCapDtos(), "RegularSync");
                    if (!isExceptionThrown) {
                        backupDb(true, "", DBHelper.DATABASE_NAME, "");
                        context.startService(new Intent(context, FirstSyncExceptionService.class));
                        return;
                    }
                    break;


                case TABLE_DPC_TRUCK_MEMO:
                    isExceptionThrown = DBHelper.getInstance(context).InsertDPcTruckMemo(table_detail_dto.getDpcTruckMemoDtos(), "RegularSync");
                    if (!isExceptionThrown) {
                        backupDb(true, "", DBHelper.DATABASE_NAME, "");
                        context.startService(new Intent(context, FirstSyncExceptionService.class));
                        return;
                    }
                    break;


                case TABLE_DPC_SPECIFICATION:
                    isExceptionThrown = DBHelper.getInstance(context).insertSpecification(table_detail_dto.getDpcSpecificationDto(), "RegularSync");
                    if (!isExceptionThrown) {
                        backupDb(true, "", DBHelper.DATABASE_NAME, "");
                        context.startService(new Intent(context, FirstSyncExceptionService.class));
                        return;
                    }
                    break;

                default:
                    Log.e("Default", "Default");
                    break;


                /*case TABLE_DPC_SOCIETY:
                    isExceptionThrown = DBHelper.getInstance(context).insertDpcSociety(table_detail_dto.getSocietyDto(), "RegularSync");
                    if (!isExceptionThrown) {
                        backupDb(true, "", DBHelper.DATABASE_NAME, "");
                        context.startService(new Intent(context, FirstSyncExceptionService.class));
                        return;
                    }
                    break;*/

                /*case TABLE_FARMER_CLASS:
                    isExceptionThrown = DBHelper.getInstance(context).insertFarmerClass(table_detail_dto.getFarmerClassDto(), "RegularSync");
                    if (!isExceptionThrown) {
                        backupDb(true, "",DBHelper.DATABASE_NAME, "");
                        context.startService(new Intent(context, FirstSyncExceptionService.class));
                        return;
                    }
                    break;*/

                 /*case TABLE_DPC_REVENUE_VILLAGE:
                    isExceptionThrown = DBHelper.getInstance(context).insertRevenueVillageName(table_detail_dto.getRevenueVillageDto(), "RegularSync");
                    if (!isExceptionThrown) {
                        backupDb(true, "", DBHelper.DATABASE_NAME, "");
                        context.startService(new Intent(context, FirstSyncExceptionService.class));
                        return;
                    }
                    break;*/
            }
            afterDatabaseInsertion(table_detail_dto);
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    @Override
    public boolean handleMessage(Message msg) {

        return false;
    }

    /*private void backupDB() {
        LocalDbRecoveryProcess localDbRecoveryPro = new LocalDbRecoveryProcess(context);
        localDbRecoveryPro.backupDb(true, "",DBHelper.DATABASE_NAME, "");
       *//* LocalDbBackup localDbBackup = new LocalDbBackup(SyncPageActivity.this);
        localDbBackup.backupDb(true, "", FPSDBHelper.DATABASE_NAME, "");*//*
    }*/

    private void checkExternalMedia() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
            //  Util.messageBar(context, context.getString(R.string.externalStorageNotWritable));
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
            //  Util.messageBar(context, context.getString(R.string.externalStorageNotAvailable));
        }
    }

    private void initialisePathDbSdcard() {
        currentDBPath = dataDbpath + context.getPackageName() + databaseDatabase + DBHelper.DATABASE_NAME;
        mSdPath = Environment.getExternalStorageDirectory();
        mData = Environment.getDataDirectory();
    }

    //Load db from sdcard
    public boolean backupDb(boolean isDialogBox, String refId, String dbName, String serverRefId) {

        initialisePathDbSdcard();//Initialise db and sdcard path

        checkExternalMedia();//Check external storage availability and Writable

        if (mExternalStorageAvailable & mExternalStorageWriteable & getExternalAvailableSize()) {

            File fileDestinationDb = new File(mData, currentDBPath);   // database is the destination
            File fileDestinationDbOldFileFolder = new File(mSdPath + "/backup");
            File fileDestinationDbOldFile;

            boolean success = true;

            if (!fileDestinationDbOldFileFolder.exists()) {
                success = fileDestinationDbOldFileFolder.mkdir();
            }
            try {
                if (success) {
                    fileDestinationDbOldFile = new File(mSdPath + "/backup/", dbName);
                    // sourceCreation(fileDestinationDb, fileDestinationDbOldFile);
                    if (isDialogBox) {
//                        RetrieveLocalDbDialog retrieveLocalDbDialog = new RetrieveLocalDbDialog(context, fileDestinationDb, fileDestinationDbOldFile);
//                        retrieveLocalDbDialog.show();
                    }
                    return true;
                    // Util.messageBar(context, context.getString(R.string.oldDbBackup));
                } else {
                    if (isDialogBox) {
                        //    Util.messageBar(context, context.getString(R.string.backupFolderNot));
                    } else {
                        //DBHelper.getInstance(context).insertTableUpgrade(0, "Backup folder not created", "fail", "Backup", 0, refId, serverRefId);
                    }
                    return false;
                }
            } catch (Exception e) {
                Log.e("backup exception", e.toString());
                if (!isDialogBox)
                    //DBHelper.getInstance(context).insertTableUpgrade(0, "Backup folder not created because:" + e.toString(), "fail", "Backup", 0, refId, serverRefId);
                    return false;
            }
        }
        return false;
    }

    private boolean getExternalAvailableSize() {
        StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
        long blockSize = statFs.getBlockSize();
        long freeSize = statFs.getFreeBlocks() * blockSize;
        String currentDBPath = dataDbpath + context.getPackageName() + databaseDatabase + DBHelper.DATABASE_NAME;
        File file = context.getDatabasePath(currentDBPath);
        long dbSize = file.length();
        if (freeSize > dbSize) {
            return true;
        }
        //   Util.messageBar(context, context.getString(R.string.noFreeInSdcard));
        return false;
    }
}
