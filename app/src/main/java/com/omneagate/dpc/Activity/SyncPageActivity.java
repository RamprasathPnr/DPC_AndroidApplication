package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omneagate.dpc.Activity.Dialog.RetryDialog;
import com.omneagate.dpc.Activity.Dialog.RetryFailedDialog;
import com.omneagate.dpc.Activity.Dialog.TableExceptionErrorDialog;
import com.omneagate.dpc.Model.EnumModel.TableNames;
import com.omneagate.dpc.Model.FirstSyncRequestDto;
import com.omneagate.dpc.Model.FirstSyncResponseDto;
import com.omneagate.dpc.Model.FistSyncInputDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Service.ResReqController;
import com.omneagate.dpc.Service.Utilities;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.DBTables;
import com.omneagate.dpc.Utility.LocalDbRecoveryProcess;
import com.omneagate.dpc.Utility.MySharedPreference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 19/7/16.
 */
public class SyncPageActivity extends BaseActivity implements Handler.Callback, ConnectivityReceiver.ConnectivityReceiverListener {
    private ScrollView loadScroll;
    private ProgressBar progressBar;
    private LinearLayout layout;
    final ResReqController controller = new ResReqController(this);
    private String device_id;
    private ArrayList<FistSyncInputDto> firstSync;
    private int totalProgress = 8;
    private int totalSentCount;
    private int totalCount;
    int retryCount = 0;
    private Button complete_btn;
    public static boolean optimize = false;
    private int autoretrycount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_page);
        device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase();
        controller.addOutboxHandler(new Handler(this));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        layout = (LinearLayout) findViewById(R.id.info);
        loadScroll = (ScrollView) findViewById(R.id.scrollData);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        findViewById(R.id.syncContinue).setVisibility(View.INVISIBLE);
        firstTimeSyncDetails();
        complete_btn = (Button) findViewById(R.id.syncContinue);
    }

    /**
     * First Time Sync
     */
    public void firstTimeSyncDetails() {
        try {
            Log.e("SyncPageActivity", "first time sync called");
            new UpdateSyncTask().execute("");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("SyncPageActivity", " first time sync Exception" + e.toString());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
    }

    /**
     * Async   task for Download Sync for table details
     */
    private class UpdateSyncTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                Log.e("SyncPageActivity", "Update Sync Task");
                HashMap<String, Object> inputParams = Utilities.getInstance().getRequestParams();
                inputParams.put("deviceNumber", device_id);
                Object data = "";
                controller.handleMessage(ResReqController.FIRST_SYNC_GET_DETAILS, inputParams, data);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("SyncPageActivity", "Update Sync Exception" + e.toString());
            }
            return null;
        }
    }

    /**
     * insert the datas in to data base accordingly to that table name.
     * response from server
     *
     * @param table_detail_dto
     */
    private void insertIntoDatabase(FirstSyncResponseDto table_detail_dto) {
        FistSyncInputDto fistSyncInputDto = firstSync.get(0);
        setTextStrings(firstSync.get(0).getEndTextToDisplay() + " items " + table_detail_dto.getTotalSentCount() + "....");
        boolean isExceptionThrown;
        switch (fistSyncInputDto.getTableNames()) {
            case TABLE_DPC_STATE:
                isExceptionThrown = DBHelper.getInstance(this).insertState(table_detail_dto.getDpcStateDtos(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;
            case TABLE_DPC_VILLAGE:
                isExceptionThrown = DBHelper.getInstance(this).insertVillageName(table_detail_dto.getDpcVillageDtos(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;
            case TABLE_BANK:
                isExceptionThrown = DBHelper.getInstance(this).insertBankData(table_detail_dto.getBankDto(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;
            case TABLE_DPC_TALUK:
                isExceptionThrown = DBHelper.getInstance(this).insertTalukData(table_detail_dto.getDpcTalukDtos(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;
            case TABLE_LAND_TYPE:
                isExceptionThrown = DBHelper.getInstance(this).insertLandTypeData(table_detail_dto.getLandTypeDto(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;
            case TABLE_DPC_DISTRICT:
                isExceptionThrown = DBHelper.getInstance(this).insertDistrictkData(table_detail_dto.getDpcDistrictDtos(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;
            case TABLE_PADDY_CATEGORY:
                isExceptionThrown = DBHelper.getInstance(this).insertPaddyCategory(table_detail_dto.getPaddyCategoryDto(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;
            case TABLE_FARMER_REG_DETAIL:
                isExceptionThrown = DBHelper.getInstance(this).InsertFirstsyncFarmerRegistrationDetails(table_detail_dto.getFarmerRegistrationRequestDto(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;
            case TABLE_FARMER_LAND_DETAIL:
                isExceptionThrown = DBHelper.getInstance(this).InsertFirstsyncLandDetails(table_detail_dto.getFarmerLandDetailsDto(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;
            case TABLE_DPC_GRADE:
                isExceptionThrown = DBHelper.getInstance(this).insertGrade(table_detail_dto.getPaddyGradeDto(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;
            case TABLE_DPC_FARMER_REGISTRATION:
                isExceptionThrown = DBHelper.getInstance(this).InsertAssociatedFarmerDetails(table_detail_dto.getFarmerRegistrationDto(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;
            case TABLE_DPC_FARMER_APPROVED_LAND_DETAILS:
                isExceptionThrown = DBHelper.getInstance(this).InsertAssociatedFarmerLandDetails(table_detail_dto.getFarmerApprovedLandDetailsDto(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;
            case TABLE_DPC_PADDY_RATE:
                isExceptionThrown = DBHelper.getInstance(this).InsertDpcRateDetails(table_detail_dto.getDpcPaddyRateDtos(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;
            case TABLE_DPC_PROCUREMENT:
                isExceptionThrown = DBHelper.getInstance(this).InsertDpcProcurementDetails(table_detail_dto.getDpcProcurementDtos(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;
            case TABLE_DPC_CAP:
                isExceptionThrown = DBHelper.getInstance(this).InsertDpcCapDetails(table_detail_dto.getDpcCapDtos(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;
            case TABLE_DPC_TRUCK_MEMO:
                isExceptionThrown = DBHelper.getInstance(this).InsertDPcTruckMemo(table_detail_dto.getDpcTruckMemoDtos(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;

                case TABLE_DPC_SPECIFICATION:
                isExceptionThrown = DBHelper.getInstance(this).insertSpecification(table_detail_dto.getDpcSpecificationDto(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;
            default:
                break;
            /*case TABLE_DPC_SOCIETY:
                isExceptionThrown = DBHelper.getInstance(this).insertDpcSociety(table_detail_dto.getSocietyDto(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;*/

           /* case TABLE_FARMER_CLASS:
                isExceptionThrown = DBHelper.getInstance(this).insertFarmerClass(table_detail_dto.getFarmerClassDto(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;*/

             /*case TABLE_DPC_REVENUE_VILLAGE:
                isExceptionThrown = DBHelper.getInstance(this).insertRevenueVillageName(table_detail_dto.getRevenueVillageDto(), "FirstSync");
                if (!isExceptionThrown) {
                    backupDB();
                    exceptionInSync();
                    return;
                }
                break;*/
        }
        afterDatabaseInsertion(table_detail_dto);
    }

    /**
     * method for after insert the data.
     * response from server
     *
     * @param table_detail_dto
     */
    private void afterDatabaseInsertion(FirstSyncResponseDto table_detail_dto) {
        FirstSyncRequestDto dpcRequest = new FirstSyncRequestDto();
        if (table_detail_dto.isHasMore() /*&& table_detail_dto.getTotalSentCount() < 100*/) {
//            if (optimize) {
//                setcount(dpcRequest);
//            } else {
            dpcRequest.setTotalCount(table_detail_dto.getTotalCount());
            dpcRequest.setTotalSentCount(table_detail_dto.getTotalSentCount());
//            }
            dpcRequest.setCurrentCount(table_detail_dto.getCurrentCount());
            dpcRequest.setTableName(firstSync.get(0).getTableName());
            setTableSyncCall(dpcRequest);
        } else {
            firstSync.remove(0);
            setDownloadedProgress();
            if (firstSync.size() > 0) {
                if (optimize) {
                    setcount(dpcRequest);
                }
                dpcRequest.setTableName(firstSync.get(0).getTableName());
                setTextStrings(firstSync.get(0).getTextToDisplay() + "....");
                setTableSyncCall(dpcRequest);
            } else {
                firstSyncSuccess();
            }
        }
    }

    private void setcount(FirstSyncRequestDto dpcRequest) {
        dpcRequest.setTotalCount(firstSync.get(0).getCount());
        dpcRequest.setTotalSentCount(DBHelper.getInstance(this).gettablecount(firstSync.get(0).getLocalTableName()));
    }

    /**
     * sync success request
     */
    private void firstSyncSuccess() {
        try {
            new SyncSuccess().execute("");
        } catch (Exception e) {
            Log.e("SyncPageActivity", e.toString(), e);
        }
    }

    /**
     * Async   task for Download Sync for data in table
     */
    private class SyncSuccess extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... f_url) {
            try {
                HashMap<String, Object> inputParams = Utilities.getInstance().getRequestParams();
                inputParams.put("deviceNumber", device_id);
                Object data = "";
                controller.handleMessage(ResReqController.FIRST_SYNC_COMPLETE, inputParams, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Progress bar setting in activity
     */
    private void setDownloadedProgress() {
        int progress = progressBar.getProgress();
        progress = progress + totalProgress;
        progressBar.setProgress(progress);
    }

    /**
     * Scrolling of received String
     */
    private void setTextStrings(String syncString) {
        TextView tv = new TextView(SyncPageActivity.this);
        tv.setText(syncString);
        tv.setTextColor(Color.parseColor("#5B5B5B"));
        tv.setTextSize(22);
        layout.addView(tv);
        loadScroll.post(new Runnable() {
            @Override
            public void run() {
                loadScroll.fullScroll(View.FOCUS_DOWN);
            }
        });
    }

    /**
     * After response received from server successfully in android
     * Table details fetched in MAP
     * if tableDetails is empty or null user need to retry
     */
    private void processSyncResponseData(FirstSyncResponseDto first_sync_response_dto) {
        try {
            String status_code = first_sync_response_dto.getStatusCode();
            Log.e("THE TABLE DETAILS ", "" + first_sync_response_dto.getTableDetails());
            if (status_code.equalsIgnoreCase("0")) {
                if (first_sync_response_dto.getTableDetails() == null || first_sync_response_dto.getTableDetails().isEmpty()) {
                    showToastMessage(getString(R.string.toast_error_sync), 3000);
                }
                syncTableDetails(first_sync_response_dto.getTableDetails());
            }
        } catch (Exception e) {
            Log.e("SyncPageActivity", e.toString(), e);
        }
    }

    /**
     * contains table details name value pair.
     *
     * @param tableDetails
     */
    private void syncTableDetails(Map<String, Integer> tableDetails) {
        firstSync = new ArrayList<>();
        int count, dbcount;
        count = tableDetails.get("TABLE_DPC_STATE");
        dbcount = DBHelper.getInstance(this).gettablecount(DBTables.TABLE_DPC_STATE);
        if (count > dbcount)
            firstSync.add(getInputDTO("TABLE_DPC_STATE", count, "Table State category downloading", "State downloaded with", TableNames.TABLE_DPC_STATE, DBTables.TABLE_DPC_STATE));
        //
        count = tableDetails.get("TABLE_DPC_DISTRICT");
        dbcount = DBHelper.getInstance(this).gettablecount(DBTables.TABLE_DPC_DISTRICT);
        if (count > dbcount)
            firstSync.add(getInputDTO("TABLE_DPC_DISTRICT", count, "Table District downloading", "District downloaded with", TableNames.TABLE_DPC_DISTRICT, DBTables.TABLE_DPC_DISTRICT));
        //
        count = tableDetails.get("TABLE_DPC_TALUK");
        dbcount = DBHelper.getInstance(this).gettablecount(DBTables.TABLE_DPC_TALUK);
        if (count > dbcount)
            firstSync.add(getInputDTO("TABLE_DPC_TALUK", count, "Table Taluk downloading", "Taluk downloaded with", TableNames.TABLE_DPC_TALUK, DBTables.TABLE_DPC_TALUK));
        count = tableDetails.get("TABLE_DPC_VILLAGE");
        dbcount = DBHelper.getInstance(this).gettablecount(DBTables.TABLE_VILLAGE);
        if (count > dbcount)
            firstSync.add(getInputDTO("TABLE_DPC_VILLAGE", count, "Table Village downloading", "Village downloaded with", TableNames.TABLE_DPC_VILLAGE, DBTables.TABLE_VILLAGE));
//        count = tableDetails.get("TABLE_DPC_REVENUE_VILLAGE");
//        firstSync.add(getInputDTO("TABLE_DPC_REVENUE_VILLAGE", count, "Table Revenue Village downloading", "Revenue Village downloaded with", TableNames.TABLE_DPC_REVENUE_VILLAGE));
        count = tableDetails.get("TABLE_BANK");
        dbcount = DBHelper.getInstance(this).gettablecount(DBTables.TABLE_BANK);
        if (count > dbcount)
            firstSync.add(getInputDTO("TABLE_BANK", count, "Table Bank downloading", "Bank downloaded with", TableNames.TABLE_BANK, DBTables.TABLE_BANK));
        //
        count = tableDetails.get("TABLE_PADDY_CATEGORY");
        dbcount = DBHelper.getInstance(this).gettablecount(DBTables.TABLE_PADDY_CATEGORY);
        if (count > dbcount)
            firstSync.add(getInputDTO("TABLE_PADDY_CATEGORY", count, "Table Paddy category downloading", "Paddy category downloaded with", TableNames.TABLE_PADDY_CATEGORY, DBTables.TABLE_PADDY_CATEGORY));
        //
        count = tableDetails.get("TABLE_LAND_TYPE");
        dbcount = DBHelper.getInstance(this).gettablecount(DBTables.TABLE_LAND_TYPE);
        if (count > dbcount)
            firstSync.add(getInputDTO("TABLE_LAND_TYPE", count, "Table Land type downloading", "Land type downloaded with", TableNames.TABLE_LAND_TYPE, DBTables.TABLE_LAND_TYPE));
        //
        count = tableDetails.get("TABLE_LAND_TYPE");
        dbcount = DBHelper.getInstance(this).gettablecount(DBTables.TABLE_GRADE);
        if (count > dbcount)
            firstSync.add(getInputDTO("TABLE_DPC_GRADE", count, "Table grade downloading", "Grade downloaded with", TableNames.TABLE_DPC_GRADE, DBTables.TABLE_GRADE));
        //
        count = tableDetails.get("TABLE_FARMER_REG_DETAIL");
        dbcount = DBHelper.getInstance(this).gettablecount(DBTables.TABLE_FARMER);
        if (count > dbcount)
            firstSync.add(getInputDTO("TABLE_FARMER_REG_DETAIL", count, "Table Farmer details downloading", "Farmer details downloaded with", TableNames.TABLE_FARMER_REG_DETAIL, DBTables.TABLE_FARMER));
        //
        count = tableDetails.get("TABLE_FARMER_LAND_DETAIL");
        dbcount = DBHelper.getInstance(this).gettablecount(DBTables.TABLE_LAND_DETAILS);
        if (count > dbcount)
            firstSync.add(getInputDTO("TABLE_FARMER_LAND_DETAIL", count, "Table Land details downloading", "Land details downloaded with", TableNames.TABLE_FARMER_LAND_DETAIL, DBTables.TABLE_LAND_DETAILS));
        //
        count = tableDetails.get("TABLE_DPC_FARMER_REGISTRATION");
        dbcount = DBHelper.getInstance(this).gettablecount(DBTables.TABLE_ASSOCIATED_FARMERS);
        if (count > dbcount)
            firstSync.add(getInputDTO("TABLE_DPC_FARMER_REGISTRATION", count, "Table Associated Farmer details downloading", "Associated Farmer details downloaded with", TableNames.TABLE_DPC_FARMER_REGISTRATION, DBTables.TABLE_ASSOCIATED_FARMERS));
        //
        count = tableDetails.get("TABLE_DPC_FARMER_APPROVED_LAND_DETAILS");
        dbcount = DBHelper.getInstance(this).gettablecount(DBTables.TABLE_ASSOCIATED_LAND_DETAILS);
        if (count > dbcount)
            firstSync.add(getInputDTO("TABLE_DPC_FARMER_APPROVED_LAND_DETAILS", count, "Table Associated Farmer Land details downloading", "Associated Farmer Land details downloaded with", TableNames.TABLE_DPC_FARMER_APPROVED_LAND_DETAILS, DBTables.TABLE_ASSOCIATED_LAND_DETAILS));
        //
        count = tableDetails.get("TABLE_DPC_PADDY_RATE");
        dbcount = DBHelper.getInstance(this).gettablecount(DBTables.TABLE_DPC_RATE);
        if (count > dbcount)
            firstSync.add(getInputDTO("TABLE_DPC_PADDY_RATE", count, "Table Rate downloading", "Rate downloaded with", TableNames.TABLE_DPC_PADDY_RATE, DBTables.TABLE_DPC_RATE));
        //
        count = tableDetails.get("TABLE_DPC_PROCUREMENT");
        dbcount = DBHelper.getInstance(this).gettablecount(DBTables.TABLE_DPC_PROCUREMENT);
        if (count > dbcount)
            firstSync.add(getInputDTO("TABLE_DPC_PROCUREMENT", count, "Table Procurement downloading", "Procurement downloaded with", TableNames.TABLE_DPC_PROCUREMENT, DBTables.TABLE_DPC_PROCUREMENT));
        //
        count = tableDetails.get("TABLE_DPC_CAP");
        dbcount = DBHelper.getInstance(this).gettablecount(DBTables.TABLE_DPC_CAB);
        Log.e("Dbcount", "TABLE_DPC_CAP" + count);
        if (count > dbcount)
            firstSync.add(getInputDTO("TABLE_DPC_CAP", count, "Table Cap downloading", "Cap downloaded with", TableNames.TABLE_DPC_CAP, DBTables.TABLE_DPC_CAB));

        //
        count = tableDetails.get("TABLE_DPC_TRUCK_MEMO");
        Log.e("Dbcount", "TABLE_DPC_TRUCK_MEMO" + count);
        dbcount = DBHelper.getInstance(this).gettablecount(DBTables.TABLE_DPC_TRUCK_MEMO);
        if (count > dbcount)
            firstSync.add(getInputDTO("TABLE_DPC_TRUCK_MEMO", count, "Table Truck Memo downloading", "Truck Memo downloaded with", TableNames.TABLE_DPC_TRUCK_MEMO, DBTables.TABLE_DPC_TRUCK_MEMO));
        //


        count = tableDetails.get("TABLE_DPC_SPECIFICATION");
        dbcount = DBHelper.getInstance(this).gettablecount(DBTables.TABLE_DPC_SPECIFICATION);
        if (count > dbcount)
            firstSync.add(getInputDTO("TABLE_DPC_SPECIFICATION", count, "Table Specification  downloading", "Specification downloaded with", TableNames.TABLE_DPC_SPECIFICATION, DBTables.TABLE_DPC_SPECIFICATION));
        //


        if (firstSync.size() > 0) {
            totalProgress = 100 / firstSync.size();
            FirstSyncRequestDto dpcRequest = new FirstSyncRequestDto();
            String deviceId = Settings.Secure.getString(
                    getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase();
            dpcRequest.setDeviceNumber(deviceId);
            dpcRequest.setTableName(firstSync.get(0).getTableName());
            if (optimize) {
                setcount(dpcRequest);
            }
            setTableSyncCall(dpcRequest);
            setTextStrings(firstSync.get(0).getTextToDisplay() + "....");
        } else {
            firstSyncSuccess();
        }
    }

    /**
     * Request for data's by giving name of table to server
     * input FirstSyncReqDto Request
     */
    private void setTableSyncCall(FirstSyncRequestDto dpcRequest) {
        try {
            String updateData = new Gson().toJson(dpcRequest);
            totalSentCount = dpcRequest.getTotalSentCount();
            totalCount = dpcRequest.getTotalCount();
            Log.e("THE SENT COUNT IS ", "" + totalSentCount);
            Log.e("input data", updateData);
            new UpdateTablesTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        } catch (Exception e) {
            Log.e("SyncPageActivity", e.toString(), e);
        }
    }

    /**
     * Async   task for Download Sync for data in table
     */
    private class UpdateTablesTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                HashMap<String, Object> inputParams = Utilities.getInstance().getRequestParams();
                inputParams.put("deviceNumber", device_id);
                Log.e("THE SERVER TABLE", "" + firstSync.get(0).getTableName());
                inputParams.put("tableName", firstSync.get(0).getTableName());
                inputParams.put("totalSentCount", totalSentCount);
                Log.e("THE TOTAL COUNT", "" + totalCount);
                inputParams.put("totalCount", totalCount);
                Object data = "";
                controller.handleMessage(ResReqController.FIRST_SYNC_GET_TABLE_DETAIL, inputParams, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * returns FistSyncInputDto of details of tables received from server
     */
    private FistSyncInputDto getInputDTO(String tableName, int count, String textToDisplay, String endText, TableNames names, String localtablename) {
        FistSyncInputDto inputDto = new FistSyncInputDto();
        inputDto.setTableName(tableName);
        inputDto.setCount(count);
        inputDto.setTableNames(names);
        inputDto.setTextToDisplay(textToDisplay);
        inputDto.setEndTextToDisplay(endText);
        inputDto.setDynamic(true);
        inputDto.setLocalTableName(localtablename);
        return inputDto;
    }

    @Override
    public boolean handleMessage(Message msg) {
        try {
            switch (msg.what) {
                case ResReqController.FIRST_SYNC_GET_DETAILS_SUCCESS:
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    Log.d("Check", "" + msg.obj.toString());
                    FirstSyncResponseDto get_details_response_dto = gson.fromJson(msg.obj.toString(), FirstSyncResponseDto.class);
                    if (get_details_response_dto.getStatusCode().equalsIgnoreCase("0")) {
                        processSyncResponseData(get_details_response_dto);
                    }
                    return true;
                case ResReqController.FIRST_SYNC_GET_DETAILS_FAILED:
                    errorInSync();
                    Log.d("SyncPageActivity", "SYNC GET FAILED" + msg.obj.toString());
                    return true;
                case ResReqController.FIRST_SYNC_GET_TABLE_DETAIL_SUCCESS:
                    GsonBuilder gsonBuilder_ = new GsonBuilder();
                    Gson gson_ = gsonBuilder_.create();
                    Log.d("Check", "" + msg.obj.toString());
//                    ResponseDto table_detail_dto  = gson_.fromJson(msg.obj.toString(), ResponseDto.class);
                    FirstSyncResponseDto table_detail_dto = gson_.fromJson(msg.obj.toString(), FirstSyncResponseDto.class);
                    if (table_detail_dto.getStatusCode().equalsIgnoreCase("0")) {
                        insertIntoDatabase(table_detail_dto);
                    }
                    return true;
                case ResReqController.FIRST_SYNC_GET_TABLE_DETAIL_FAILED:
                    errorInSync();
                    Log.d("Error", "" + msg.obj.toString());
                    return true;
                case ResReqController.FIRST_SYNC_COMPLETE_SUCCESS:
                    GsonBuilder gsonBuilder_complete = new GsonBuilder();
                    Gson gson_complete = gsonBuilder_complete.create();
                    Log.d("Check", "" + msg.obj.toString());
                    FirstSyncResponseDto sync_success_dto = gson_complete.fromJson(msg.obj.toString(), FirstSyncResponseDto.class);
                    if (sync_success_dto.getStatusCode().equalsIgnoreCase("0")) {
                        progressBar.setProgress(100);
                        DBHelper.getInstance(SyncPageActivity.this).updateMaserData("syncTime", sync_success_dto.getLastSyncTime());
                        MySharedPreference.writeBoolean(SyncPageActivity.this, MySharedPreference.SYNC_COMPLETE, true);
                        complete_btn.setVisibility(View.VISIBLE);
                        complete_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(SyncPageActivity.this, AdminActivity.class);
                                startActivity(i);
                            }
                        });
                    }
                    return true;
                case ResReqController.FIRST_SYNC_COMPLETE_FAILED:
                    showToastMessage(getString(R.string.toast_server_unreachable), 3000);
                    Log.d("Error", "" + msg.obj.toString());
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("SyncPageActivity", "SYNC GET FAILED" + msg.obj.toString());
        }
        return false;
    }

    private void errorInSync() {
        if (autoretrycount > 3) {
            autoretrycount = 0;
            layout.removeAllViews();
            progressBar.setProgress(0);
            retryCount++;
            if (retryCount >= 3) {
                new RetryFailedDialog(this).show();
            } else {
                new RetryDialog(this, retryCount).show();
            }
        } else {
            autoretrycount += 1;
            new UpdateTablesTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
        }
    }

    public void call_failed_dialog() {
        retryCount++;
        if (retryCount >= 3) {
            new RetryFailedDialog(this).show();
        } else {
            new RetryDialog(this, retryCount).show();
        }
    }

    /**
     * user logout
     */
    public void logOut() {
        DBHelper.getInstance(this).closeConnection();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void backupDB() {
        LocalDbRecoveryProcess localDbRecoveryPro = new LocalDbRecoveryProcess(SyncPageActivity.this);
        localDbRecoveryPro.backupDb(true, "", DBHelper.DATABASE_NAME, "");
    }

    public void exceptionInSync() {
        layout.removeAllViews();
        progressBar.setProgress(0);
        new TableExceptionErrorDialog(this).show();
    }
}
