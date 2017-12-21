package com.omneagate.dpc.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Activity.SplashActivity;
import com.omneagate.dpc.Activity.SyncPageActivity;
import com.omneagate.dpc.Model.AssosiatedFarmersListLocalDto;
import com.omneagate.dpc.Model.BankDto;
import com.omneagate.dpc.Model.DPCProcurementDto;
import com.omneagate.dpc.Model.DPCProfileDto;
import com.omneagate.dpc.Model.DPCSyncExceptionDto;
import com.omneagate.dpc.Model.DeviceDto;
import com.omneagate.dpc.Model.DpcCapDto;
import com.omneagate.dpc.Model.DpcDistrictDto;
import com.omneagate.dpc.Model.DpcPaddyRateDto;
import com.omneagate.dpc.Model.DpcSpecificationDto;
import com.omneagate.dpc.Model.DpcStateDto;
import com.omneagate.dpc.Model.DpcTalukDto;
import com.omneagate.dpc.Model.DpcTruckMemoDto;
import com.omneagate.dpc.Model.DpcVillageDto;
import com.omneagate.dpc.Model.FarmerApprovedLandDetailsDto;
import com.omneagate.dpc.Model.FarmerClassDto;
import com.omneagate.dpc.Model.FarmerLandDetailsDto;
import com.omneagate.dpc.Model.FarmerListLocalDto;
import com.omneagate.dpc.Model.FarmerRegistrationDto;
import com.omneagate.dpc.Model.FarmerRegistrationRequestDto;
import com.omneagate.dpc.Model.FirstSyncDistrictDto;
import com.omneagate.dpc.Model.FirstSyncTalukDto;
import com.omneagate.dpc.Model.LandTypeDto;
import com.omneagate.dpc.Model.LoginHistoryDto;
import com.omneagate.dpc.Model.PaddyCategoryDto;
import com.omneagate.dpc.Model.PaddyGradeDto;
import com.omneagate.dpc.Model.ResponseDto;
import com.omneagate.dpc.Model.SocietyDto;
import com.omneagate.dpc.Model.UserDetailDto;
import com.omneagate.dpc.Model.VillageDtoId;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Created by user on 27/6/16.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = DBHelper.class.getCanonicalName();
    // Database Name
    public static final String DATABASE_NAME = "dpc.db";
    //Key for id in tables
    public final static String KEY_ID = "_id";
    // Database Version
    private static final int DATABASE_VERSION = 3;
    // All Static variables
    private static DBHelper dbHelper = null;
    private static SQLiteDatabase database = null;
    private static Context contextValue;
    List<SocietyDto> society_list = new ArrayList<SocietyDto>();
    List<FarmerClassDto> farmer_class_list = new ArrayList<FarmerClassDto>();
    List<BankDto> bank_name_list = new ArrayList<BankDto>();
    List<FirstSyncDistrictDto> district_name_list = new ArrayList<FirstSyncDistrictDto>();
    List<PaddyCategoryDto> paddy_category_list = new ArrayList<PaddyCategoryDto>();
    List<LandTypeDto> land_list = new ArrayList<LandTypeDto>();
    List<PaddyGradeDto> grade_list = new ArrayList<PaddyGradeDto>();
    List<FarmerListLocalDto> farmer_list = new ArrayList<FarmerListLocalDto>();
    List<AssosiatedFarmersListLocalDto> associated_farmer_list = new ArrayList<AssosiatedFarmersListLocalDto>();
    List<FarmerRegistrationRequestDto> farmer_list_sync = new ArrayList<FarmerRegistrationRequestDto>();
    List<FarmerLandDetailsDto> farmer_list_sync_land = new ArrayList<FarmerLandDetailsDto>();
    private List<DPCProcurementDto> procurement_list_sync = new ArrayList<DPCProcurementDto>();

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        super(context, Environment.getExternalStorageDirectory()
//                + File.separator + "/DataBase/" + File.separator
//                + DATABASE_NAME, null, DATABASE_VERSION);
        database = this.getWritableDatabase();
//        database.execSQL("DROP TABLE "+DBTables.TABLE_DPC_PROCUREMENT);
//        database.execSQL(DBTables.CREATE_TABLE_DPC_PROCUREMENT);
        dbHelper = this;
        contextValue = context;
    }

    //Singleton to Instantiate the SQLiteOpenHelper
    public static DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
            openConnection();
        }
        contextValue = context;
        return dbHelper;
    }

    // It is used to open database
    private static void openConnection() {
        if (database == null) {
            database = dbHelper.getWritableDatabase();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("***DBHelper****", "selected query= " + DBTables.CREATE_TABLE_BANK);
        db.execSQL(DBTables.CREATE_TABLE_USERS);
        db.execSQL(DBTables.CREATE_MASTER_TABLE);
        db.execSQL(DBTables.CREATE_TABLE_DPC_PROFILE);
        db.execSQL(DBTables.CREATE_TABLE_BANK);
        db.execSQL(DBTables.CREATE_TABLE_TALUK);
        db.execSQL(DBTables.CREATE_TABLE_VILLAGE);
        db.execSQL(DBTables.CREATE_TABLE_LAND);
        db.execSQL(DBTables.CREATE_TABLE_DISTRICT);
        db.execSQL(DBTables.CREATE_TABLE_PADDY_CATEGORY);
        db.execSQL(DBTables.CREATE_TABLE_STATE);
        db.execSQL(DBTables.CREATE_TABLE_GRADE);
        db.execSQL(DBTables.CREATE_TABLE_UPGRADE);
        db.execSQL(DBTables.CREATE_TABLE_FARMER);
        db.execSQL(DBTables.CREATE_TABLE_LAND_DETAILS);
        db.execSQL(DBTables.CREATE_TABLE_SYNC_EXCEPTION);
        db.execSQL(DBTables.CREATE_TABLE_ASSOCIATED_FARMER);
        db.execSQL(DBTables.CREATE_TABLE_ASSOCIATED_LAND_DETAILS);
        db.execSQL(DBTables.CREATE_TABLE_LOGIN_HISTORY);
        db.execSQL(DBTables.CREATE_TABLE_DPC_RATE);
        db.execSQL(DBTables.CREATE_TABLE_DPC_PROCUREMENT);
        db.execSQL(DBTables.CREATE_TABLE_DPC_CAP);
        db.execSQL(DBTables.CREATE_TABLE_TRUCK_MEMO);
        db.execSQL(DBTables.CREATE_TABLE_SPECIFICATION);
        //after second release
//        db.execSQL("alter table " + DBTables.TABLE_DPC_TRUCK_MEMO + " add column " + DbConstants.KEY_TRUCKMEMO_SPECIFICATION_ID + " INTEGER");
        Log.e(DBHelper.class.getCanonicalName(), "Execution table created");
    }

    public synchronized void closeConnection() {
        if (dbHelper != null) {
            dbHelper.close();
            database.close();
            dbHelper = null;
            database = null;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            Log.e("DB helper", " oldversion : " + oldVersion + "new version : " + newVersion);
            switch (oldVersion) {
                case 1:
                    Log.e("oldversion", "" + oldVersion);
                    Log.e("new_version", "" + newVersion);
                case 2:
                    if (newVersion < 3) {
                        Log.e("DBHelper", "Case 2 Started");
                        db.execSQL("drop table if exists TABLE_BANK");
                        db.execSQL("drop table if exists TABLE_DISTRICT");
                        db.execSQL("drop table if exists TABLE_DPC_SOCIETY");
                        db.execSQL("drop table if exists TABLE_FARMER_CLASS");
                        db.execSQL("drop table if exists TABLE_GRADE");
                        db.execSQL("drop table if exists TABLE_LAND_TYPE");
                        db.execSQL("drop table if exists TABLE_PADDY_CATEGORY");
                        db.execSQL("drop table if exists TABLE_STATE");
                        db.execSQL("drop table if exists TABLE_TALUK");
                        db.execSQL("drop table if exists TABLE_VILLAGE");
                        db.execSQL("drop table if exists table_land_details");
                        db.execSQL("drop table if exists table_farmer");
                        db.execSQL(DBTables.CREATE_TABLE_USERS);
                        db.execSQL(DBTables.CREATE_TABLE_BANK);
                        db.execSQL(DBTables.CREATE_TABLE_TALUK);
                        db.execSQL(DBTables.CREATE_TABLE_VILLAGE);
                        db.execSQL(DBTables.CREATE_TABLE_LAND);
                        db.execSQL(DBTables.CREATE_TABLE_DISTRICT);
                        db.execSQL(DBTables.CREATE_TABLE_PADDY_CATEGORY);
                        db.execSQL(DBTables.CREATE_TABLE_STATE);
                        db.execSQL(DBTables.CREATE_TABLE_GRADE);
                        db.execSQL(DBTables.CREATE_TABLE_FARMER);
                        db.execSQL(DBTables.CREATE_TABLE_LAND_DETAILS);
                        db.execSQL(DBTables.CREATE_TABLE_ASSOCIATED_FARMER);
                        db.execSQL(DBTables.CREATE_TABLE_ASSOCIATED_LAND_DETAILS);
                        db.execSQL(DBTables.CREATE_TABLE_DPC_PROFILE);
                        db.execSQL(DBTables.CREATE_TABLE_DPC_RATE);
                        db.execSQL(DBTables.CREATE_TABLE_LOGIN_HISTORY);
                        db.execSQL(DBTables.CREATE_TABLE_DPC_PROCUREMENT);
                        db.execSQL(DBTables.CREATE_TABLE_DPC_CAP);
                        db.execSQL(DBTables.CREATE_TABLE_TRUCK_MEMO);
                        db.execSQL(DBTables.CREATE_TABLE_SPECIFICATION);
                        Log.e("DBHelper", "Case 2 finished");
                    }
                case 3:
                    //after second release
                    Log.e("DBHelper", "Case 3 Started");
                    db.execSQL("alter table " + DBTables.TABLE_DPC_TRUCK_MEMO + " add column " + DbConstants.KEY_TRUCKMEMO_SPECIFICATION_ID + " INTEGER");
                    Log.e("DBHelper", "Case 3 finished");
                default:
                    break;
            }
//            SharedPreferences sharedpreferences;
            if (SplashActivity.context == null) {
                Log.e("Null", "Context null");
            }
           /* sharedpreferences = SplashActivity.context.getSharedPreferences("DBData", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt("version", newVersion);
            editor.apply();*/
//            MySharedPreference.writeInteger(contextValue, MySharedPreference.VERSION, newVersion);
        } catch (Exception e) {
            Log.e("Upgrade Error", e.toString(), e);
        }
    }

    /*
     * Configuration table
     */
    public String getMasterData(String key) {
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_CONFIG_TABLE + " where name = '" + key + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String value = null;
        try {
            if (cursor.moveToFirst()) {
                value = cursor.getString(cursor
                        .getColumnIndex("value"));
            }
        } catch (Exception e) {
            Log.e("Error", e.toString(), e);
        }
        cursor.close();
        return value;
    }

    public void insertValues() {
//        insertMaserData("serverUrl", "http://192.168.1.50:8089");
        insertMaserData("serverUrl", "http://192.168.2.149:8089");
        insertMaserData("purgeBill", "30");
        insertMaserData("syncTime", null);
        insertMaserData("status", null);
        insertMaserData("printer", null);
        insertMaserData("language", "ta");
    }

    private void insertMaserData(String name, String value) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("value", value);
        if (database == null) {
            database = dbHelper.getWritableDatabase();
        }
        database.insertWithOnConflict(DBTables.TABLE_CONFIG_TABLE, "name", values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void updateMaserData(String name, String value) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("value", value);
        Log.e("checkdata", "" + value);
        database.update(DBTables.TABLE_CONFIG_TABLE, values, "name='" + name + "'", null);
    }

    //This function inserts details to village table
    public boolean insertVillageName(Set<DpcVillageDto> village_dto, String syncType) {
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        DpcVillageDto vill_dto = null;
        if (!village_dto.isEmpty()) {
            database.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (DpcVillageDto village : village_dto) {
                    recordId = village.getId();
                    vill_dto = village;
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(village.getId()));
                    values.put(DbConstants.KEY_CODE, String.valueOf(village.getCode()));
                    values.put(DbConstants.KEY_CREATED_BY_ID, String.valueOf(village.getCreatedById()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(village.getCreatedDate()));
                    values.put(DbConstants.KEY_VILLAGE_LVILLAGE_NAME, String.valueOf(village.getLvillageName()));
                    values.put(DbConstants.KEY_MODIFIED_BY_ID, String.valueOf(village.getModifiedById()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(village.getModifiedDate()));
                    values.put(DbConstants.KEY_VILLAGE_NAME, String.valueOf(village.getName()));
                    values.put(DbConstants.KEY_STATUS, returnInteger(village.isStatus()));
                    values.put(DbConstants.KEY_VILLAGE_TALUK_ID, String.valueOf(village.getDpcTalukDto().getId()));
                    values.put(DbConstants.KEY_NEW_VILLAGE_CODE, String.valueOf(village.getNewVillageCode()));
                    database.insertWithOnConflict(DBTables.TABLE_VILLAGE, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(vill_dto);
                    insertSyncException("TABLE_VILLAGE", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }


    /*//revenue_village table
    public boolean insertRevenueVillageName(Set<RevenueVillageDto> revenue_village_dto, String syncType) {
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        RevenueVillageDto rev_vil_dto = null;

        if (!revenue_village_dto.isEmpty()) {
            database.beginTransaction();
            try {
                for (RevenueVillageDto revenue_village : revenue_village_dto) {
                    recordId = revenue_village.getId();
                    rev_vil_dto = revenue_village;
                    ContentValues values = new ContentValues();
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(revenue_village.getId()));
                    values.put(DbConstants.KEY_STATUS, returnInteger(revenue_village.isStatus()));
                    values.put(DbConstants.KEY_CREATED_BY_ID, String.valueOf(revenue_village.getCreatedById()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(revenue_village.getCreatedDate()));
                    values.put(DbConstants.KEY_MODIFIED_BY_ID, String.valueOf(revenue_village.getModifiedById()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(revenue_village.getModifiedDate()));
                    values.put(DbConstants.KEY_REVRNUE_VILLAGE_NAME, String.valueOf(revenue_village.getRevVillageName()));
                    values.put(DbConstants.KEY_REVRNUE_VILLAGE_LVILLAGE_NAME, String.valueOf(revenue_village.getRevVillageLname()));
                    values.put(DbConstants.KEY_REVRNUE_VILLAGE_CODE, String.valueOf(revenue_village.getVillageCode()));
                    values.put(DbConstants.KEY_REVRNUE_VILLAGE_TALUK_ID, String.valueOf(revenue_village.getTalukDto().getId()));

                    database.insertWithOnConflict(DBTables.TABLE_REVENVE_VILLAGE, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();

            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(rev_vil_dto);
                    insertSyncException("TABLE_DPC_REVENUE_VILLAGE", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }*/

    //bank
    public boolean insertBankData(Set<BankDto> bank_dto, String syncType) {
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        BankDto bnk_dto = null;
        if (!bank_dto.isEmpty()) {
            database.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (BankDto bank : bank_dto) {
                    recordId = bank.getId();
                    bnk_dto = bank;
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(bank.getId()));
                    values.put(DbConstants.KEY_BANK_NAME, String.valueOf(bank.getBankName()));
                    values.put(DbConstants.KEY_BRANCH_NAME, String.valueOf(bank.getBranch()));
                    values.put(DbConstants.KEY_CODE, String.valueOf(bank.getCode()));
                    values.put(DbConstants.KEY_CONTACT_PERSON, String.valueOf(bank.getContactPerson()));
                    values.put(DbConstants.KEY_CREATED_BY, String.valueOf(bank.getCreatedBy()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(bank.getCreatedDate()));
                    values.put(DbConstants.KEY_BANK_REGIONAL_NAME, String.valueOf(bank.getLname()));
                    values.put(DbConstants.KEY_MOBILE_NUMBER, String.valueOf(bank.getMobileNo()));
                    values.put(DbConstants.KEY_MODIFIED_BY, String.valueOf(bank.getModifiedBy()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(bank.getModifiedDate()));
                    values.put(DbConstants.KEY_STATUS, returnInteger(bank.isStatus()));
//                    values.put(DbConstants.KEY_BANK_DISTRICT_ID, String.valueOf(bank.getDistrictDto().getId()));
//                    values.put(DbConstants.KEY_BANK_TALUK_ID, String.valueOf(bank.getTalukDto().getId()));
                    database.insertWithOnConflict(DBTables.TABLE_BANK, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(bnk_dto);
                    insertSyncException("TABLE_BANK", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }

    public boolean insertLandTypeData(Set<LandTypeDto> land_type_dto, String syncType) {
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        LandTypeDto lnd_dto = null;
        if (!land_type_dto.isEmpty()) {
            database.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (LandTypeDto land_type : land_type_dto) {
                    recordId = land_type.getId();
                    lnd_dto = land_type;
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(land_type.getId()));
                    values.put(DbConstants.KEY_CREATED_BY, String.valueOf(land_type.getCreatedBy()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(land_type.getCreatedDate()));
                    values.put(DbConstants.KEY_MODIFIED_BY, String.valueOf(land_type.getModifiedBy()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(land_type.getModifiedDate()));
                    values.put(DbConstants.KEY_LAND_TYPE, String.valueOf(land_type.getName()));
                    values.put(DbConstants.KEY_STATUS, returnInteger(land_type.isStatus()));
                    database.insertWithOnConflict(DBTables.TABLE_LAND_TYPE, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(lnd_dto);
                    insertSyncException("TABLE_LAND_TYPE", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }

    public boolean insertTalukData(Set<DpcTalukDto> taluk_dto, String syncType) {
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        DpcTalukDto tlk_dto = null;
        if (!taluk_dto.isEmpty()) {
            database.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (DpcTalukDto taluk : taluk_dto) {
                    recordId = taluk.getId();
                    tlk_dto = taluk;
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(taluk.getId()));
                    values.put(DbConstants.KEY_CODE, String.valueOf(taluk.getCode()));
                    values.put(DbConstants.KEY_CREATED_BY_ID, String.valueOf(taluk.getCreatedById()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(taluk.getCreatedDate()));
                    values.put(DbConstants.KEY_TALUK_REGIONAL_NAME, String.valueOf(taluk.getLtalukName()));
                    values.put(DbConstants.KEY_MODIFIED_BY_ID, String.valueOf(taluk.getModifiedById()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(taluk.getModifiedDate()));
                    values.put(DbConstants.KEY_TALUK_NAME, String.valueOf(taluk.getName()));
                    values.put(DbConstants.KEY_STATUS, returnInteger(taluk.isStatus()));
                    values.put(DbConstants.KEY_TALUK_DISTRICT_ID, String.valueOf(taluk.getDpcDistrictDto().getId()));
                    database.insertWithOnConflict(DBTables.TABLE_DPC_TALUK, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(tlk_dto);
                    insertSyncException("TABLE_DPC_TALUK", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }

    public boolean insertState(Set<DpcStateDto> dpc_state_dto, String syncType) {
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        DpcStateDto st_dto = null;
        if (!dpc_state_dto.isEmpty()) {
            database.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (DpcStateDto state : dpc_state_dto) {
                    recordId = state.getId();
                    st_dto = state;
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(state.getId()));
                    values.put(DbConstants.KEY_CODE, String.valueOf(state.getCode()));
                    values.put(DbConstants.KEY_CREATED_BY_ID, String.valueOf(state.getCreatedById()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(state.getCreatedDate()));
                    values.put(DbConstants.KEY_STATE_REGIONAL_NAME, String.valueOf(state.getLstateName()));
                    values.put(DbConstants.KEY_MODIFIED_BY_ID, String.valueOf(state.getModifiedById()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(state.getModifiedDate()));
                    values.put(DbConstants.KEY_STATE_NAME, String.valueOf(state.getName()));
                    values.put(DbConstants.KEY_STATUS, returnInteger(state.isStatus()));
                    database.insertWithOnConflict(DBTables.TABLE_DPC_STATE, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(st_dto);
                    insertSyncException("TABLE_DPC_STATE", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }

    //district
    public boolean insertDistrictkData(Set<DpcDistrictDto> dpc_district_dto, String syncType) {
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        DpcDistrictDto dis_dto = null;
        if (!dpc_district_dto.isEmpty()) {
            database.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (DpcDistrictDto district : dpc_district_dto) {
                    recordId = district.getId();
                    dis_dto = district;
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(district.getId()));
                    values.put(DbConstants.KEY_CODE, String.valueOf(district.getCode()));
                    values.put(DbConstants.KEY_CREATED_BY_ID, String.valueOf(district.getCreatedById()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(district.getCreatedDate()));
                    values.put(DbConstants.KEY_DISTRICT_STATE_NAME, String.valueOf(district.getDpcStateDto().getName()));
                    values.put(DbConstants.KEY_REGIONAL_DISTRICT_NAME, String.valueOf(district.getLdistrictName()));
                    values.put(DbConstants.KEY_MODIFIED_BY_ID, String.valueOf(district.getModifiedById()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(district.getModifiedDate()));
                    values.put(DbConstants.KEY_DISTRICT_NAME, String.valueOf(district.getName()));
                    values.put(DbConstants.KEY_STATUS, returnInteger(district.isStatus()));
                    values.put(DbConstants.KEY_DISTRICT_STATE_ID, String.valueOf(district.getDpcStateDto().getId()));
                    values.put(DbConstants.KEY_DISTRICT_REGION_CODE, String.valueOf(district.getDpcStateDto().getId()));
                    values.put(DbConstants.KEY_DELTA_REGION, returnInteger(district.isStatus()));
                    database.insertWithOnConflict(DBTables.TABLE_DPC_DISTRICT, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(dis_dto);
                    insertSyncException("TABLE_DPC_DISTRICT", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }


   /* public boolean insertDpcSociety(Set<SocietyDto> society_dto, String syncType) {
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        SocietyDto soc_dto = null;
        if (!society_dto.isEmpty()) {
            database.beginTransaction();
            try {
             ContentValues values = new ContentValues();
                for (SocietyDto society : society_dto) {
                    recordId = society.getId();
                    soc_dto = society;

                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(society.getId()));
                    values.put(DbConstants.KEY_SOCIETY_CODE, String.valueOf(society.getCode()));
                    values.put(DbConstants.KEY_SOCIETY_NAME, String.valueOf(society.getName()));
                    values.put(DbConstants.KEY_STATUS, returnInteger(society.isStatus()));
                    values.put(DbConstants.KEY_SOCIETY_REGIONAL_NAME, String.valueOf(society.getLname()));
                    values.put(DbConstants.KEY_SOCIETY_ADDRESS, String.valueOf(society.getAddress()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(society.getCreatedDate()));
                    values.put(DbConstants.KEY_CREATED_BY, String.valueOf(society.getCreatedBy()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(society.getModifiedDate()));
                    values.put(DbConstants.KEY_MODIFIED_BY, String.valueOf(society.getModifiedBy()));
                    database.insertWithOnConflict(DBTables.TABLE_DPC_SOCIETY, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }

                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();

            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(soc_dto);
                    insertSyncException("TABLE_DPC_SOCIETY", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }*/


   /* public boolean insertFarmerClass(Set<FarmerClassDto> farmer_class, String syncType) {

        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        FarmerClassDto frc_dto = null;

        if (!farmer_class.isEmpty()) {
            database.beginTransaction();
            try {
             ContentValues values = new ContentValues();
                for (FarmerClassDto farmer : farmer_class) {
                    recordId = farmer.getId();
                    frc_dto = farmer;

                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(farmer.getId()));
                    values.put(DbConstants.KEY_FARMER_CLASS_CODE, String.valueOf(farmer.getCode()));
                    values.put(DbConstants.KEY_FARMER_CLASS_NAME, String.valueOf(farmer.getName()));
                    values.put(DbConstants.KEY_STATUS, returnInteger(farmer.isStatus()));
                    values.put(DbConstants.KEY_FARMER_CLASS_REGIONAL_NAME, String.valueOf(farmer.getLname()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(farmer.getCreatedDate()));
                    values.put(DbConstants.KEY_CREATED_BY, String.valueOf(farmer.getCreatedBy()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(farmer.getModifiedDate()));
                    values.put(DbConstants.KEY_MODIFIED_BY, String.valueOf(farmer.getModifiedBy()));
                    database.insertWithOnConflict(DBTables.TABLE_FARMER_CLASS, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(frc_dto);
                    insertSyncException("TABLE_FARMER_CLASS", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }*/

    public boolean insertPaddyCategory(Set<PaddyCategoryDto> paddy_category, String syncType) {
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        PaddyCategoryDto pdc_dto = null;
        if (!paddy_category.isEmpty()) {
            database.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (PaddyCategoryDto paddy : paddy_category) {
                    recordId = paddy.getId();
                    pdc_dto = paddy;
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(paddy.getId()));
                    values.put(DbConstants.KEY_CODE, String.valueOf(paddy.getCode()));
                    values.put(DbConstants.KEY_PADDY_CATEGORY_NAME, String.valueOf(paddy.getName()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(paddy.getCreatedDate()));
                    values.put(DbConstants.KEY_STATUS, returnInteger(paddy.isStatus()));
                    values.put(DbConstants.KEY_PADDY_CATEGORY_REGIONAL_NAME, String.valueOf(paddy.getLname()));
                    values.put(DbConstants.KEY_CREATED_BY, String.valueOf(paddy.getCreatedBy()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(paddy.getModifiedDate()));
                    values.put(DbConstants.KEY_MODIFIED_BY, String.valueOf(paddy.getModifiedBy()));
                   /* values.put(DbConstants.KEY_BONOUS_RATE_PER_QUINTAL, String.valueOf(paddy.getBonusRatePerQuintal()));
                    values.put(DbConstants.KEY_PURCHASE_RATE_PER_QUINTAL, String.valueOf(paddy.getPurchaseRatePerQunital()));
                    values.put(DbConstants.KEY_TOTAL_RATE_PER_QUINTAL, String.valueOf(paddy.getTotalRatePerQunital()));
                    values.put(DbConstants.KEY_DPC_GRADE_ID, String.valueOf(paddy.getPaddyGradeDto().getId()));*/
                    database.insertWithOnConflict(DBTables.TABLE_PADDY_CATEGORY, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(pdc_dto);
                    insertSyncException("TABLE_PADDY_CATEGORY", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }

    public boolean insertGrade(Set<PaddyGradeDto> grade_dto, String syncType) {
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        PaddyGradeDto gd_dto = null;
        if (!grade_dto.isEmpty()) {
            database.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (PaddyGradeDto grade : grade_dto) {
                    recordId = grade.getId();
                    gd_dto = grade;
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(grade.getId()));
                    values.put(DbConstants.KEY_CODE, String.valueOf(grade.getCode()));
                    values.put(DbConstants.KEY_GRADE_NAME, String.valueOf(grade.getName()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(grade.getCreatedDate()));
                    values.put(DbConstants.KEY_STATUS, returnInteger(grade.isStatus()));
                    values.put(DbConstants.KEY_GRADE_REGIONAL_NAME, String.valueOf(grade.getLname()));
                    values.put(DbConstants.KEY_CREATED_BY, String.valueOf(grade.getCreatedBy()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(grade.getModifiedDate()));
                    values.put(DbConstants.KEY_MODIFIED_BY, String.valueOf(grade.getModifiedBy()));
                    database.insertWithOnConflict(DBTables.TABLE_GRADE, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(gd_dto);
                    insertSyncException("TABLE_DPC_GRADE", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }

    /* GET SOCIETY NAME *//*
    public List<SocietyDto> getSocietyName() {
        String selectQuery = "SELECT  * FROM TABLE_DPC_SOCIETY ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SocietyDto society = new SocietyDto();
                society.setName(cursor.getString(cursor.getColumnIndex("society_name")));
                society.setLname(cursor.getString(cursor.getColumnIndex("society_regional_name")));
                society.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                society_list.add(society);
            } while (cursor.moveToNext());
        }
        return society_list;
    }*/


    /* GET FARMER CLASS *//*
    public List<FarmerClassDto> getFormerClass() {
        String selectQuery = "SELECT  * FROM  TABLE_FARMER_CLASS ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                FarmerClassDto farmer = new FarmerClassDto();
                farmer.setName(cursor.getString(cursor.getColumnIndex("farmer_class_name")));
                farmer.setLname(cursor.getString(cursor.getColumnIndex("farmer_class_regional_name")));
                farmer.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                farmer_class_list.add(farmer);
            } while (cursor.moveToNext());
        }
        return farmer_class_list;
    }*/

    /*GET BANK NAME*/
    public List<BankDto> getBankName() {
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_BANK + " order by  " + DbConstants.KEY_BANK_NAME;
        /* String selectQuery = "SELECT  * FROM " + DBTables.TABLE_BANK + " order by name ";*/
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                BankDto bank = new BankDto();
                bank.setBankName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_BANK_NAME)));
                bank.setLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_BANK_REGIONAL_NAME)));
                bank.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                bank_name_list.add(bank);
            } while (cursor.moveToNext());
        }
        return bank_name_list;
    }

    public String getAssociatedBankName(long id) {
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_BANK + " where " + DbConstants.KEY_ID_SERVER + " = " + id;
        String bnk = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                if (GlobalAppState.language.equalsIgnoreCase("ta")) {
                    bnk = cursor.getString(cursor.getColumnIndex(DbConstants.KEY_BANK_REGIONAL_NAME));
                } else {
                    bnk = cursor.getString(cursor.getColumnIndex(DbConstants.KEY_BANK_NAME));
                }
            } while (cursor.moveToNext());
        }
        return bnk;
    }

    /*GET TALUKS */
    public List<FirstSyncTalukDto> getTalukName(long distriict_id) {
        List<FirstSyncTalukDto> taluk_name_list = new ArrayList<FirstSyncTalukDto>();
//        district_id = MySharedPreference.readString(context, MySharedPreference.DISTRICT_ID, "");
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_DPC_TALUK + "  where " + DbConstants.KEY_TALUK_DISTRICT_ID + " =" + distriict_id + " ORDER BY " + DbConstants.KEY_TALUK_NAME + " ASC ";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                FirstSyncTalukDto taluk = new FirstSyncTalukDto();
                taluk.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TALUK_NAME)));
                taluk.setLtalukName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TALUK_REGIONAL_NAME)));
                taluk.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                taluk_name_list.add(taluk);
            } while (cursor.moveToNext());
        }
        return taluk_name_list;
    }

    /*GET District */
    public List<FirstSyncDistrictDto> getDistrictName() {
//        district_id = MySharedPreference.readString(context, MySharedPreference.DISTRICT_ID, "");
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_DPC_DISTRICT + " ORDER BY " + DbConstants.KEY_DISTRICT_NAME + " ASC ";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                FirstSyncDistrictDto dist = new FirstSyncDistrictDto();
                dist.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_DISTRICT_NAME)));
                dist.setLdistrictName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_REGIONAL_DISTRICT_NAME)));
                dist.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                district_name_list.add(dist);
            } while (cursor.moveToNext());
        }
        return district_name_list;
    }

    /*GET PADDY CATEGORY */
    public List<PaddyCategoryDto> getPaddyCategory() {
        paddy_category_list.clear();
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_PADDY_CATEGORY;
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PaddyCategoryDto paddy = new PaddyCategoryDto();
                paddy.setLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PADDY_CATEGORY_REGIONAL_NAME)));
                paddy.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PADDY_CATEGORY_NAME)));
                paddy.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                paddy_category_list.add(paddy);
            } while (cursor.moveToNext());
        }
        return paddy_category_list;
    }

    public List<LandTypeDto> getLand() {
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_LAND_TYPE;
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                LandTypeDto land = new LandTypeDto();
                land.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_LAND_TYPE)));
                land.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                land_list.add(land);
            } while (cursor.moveToNext());
        }
        return land_list;
    }

    public String getTalukId(String selected_taluk) {
        String taluk_id = "";
        String selectQuery = "SELECT " + DbConstants.KEY_ID_SERVER + " FROM " + DBTables.TABLE_DPC_TALUK + " where " + DbConstants.KEY_TALUK_NAME + " = '" + selected_taluk + "'";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                taluk_id = cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER));
            } while (cursor.moveToNext());
        }
        return taluk_id;
    }

    public List<VillageDtoId> getvillsges(String taluk_id) {
        String village_name;
        List<VillageDtoId> village_list = new ArrayList<VillageDtoId>();
        Log.e("DBHelper", "talukid" + taluk_id);
        String selectQuery = "SELECT * FROM " + DBTables.TABLE_VILLAGE + " where " + DbConstants.KEY_VILLAGE_TALUK_ID + " = " + taluk_id + " ORDER BY " + DbConstants.KEY_VILLAGE_NAME + " ASC ";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                VillageDtoId village = new VillageDtoId();
                village.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_VILLAGE_NAME)));
                village.setVillagelname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_VILLAGE_LVILLAGE_NAME)));
                village.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
//                village_name = cursor.getString(cursor.getColumnIndex(DbConstants.KEY_VILLAGE_NAME));
                village_list.add(village);
            } while (cursor.moveToNext());
        }
        return village_list;
    }

    /*GET PADDY CATEGORY */
    public List<PaddyGradeDto> getGrade() {
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_GRADE;
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                PaddyGradeDto grade = new PaddyGradeDto();
                grade.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_GRADE_NAME)));
                grade_list.add(grade);
            } while (cursor.moveToNext());
        }
        return grade_list;
    }

    public PaddyGradeDto getGradeById(long id) {
        String selectQuery = "SELECT " + DbConstants.KEY_GRADE_NAME + "," + DbConstants.KEY_GRADE_REGIONAL_NAME + " FROM " + DBTables.TABLE_GRADE + " where " + DbConstants.KEY_ID_SERVER + " = " + id;
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        PaddyGradeDto grade = new PaddyGradeDto();
        if (cursor.moveToFirst()) {
            do {
                grade.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_GRADE_NAME)));
                grade.setLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_GRADE_REGIONAL_NAME)));
            } while (cursor.moveToNext());
        }
        return grade;
    }

    private int returnInteger(boolean value) {
        if (value)
            return 1;
        return 0;
    }

    public void insertTableUpgrade(int android_version, String userLog, String status, String state, int androidNewVersion, String refId, String serverRefId) {
        ContentValues values = new ContentValues();
        try {
            values.put("android_old_version", android_version);
            values.put("ref_id", refId);
            values.put("android_new_version", androidNewVersion);
            values.put("description", userLog);
            values.put("status", status.toUpperCase());
            values.put("state", state);
            values.put("refer_id", serverRefId);
            SimpleDateFormat regDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.getDefault());
            values.put("created_date", regDate.format(new Date()));
            Log.e("updateinfo", "" + values);
            database.insert(DBTables.TABLE_UPGRADE, null, values);
        } catch (Exception e) {
            Log.e("Table Upgrade", "Exception", e);
        }
    }

    public void insertTableUpgradeExec(int android_version, String userLog, String status, String state, int androidNewVersion, String refId, String serverRefId) {
        ContentValues values = new ContentValues();
        try {
            values.put("android_old_version", android_version);
            values.put("ref_id", refId);
            values.put("android_new_version", androidNewVersion);
            values.put("description", userLog);
            values.put("status", status.toUpperCase());
            values.put("state", state);
            SimpleDateFormat regDate = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a", Locale.getDefault());
            values.put("created_date", "" + regDate.format(new Date()));
            values.put("server_status", 0);
            values.put("refer_id", serverRefId);
            database.insert(DBTables.TABLE_UPGRADE, null, values);
        } catch (Exception e) {
            Log.e("Table Upgrade", e.toString(), e);
        }
    }

    public void insertFarmerRegistrationDetails(FarmerRegistrationRequestDto farmerRegistrationRequestDto) {
        Log.e("insert method", "insert");
        ContentValues values = new ContentValues();
        try {
            Log.e("insert try", "insert");
            values.put(DbConstants.KEY_REFERENCE_NUMBER, farmerRegistrationRequestDto.getRequestedReferenceNumber());
            SimpleDateFormat registrationDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
            values.put(DbConstants.KEY_REGISTERED_DATE_TIME, registrationDate.format(new Date()));
            values.put(DbConstants.KEY_FARMER_NAME, farmerRegistrationRequestDto.getFarmerName());
            values.put(DbConstants.KEY_GUARDIAN_TYPE, farmerRegistrationRequestDto.getGuardian());
            values.put(DbConstants.KEY_FARMER_LOCAL_NAME, farmerRegistrationRequestDto.getFarmerLname());
            values.put(DbConstants.KEY_FARMER_FATHER_HUSBAND_NAME, farmerRegistrationRequestDto.getGuardianName());
            values.put(DbConstants.KEY_FARMER_FATHER_HUSBAND_NAME_LOCAL, farmerRegistrationRequestDto.getGuardianLname());
            values.put(DbConstants.KEY_DATE_OF_BIRTH, farmerRegistrationRequestDto.getDateOfBirth());
            values.put(DbConstants.KEY_FARMER_MOBILE_NUMBER, farmerRegistrationRequestDto.getMobileNumber());
            values.put(DbConstants.KEY_FARMER_ADDRESS1, farmerRegistrationRequestDto.getAddress1());
            values.put(DbConstants.KEY_FARMER_ADDRESS2, farmerRegistrationRequestDto.getAddress2());
            if (farmerRegistrationRequestDto.getDpcVillageDto() != null)
                values.put(DbConstants.KEY_FARMER_VILLAGE_ID, farmerRegistrationRequestDto.getDpcVillageDto().getId());
            if (farmerRegistrationRequestDto.getDpcTalukDto() != null)
                values.put(DbConstants.KEY_FARMER_TALUK_ID, farmerRegistrationRequestDto.getDpcTalukDto().getId());
            if (farmerRegistrationRequestDto.getDpcDistrictDto() != null)
                values.put(DbConstants.KEY_FARMER_DISTRICT_ID, farmerRegistrationRequestDto.getDpcDistrictDto().getId());
            values.put(DbConstants.KEY_FARMER_PINCODE, farmerRegistrationRequestDto.getPincode());
            values.put(DbConstants.KEY_FARMER_COMMUNITY_ID, farmerRegistrationRequestDto.getFarmerClassDto().getId());
            values.put(DbConstants.KEY_FARMER_AADHAR_NUMBER, farmerRegistrationRequestDto.getAadhaarNumber());
            if (farmerRegistrationRequestDto.getBankDto() != null)
                values.put(DbConstants.KEY_FARMER_BANK_ID, farmerRegistrationRequestDto.getBankDto().getId());
            values.put(DbConstants.KEY_FARMER_BRANCH_NAME, farmerRegistrationRequestDto.getBranchName());
            values.put(DbConstants.KEY_FARMER_ACCOUNT_NUMBER, farmerRegistrationRequestDto.getAccountNumber());
            values.put(DbConstants.KEY_FARMER_IFSC_CODE, farmerRegistrationRequestDto.getIfscCode());
            values.put(DbConstants.KEY_FARMER_DPC_ID, farmerRegistrationRequestDto.getDpcProfileDto().getId());
            values.put(DbConstants.KEY_IS_SERVER_ADDED, "R");
            values.put(DbConstants.KEY_FARMER_RATION_CARD_NUMBER, farmerRegistrationRequestDto.getRationCardNumber());
//            values.put(DbConstants.KEY_FARMER_LOAN_BOOK_NUMBER, farmerRegistrationRequestDto.getLoanAccountNumber());
            for (FarmerLandDetailsDto farmerLandDetailsDto : farmerRegistrationRequestDto.getFarmerLandDetailsDtoList()) {
                try {
                    Log.e("insert", "insert FarmerLandDetailsDto");
                    ContentValues values1 = new ContentValues();
                    Log.e("farmerLandDetailsDto", "" + farmerLandDetailsDto.getLandTypeDto());
                    values1.put(DbConstants.KEY_LAND_TYPE_ID, farmerLandDetailsDto.getLandTypeDto().getId());
                    values1.put(DbConstants.KEY_LAND_VILLAGE_ID, farmerLandDetailsDto.getDpcVillageDto().getId());
                    values1.put(DbConstants.KEY_LAND_TALUK_ID, farmerLandDetailsDto.getDpcTalukDto().getId());
                    values1.put(DbConstants.KEY_LAND_DISTRICT_ID, farmerLandDetailsDto.getDpcDistrictDto().getId());
                    values1.put(DbConstants.KEY_LAND_FARMER_ID, farmerRegistrationRequestDto.getRequestedReferenceNumber());// need to change
                    values1.put(DbConstants.KEY_LAND_MAIN_LORD_NAME, farmerLandDetailsDto.getMainLandLordName());
                    values1.put(DbConstants.KEY_FARMER_CROP_NAME_ID, farmerLandDetailsDto.getPaddyCategoryDto().getId());
                    values1.put(DbConstants.KEY_LAND_SURVEY_NUMBER, farmerLandDetailsDto.getSurveyNumber());
                    values1.put(DbConstants.KEY_LAND_LOAN_BOOK_NUMBER, farmerLandDetailsDto.getLoanBookNumber());
                    values1.put(DbConstants.KEY_FARMER_AREA, farmerLandDetailsDto.getArea());
                    values1.put(DbConstants.KEY_LAND_SEED_ACCUMULATED_KG, farmerLandDetailsDto.getSowedAccumulated());
                    values1.put(DbConstants.KEY_LAND_SEED_NON_ACCUMULATED_KG, farmerLandDetailsDto.getSowedNonAccumulated());
                    values1.put(DbConstants.KEY_LAND_SEED_ACCUMULATED_QUINTAL, farmerLandDetailsDto.getExpectedAccumulated());
                    values1.put(DbConstants.KEY_LAND_SEED_NON_ACCUMULATED_QUINTAL, farmerLandDetailsDto.getExpectedNonAccumulated());
                    values1.put(DbConstants.KEY_LAND_SEED_SHOWED_TOTAL_KG, farmerLandDetailsDto.getSowedTotal());
                    values1.put(DbConstants.KEY_LAND_SEED_EXCEPTED_TOTAL_QUINTAL, farmerLandDetailsDto.getExpectedTotal());
                    values1.put(DbConstants.KEY_LAND_PATTA_NUMBER, farmerLandDetailsDto.getPattaNumber());
                    values1.put(DbConstants.KEY_LAND_PROCUREMENT_EXPECTED_DATE, farmerLandDetailsDto.getExpectedProcuringDate());
                    database.insert(DBTables.TABLE_LAND_DETAILS, null, values1);
                } catch (Exception e) {
                    Log.e("exception", "exception");
                    e.printStackTrace();
                }
            }
            Log.e("before", "insertdb in helper");
            database.insert(DBTables.TABLE_FARMER, null, values);
            Log.e("after", "insertdb in helper");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean InsertFirstsyncFarmerRegistrationDetails(Set<FarmerRegistrationRequestDto> farmerRegistrationRequestDto, String syncType) {
        Log.e("insert method", "insert");
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        FarmerRegistrationRequestDto fr_reg_dto = null;
        if (!farmerRegistrationRequestDto.isEmpty()) {
            database.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (FarmerRegistrationRequestDto reg_farmer : farmerRegistrationRequestDto) {
//                    Log.e("FarmerRegistration","-------------------------"+reg_farmer);
                    recordId = reg_farmer.getId();
                    fr_reg_dto = reg_farmer;
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(reg_farmer.getId()));
                    values.put(DbConstants.KEY_REFERENCE_NUMBER, String.valueOf(reg_farmer.getRequestedReferenceNumber()));
                    values.put(DbConstants.KEY_FARMER_NAME, String.valueOf(reg_farmer.getFarmerName()));
                    values.put(DbConstants.KEY_FARMER_LOCAL_NAME, String.valueOf(reg_farmer.getFarmerLname()));
                    values.put(DbConstants.KEY_GUARDIAN_TYPE, String.valueOf(reg_farmer.getGuardian()));
                    values.put(DbConstants.KEY_FARMER_FATHER_HUSBAND_NAME, String.valueOf(reg_farmer.getGuardianName()));
                    values.put(DbConstants.KEY_FARMER_FATHER_HUSBAND_NAME_LOCAL, String.valueOf(reg_farmer.getGuardianLname()));
                    values.put(DbConstants.KEY_DATE_OF_BIRTH, String.valueOf(reg_farmer.getDateOfBirth()));
                    values.put(DbConstants.KEY_FARMER_MOBILE_NUMBER, String.valueOf(reg_farmer.getMobileNumber()));
                    values.put(DbConstants.KEY_FARMER_ADDRESS1, String.valueOf(reg_farmer.getAddress1()));
                    values.put(DbConstants.KEY_FARMER_ADDRESS2, String.valueOf(reg_farmer.getAddress2()));
                    values.put(DbConstants.KEY_FARMER_VILLAGE_ID, String.valueOf(reg_farmer.getDpcVillageDto().getId()));
                    values.put(DbConstants.KEY_FARMER_TALUK_ID, String.valueOf(reg_farmer.getDpcTalukDto().getId()));
                    values.put(DbConstants.KEY_FARMER_DISTRICT_ID, String.valueOf(reg_farmer.getDpcDistrictDto().getId()));
                    values.put(DbConstants.KEY_FARMER_PINCODE, String.valueOf(reg_farmer.getPincode()));
//                    values.put(DbConstants.KEY_FARMER_COMMUNITY_ID, String.valueOf(reg_farmer.getFarmerClassDto().getId()));
                    values.put(DbConstants.KEY_FARMER_AADHAR_NUMBER, String.valueOf(reg_farmer.getAadhaarNumber()));
                    values.put(DbConstants.KEY_FARMER_BANK_ID, String.valueOf(reg_farmer.getBankDto().getId()));
                    values.put(DbConstants.KEY_FARMER_BRANCH_NAME, String.valueOf(reg_farmer.getBranchName()));
                    values.put(DbConstants.KEY_FARMER_ACCOUNT_NUMBER, String.valueOf(reg_farmer.getAccountNumber()));
                    values.put(DbConstants.KEY_FARMER_IFSC_CODE, String.valueOf(reg_farmer.getIfscCode()));
                    values.put(DbConstants.KEY_FARMER_DPC_ID, String.valueOf(reg_farmer.getDpcProfileDto().getId()));
                    values.put(DbConstants.KEY_FARMER_RATION_CARD_NUMBER, String.valueOf(reg_farmer.getRationCardNumber()));
                    values.put(DbConstants.KEY_IS_SERVER_ADDED, String.valueOf("T"));
                    database.insertWithOnConflict(DBTables.TABLE_FARMER, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(fr_reg_dto);
                    insertSyncException("TABLE_FARMER_REG_DETAIL", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }

    public boolean InsertFirstsyncLandDetails(Set<FarmerLandDetailsDto> landDetails_dto, String syncType) {
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        FarmerLandDetailsDto fm_lnd_dto = null;
        long pdt = 0;
        if (!landDetails_dto.isEmpty()) {
            database.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (FarmerLandDetailsDto farmer_land_details : landDetails_dto) {
                    recordId = farmer_land_details.getId();
                    fm_lnd_dto = farmer_land_details;
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(farmer_land_details.getId()));
                    values.put(DbConstants.KEY_LAND_TYPE_ID, String.valueOf(farmer_land_details.getLandTypeDto().getId()));
                    values.put(DbConstants.KEY_LAND_VILLAGE_ID, String.valueOf(farmer_land_details.getDpcVillageDto().getId()));
                    values.put(DbConstants.KEY_LAND_TALUK_ID, String.valueOf(farmer_land_details.getDpcTalukDto().getId()));
                    values.put(DbConstants.KEY_LAND_DISTRICT_ID, String.valueOf(farmer_land_details.getDpcDistrictDto().getId()));
                    values.put(DbConstants.KEY_LAND_FARMER_ID, String.valueOf(farmer_land_details.getFarmerRegistrationRequestDto().getRequestedReferenceNumber()));
                    values.put(DbConstants.KEY_LAND_MAIN_LORD_NAME, String.valueOf(farmer_land_details.getMainLandLordName()));
                    values.put(DbConstants.KEY_FARMER_CROP_NAME_ID, String.valueOf(farmer_land_details.getPaddyCategoryDto().getId()));
                    values.put(DbConstants.KEY_LAND_SURVEY_NUMBER, String.valueOf(farmer_land_details.getSurveyNumber()));
                    values.put(DbConstants.KEY_LAND_LOAN_BOOK_NUMBER, String.valueOf(farmer_land_details.getLoanBookNumber()));
                    values.put(DbConstants.KEY_FARMER_AREA, String.valueOf(farmer_land_details.getArea()));
                    values.put(DbConstants.KEY_LAND_SEED_ACCUMULATED_KG, String.valueOf(farmer_land_details.getSowedAccumulated()));
                    values.put(DbConstants.KEY_LAND_SEED_NON_ACCUMULATED_KG, String.valueOf(farmer_land_details.getSowedNonAccumulated()));
                    values.put(DbConstants.KEY_LAND_SEED_ACCUMULATED_QUINTAL, String.valueOf(farmer_land_details.getExpectedAccumulated()));
                    values.put(DbConstants.KEY_LAND_SEED_NON_ACCUMULATED_QUINTAL, String.valueOf(farmer_land_details.getExpectedNonAccumulated()));
                    values.put(DbConstants.KEY_LAND_SEED_SHOWED_TOTAL_KG, String.valueOf(farmer_land_details.getSowedTotal()));
                    values.put(DbConstants.KEY_LAND_SEED_EXCEPTED_TOTAL_QUINTAL, String.valueOf(farmer_land_details.getExpectedTotal()));
                    values.put(DbConstants.KEY_LAND_PATTA_NUMBER, String.valueOf(farmer_land_details.getPattaNumber()));
                    if (farmer_land_details.getExpectedProcuringDate() != null) {
                        pdt = Long.parseLong(farmer_land_details.getExpectedProcuringDate());
                    }
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    String date = dateFormat.format(pdt);
                    values.put(DbConstants.KEY_LAND_PROCUREMENT_EXPECTED_DATE, String.valueOf(date));
                    database.insertWithOnConflict(DBTables.TABLE_LAND_DETAILS, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(fm_lnd_dto);
                    insertSyncException("TABLE_FARMER_LAND_DETAIL", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }

    //insert in to associated farmers list
    public boolean InsertAssociatedFarmerDetails(Set<FarmerRegistrationDto> farmerRegistrationDto, String syncType) {
        Log.e("insert method", "insert");
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        FarmerRegistrationDto fr_reg_dto = null;
        if (!farmerRegistrationDto.isEmpty()) {
            database.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (FarmerRegistrationDto associated_farmer : farmerRegistrationDto) {
//                    Log.e("FarmerRegistration","-------------------------"+reg_farmer);
                    recordId = associated_farmer.getId();
                    fr_reg_dto = associated_farmer;
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(associated_farmer.getId()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_AADHAAR_NUMBER, String.valueOf(associated_farmer.getAadhaarNumber()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_ACCOUNT_NUMBER, String.valueOf(associated_farmer.getAccountNumber()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_ADDRESS_1, String.valueOf(associated_farmer.getAddress1()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_ADDRESS_2, String.valueOf(associated_farmer.getAddress2()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_BRANCH_NAME, String.valueOf(associated_farmer.getBranchName()));
                    values.put(DbConstants.KEY_CREATED_BY, String.valueOf(associated_farmer.getCreatedBy()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(associated_farmer.getCreatedDate()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_CODE, String.valueOf(associated_farmer.getFarmerCode()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_REGIONAL_NAME, String.valueOf(associated_farmer.getFarmerLname()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_NAME, String.valueOf(associated_farmer.getFarmerName()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_GUARDIAN_TYPE, String.valueOf(associated_farmer.getGuardian()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_GUARDIAN_REGIONAL_NAME, String.valueOf(associated_farmer.getGuardianLname()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_GUARDIAN_NAME, String.valueOf(associated_farmer.getGuardianName()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_IFSC_CODE, String.valueOf(associated_farmer.getIfscCode()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_MOBILE_NUMBER, String.valueOf(associated_farmer.getMobileNumber()));
                    values.put(DbConstants.KEY_MODIFIED_BY, String.valueOf(associated_farmer.getModifiedBy()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(associated_farmer.getModifiedDate()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_PIN_CODE, String.valueOf(associated_farmer.getPinCode()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_BANK_ID, String.valueOf(associated_farmer.getBankDto().getId()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_DPC_DISTRICT_ID, String.valueOf(associated_farmer.getDpcDistrictDto().getId()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_DPC_PROFILE, String.valueOf(associated_farmer.getDpcProfileDto().getId()));
                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_DPC_TALUK_ID, String.valueOf(associated_farmer.getDpcTalukDto().getId()));

                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_DPC_VILLAGE_ID, String.valueOf(associated_farmer.getDpcVillageDto().getId()));

                    values.put(DbConstants.KEY_ASSOCIATED_FARMER_RATION_CARD_NUMBER, String.valueOf(associated_farmer.getRationCardNumber()));
                    database.insertWithOnConflict(DBTables.TABLE_ASSOCIATED_FARMERS, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(fr_reg_dto);
                    insertSyncException("TABLE_DPC_FARMER_REGISTRATION", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }

    //insert into associated land details list
    public boolean InsertAssociatedFarmerLandDetails(Set<FarmerApprovedLandDetailsDto> approved_landDetails_dto, String syncType) {
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        FarmerApprovedLandDetailsDto fm_lnd_dto = null;
        if (!approved_landDetails_dto.isEmpty()) {
            database.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (FarmerApprovedLandDetailsDto approved_land_details : approved_landDetails_dto) {
                    recordId = approved_land_details.getId();
                    fm_lnd_dto = approved_land_details;
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(approved_land_details.getId()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_AREA, String.valueOf(approved_land_details.getArea()));
                    values.put(DbConstants.KEY_CREATED_BY, String.valueOf(approved_land_details.getCreatedBy()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(approved_land_details.getCreatedDate()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_EXPECTED_ACCUMULATED, String.valueOf(approved_land_details.getExpectedAccumulated()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_EXPECTED_NON_ACCUMULATED, String.valueOf(approved_land_details.getExpectedNonAccumulated()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_EXPECTED_PROCURING_DATE, String.valueOf(approved_land_details.getExpectedProcuringDate()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_EXPECTED_TOTAL, String.valueOf(approved_land_details.getExpectedTotal()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_MAIN_LOARD_REGIONAL_NAME, String.valueOf(approved_land_details.getMainLandLordLname()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_MAIN_LOARD_NAME, String.valueOf(approved_land_details.getMainLandLordName()));
                    values.put(DbConstants.KEY_MODIFIED_BY, String.valueOf(approved_land_details.getModifiedBy()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(approved_land_details.getModifiedDate()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_PATTA_NUMBER, String.valueOf(approved_land_details.getPattaNumber()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_SHOWED_ACCUMULATED, String.valueOf(approved_land_details.getSowedAccumulated()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_SHOWED_NON_ACCUMULATED, String.valueOf(approved_land_details.getSowedNonAccumulated()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_SHOWED_TOTAL, String.valueOf(approved_land_details.getSowedTotal()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_SURVEY_NUMBER, String.valueOf(approved_land_details.getSurveyNumber()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_DPC_DISTRICT_ID, String.valueOf(approved_land_details.getDpcDistrictDto().getId()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_FARMER_REGISTRATION_ID, String.valueOf(approved_land_details.getFarmerRegistrationDto().getId()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_LANDTYPE_ID, String.valueOf(approved_land_details.getLandTypeDto().getId()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_PADYCATEGORY_ID, String.valueOf(approved_land_details.getPaddyCategoryDto().getId()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_DPC_TALUK_ID, String.valueOf(approved_land_details.getDpcTalukDto().getId()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_DPC_VILLAGE_ID, String.valueOf(approved_land_details.getDpcVillageDto().getId()));
                    values.put(DbConstants.KEY_ASSOCIATED_LAND_SHOWED_TOTAL, String.valueOf(approved_land_details.getSowedTotal()));
                    database.insertWithOnConflict(DBTables.TABLE_ASSOCIATED_LAND_DETAILS, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(fm_lnd_dto);
                    insertSyncException("TABLE_FARMER_LAND_DETAIL", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }

    public boolean InsertDpcRateDetails(Set<DpcPaddyRateDto> dpc_rate_dto, String syncType) {
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        DpcPaddyRateDto rt_dto = null;
        if (!dpc_rate_dto.isEmpty()) {
            database.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (DpcPaddyRateDto rate : dpc_rate_dto) {
                    recordId = rate.getId();
                    rt_dto = rate;
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(rate.getId()));
                    values.put(DbConstants.KEY_BONUS_RATE, String.valueOf(rate.getBonusRate()));
                    values.put(DbConstants.KEY_CREATED_BY_ID, String.valueOf(rate.getCreatedById()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(rate.getCreatedDate()));
                    values.put(DbConstants.KEY_MODIFIED_BY_ID, String.valueOf(rate.getModifiedById()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(rate.getModifiedDate()));
                    values.put(DbConstants.KEY_PURCHASE_RATE, String.valueOf(rate.getPurchaseRate()));
                    values.put(DbConstants.KEY_STATUS, returnInteger(rate.isStatus()));
                    values.put(DbConstants.KEY_TOTAL_RATE, String.valueOf(rate.getTotalRate()));
                    values.put(DbConstants.KEY_VALIDITY_DATE_FROM, String.valueOf(rate.getValidityDateFrom()));
                    values.put(DbConstants.KEY_VALIDITY_DATE_TO, String.valueOf(rate.getValidityDateTo()));
                    values.put(DbConstants.KEY_RATE_DPC_PADDY_GRADE_ID, String.valueOf(rate.getPaddyGradeDto().getId()));
                    database.insertWithOnConflict(DBTables.TABLE_DPC_RATE, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(rt_dto);
                    insertSyncException("TABLE_DPC_PADDY_RATE", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }

    public boolean InsertDpcProcurementDetails(Set<DPCProcurementDto> dpc_procurement_dto, String syncType) {
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        DPCProcurementDto pro_dto = null;
        if (!dpc_procurement_dto.isEmpty()) {
            database.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (DPCProcurementDto procurement : dpc_procurement_dto) {
                    recordId = procurement.getId();
                    pro_dto = procurement;
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(procurement.getId()));
                    values.put(DbConstants.KEY_PROCUREMENT_ADDITIONAL_CUT, String.valueOf(procurement.getAdditionalCut()));
                    values.put(DbConstants.KEY_CREATED_BY, String.valueOf(procurement.getCreatedBy()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(procurement.getCreatedDate()));
                    values.put(DbConstants.KEY_PROCUREMENT_GRADE_CUT, String.valueOf(procurement.getGradeCut()));
                    values.put(DbConstants.KEY_PROCUREMENT_LOT_NUMBER, String.valueOf(procurement.getLotNumber()));
                    values.put(DbConstants.KEY_MODIFIED_BY, String.valueOf(procurement.getModifiedBy()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(procurement.getModifiedDate()));
                    values.put(DbConstants.KEY_PROCUREMENT_MOISTURE_CONTENT, String.valueOf(procurement.getMoistureContent()));
                    values.put(DbConstants.KEY_PROCUREMENT_MOISTURE_CUT, String.valueOf(procurement.getMoistureCut()));
                    values.put(DbConstants.KEY_PROCUREMENT_NET_AMOUNT, String.valueOf(procurement.getNetAmount()));
                    values.put(DbConstants.KEY_PROCUREMENT_NET_WEIGHT, String.valueOf(procurement.getNetWeight()));
                    values.put(DbConstants.KEY_PROCUREMENT_NUMBER_OF_BAGS, String.valueOf(procurement.getNumberOfBags()));
                    values.put(DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER, String.valueOf(procurement.getProcurementReceiptNo()));
                    values.put(DbConstants.KEY_PROCUREMENT_SPILLAGE_QTY, String.valueOf(procurement.getSpillageQuantity()));
                    values.put(DbConstants.KEY_PROCUREMENT_TOTAL_AMOUNT, String.valueOf(procurement.getTotalAmount()));
                    values.put(DbConstants.KEY_PROCUREMENT_TOTAL_CUT, String.valueOf(procurement.getTotalCut()));
                    values.put(DbConstants.KEY_PROCUREMENT_TOTAL_CUT_AMOUNT, String.valueOf(procurement.getTotalCutAmount()));
                    values.put(DbConstants.KEY_PROCUREMENT_DEVICE_ID, String.valueOf(procurement.getDeviceDto().getId()));
                    values.put(DbConstants.KEY_PROCUREMENT_PADDY_RATE_ID, String.valueOf(procurement.getDpcPaddyRateDto().getId()));
                    values.put(DbConstants.KEY_PROCUREMENT_PROFILE_ID, String.valueOf(procurement.getDpcProfileDto().getId()));
                    values.put(DbConstants.KEY_PROCUREMENT_FARMER_REG_ID, String.valueOf(procurement.getFarmerRegistrationDto().getId()));
                    values.put(DbConstants.KEY_PROCUREMENT_PADDY_CATEGORY_ID, String.valueOf(procurement.getPaddyCategoryDto().getId()));
                    values.put(DbConstants.KEY_PROCUREMENT_PADDY_GRADE_ID, String.valueOf(procurement.getPaddyGradeDto().getId()));
                    values.put(DbConstants.KEY_PROCUREMENT_SERVER_BILL_NO, String.valueOf(procurement.getServerProcurementBillNo()));
                    values.put(DbConstants.KEY_PROCUREMENT_SYNC_STATUS, String.valueOf(procurement.getSyncStatus()));
                    values.put(DbConstants.KEY_PROCUREMENT_MODE, String.valueOf(procurement.getMode()));
                    values.put(DbConstants.KEY_PROCUREMENT_SMS_REFERENCE_NUMBER, String.valueOf(procurement.getSmsRefNumber()));
                    values.put(DbConstants.KEY_PROCUREMENT_LAST_SYNC_TIME, String.valueOf(procurement.getLastSyncTime()));
                    values.put(DbConstants.KEY_PROCUREMENT_TRANSACTION_DATE, String.valueOf(procurement.getTxnDateTime()));
                    values.put(DbConstants.KEY_PROCUREMENT_SPECIFICATION_ID, String.valueOf(procurement.getDpcSpecificationDto().getId()));
                    database.insertWithOnConflict(DBTables.TABLE_DPC_PROCUREMENT, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(pro_dto);
                    insertSyncException("TABLE_DPC_PROCUREMENT", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }

    public boolean InsertDpcCapDetails(Set<DpcCapDto> dpcCapDtos, String syncType) {
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        DpcCapDto cap_dto = null;
        if (!dpcCapDtos.isEmpty()) {
            database.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (DpcCapDto dpcCapDto : dpcCapDtos) {
                    recordId = dpcCapDto.getId();
                    cap_dto = dpcCapDto;
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(dpcCapDto.getId()));
                    values.put(DbConstants.KEY_GOWNDOWN_ADDRESS, String.valueOf(dpcCapDto.getAddress()));
                    values.put(DbConstants.KEY_GOWNDOWN_CONTACT_NUMBER, String.valueOf(dpcCapDto.getContactNum()));
                    values.put(DbConstants.KEY_CREATED_BY, String.valueOf(dpcCapDto.getCreatedby()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(dpcCapDto.getCreatedDate()));
                    values.put(DbConstants.KEY_GOWNDOWN_LATITUDE, String.valueOf(dpcCapDto.getLatitude()));
                    values.put(DbConstants.KEY_GOWNDOWN_LONGITIUDE, String.valueOf(dpcCapDto.getLongitude()));
                    values.put(DbConstants.KEY_MODIFIED_BY, String.valueOf(dpcCapDto.getModifiedby()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(dpcCapDto.getModifiedDate()));
                    values.put(DbConstants.KEY_GOWNDOWN_NAME, String.valueOf(dpcCapDto.getName()));
                    values.put(DbConstants.KEY_STATUS, returnInteger(dpcCapDto.isStatus()));
                    values.put(DbConstants.KEY_GOWNDOWN_DISTRICT_ID, String.valueOf(dpcCapDto.getDpcDistrictDto().getId()));
                    values.put(DbConstants.KEY_GOWNDOWN_TALUK_ID, String.valueOf(dpcCapDto.getDpcTalukDto().getId()));
                    values.put(DbConstants.KEY_GOWNDOWN_CONTACT_PERSON_NAME, String.valueOf(dpcCapDto.getContactPerson()));
                    values.put(DbConstants.KEY_GOWNDOWN_EMAIL_ADDRESS, String.valueOf(dpcCapDto.getEmailAddress()));
                    values.put(DbConstants.KEY_GOWNDOWN_GENERATED_CAP_CODE, String.valueOf(dpcCapDto.getGeneratedCode()));
                    values.put(DbConstants.KEY_GOWNDOWN_GEOFENCING, returnInteger(dpcCapDto.isGeofencing()));
                    values.put(DbConstants.KEY_GOWNDOWN_MOISTURE_METER, String.valueOf(dpcCapDto.getMoistureMeter()));
                    values.put(DbConstants.KEY_GOWNDOWN_NUMBER_PEOPLE, String.valueOf(dpcCapDto.getNoofPeople()));
                    values.put(DbConstants.KEY_GOWNDOWN_PINCODE, String.valueOf(dpcCapDto.getPinCode()));
                    values.put(DbConstants.KEY_GOWNDOWN_PLATFORM_SCALE, String.valueOf(dpcCapDto.getPlatformScales()));
                    values.put(DbConstants.KEY_GOWNDOWN_REMOTE_LOG, returnInteger(dpcCapDto.isRemoteLogEnabled()));
                    values.put(DbConstants.KEY_GOWNDOWN_VERSION, String.valueOf(dpcCapDto.getVersion()));
                    values.put(DbConstants.KEY_GOWNDOWN_WEIGHING_MACHINE, String.valueOf(dpcCapDto.getWeighingCapacity()));
                    values.put(DbConstants.KEY_GOWNDOWN_WINNOWING_MACHINE, String.valueOf(dpcCapDto.getWinnowingMachine()));
                    if (dpcCapDto.getDeviceDto() != null) {
                        values.put(DbConstants.KEY_GOWNDOWN_DEVICE_ID, String.valueOf(dpcCapDto.getDeviceDto().getId()));
                    }
                    values.put(DbConstants.KEY_GOWNDOWN_DPC_VILLAGE_ID, String.valueOf(dpcCapDto.getDpcVillageDto().getId()));
                    values.put(DbConstants.KEY_GOWNDOWN_CAP_REGIONAL_NAME, String.valueOf(dpcCapDto.getLname()));
                    values.put(DbConstants.KEY_GOWNDOWN_CAP_CATEGORY, String.valueOf(dpcCapDto.getCapCategory()));
                    values.put(DbConstants.KEY_GOWNDOWN_CAP_TYPE, String.valueOf(dpcCapDto.getCapType()));
                    values.put(DbConstants.KEY_GOWNDOWN_STORAGE_CAPACITY, String.valueOf(dpcCapDto.getStorageCapacity()));
                    database.insertWithOnConflict(DBTables.TABLE_DPC_CAB, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(cap_dto);
                    insertSyncException("TABLE_DPC_CAP", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }

    public boolean InsertDPcTruckMemo(Set<DpcTruckMemoDto> dpcTruckMemoDtos, String syncType) {
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        DpcTruckMemoDto truck_memo_dto = null;
        if (!dpcTruckMemoDtos.isEmpty()) {
            database.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (DpcTruckMemoDto dpcTruckMemoDto : dpcTruckMemoDtos) {
                    recordId = dpcTruckMemoDto.getId();
                    truck_memo_dto = dpcTruckMemoDto;
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(dpcTruckMemoDto.getId()));
                    values.put(DbConstants.KEY_TRUCKMEMO_CONDITION_OF_GUNNY, String.valueOf(dpcTruckMemoDto.getConditionOfGunny()));
                    values.put(DbConstants.KEY_TRUCKMEMO_GUNNY_CAPACITY, String.valueOf(dpcTruckMemoDto.getGunnyCapacity()));
                    values.put(DbConstants.KEY_TRUCKMEMO_LORRY_NUMBR, String.valueOf(dpcTruckMemoDto.getLorryNumber()));
                    values.put(DbConstants.KEY_TRUCKMEMO_MOISTURE_CONTENT, String.valueOf(dpcTruckMemoDto.getMoistureContent()));
                    values.put(DbConstants.KEY_TRUCKMEMO_NET_QUANTITY, String.valueOf(dpcTruckMemoDto.getNetQuantity()));
                    values.put(DbConstants.KEY_TRUCKMEMO_NUMBER_OF_BAGS, String.valueOf(dpcTruckMemoDto.getNumberOfBags()));
                    values.put(DbConstants.KEY_TRUCKMEMO_TRANSPORT_TYPE, String.valueOf(dpcTruckMemoDto.getTransportType()));
                    values.put(DbConstants.KEY_TRUCKMEMO_TRANSPORTER_NAME, String.valueOf(dpcTruckMemoDto.getTransporterName()));
                    values.put(DbConstants.KEY_TRUCKMEMO_TRUCK_MEMO_NUMBER, String.valueOf(dpcTruckMemoDto.getTruckMemoNumber()));
                    values.put(DbConstants.KEY_TRUCKMEMO_DPC_CAP_ID, String.valueOf(dpcTruckMemoDto.getDpcCapDto().getId()));
                    values.put(DbConstants.KEY_TRUCKMEMO_PADDY_CATEGORY_ID, String.valueOf(dpcTruckMemoDto.getPaddyCategoryDto().getId()));
                    values.put(DbConstants.KEY_TRUCKMEMO_GRADE_ID, String.valueOf(dpcTruckMemoDto.getPaddyGradeDto().getId()));
                    values.put(DbConstants.KEY_CREATED_BY, String.valueOf(dpcTruckMemoDto.getCreatedBy()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(dpcTruckMemoDto.getCreatedDate()));
                    values.put(DbConstants.KEY_MODIFIED_BY, String.valueOf(dpcTruckMemoDto.getModifiedBy()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(dpcTruckMemoDto.getModifiedDate()));
                    values.put(DbConstants.KEY_TRUCKMEMO_SERVER_TRUCK_MEMO_NO, String.valueOf(dpcTruckMemoDto.getServerTruckMemoNo()));
                    values.put(DbConstants.KEY_TRUCKMEMO_PROFILE_ID, String.valueOf(dpcTruckMemoDto.getDpcProfileDto().getId()));
                    values.put(DbConstants.KEY_TRUCKMEMO_SYNC_STATUS, String.valueOf(dpcTruckMemoDto.getSyncStatus()));
                    values.put(DbConstants.KEY_TRUCKMEMO_TXN_DATE_TIME, String.valueOf(dpcTruckMemoDto.getTxnDateTime()));
                    values.put(DbConstants.KEY_TRUCKMEMO_SPECIFICATION_ID, String.valueOf(dpcTruckMemoDto.getDpcSpecificationDto().getId()));
//                    if (dpcTruckMemoDto.getDpcSpecificationDto().getId() != null) {
//                        values.put(DbConstants.KEY_TRUCKMEMO_SPECIFICATION_ID, String.valueOf(dpcTruckMemoDto.getDpcSpecificationDto().getId()));
//                    }
                    database.insertWithOnConflict(DBTables.TABLE_DPC_TRUCK_MEMO, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(truck_memo_dto);
                    insertSyncException("TABLE_DPC_TRUCK_MEMO", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }

    public boolean insertSpecification(Set<DpcSpecificationDto> dpcSpecificationDto, String syncType) {
        boolean isSuccessFullyInserted = false;
        Long recordId = null;
        DpcSpecificationDto speci_dto = null;
        if (!dpcSpecificationDto.isEmpty()) {
            database.beginTransaction();
            try {
                ContentValues values = new ContentValues();
                for (DpcSpecificationDto specification : dpcSpecificationDto) {
                    recordId = specification.getId();
                    speci_dto = specification;
                    values.put(DbConstants.KEY_ID_SERVER, String.valueOf(specification.getId()));
                    values.put(DbConstants.KEY_SPECIFICATION_START_DATE, String.valueOf(specification.getStartDate()));
                    values.put(DbConstants.KEY_SPECIFICATION_END_DATE, String.valueOf(specification.getEndDate()));
                    values.put(DbConstants.KEY_SPECIFICATION_MOISTURE_FROM, String.valueOf(specification.getMoisturePercentageFrom()));
                    values.put(DbConstants.KEY_SPECIFICATION_MOISTURE_TO, String.valueOf(specification.getMoisturePercentageTo()));
                    values.put(DbConstants.KEY_SPECIFICATION_TYPE, String.valueOf(specification.getSpecificationType()));
                    values.put(DbConstants.KEY_SPECIFICATION_CODE, String.valueOf(specification.getSpecificationCode()));
                    values.put(DbConstants.KEY_CREATED_BY, String.valueOf(specification.getCreatedBy()));
                    values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(specification.getCreatedDate()));
                    values.put(DbConstants.KEY_MODIFIED_BY, String.valueOf(specification.getModifiedBy()));
                    values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(specification.getModifiedDate()));
                    values.put(DbConstants.KEY_STATUS, returnInteger(specification.isStatus()));
                    database.insertWithOnConflict(DBTables.TABLE_DPC_SPECIFICATION, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                isSuccessFullyInserted = true;
                database.setTransactionSuccessful();
            } catch (Exception e) {
                isSuccessFullyInserted = false;
                e.printStackTrace();
            } finally {
                database.endTransaction();
                if (!isSuccessFullyInserted) {
                    String json = new Gson().toJson(speci_dto);
                    insertSyncException("TABLE_DPC_SPECIFICATION", syncType, recordId, json);
                }
            }
        }
        return isSuccessFullyInserted;
    }

    public void InsertDPcTruckMemo(DpcTruckMemoDto dpcTruckMemoDtos) {
        try {
            ContentValues values = new ContentValues();
            values.put(DbConstants.KEY_TRUCKMEMO_CONDITION_OF_GUNNY, String.valueOf(dpcTruckMemoDtos.getConditionOfGunny()));
            values.put(DbConstants.KEY_TRUCKMEMO_GUNNY_CAPACITY, String.valueOf(dpcTruckMemoDtos.getGunnyCapacity()));
            values.put(DbConstants.KEY_TRUCKMEMO_LORRY_NUMBR, String.valueOf(dpcTruckMemoDtos.getLorryNumber()));
            values.put(DbConstants.KEY_TRUCKMEMO_MOISTURE_CONTENT, String.valueOf(dpcTruckMemoDtos.getMoistureContent()));
            values.put(DbConstants.KEY_TRUCKMEMO_NET_QUANTITY, String.valueOf(dpcTruckMemoDtos.getNetQuantity()));
            values.put(DbConstants.KEY_TRUCKMEMO_NUMBER_OF_BAGS, String.valueOf(dpcTruckMemoDtos.getNumberOfBags()));
            values.put(DbConstants.KEY_TRUCKMEMO_TRANSPORT_TYPE, String.valueOf(dpcTruckMemoDtos.getTransportType()));
            values.put(DbConstants.KEY_TRUCKMEMO_TRANSPORTER_NAME, String.valueOf(dpcTruckMemoDtos.getTransporterName()));
            values.put(DbConstants.KEY_TRUCKMEMO_TRUCK_MEMO_NUMBER, String.valueOf(dpcTruckMemoDtos.getTruckMemoNumber()));
            values.put(DbConstants.KEY_TRUCKMEMO_DPC_CAP_ID, String.valueOf(dpcTruckMemoDtos.getDpcCapDto().getId()));
            values.put(DbConstants.KEY_TRUCKMEMO_PADDY_CATEGORY_ID, String.valueOf(dpcTruckMemoDtos.getPaddyCategoryDto().getId()));
            values.put(DbConstants.KEY_TRUCKMEMO_GRADE_ID, String.valueOf(dpcTruckMemoDtos.getPaddyGradeDto().getId()));
            values.put(DbConstants.KEY_CREATED_BY, String.valueOf(dpcTruckMemoDtos.getCreatedBy()));
            values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(dpcTruckMemoDtos.getCreatedDate()));
            values.put(DbConstants.KEY_MODIFIED_BY, String.valueOf(dpcTruckMemoDtos.getModifiedBy()));
            values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(dpcTruckMemoDtos.getCreatedDate()));
            values.put(DbConstants.KEY_TRUCKMEMO_TXN_DATE_TIME, String.valueOf(dpcTruckMemoDtos.getTxnDateTime()));
            values.put(DbConstants.KEY_TRUCKMEMO_SERVER_TRUCK_MEMO_NO, String.valueOf(dpcTruckMemoDtos.getServerTruckMemoNo()));
            values.put(DbConstants.KEY_TRUCKMEMO_PROFILE_ID, String.valueOf(dpcTruckMemoDtos.getDpcProfileDto().getId()));
            values.put(DbConstants.KEY_TRUCKMEMO_SYNC_STATUS, String.valueOf(dpcTruckMemoDtos.getSyncStatus()));
            values.put(DbConstants.KEY_TRUCKMEMO_SPECIFICATION_ID, String.valueOf(dpcTruckMemoDtos.getDpcSpecificationDto().getId()));
            database.insert(DBTables.TABLE_DPC_TRUCK_MEMO, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /*public void insertLoginHistory(LoginHistoryDto loginHistory) {
        try {
            ContentValues values = new ContentValues();
            values.put("login_time", loginHistory.getLoginTime());
            values.put("login_type", loginHistory.getLoginType());
            values.put("user_id", loginHistory.getUserId());
            values.put("fps_id", loginHistory.getFpsId());
            values.put("transaction_id", loginHistory.getTransactionId());
            values.put("created_time", new Date().getTime());
            values.put("is_sync", 0);
            if (!database.isOpen()) {
                database = dbHelper.getWritableDatabase();
            }
            database.insert(FPSDBTables.TABLE_LOGIN_HISTORY, null, values);
        } catch (Exception e) {
            Log.e("Login History", e.toString(), e);
        }

    }
*/

    public List<DpcTruckMemoDto> GetUnsynedTruckMemo() {
        List<DpcTruckMemoDto> dpcTruckMemoList = new ArrayList<>();
        try {
            String selectQuery = "SELECT  * FROM " + DBTables.TABLE_DPC_TRUCK_MEMO + " where " + DbConstants.KEY_TRUCKMEMO_SYNC_STATUS + " = 'A'";
            Log.e("THE QUERY IS", "" + selectQuery);
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    DpcTruckMemoDto dpcTruckMemoDto = new DpcTruckMemoDto();
                    dpcTruckMemoDto.setConditionOfGunny(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_CONDITION_OF_GUNNY)));
                    dpcTruckMemoDto.setGunnyCapacity(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_GUNNY_CAPACITY)));
                    dpcTruckMemoDto.setNetQuantity(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_NET_QUANTITY)));
                    dpcTruckMemoDto.setLorryNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_LORRY_NUMBR)));
                    dpcTruckMemoDto.setMoistureContent(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_MOISTURE_CONTENT)));
                    dpcTruckMemoDto.setNumberOfBags(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_NUMBER_OF_BAGS)));
                    dpcTruckMemoDto.setTransporterName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TRANSPORTER_NAME)));
                    dpcTruckMemoDto.setTransportType(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TRANSPORT_TYPE)));
                    dpcTruckMemoDto.setTruckMemoNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TRUCK_MEMO_NUMBER)));
                    PaddyCategoryDto paddyCategoryDto = new PaddyCategoryDto();
                    paddyCategoryDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_PADDY_CATEGORY_ID)));
                    dpcTruckMemoDto.setPaddyCategoryDto(paddyCategoryDto);
                    PaddyGradeDto paddyGradeDto = new PaddyGradeDto();
                    paddyGradeDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_GRADE_ID)));
                    dpcTruckMemoDto.setPaddyGradeDto(paddyGradeDto);
                    DpcCapDto dpcCapDto = new DpcCapDto();
                    dpcCapDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_DPC_CAP_ID)));
                    dpcTruckMemoDto.setDpcCapDto(dpcCapDto);
                    DPCProfileDto profile_dto = new DPCProfileDto();
                    profile_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_PROFILE_ID)));
                    dpcTruckMemoDto.setDpcProfileDto(profile_dto);
                    DeviceDto device_dto = new DeviceDto();
                    device_dto.setDeviceNumber(Util.device_number);
                    dpcTruckMemoDto.setDeviceDto(device_dto);
                    DpcSpecificationDto spec = new DpcSpecificationDto();
                    spec.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_SPECIFICATION_ID)));
                    dpcTruckMemoDto.setDpcSpecificationDto(spec);
                    dpcTruckMemoDto.setTxnDateTime(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TXN_DATE_TIME)));
                    dpcTruckMemoDto.setSyncStatus(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_SYNC_STATUS)));
                    dpcTruckMemoDto.setCreatedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_BY)));
                    dpcTruckMemoDto.setModifiedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_BY)));
                    dpcTruckMemoList.add(dpcTruckMemoDto);
                } while (cursor.moveToNext());
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpcTruckMemoList;
    }

    public List<DpcTruckMemoDto> getTruckMemoPaddy(long paddyId) {
        List<DpcTruckMemoDto> dpcTruckMemoList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_DPC_TRUCK_MEMO + " where " + DbConstants.KEY_TRUCKMEMO_PADDY_CATEGORY_ID + " = " + paddyId + " ORDER BY " + DbConstants.KEY_TRUCKMEMO_TRUCK_MEMO_NUMBER + " DESC ";
        Log.e("THE QUERY IS", "" + selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DpcTruckMemoDto dpcTruckMemoDto = new DpcTruckMemoDto();
                dpcTruckMemoDto.setConditionOfGunny(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_CONDITION_OF_GUNNY)));
                dpcTruckMemoDto.setGunnyCapacity(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_GUNNY_CAPACITY)));
                dpcTruckMemoDto.setNetQuantity(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_NET_QUANTITY)));
                dpcTruckMemoDto.setLorryNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_LORRY_NUMBR)));
                dpcTruckMemoDto.setMoistureContent(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_MOISTURE_CONTENT)));
                dpcTruckMemoDto.setNumberOfBags(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_NUMBER_OF_BAGS)));
                dpcTruckMemoDto.setTransporterName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TRANSPORTER_NAME)));
                dpcTruckMemoDto.setTransportType(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TRANSPORT_TYPE)));
                dpcTruckMemoDto.setTruckMemoNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TRUCK_MEMO_NUMBER)));
                PaddyCategoryDto paddyCategoryDto = new PaddyCategoryDto();
                paddyCategoryDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_PADDY_CATEGORY_ID)));
                dpcTruckMemoDto.setPaddyCategoryDto(paddyCategoryDto);
                PaddyGradeDto paddyGradeDto = new PaddyGradeDto();
                paddyGradeDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_GRADE_ID)));
                dpcTruckMemoDto.setPaddyGradeDto(paddyGradeDto);
                DpcCapDto dpcCapDto = new DpcCapDto();
                dpcCapDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_DPC_CAP_ID)));
//                dpcCapDto.setGeneratedCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_GOWNDOWN_GENERATED_CAP_CODE)));
//                dpcCapDto.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_GOWNDOWN_NAME)));
                dpcTruckMemoDto.setDpcCapDto(dpcCapDto);
                DPCProfileDto profile_dto = new DPCProfileDto();
                profile_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_PROFILE_ID)));
                dpcTruckMemoDto.setDpcProfileDto(profile_dto);
                DeviceDto device_dto = new DeviceDto();
                device_dto.setDeviceNumber(Util.device_number);
                dpcTruckMemoDto.setDeviceDto(device_dto);
                DpcSpecificationDto spec = new DpcSpecificationDto();
                spec.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_SPECIFICATION_ID)));
                dpcTruckMemoDto.setDpcSpecificationDto(spec);
                dpcTruckMemoDto.setTxnDateTime(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TXN_DATE_TIME)));
                dpcTruckMemoDto.setSyncStatus(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_SYNC_STATUS)));
                dpcTruckMemoDto.setCreatedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_BY)));
                dpcTruckMemoDto.setModifiedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_BY)));
                dpcTruckMemoList.add(dpcTruckMemoDto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dpcTruckMemoList;
    }

    public DpcTruckMemoDto getTruckMemoByNumber(String number) {
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_DPC_TRUCK_MEMO + " where " + DbConstants.KEY_TRUCKMEMO_TRUCK_MEMO_NUMBER + " = '" + number + "'";
        Log.e("THE QUERY IS", "" + selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        DpcTruckMemoDto dpcTruckMemoDto = null;
        if (cursor.moveToFirst()) {
            do {
                dpcTruckMemoDto = new DpcTruckMemoDto();
                dpcTruckMemoDto.setConditionOfGunny(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_CONDITION_OF_GUNNY)));
                dpcTruckMemoDto.setGunnyCapacity(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_GUNNY_CAPACITY)));
                dpcTruckMemoDto.setNetQuantity(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_NET_QUANTITY)));
                dpcTruckMemoDto.setLorryNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_LORRY_NUMBR)));
                dpcTruckMemoDto.setMoistureContent(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_MOISTURE_CONTENT)));
                dpcTruckMemoDto.setNumberOfBags(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_NUMBER_OF_BAGS)));
                dpcTruckMemoDto.setTransporterName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TRANSPORTER_NAME)));
                dpcTruckMemoDto.setTransportType(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TRANSPORT_TYPE)));
                dpcTruckMemoDto.setTruckMemoNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TRUCK_MEMO_NUMBER)));
                PaddyCategoryDto paddyCategoryDto = new PaddyCategoryDto();
                paddyCategoryDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_PADDY_CATEGORY_ID)));
                dpcTruckMemoDto.setPaddyCategoryDto(paddyCategoryDto);
                PaddyGradeDto paddyGradeDto = new PaddyGradeDto();
                paddyGradeDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_GRADE_ID)));
                dpcTruckMemoDto.setPaddyGradeDto(paddyGradeDto);
                DpcCapDto dpcCapDto = new DpcCapDto();
                dpcCapDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_DPC_CAP_ID)));
//                dpcCapDto.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_GOWNDOWN_NAME)));
                dpcTruckMemoDto.setDpcCapDto(dpcCapDto);
                DPCProfileDto profile_dto = new DPCProfileDto();
                profile_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_PROFILE_ID)));
                dpcTruckMemoDto.setDpcProfileDto(profile_dto);
                DeviceDto device_dto = new DeviceDto();
                device_dto.setDeviceNumber(Util.device_number);
                dpcTruckMemoDto.setDeviceDto(device_dto);
                DpcSpecificationDto spec = new DpcSpecificationDto();
                spec.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_SPECIFICATION_ID)));
                dpcTruckMemoDto.setDpcSpecificationDto(spec);
                dpcTruckMemoDto.setTxnDateTime(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TXN_DATE_TIME)));
                dpcTruckMemoDto.setSyncStatus(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_SYNC_STATUS)));
                dpcTruckMemoDto.setCreatedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_BY)));
                dpcTruckMemoDto.setModifiedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_BY)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dpcTruckMemoDto;
    }

    public List<DpcTruckMemoDto> getTruckMemoByDate(String date) {
        List<DpcTruckMemoDto> dpcTruckMemoList = new ArrayList<>();
        /*SELECT * FROM dpc_truck_memo where date(tnx_date_time) = '2016-10-05'*/
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_DPC_TRUCK_MEMO + " where date(" + DbConstants.KEY_TRUCKMEMO_TXN_DATE_TIME + ")  = '" + date + "' ORDER BY " + DbConstants.KEY_TRUCKMEMO_TRUCK_MEMO_NUMBER + " DESC ";
        Log.e("THE QUERY IS", "" + selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DpcTruckMemoDto dpcTruckMemoDto = new DpcTruckMemoDto();
                dpcTruckMemoDto.setConditionOfGunny(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_CONDITION_OF_GUNNY)));
                dpcTruckMemoDto.setGunnyCapacity(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_GUNNY_CAPACITY)));
                dpcTruckMemoDto.setNetQuantity(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_NET_QUANTITY)));
                dpcTruckMemoDto.setLorryNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_LORRY_NUMBR)));
                dpcTruckMemoDto.setMoistureContent(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_MOISTURE_CONTENT)));
                dpcTruckMemoDto.setNumberOfBags(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_NUMBER_OF_BAGS)));
                dpcTruckMemoDto.setTransporterName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TRANSPORTER_NAME)));
                dpcTruckMemoDto.setTransportType(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TRANSPORT_TYPE)));
                dpcTruckMemoDto.setTruckMemoNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TRUCK_MEMO_NUMBER)));
                PaddyCategoryDto paddyCategoryDto = new PaddyCategoryDto();
                paddyCategoryDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_PADDY_CATEGORY_ID)));
                dpcTruckMemoDto.setPaddyCategoryDto(paddyCategoryDto);
                PaddyGradeDto paddyGradeDto = new PaddyGradeDto();
                paddyGradeDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_GRADE_ID)));
                dpcTruckMemoDto.setPaddyGradeDto(paddyGradeDto);
                DpcCapDto dpcCapDto = new DpcCapDto();
                dpcCapDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_DPC_CAP_ID)));
//                dpcCapDto.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_GOWNDOWN_NAME)));
                dpcTruckMemoDto.setDpcCapDto(dpcCapDto);
                DPCProfileDto profile_dto = new DPCProfileDto();
                profile_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_PROFILE_ID)));
                dpcTruckMemoDto.setDpcProfileDto(profile_dto);
                DeviceDto device_dto = new DeviceDto();
                device_dto.setDeviceNumber(Util.device_number);
                dpcTruckMemoDto.setDeviceDto(device_dto);
                DpcSpecificationDto spec = new DpcSpecificationDto();
                spec.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_SPECIFICATION_ID)));
                dpcTruckMemoDto.setDpcSpecificationDto(spec);
                dpcTruckMemoDto.setTxnDateTime(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TXN_DATE_TIME)));
                dpcTruckMemoDto.setSyncStatus(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_SYNC_STATUS)));
                dpcTruckMemoDto.setCreatedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_BY)));
                dpcTruckMemoDto.setModifiedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_BY)));
                dpcTruckMemoList.add(dpcTruckMemoDto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dpcTruckMemoList;
    }

    public List<DpcTruckMemoDto> getTruckMemoByCapCode(Long capCode) {
        List<DpcTruckMemoDto> dpcTruckMemoList = new ArrayList<>();
        /*SELECT * FROM dpc_truck_memo where dpc_cap_id = 3*/
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_DPC_TRUCK_MEMO + " where " + DbConstants.KEY_TRUCKMEMO_DPC_CAP_ID + "  = " + capCode + " ORDER BY " + DbConstants.KEY_TRUCKMEMO_TRUCK_MEMO_NUMBER + " DESC ";
        Log.e("THE QUERY IS", "" + selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DpcTruckMemoDto dpcTruckMemoDto = new DpcTruckMemoDto();
                dpcTruckMemoDto.setConditionOfGunny(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_CONDITION_OF_GUNNY)));
                dpcTruckMemoDto.setGunnyCapacity(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_GUNNY_CAPACITY)));
                dpcTruckMemoDto.setNetQuantity(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_NET_QUANTITY)));
                dpcTruckMemoDto.setLorryNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_LORRY_NUMBR)));
                dpcTruckMemoDto.setMoistureContent(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_MOISTURE_CONTENT)));
                dpcTruckMemoDto.setNumberOfBags(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_NUMBER_OF_BAGS)));
                dpcTruckMemoDto.setTransporterName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TRANSPORTER_NAME)));
                dpcTruckMemoDto.setTransportType(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TRANSPORT_TYPE)));
                dpcTruckMemoDto.setTruckMemoNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TRUCK_MEMO_NUMBER)));
                PaddyCategoryDto paddyCategoryDto = new PaddyCategoryDto();
                paddyCategoryDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_PADDY_CATEGORY_ID)));
                dpcTruckMemoDto.setPaddyCategoryDto(paddyCategoryDto);
                PaddyGradeDto paddyGradeDto = new PaddyGradeDto();
                paddyGradeDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_GRADE_ID)));
                dpcTruckMemoDto.setPaddyGradeDto(paddyGradeDto);
                DpcCapDto dpcCapDto = new DpcCapDto();
                dpcCapDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_DPC_CAP_ID)));
//                dpcCapDto.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_GOWNDOWN_NAME)));
                dpcTruckMemoDto.setDpcCapDto(dpcCapDto);
                DPCProfileDto profile_dto = new DPCProfileDto();
                profile_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_PROFILE_ID)));
                dpcTruckMemoDto.setDpcProfileDto(profile_dto);
                DeviceDto device_dto = new DeviceDto();
                device_dto.setDeviceNumber(Util.device_number);
                dpcTruckMemoDto.setDeviceDto(device_dto);
                DpcSpecificationDto spec = new DpcSpecificationDto();
                spec.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_SPECIFICATION_ID)));
                dpcTruckMemoDto.setDpcSpecificationDto(spec);
                dpcTruckMemoDto.setTxnDateTime(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TXN_DATE_TIME)));
                dpcTruckMemoDto.setSyncStatus(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_SYNC_STATUS)));
                dpcTruckMemoDto.setCreatedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_BY)));
                dpcTruckMemoDto.setModifiedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_BY)));
                dpcTruckMemoList.add(dpcTruckMemoDto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dpcTruckMemoList;
    }

    public String lastRegistrationToday(String dpcid) {
        try {
            String maxDate = "";
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMyy", Locale.getDefault());
            String selectQuery = "SELECT reference_number FROM dpc_farmer_registration_request where reference_number LIKE '" + dpcid + dateFormat.format(new Date()) + "%' order by registered_date desc";
            Log.e("DBhelper", "selected query for last Registration " + selectQuery);
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                maxDate = cursor.getString(cursor.getColumnIndex("reference_number"));
            }
            cursor.close();
            return maxDate;
        } catch (Exception e) {
            Log.e("last Registration Today", e.toString(), e);
            return null;
        }
    }

    public String lastRegistrationNumber() {
        try {
            String referenecNumber = "";
            String selectQuery = "SELECT reference_number FROM dpc_farmer_registration_request order by  reference_number  desc limit 1";
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                referenecNumber = cursor.getString(cursor.getColumnIndex("reference_number"));
            }
            cursor.close();
            return referenecNumber;
        } catch (Exception e) {
            return null;
        }
    }

    public String lastReceiptNumber() {
        try {
            String receipt_number = "";
            String selectQuery = "SELECT " + DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER + " FROM " + DBTables.TABLE_DPC_PROCUREMENT + " order by " + DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER + " desc limit 1";
            Log.e("THE QUETY IS", "" + selectQuery);
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                receipt_number = cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER));
            }
            cursor.close();
            return receipt_number;
        } catch (Exception e) {
            return null;
        }
    }

    public String lastReceiptNumber_() {
        try {
            String receipt_number = "";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM", Locale.getDefault());
            String selectQuery = "SELECT " + DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER + " FROM " + DBTables.TABLE_DPC_PROCUREMENT + " where " + DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER + " LIKE '" + dateFormat.format(new Date()) + "%' order by _id desc";
            Log.e("The query", "selectQuery query " + selectQuery);
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                receipt_number = cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER));
            }
            cursor.close();
            return receipt_number;
        } catch (Exception e) {
            return null;
        }
    }

    public String lastTruckMemo() {
        try {
            String truckmemo_number = "";
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM", Locale.getDefault());
            String selectQuery = "SELECT truck_memo_number FROM dpc_truck_memo where truck_memo_number LIKE '" + dateFormat.format(new Date()) + "%' order by _id desc";
            Log.e("The query", "selectQuery query " + selectQuery);
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                truckmemo_number = cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TRUCKMEMO_TRUCK_MEMO_NUMBER));
            }
            cursor.close();
            return truckmemo_number;
        } catch (Exception e) {
            return null;
        }
    }

    public List<FarmerListLocalDto> getFarmerList() {
        /*SELECT  *,(select count(b.id)  from dpc_farmer_land_details as b where b.farmer_id = a.reference_number ) as landcount from dpc_farmer_registration_request as a*/

        /*OLD QURY----"SELECT * from " + DBTables.TABLE_FARMER + " as a, " + DBTables.TABLE_LAND_DETAILS + " as b where a.reference_number = b.farmer_id group by a._id order by reference_number "*/
        String selectQuery = "SELECT  *,(select count(*)  from dpc_farmer_land_details as b where b.farmer_id = a.reference_number ) as landcount from dpc_farmer_registration_request as a ORDER BY a.reference_number DESC";
        Log.e("THE FARMER QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                FarmerListLocalDto list = new FarmerListLocalDto();
                list.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                list.setFarmer_name(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_NAME)));
                list.setReference_number(cursor.getString(cursor.getColumnIndex("reference_number")));
                list.setMobileNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_MOBILE_NUMBER)));
//                list.setId_crop(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_CROP_NAME_ID)));
//                list.setArea(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_AREA)));
//                list.setLandType(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_LAND_TYPE_ID)));
                list.setAadhar(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_AADHAR_NUMBER)));
//                list.setAcc(cursor.getDouble(cursor.getColumnIndex("expected_procurement_accumulated_quintal")));
//                list.setNonacc(cursor.getDouble(cursor.getColumnIndex("expected_procurement_non_accumulated_quintal")));
                list.setRationCardNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_RATION_CARD_NUMBER)));
                list.setBank_id(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_BANK_ID)));
                list.setLandCount(cursor.getString(cursor.getColumnIndex("landcount")));
                farmer_list.add(list);
            } while (cursor.moveToNext());
        }
        return farmer_list;
    }

    public List<AssosiatedFarmersListLocalDto> getAllAssociatedFarmerList() {
         /*SELECT *  from dpc_farmer_registration  as a, dpc_farmer_approved_land_details as b where  a.id =  b.farmer_registration_id group by a.id*/
        String selectQuery = "SELECT  *,(select count(b.id)  from dpc_farmer_approved_land_details as b where b.farmer_registration_id = a.id) as landcount from dpc_farmer_registration  as a ORDER BY a.id DESC";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                AssosiatedFarmersListLocalDto associated_farmers_dto = new AssosiatedFarmersListLocalDto();
                associated_farmers_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                associated_farmers_dto.setFarmerCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_CODE)));
                associated_farmers_dto.setFarmerName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_NAME)));
                associated_farmers_dto.setFarmerLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_REGIONAL_NAME)));
                associated_farmers_dto.setMobileNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_MOBILE_NUMBER)));
                associated_farmers_dto.setBranchName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_BRANCH_NAME)));
                associated_farmers_dto.setBank_id(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_BANK_ID)));
                associated_farmers_dto.setLandCount(cursor.getString(cursor.getColumnIndex("landcount")));
                associated_farmers_dto.setAadhaarNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_AADHAAR_NUMBER)));
                associated_farmers_dto.setRationCardNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_RATION_CARD_NUMBER)));
                associated_farmer_list.add(associated_farmers_dto);
            } while (cursor.moveToNext());
        }
        return associated_farmer_list;
    }

    public List<FarmerListLocalDto> getFarmerListbyFarmerName(String farmerName, String mobile, String referencenumber) {
//        List<FarmerListLocalDto> search_list = new ArrayList<FarmerListLocalDto>();
        farmer_list.clear();
        Log.e("farmerName", farmerName);
        Log.e("mobile", mobile);
        Log.e("referencenumber", referencenumber);
        StringBuilder stbuilder = new StringBuilder();
        String selectQuery = "SELECT * from " + DBTables.TABLE_FARMER + " as a, " + DBTables.TABLE_LAND_DETAILS + " as b where a.reference_number = b.farmer_id";
        stbuilder.append(selectQuery);
        // boolean isFarmer,isMobile,isReference;
        try {
            if (farmerName.length() > 0) {
                Log.e("INSIDE FARMER ", "inside -----------");
                stbuilder.append(" and a.farmer_name = '" + farmerName + "'");
            }
            if (mobile.length() > 0) {
                Log.e("INSIDE Mobile", "inside -----------");
                stbuilder.append(" and a.mobile_number = '" + mobile + "'");
            }
            if (referencenumber.length() > 0) {
                Log.e("INSIDE REF NUM", "inside -----------");
                stbuilder.append(" and a.reference_number = '" + referencenumber + "'");
            }
            Log.e("THE QUERY IS farmer :", stbuilder.toString());
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(stbuilder.toString() + " group by a._id", null);
            if (cursor.moveToFirst()) {
                do {
                    FarmerListLocalDto list = new FarmerListLocalDto();
                    list.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                    list.setFarmer_name(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_NAME)));
                    list.setReference_number(cursor.getString(cursor.getColumnIndex("reference_number")));
                    list.setMobileNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_MOBILE_NUMBER)));
                    list.setId_crop(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_CROP_NAME_ID)));
                    list.setArea(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_AREA)));
                    list.setLandType(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_LAND_TYPE_ID)));
                    list.setAadhar(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_AADHAR_NUMBER)));
                    list.setAcc(cursor.getDouble(cursor.getColumnIndex("expected_procurement_accumulated_quintal")));
                    list.setNonacc(cursor.getDouble(cursor.getColumnIndex("expected_procurement_non_accumulated_quintal")));
                    farmer_list.add(list);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return farmer_list;
    }

    public List<AssosiatedFarmersListLocalDto> getSearchedAssociatedFarmerList(String farmerName, String mobile, String farmer_id) {
        associated_farmer_list.clear();
        Log.e("ASSOfarmerName", "--------" + farmerName);
        Log.e("ASSOmobile", "---------" + mobile);
        Log.e("ASSOreferencenumber", "---------" + farmer_id);
        StringBuilder stbuilder = new StringBuilder();
        String selectQuery = "SELECT *,(select count(b.id) from dpc_farmer_approved_land_details as b where b.farmer_registration_id = a.id) as landcount  from " + DBTables.TABLE_ASSOCIATED_FARMERS + " as a where ";
        stbuilder.append(selectQuery);
        try {
            String text = "";
            if (farmerName.length() > 0) {
                Log.e("ASSOINSIDE FARMER ", "inside -----------");
                stbuilder.append(" a.farmer_name = '" + farmerName + "'");
                text = " and ";
            }
            if (mobile.length() > 0) {
                Log.e("ASSOINSIDE Mobile", "inside -----------");
                stbuilder.append(text + " a.mobile_number = '" + mobile + "'");
                text = " and ";
            }
            if (farmer_id.length() > 0) {
                Log.e("ASSOINSIDE REF NUM", "inside -----------");
                stbuilder.append(text + " a.farmer_code = '" + farmer_id + "'");
            }
            Log.e("THE QUERY IS farmer :", stbuilder.toString());
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(stbuilder.toString() + " group by a.id", null);
            if (cursor.moveToFirst()) {
                do {
                    AssosiatedFarmersListLocalDto list = new AssosiatedFarmersListLocalDto();
                    list.setFarmerName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_NAME)));
                    list.setFarmerCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_CODE)));
                    list.setMobileNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_MOBILE_NUMBER)));
                    list.setBranchName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_BRANCH_NAME)));
                    list.setBank_id(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_BANK_ID)));
                    list.setAadhaarNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_AADHAAR_NUMBER)));
                    list.setRationCardNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_RATION_CARD_NUMBER)));
                    list.setLandCount(cursor.getString(cursor.getColumnIndex("landcount")));
                    associated_farmer_list.add(list);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return associated_farmer_list;
    }

    public String getlandType(long id) {
        String land = "";
        String selectQuery = " SELECT  * FROM " + DBTables.TABLE_LAND_TYPE + " where " + DbConstants.KEY_ID_SERVER + " = " + id;
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                land = cursor.getString(cursor.getColumnIndex(DbConstants.KEY_LAND_TYPE));
            } while (cursor.moveToNext());
        }
        return land;
    }

    public String getpaddy(long id) {
        String paddy = "";
        String selectQuery = " SELECT * FROM " + DBTables.TABLE_PADDY_CATEGORY + " where " + DbConstants.KEY_ID_SERVER + " = " + id;
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                if (GlobalAppState.language.equalsIgnoreCase("ta"))
                    paddy = cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PADDY_CATEGORY_REGIONAL_NAME));
                else
                    paddy = cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PADDY_CATEGORY_NAME));
            } while (cursor.moveToNext());
        }
        return paddy;
    }

    public String getSpecificationById(long id) {
        String specification = "";
        String selectQuery = " SELECT * FROM " + DBTables.TABLE_DPC_SPECIFICATION + " where " + DbConstants.KEY_ID_SERVER + " = " + id;
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                specification = cursor.getString(cursor.getColumnIndex(DbConstants.KEY_SPECIFICATION_TYPE));
            } while (cursor.moveToNext());
        }
        return specification;
    }

    public int getTotalFarmer() {
        int tot = 0;
        String selectQuery = " SELECT count(*) as farmer_count FROM dpc_farmer_registration_request";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                tot = cursor.getInt(cursor.getColumnIndex("farmer_count"));
            } while (cursor.moveToNext());
        }
        return tot;
    }

    public int getTotalAssociatedFarmer() {
        int tot = 0;
        String selectQuery = "SELECT count(*) as farmer_count FROM dpc_farmer_registration";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                tot = cursor.getInt(cursor.getColumnIndex("farmer_count"));
            } while (cursor.moveToNext());
        }
        return tot;
    }

    public void RemoveDuplicateNumber(String reference_number) {
        try {
            Log.e("reference_number delete", "-------------------------------" + reference_number);
            database.execSQL("DELETE FROM dpc_farmer_registration_request WHERE reference_number='" + reference_number + "'  ");
            database.execSQL("DELETE FROM dpc_farmer_land_details WHERE farmer_id='" + reference_number + "'  ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void RemoveDuplicateReceiptNumber(String receipt_number) {
        try {
            Log.e("reference_number delete", "-------------------------------" + receipt_number);
            database.execSQL("DELETE FROM " + DBTables.CREATE_TABLE_DPC_PROCUREMENT + " WHERE " + DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER + "='" + receipt_number + "' ");
//            database.execSQL("DELETE FROM dpc_farmer_land_details WHERE farmer_id='" + receipt_number + "'  ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void RemoveDuplicateAadhaarNumber(String aadhaar_number) {
        try {
            database.execSQL("DELETE FROM dpc_farmer_registration_request WHERE aadhaar_number='" + aadhaar_number + "'  ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean chechMobileNumberRequest(String mobile_number) {
        String mob_number = null;
        boolean mob = true;
        String selectQuery = "SELECT " + DbConstants.KEY_FARMER_MOBILE_NUMBER + " from " + DBTables.TABLE_FARMER + " where " + DbConstants.KEY_FARMER_MOBILE_NUMBER + " = '" + mobile_number + "'";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                mob_number = cursor.getString(cursor.getColumnIndex("mobile_number"));
                if (mob_number != null || mob_number.equalsIgnoreCase(""))
                    mob = false;
                else {
                    mob = true;
                }
            } while (cursor.moveToNext());
        }
        return mob;
    }

    public boolean chechMobileNumber(String mobile_number) {
        String mob_number = null;
        boolean mob = true;
        String selectQuery = "SELECT " + DbConstants.KEY_ASSOCIATED_FARMER_MOBILE_NUMBER + " from " + DBTables.TABLE_ASSOCIATED_FARMERS + " where " + DbConstants.KEY_ASSOCIATED_FARMER_MOBILE_NUMBER + " = '" + mobile_number + "'";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                mob_number = cursor.getString(cursor.getColumnIndex("mobile_number"));
                if (mob_number != null || mob_number.equalsIgnoreCase(""))
                    mob = false;
                else {
                    mob = true;
                }
            } while (cursor.moveToNext());
        }
        return mob;
    }

   /* public boolean CheckMobileAlreadyExist(String mobileNumber) {
        String selectQuery = "SELECT * from table_farmer where mobile_number = '" + mobileNumber + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }*/

    public boolean CheckAadharNumberAlreadyExist(String aadharNumber) {
        String selectQuery = "SELECT aadhaar_number from  " + DBTables.TABLE_FARMER + " where  aadhaar_number = '" + aadharNumber + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean CheckAadharNumberAlreadyExistinRegistration(String aadharNumber) {
        String selectQuery = "SELECT " + DbConstants.KEY_ASSOCIATED_FARMER_AADHAAR_NUMBER + " from " + DBTables.TABLE_ASSOCIATED_FARMERS + " where " + DbConstants.KEY_ASSOCIATED_FARMER_AADHAAR_NUMBER + " ='" + aadharNumber + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean checkaccountNumberRequeat(String account_number) {
        String selectQuery = "SELECT " + DbConstants.KEY_FARMER_ACCOUNT_NUMBER + " from " + DBTables.TABLE_FARMER + " where " + DbConstants.KEY_FARMER_ACCOUNT_NUMBER + " = '" + account_number + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean checkProcurementNumber(String number) {
        String selectQuery = "SELECT " + DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER + " from " + DBTables.TABLE_DPC_PROCUREMENT + " where " + DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER + " = '" + number + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean checkProcurementFarmerCode(long id) {
        String selectQuery = "SELECT * from " + DBTables.TABLE_DPC_PROCUREMENT + " where " + DbConstants.KEY_PROCUREMENT_FARMER_REG_ID + " = " + id;
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.e("QUERY", selectQuery);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean checkProcurementPaddy(long id) {
        String selectQuery = "SELECT " + DbConstants.KEY_PROCUREMENT_PADDY_CATEGORY_ID + " from " + DBTables.TABLE_DPC_PROCUREMENT + " where " + DbConstants.KEY_PROCUREMENT_PADDY_CATEGORY_ID + " = " + id;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean checkProcurementDate(String date) {
        String selectQuery = "SELECT  date(" + DbConstants.KEY_PROCUREMENT_TRANSACTION_DATE + ") from " + DBTables.TABLE_DPC_PROCUREMENT + " where date(" + DbConstants.KEY_PROCUREMENT_TRANSACTION_DATE + ") ='" + date + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.e("checkProcurementDate", selectQuery);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean checkTruckmemoByPaddy(long paddyId) {
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_DPC_TRUCK_MEMO + " where " + DbConstants.KEY_TRUCKMEMO_PADDY_CATEGORY_ID + " = " + paddyId;
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean checkTruckmemoByNumber(String number) {
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_DPC_TRUCK_MEMO + " where " + DbConstants.KEY_TRUCKMEMO_TRUCK_MEMO_NUMBER + " = '" + number + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean checkTruckmemoByDate(String date) {
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_DPC_TRUCK_MEMO + " where date(" + DbConstants.KEY_TRUCKMEMO_TXN_DATE_TIME + ")  = '" + date + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.e("checkProcurementDate", selectQuery);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean checkTruckmemoByCapcode(Long capCode) {
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_DPC_TRUCK_MEMO + " where " + DbConstants.KEY_TRUCKMEMO_DPC_CAP_ID + "  = " + capCode;
        Cursor cursor = database.rawQuery(selectQuery, null);
        Log.e("checkProcurementDate", selectQuery);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean checkaccountNumberRegistration(String account_number) {
        String selectQuery = "SELECT " + DbConstants.KEY_ASSOCIATED_FARMER_ACCOUNT_NUMBER + " from " + DBTables.TABLE_ASSOCIATED_FARMERS + " where " + DbConstants.KEY_ASSOCIATED_FARMER_ACCOUNT_NUMBER + " = '" + account_number + "'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public void UpdateRegistrationNumber(String reference_number) {
        try {
            ContentValues values = new ContentValues();
            Log.e("updateRegistration", reference_number);
            values.put("is_server_added", "T");
            database.update(DBTables.TABLE_FARMER, values, "reference_number = '" + reference_number + "'", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public void UpdateTruckMemo(String truckmemoNumber) {
//        try {
//            ContentValues values = new ContentValues();
//            Log.e("updateRegistration", truckmemoNumber);
//            values.put("is_sync", "T");
//            database.update(DBTables.TABLE_DPC_TRUCK_MEMO, values, "is_sync = '" + truckmemoNumber + "'", null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void UpdateTruckMemoNumber(String receiptNumber) {
        try {
            ContentValues values = new ContentValues();
            Log.e("updateRegistration", receiptNumber);
            values.put(DbConstants.KEY_TRUCKMEMO_SYNC_STATUS, "B");
            database.update(DBTables.TABLE_DPC_TRUCK_MEMO, values, DbConstants.KEY_TRUCKMEMO_TRUCK_MEMO_NUMBER + " = '" + receiptNumber + "'", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void UpdateProcurementReceiptNumber(String receiptNumber) {
        try {
            ContentValues values = new ContentValues();
            Log.e("updateRegistration", receiptNumber);
            values.put(DbConstants.KEY_PROCUREMENT_SYNC_STATUS, "B");
            database.update(DBTables.TABLE_DPC_PROCUREMENT, values, DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER + " = '" + receiptNumber + "'", null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertSyncException(String tableName, String syncType, Long recordId, String rawData) {
        try {
            Log.e("Siva insertException", "insertSyncException");
            /*--------------------------------------------------------------------*/
            DPCSyncExceptionDto dpcSyncExceptionDto = new DPCSyncExceptionDto();
            dpcSyncExceptionDto.setSyncMode(syncType);
            dpcSyncExceptionDto.setDpcProfileId(LoginData.getInstance().getResponseData().getDpcProfileDto().getId());
            Log.e("siva device", "" + Util.device_number);
            dpcSyncExceptionDto.setDeviceNumber(Util.device_number);
            dpcSyncExceptionDto.setTableName(tableName);
            dpcSyncExceptionDto.setAction("INSERT");
            dpcSyncExceptionDto.setRecordId(recordId);
            dpcSyncExceptionDto.setRawData(rawData);
            dpcSyncExceptionDto.setSyncErrorDescription("Exception while inserting " + tableName);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
            String dateStr = df.format(new Date());
            Date currentDate = df.parse(dateStr);
            dpcSyncExceptionDto.setLastSyncTime(currentDate.getTime());
            DBHelper.getInstance(contextValue).insertSyncExcData(dpcSyncExceptionDto);
        } catch (Exception e1) {
            e1.printStackTrace();
//            Util.LoggingQueue(contextValue, "^^^^FPSDBHelper^^^^ ", "" + "Exception while inserting "+tableName+" : "+e1);
        }
    }

    //This function inserts sync exc data
    public void insertSyncExcData(DPCSyncExceptionDto dpcSyncExceptionDto) {
        try {
            Log.e("siva insert db", "db");
//            com.omneagate.Utility.Util.LoggingQueue(contextValue, "insert syncException...", dpcSyncExceptionDto.toString());
            ContentValues values = new ContentValues();
//            com.omneagate.Util.Util.LoggingQueue(contextValue, "fpsId", ""+dpcSyncExceptionDto.getFpsId());
            values.put("dpcId", String.valueOf(dpcSyncExceptionDto.getDpcProfileId()));
//            Utility.Util.LoggingQueue(contextValue, "syncMode", ""+dpcSyncExceptionDto.getSyncMode());
            values.put("syncMode", dpcSyncExceptionDto.getSyncMode());
//            com.omneagate.Util.Util.LoggingQueue(contextValue, "tableName", ""+dpcSyncExceptionDto.getTableName());
            values.put("tableName", dpcSyncExceptionDto.getTableName());
//            com.omneagate.Util.Util.LoggingQueue(contextValue, "action", ""+dpcSyncExceptionDto.getAction());
            values.put("action", dpcSyncExceptionDto.getAction());
//            com.omneagate.Util.Util.LoggingQueue(contextValue, "recordId", ""+dpcSyncExceptionDto.getRecordId());
            values.put("recordId", "" + dpcSyncExceptionDto.getRecordId());
//            com.omneagate.Util.Util.LoggingQueue(contextValue, "lastSyncTime", ""+dpcSyncExceptionDto.getLastSyncTime());
            values.put("lastSyncTime", "" + dpcSyncExceptionDto.getLastSyncTime());
//            com.omneagate.Util.Util.LoggingQueue(contextValue, "rawData", ""+dpcSyncExceptionDto.getRawData());
            values.put("rawData", dpcSyncExceptionDto.getRawData());
//            com.omneagate.Util.Util.LoggingQueue(contextValue, "errorDescription", ""+dpcSyncExceptionDto.getErrorDescription());
            values.put("errorDescription", dpcSyncExceptionDto.getSyncErrorDescription());
//            com.omneagate.Util.Util.LoggingQueue(contextValue, "isSynced", "0");
            values.put("isSynced", "0");
            values.put("device_id", dpcSyncExceptionDto.getDeviceNumber());
            long returnedValue = database.insert("syncException", null, values);
//            com.omneagate.Util.Util.LoggingQueue(contextValue, "returnedValue", ""+returnedValue);
        } catch (Exception e) {
            e.printStackTrace();
//            com.omneagate.Util.Util.LoggingQueue(contextValue, "insert syncException Exception...", e.toString());
        }
    }

    public List<DPCSyncExceptionDto> getAllSyncExcData() {
        List<DPCSyncExceptionDto> dpcSyncExceptionDtoList = new ArrayList<>();
        String selectQuery = "SELECT * FROM syncException where isSynced = '0'";
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                DPCSyncExceptionDto posSyncExceptionDto = new DPCSyncExceptionDto(cursor);
                dpcSyncExceptionDtoList.add(posSyncExceptionDto);
                Log.i("posSyncExceptionDto", dpcSyncExceptionDtoList.toString());
                cursor.moveToNext();
            }
        }
        cursor.close();
        return dpcSyncExceptionDtoList;
    }

    //Update sync exc data
    public void updateSyncExcData(DPCSyncExceptionDto dpcSyncExceptionDto, String localId) {
        try {
//            Util.LoggingQueue(contextValue, "localId 2...", localId);
            ContentValues values = new ContentValues();
            values.put("dpcId", String.valueOf(dpcSyncExceptionDto.getDpcProfileId()));
            values.put("isSynced", "1");
            database.update("syncException", values, "_id = " + localId, null);
        } catch (Exception e) {
            e.printStackTrace();
//            com.omneagate.Util.Util.LoggingQueue(contextValue, "update syncException Exception...", e.toString());
        }
    }

    public void insertUserDetails(UserDetailDto userDetailDto, String password, String statusCode) {
        try {
            Log.e("Shiva ", "Enter the UserInsert Table");
            ContentValues values = new ContentValues();
            values.put(DbConstants.KEY_ID_SERVER, String.valueOf(userDetailDto.getId()));
            values.put(DbConstants.KEY_USER_NAME, String.valueOf(userDetailDto.getUsername()));
            values.put(DbConstants.KEY_PROFILE, String.valueOf(userDetailDto.getProfile()));
            values.put(DbConstants.KEY_USER_ID, userDetailDto.getUserId());
            values.put(DbConstants.KEY_USER_ACTIVE, userDetailDto.isActive());
            values.put(DbConstants.KEY_USERS_PASS_HASH, String.valueOf(userDetailDto.getPassword()));
            values.put(DbConstants.KEY_DPC_CONTACT_PERSON, String.valueOf(userDetailDto.getContactNumber()));
            values.put(DbConstants.KEY_CONTACT_NUMBER, String.valueOf(userDetailDto.getContactNumber()));
            values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(userDetailDto.getCreatedDate()));
            values.put(DbConstants.KEY_CREATED_BY, String.valueOf(userDetailDto.getCreatedBy()));
            values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(userDetailDto.getModifiedDate()));
            values.put(DbConstants.KEY_STATUS, String.valueOf(statusCode));
            values.put(DbConstants.KEY_MODIFIED_BY, String.valueOf(userDetailDto.getModifiedBy()));
            values.put(DbConstants.KEY_USER_ENCRYPTED_PASSWORD, String.valueOf(Util.EncryptPassword(password)));
            if (userDetailDto.getDpcProfileDto() != null)
                values.put(DbConstants.KEY_USER_DPC_ID, String.valueOf(userDetailDto.getDpcProfileDto().getId()));
            database.insert(DBTables.TABLE_USER, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dpcprofile(DPCProfileDto dpc_profile_dto) {
        Log.e("Shiva ", "Enter the dpcprofile Table" + dpc_profile_dto);
        try {
            ContentValues values = new ContentValues();
            values.put(DbConstants.KEY_ID_SERVER, dpc_profile_dto.getId());
            values.put(DbConstants.KEY_USER_DPC_CODE, dpc_profile_dto.getCode());
            values.put(DbConstants.KEY_USER_DPC_CONTACT_NUMBER, dpc_profile_dto.getContactNum());
            values.put(DbConstants.KEY_USER_DPC_CONTACT_PERSON, dpc_profile_dto.getContactPerson());
            values.put(DbConstants.KEY_USER_DPC_CREATED_BY, dpc_profile_dto.getCreatedby());
            values.put(DbConstants.KEY_USER_DPC_CREATED_DATE, dpc_profile_dto.getCreatedDate());
            values.put(DbConstants.KEY_USER_DPC_GEOFENCING, dpc_profile_dto.getGeofencing());
            values.put(DbConstants.KEY_USER_DPC_LATITUDE, dpc_profile_dto.getLatitude());
            values.put(DbConstants.KEY_USER_DPC_LONGITUDE, dpc_profile_dto.getLongitude());
            values.put(DbConstants.KEY_USER_DPC_MODIFIED_BY, dpc_profile_dto.getModifiedby());
            values.put(DbConstants.KEY_USER_DPC_MODIFIED_DATE, dpc_profile_dto.getModifiedDate());
            values.put(DbConstants.KEY_USER_DPC_NAME, dpc_profile_dto.getName());
            values.put(DbConstants.KEY_USER_DPC_PINCODE, dpc_profile_dto.getPinCode());
            values.put(DbConstants.KEY_USER_DPC_VERSION, dpc_profile_dto.getVersion());
//        values.put(DbConstants.KEY_USER_DPC_DEVICE_ID, dpc_profile_dto.getDeviceDto().getId());
            values.put(DbConstants.KEY_USER_DPC_ADDRESS, dpc_profile_dto.getAddress());
            values.put(DbConstants.KEY_USER_DPC_CATEGORY, dpc_profile_dto.getDpcCategory());
            values.put(DbConstants.KEY_USER_DPC_TYPE, dpc_profile_dto.getDpcType());
            values.put(DbConstants.KEY_USER_DPC_EMAIL_ADDRESS, dpc_profile_dto.getEmailAddress());
            values.put(DbConstants.KEY_USER_DPC_GENERATED_CODE, dpc_profile_dto.getGeneratedCode());
            values.put(DbConstants.KEY_USER_DPC_NUMBER_OF_PEOPLE, dpc_profile_dto.getNoofPeople());
            values.put(DbConstants.KEY_USER_DPC_REMOTE_LOG, dpc_profile_dto.getRemoteLogEnabled());
            values.put(DbConstants.KEY_USER_DPC_STORAGE_CAPACITY, dpc_profile_dto.getStorageCapacity());
            values.put(DbConstants.KEY_USER_DPC_WEIGHING_MACHINE, dpc_profile_dto.getWeighingCapacity());
            values.put(DbConstants.KEY_USER_DPC_STATUS, returnInteger(dpc_profile_dto.isStatus()));
            values.put(DbConstants.KEY_USER_DPC_REGIONAL_NAME, dpc_profile_dto.getLname());
            values.put(DbConstants.KEY_USER_DPC_MOISTURE_METER, dpc_profile_dto.getMoistureMeter());
            values.put(DbConstants.KEY_USER_DPC_PLATFORM_SCALES, dpc_profile_dto.getPlatformScales());
            values.put(DbConstants.KEY_USER_DPC_WINNOING_MACHINE, dpc_profile_dto.getWinnowingMachine());
            values.put(DbConstants.KEY_USER_DPC_DISTRICT_ID, dpc_profile_dto.getDpcDistrictDto().getId());
            values.put(DbConstants.KEY_USER_DPC_TALUK_ID, dpc_profile_dto.getDpcTalukDto().getId());
            values.put(DbConstants.KEY_USER_DPC_VILLAGE_ID, dpc_profile_dto.getDpcVillageDto().getId());
            database.insertWithOnConflict(DBTables.TABLE_DPC_PROFILE, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
//        database.insert(DBTables.TABLE_DPC_PROFILE, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getDpcGeneratedCode() {
        String selectQuery = " SELECT " + DbConstants.KEY_USER_DPC_GENERATED_CODE + " FROM " + DBTables.TABLE_DPC_PROFILE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String generatedCode = null;
        if (cursor.moveToFirst()) {
            do {
                generatedCode = cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_GENERATED_CODE));
            } while (cursor.moveToNext());
        }
        return generatedCode;
    }

    public String getDpcCode() {
        String selectQuery = " SELECT " + DbConstants.KEY_USER_DPC_CODE + " FROM " + DBTables.TABLE_DPC_PROFILE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String dpcCode = null;
        if (cursor.moveToFirst()) {
            do {
                dpcCode = cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_CODE));
            } while (cursor.moveToNext());
        }
        return dpcCode;
    }

    public UserDetailDto getBackgroundLogin(Long userId) {
        try {
            System.out.println("Shiva enter the getUserDetails method :::");
            String selectQuery = "SELECT  * FROM " + DBTables.TABLE_USER + " where "
                    + DbConstants.KEY_ID_SERVER + " = '" + userId + "'";
            Log.e("THE QUERY IS  :", selectQuery);
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            UserDetailDto userDetail = null;
            if (cursor.moveToFirst()) {
                userDetail = new UserDetailDto();
                userDetail.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                userDetail.setUsername(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_ID)));
                userDetail.setUserId(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_NAME)));
                userDetail.setEncryptedPassword(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_ENCRYPTED_PASSWORD)));
                userDetail.setPassword(cursor.getString(cursor
                        .getColumnIndex(DbConstants.KEY_USERS_PASS_HASH)));
            }
            return userDetail;
        } catch (Exception e) {
            Log.e("UserDetailDto", "Exception = " + e);
        }
        return null;
    }

    public ResponseDto getUserDetails(String userName) {
        try {
            System.out.println("Shiva enter the getUserDetails method :::");
            String selectQuery = "SELECT  * ,u.dpc_id as dpcid FROM " + DBTables.TABLE_USER + " u LEFT JOIN " + DBTables.TABLE_DPC_PROFILE + " p ON u." + DbConstants.KEY_USER_DPC_ID + " = p." + DbConstants.KEY_ID_SERVER + " where "
                    + DbConstants.KEY_USER_ID + " = '" + userName.toLowerCase() + "'";
            Log.e("THE QUERY IS  :", selectQuery);
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            UserDetailDto userDetail = null;
            ResponseDto loginResponse = null;
            if (cursor.moveToFirst()) {
                System.out.println("Shiva enter the moveToFirst method :::");
                userDetail = new UserDetailDto();
                loginResponse = new ResponseDto();
                userDetail.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                userDetail.setCreatedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                userDetail.setModifiedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                userDetail.setUsername(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_ID)));
                userDetail.setProfile(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROFILE)));
                userDetail.setUserId(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_NAME)));
                userDetail.setPassword(cursor.getString(cursor
                        .getColumnIndex(DbConstants.KEY_USERS_PASS_HASH)));
                userDetail.setProfile(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROFILE)));
                DPCProfileDto dpc = new DPCProfileDto();
                dpc.setId(cursor.getLong(cursor.getColumnIndex("dpcid")));
                dpc.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_NAME)));
                dpc.setLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_REGIONAL_NAME)));
                dpc.setGeneratedCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_GENERATED_CODE)));
                dpc.setCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_CODE)));
                DpcTalukDto dpctaluk = new DpcTalukDto();
                dpctaluk.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_TALUK_ID)));
//                dpctaluk.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TALUK_NAME)));
//                dpctaluk.setLtalukName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TALUK_REGIONAL_NAME)));
                dpc.setDpcTalukDto(dpctaluk);
                DpcDistrictDto dpcdistrictdto = new DpcDistrictDto();
                dpcdistrictdto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_DISTRICT_ID)));
                dpc.setDpcDistrictDto(dpcdistrictdto);
                userDetail.setDpcProfileDto(dpc);
                int status = cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_USER_ACTIVE));
                if (status == 0) userDetail.setActive(false);
                else userDetail.setActive(true);
                loginResponse.setUserDetailDto(userDetail);
            } else {
                System.out.println("Shiva enter the moveToFirst else method :::");
            }
            return loginResponse;
        } catch (Exception e) {
            Log.e("UserDetailDto", "Exception = " + e);
        }
        return null;
    }

    public List<FarmerRegistrationRequestDto> getAllFarmers() {
        if(farmer_list_sync.size()>0){
            farmer_list_sync.clear();
        }
        String selectQuery = "SELECT * from dpc_farmer_registration_request  where is_server_added = 'R' ";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                FarmerRegistrationRequestDto list = new FarmerRegistrationRequestDto();
                list.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                list.setFarmerName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_NAME)));
                list.setFarmerLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_NAME)));
                list.setChannel("DPC_POS_APP");
                list.setGuardian(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_GUARDIAN_TYPE)));
                list.setGuardianName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_FATHER_HUSBAND_NAME)));
                list.setGuardianLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_NAME)));
                list.setAddress1(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_ADDRESS1)));
                list.setAddress2(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_ADDRESS2)));
                DpcDistrictDto districtDto = new DpcDistrictDto();
                districtDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_DISTRICT_ID)));
                list.setDpcDistrictDto(districtDto);
                DpcTalukDto talukDto = new DpcTalukDto();
                talukDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_TALUK_ID)));
                list.setDpcTalukDto(talukDto);
                DpcVillageDto villageDto = new DpcVillageDto();
                villageDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_VILLAGE_ID)));
                list.setDpcVillageDto(villageDto);
                list.setMobileNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_MOBILE_NUMBER)));
                FarmerClassDto farmerClassDto = new FarmerClassDto();
                farmerClassDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_COMMUNITY_ID)));
                list.setFarmerClassDto(farmerClassDto);
                list.setAadhaarNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_AADHAR_NUMBER)));
                BankDto bankDto = new BankDto();
                bankDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_BANK_ID)));
                list.setBankDto(bankDto);
                list.setAccountNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_ACCOUNT_NUMBER)));
                list.setBranchName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_BRANCH_NAME)));
                list.setIfscCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_IFSC_CODE)));
                list.setRequestedReferenceNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_REFERENCE_NUMBER)));
                list.setPincode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_PINCODE)));
                list.setDeviceNumber(Util.device_number);
                list.setCreatedBy(LoginData.getInstance().getResponseData().getUserDetailDto().getCreatedBy());
                list.setModifiedBy(LoginData.getInstance().getResponseData().getUserDetailDto().getModifiedBy());
//               Log.e("User Profile Dto", "user profile Dto" + LoginData.getInstance().getResponseData().getDpcProfileDto());
                list.setDpcProfileDto(LoginData.getInstance().getResponseData().getDpcProfileDto());
                list.setFarmerLandDetailsDtoList(getLandDetails(list.getRequestedReferenceNumber()));
                farmer_list_sync.add(list);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return farmer_list_sync;
    }

    private List<FarmerLandDetailsDto> getLandDetails(String registerNumber) {
        if(farmer_list_sync_land.size()>0){
            farmer_list_sync_land.clear();
        }
        String selectQuery = "SELECT * from dpc_farmer_land_details  where farmer_id = '" + registerNumber + "'";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                FarmerLandDetailsDto farmerLandDetailsDto = new FarmerLandDetailsDto();
                farmerLandDetailsDto.setMainLandLordName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_LAND_MAIN_LORD_NAME)));
                farmerLandDetailsDto.setMainLandLordLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_LAND_MAIN_LORD_NAME)));
                farmerLandDetailsDto.setLoanBookNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_LAND_LOAN_BOOK_NUMBER)));
                farmerLandDetailsDto.setArea(cursor.getDouble(cursor.getColumnIndex("farmer_area")));
                farmerLandDetailsDto.setSurveyNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_LAND_SURVEY_NUMBER)));
                farmerLandDetailsDto.setSowedAccumulated(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_LAND_SEED_ACCUMULATED_KG)));
                farmerLandDetailsDto.setSowedNonAccumulated(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_LAND_SEED_NON_ACCUMULATED_KG)));
                farmerLandDetailsDto.setExpectedAccumulated(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_LAND_SEED_ACCUMULATED_KG)));
                farmerLandDetailsDto.setExpectedNonAccumulated(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_LAND_SEED_NON_ACCUMULATED_KG)));
                farmerLandDetailsDto.setCreatedBy(LoginData.getInstance().getResponseData().getUserDetailDto().getCreatedBy());
                farmerLandDetailsDto.setModifiedBy(LoginData.getInstance().getResponseData().getUserDetailDto().getModifiedBy());
                Log.e("User Profile Dto", "user profile Dto" + LoginData.getInstance().getResponseData().getDpcProfileDto());
                farmerLandDetailsDto.setExpectedTotal(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_LAND_SEED_EXCEPTED_TOTAL_QUINTAL)));
                farmerLandDetailsDto.setSowedTotal(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_LAND_SEED_SHOWED_TOTAL_KG)));
                DpcDistrictDto districtDto = new DpcDistrictDto();
                districtDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_LAND_DISTRICT_ID)));
                farmerLandDetailsDto.setDpcDistrictDto(districtDto);
                DpcTalukDto talukDto = new DpcTalukDto();
                talukDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_LAND_TALUK_ID)));
                farmerLandDetailsDto.setDpcTalukDto(talukDto);
                DpcVillageDto villageDto = new DpcVillageDto();
                villageDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_LAND_VILLAGE_ID)));
                farmerLandDetailsDto.setDpcVillageDto(villageDto);
                LandTypeDto landTypeDto = new LandTypeDto();
                landTypeDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_LAND_TYPE_ID)));
                farmerLandDetailsDto.setLandTypeDto(landTypeDto);
                PaddyCategoryDto paddyCategoryDto = new PaddyCategoryDto();
                paddyCategoryDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_CROP_NAME_ID)));
                farmerLandDetailsDto.setPaddyCategoryDto(paddyCategoryDto);
                farmer_list_sync_land.add(farmerLandDetailsDto);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return farmer_list_sync_land;
    }

    /*GET PADDY GRADE */
    public List<PaddyGradeDto> getpaddygrade() {
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_GRADE;
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<PaddyGradeDto> paddy_grade_list = new ArrayList<PaddyGradeDto>();
        if (cursor.moveToFirst()) {
            do {
                PaddyGradeDto paddy = new PaddyGradeDto();
                paddy.setLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PADDY_CATEGORY_REGIONAL_NAME)));
                paddy.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PADDY_CATEGORY_NAME)));
                paddy.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                paddy_grade_list.add(paddy);
            } while (cursor.moveToNext());
        }
        return paddy_grade_list;
    }

    public List<DpcCapDto> getCap() {
        String selectQuery = "SELECT *FROM " + DBTables.TABLE_DPC_CAB;
        Log.e("query", "Cap" + selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<DpcCapDto> cap_list = new ArrayList<DpcCapDto>();
        if (cursor.moveToFirst()) {
            do {
                DpcCapDto cap = new DpcCapDto();
                cap.setGeneratedCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_GOWNDOWN_GENERATED_CAP_CODE)));
                cap.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_GOWNDOWN_NAME)));
                cap.setLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_GOWNDOWN_CAP_REGIONAL_NAME)));
                cap.setId(cursor.getLong(cursor.getColumnIndex("id")));
                cap_list.add(cap);
            } while (cursor.moveToNext());
        }
        return cap_list;
    }

    /*GET PADDY GRADE */
    public List<DpcSpecificationDto> getSpecification() {
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_DPC_SPECIFICATION;
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        List<DpcSpecificationDto> specification_list = new ArrayList<DpcSpecificationDto>();
        if (cursor.moveToFirst()) {
            do {
                DpcSpecificationDto specification = new DpcSpecificationDto();
                specification.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                specification.setStartDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_SPECIFICATION_START_DATE)));
                specification.setEndDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_SPECIFICATION_END_DATE)));
                specification.setMoisturePercentageFrom(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_SPECIFICATION_MOISTURE_FROM)));
                specification.setMoisturePercentageTo(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_SPECIFICATION_MOISTURE_TO)));
                specification.setSpecificationType(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_SPECIFICATION_TYPE)));
                specification.setSpecificationCode(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_SPECIFICATION_CODE)));
                specification.setCreatedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_BY)));
                specification.setCreatedDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_DATE)));
                specification.setModifiedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_BY)));
                specification.setModifiedDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_DATE)));
                specification_list.add(specification);
            } while (cursor.moveToNext());
        }
        return specification_list;
    }

    public DpcSpecificationDto getMoistureContent(String type) {
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_DPC_SPECIFICATION + " where " + DbConstants.KEY_SPECIFICATION_TYPE + " = '" + type + "'";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        DpcSpecificationDto specification = null;
        if (cursor.moveToFirst()) {
            do {
                specification = new DpcSpecificationDto();
                specification.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                specification.setStartDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_SPECIFICATION_START_DATE)));
                specification.setEndDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_SPECIFICATION_END_DATE)));
                specification.setMoisturePercentageFrom(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_SPECIFICATION_MOISTURE_FROM)));
                specification.setMoisturePercentageTo(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_SPECIFICATION_MOISTURE_TO)));
                specification.setSpecificationType(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_SPECIFICATION_TYPE)));
                specification.setSpecificationCode(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_SPECIFICATION_CODE)));
                specification.setCreatedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_BY)));
                specification.setCreatedDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_DATE)));
                specification.setModifiedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_BY)));
                specification.setModifiedDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_DATE)));
            } while (cursor.moveToNext());
        }
        return specification;
    }

    public DpcCapDto getCapName(long cap_id) {
        String selectQuery = "SELECT " + DbConstants.KEY_GOWNDOWN_GENERATED_CAP_CODE + "," + DbConstants.KEY_GOWNDOWN_NAME + "," + DbConstants.KEY_GOWNDOWN_CAP_REGIONAL_NAME + " FROM " + DBTables.TABLE_DPC_CAB + " where " + DbConstants.KEY_ID_SERVER + " = " + cap_id;
        Log.e("query", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        DpcCapDto cap = new DpcCapDto();
        if (cursor.moveToFirst()) {
            do {
                cap.setGeneratedCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_GOWNDOWN_GENERATED_CAP_CODE)));
                cap.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_GOWNDOWN_NAME)));
                cap.setLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_GOWNDOWN_CAP_REGIONAL_NAME)));
            } while (cursor.moveToNext());
        }
        return cap;
    }

    public long getDistrictId() {
        String selectQuery = "SELECT dpc_district_id FROM " + DBTables.TABLE_DPC_PROFILE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        long DistrictId = 0;
        if (cursor.moveToFirst()) {
            do {
                DistrictId = cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_DISTRICT_ID));
            } while (cursor.moveToNext());
        }
        return DistrictId;
    }

    public FarmerRegistrationRequestDto getFarmerviewdetails(String id) {
        farmer_list_sync_land.clear();
        String selectQuery = "SELECT *, a." + DbConstants.KEY_FARMER_DISTRICT_ID + " as " + DbConstants.KEY_FARMER_DISTRICT_ID + ", a." + DbConstants.KEY_FARMER_TALUK_ID + " as " + DbConstants.KEY_FARMER_TALUK_ID + ", a." + DbConstants.KEY_FARMER_VILLAGE_ID + " as " + DbConstants.KEY_FARMER_VILLAGE_ID + " from " + DBTables.TABLE_FARMER + " as a join " + DBTables.TABLE_LAND_DETAILS + " as b on a.reference_number = b.farmer_id where a.reference_number = '" + id + "' ";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
//        List<FramerViewDetailsDto> list_framerviewDetails = new ArrayList<FramerViewDetailsDto>();
        FarmerRegistrationRequestDto list = new FarmerRegistrationRequestDto();
        if (cursor.moveToFirst()) {
            do {
                list.setId(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
                list.setFarmerName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_NAME)));
                list.setFarmerLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_NAME)));
                list.setGuardian(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_GUARDIAN_TYPE)));
                list.setGuardianName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_FATHER_HUSBAND_NAME)));
                list.setGuardianLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_NAME)));
                list.setRationCardNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_RATION_CARD_NUMBER)));
                list.setAddress1(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_ADDRESS1)));
                list.setAddress2(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_ADDRESS2)));
                DpcDistrictDto districtDto = new DpcDistrictDto();
                districtDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_DISTRICT_ID)));
                list.setDpcDistrictDto(districtDto);
                DpcTalukDto talukDto = new DpcTalukDto();
                talukDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_TALUK_ID)));
                list.setDpcTalukDto(talukDto);
                DpcVillageDto villageDto = new DpcVillageDto();
                villageDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_VILLAGE_ID)));
                list.setDpcVillageDto(villageDto);
                list.setMobileNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_MOBILE_NUMBER)));
                FarmerClassDto farmerClassDto = new FarmerClassDto();
                farmerClassDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_COMMUNITY_ID)));
                list.setFarmerClassDto(farmerClassDto);
                list.setAadhaarNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_AADHAR_NUMBER)));
                BankDto bankDto = new BankDto();
                bankDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_BANK_ID)));
                list.setBankDto(bankDto);
                list.setAccountNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_ACCOUNT_NUMBER)));
                list.setBranchName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_BRANCH_NAME)));
                list.setIfscCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_IFSC_CODE)));
                list.setRequestedReferenceNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_REFERENCE_NUMBER)));
                list.setPincode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_FARMER_PINCODE)));
                list.setDeviceNumber(Util.device_number);
//                list.setCreatedBy(LoginData.getInstance().getResponseData().getUserDetailDto().getCreatedBy());
//                list.setModifiedBy(LoginData.getInstance().getResponseData().getUserDetailDto().getModifiedBy());
//               Log.e("User Profile Dto", "user profile Dto" + LoginData.getInstance().getResponseData().getDpcProfileDto());
//                list.setDpcProfileDto(LoginData.getInstance().getResponseData().getDpcProfileDto());
                //farmer land details
                FarmerLandDetailsDto farmerLandDetailsDto = new FarmerLandDetailsDto();
                farmerLandDetailsDto.setMainLandLordName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_LAND_MAIN_LORD_NAME)));
                farmerLandDetailsDto.setMainLandLordLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_LAND_MAIN_LORD_NAME)));
                farmerLandDetailsDto.setLoanBookNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_LAND_LOAN_BOOK_NUMBER)));
                farmerLandDetailsDto.setArea(cursor.getDouble(cursor.getColumnIndex("farmer_area")));
                farmerLandDetailsDto.setSurveyNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_LAND_SURVEY_NUMBER)));
                farmerLandDetailsDto.setSowedAccumulated(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_LAND_SEED_ACCUMULATED_KG)));
                farmerLandDetailsDto.setSowedNonAccumulated(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_LAND_SEED_NON_ACCUMULATED_KG)));
                farmerLandDetailsDto.setExpectedAccumulated(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_LAND_SEED_ACCUMULATED_KG)));
                farmerLandDetailsDto.setExpectedNonAccumulated(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_LAND_SEED_NON_ACCUMULATED_KG)));
//                ResponseDto what = LoginData.getInstance().getResponseData();
//                farmerLandDetailsDto.setCreatedBy(LoginData.getInstance().getResponseData().getUserDetailDto().getCreatedBy());
//                farmerLandDetailsDto.setModifiedBy(LoginData.getInstance().getResponseData().getUserDetailDto().getModifiedBy());
                farmerLandDetailsDto.setPattaNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_LAND_PATTA_NUMBER)));
                Log.e("User Profile Dto", "user profile Dto" + LoginData.getInstance().getResponseData().getDpcProfileDto());
                farmerLandDetailsDto.setExpectedTotal(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_LAND_SEED_EXCEPTED_TOTAL_QUINTAL)));
                farmerLandDetailsDto.setSowedTotal(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_LAND_SEED_SHOWED_TOTAL_KG)));
                DpcDistrictDto districtDto2 = new DpcDistrictDto();
                districtDto2.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_LAND_DISTRICT_ID)));
                farmerLandDetailsDto.setDpcDistrictDto(getDistrictName_byid(districtDto2.getId(), districtDto2));
                DpcTalukDto talukDto2 = new DpcTalukDto();
                talukDto2.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_LAND_TALUK_ID)));
                farmerLandDetailsDto.setDpcTalukDto(getTalukName_byid(talukDto2.getId(), talukDto2));
                DpcVillageDto villageDto2 = new DpcVillageDto();
                villageDto2.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_LAND_VILLAGE_ID)));
                farmerLandDetailsDto.setDpcVillageDto(getVillageName_byid(villageDto2.getId(), villageDto2));
                LandTypeDto landTypeDto = new LandTypeDto();
                landTypeDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_LAND_TYPE_ID)));
                farmerLandDetailsDto.setLandTypeDto(getLandtype_byid(landTypeDto.getId(), landTypeDto));
                PaddyCategoryDto paddyCategoryDto = new PaddyCategoryDto();
                paddyCategoryDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_CROP_NAME_ID)));
                paddyCategoryDto.setName(getpaddy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_CROP_NAME_ID))));
                paddyCategoryDto.setLname(getpaddy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_CROP_NAME_ID))));
                farmerLandDetailsDto.setPaddyCategoryDto(paddyCategoryDto);
                farmerLandDetailsDto.setExpectedProcuringDate(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_LAND_PROCUREMENT_EXPECTED_DATE)));
                farmer_list_sync_land.add(farmerLandDetailsDto);
                list.setFarmerLandDetailsDtoList(farmer_list_sync_land);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /*GET District by id*/
    public DpcDistrictDto getDistrictName_byid(long district_id, DpcDistrictDto dpcDistrictDto) {
//        district_id = MySharedPreference.readString(context, MySharedPreference.DISTRICT_ID, "");
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_DPC_DISTRICT + " WHERE " + DbConstants.KEY_ID_SERVER + " = " + district_id + "";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                dpcDistrictDto.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_DISTRICT_NAME)));
                dpcDistrictDto.setLdistrictName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_REGIONAL_DISTRICT_NAME)));
            } while (cursor.moveToNext());
        }
        return dpcDistrictDto;
    }

    public DpcTalukDto getTalukName_byid(long taluk_id, DpcTalukDto dpcTalukDto) {
//        district_id = MySharedPreference.readString(context, MySharedPreference.DISTRICT_ID, "");
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_DPC_TALUK + "  where " + DbConstants.KEY_ID_SERVER + " =" + taluk_id + " ORDER BY " + DbConstants.KEY_TALUK_NAME + " ASC ";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                dpcTalukDto.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TALUK_NAME)));
                dpcTalukDto.setLtalukName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TALUK_REGIONAL_NAME)));
            } while (cursor.moveToNext());
        }
        return dpcTalukDto;
    }

    public DpcVillageDto getVillageName_byid(long village_id, DpcVillageDto dpcVillageDto) {
        String selectQuery = "SELECT * FROM " + DBTables.TABLE_VILLAGE + " where " + DbConstants.KEY_ID_SERVER + " = " + village_id + " ORDER BY " + DbConstants.KEY_VILLAGE_NAME + " ASC ";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                dpcVillageDto.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_VILLAGE_NAME)));
                dpcVillageDto.setLvillageName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_VILLAGE_LVILLAGE_NAME)));
            } while (cursor.moveToNext());
        }
        return dpcVillageDto;
    }

    public BankDto getBankName_id(long id, BankDto bankDto) {
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_BANK + " where " + DbConstants.KEY_ID_SERVER + " = " + id;
        String bnk = "";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                bankDto.setBankName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_BANK_NAME)));
                bankDto.setLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_BANK_REGIONAL_NAME)));
            } while (cursor.moveToNext());
        }
        return bankDto;
    }

    public LandTypeDto getLandtype_byid(long id, LandTypeDto landdto) {
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_LAND_TYPE + " WHERE " + DbConstants.KEY_ID_SERVER + " = " + id + "";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                landdto.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_LAND_TYPE)));
            } while (cursor.moveToNext());
        }
        return landdto;
    }

    public UserDetailDto checkuserdetails(String userid) {
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_USER + " WHERE " + DbConstants.KEY_USER_ID + " = '" + userid + "'";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        UserDetailDto userdto = null;
        if (cursor.moveToFirst()) {
            do {
                userdto = new UserDetailDto();
                userdto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                userdto.setProfile(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROFILE)));
                userdto.setStatusCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_STATUS)));
                userdto.setPassword(cursor.getString(cursor
                        .getColumnIndex(DbConstants.KEY_USERS_PASS_HASH)));
                userdto.setEncryptedPassword(cursor.getString(cursor
                        .getColumnIndex(DbConstants.KEY_USER_ENCRYPTED_PASSWORD)));
                DPCProfileDto dpc = new DPCProfileDto();
                dpc.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_ID)));
                userdto.setDpcProfileDto(dpc);
            } while (cursor.moveToNext());
        }
        return userdto;
    }

    public void updatetUserDetails(UserDetailDto userDetailDto, String status) {
        Log.e("Shiva ", "Enter the UserInsert Table");
        ContentValues values = new ContentValues();
//        values.put(DbConstants.KEY_ID_SERVER, String.valueOf(userDetailDto.getId()));
//        values.put(DbConstants.KEY_USER_NAME, String.valueOf(userDetailDto.getUsername()));
//        values.put(DbConstants.KEY_PROFILE, String.valueOf(userDetailDto.getProfile()));
//        values.put(DbConstants.KEY_USER_ID, userDetailDto.getUserId());
//        values.put(DbConstants.KEY_USER_ACTIVE, userDetailDto.isActive());
//        values.put(DbConstants.KEY_USERS_PASS_HASH, String.valueOf(userDetailDto.getPassword()));
//        values.put(DbConstants.KEY_DPC_CONTACT_PERSON, String.valueOf(userDetailDto.getContactNumber()));
//        values.put(DbConstants.KEY_CONTACT_NUMBER, String.valueOf(userDetailDto.getContactNumber()));
//        values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(userDetailDto.getCreatedDate()));
//        values.put(DbConstants.KEY_CREATED_BY, String.valueOf(userDetailDto.getCreatedBy()));
//        values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(userDetailDto.getModifiedDate()));
        values.put(DbConstants.KEY_STATUS, String.valueOf(userDetailDto.getStatusCode()));
//        values.put(DbConstants.KEY_MODIFIED_BY, String.valueOf(userDetailDto.getModifiedBy()));
//        values.put(DbConstants.KEY_USER_ENCRYPTED_PASSWORD, String.valueOf(Util.EncryptPassword(password)));
        database.update(DBTables.TABLE_USER, values, DbConstants.KEY_USER_ID + " ='" + userDetailDto.getUserId() + "'", null);
    }

    public FarmerRegistrationDto getFarmerDetailsProcurement(String number) {
        String selectQuery = "SELECT  * from dpc_farmer_registration where aadhaar_number = '" + number + "' OR " + "farmer_code = '" + number + "'";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        FarmerRegistrationDto farmer_dto = null;
        if (cursor.moveToFirst()) {
            do {
                farmer_dto = new FarmerRegistrationDto();
                farmer_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                farmer_dto.setFarmerLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_REGIONAL_NAME)));
                farmer_dto.setFarmerName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_NAME)));
                farmer_dto.setFarmerCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_CODE)));
                farmer_dto.setMobileNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_MOBILE_NUMBER)));
                farmer_dto.setBranchName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_BRANCH_NAME)));
                farmer_dto.setAccountNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_ACCOUNT_NUMBER)));
                farmer_dto.setIfscCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_IFSC_CODE)));
                farmer_dto.setAddress1(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_ADDRESS_1)));
                farmer_dto.setAddress2(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_ADDRESS_2)));
                BankDto bankdto = new BankDto();
                bankdto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_BANK_ID)));
                getBankName_id(bankdto.getId(), bankdto);
                farmer_dto.setBankDto(bankdto);
            } while (cursor.moveToNext());
        }
        return farmer_dto;
    }

    public FarmerRegistrationDto getFarmerDetailsProcurementDuplicate(Long id) {
        String selectQuery = "SELECT  * from " + DBTables.TABLE_ASSOCIATED_FARMERS + " where " + DbConstants.KEY_ID_SERVER + " = " + id;
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        FarmerRegistrationDto farmer_dto = null;
        if (cursor.moveToFirst()) {
            do {
                farmer_dto = new FarmerRegistrationDto();
                farmer_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                farmer_dto.setFarmerLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_REGIONAL_NAME)));
                farmer_dto.setFarmerName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_NAME)));
                farmer_dto.setFarmerCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_CODE)));
                farmer_dto.setMobileNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_MOBILE_NUMBER)));
                farmer_dto.setBranchName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_BRANCH_NAME)));
                farmer_dto.setAccountNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_ACCOUNT_NUMBER)));
                farmer_dto.setIfscCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_IFSC_CODE)));
                farmer_dto.setAddress1(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_ADDRESS_1)));
                farmer_dto.setAddress2(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_ADDRESS_2)));
                BankDto bankdto = new BankDto();
                bankdto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_FARMER_BANK_ID)));
                getBankName_id(bankdto.getId(), bankdto);
                farmer_dto.setBankDto(bankdto);
            } while (cursor.moveToNext());
        }
        return farmer_dto;
    }

    public FarmerRegistrationDto getAssociatedFarmerviewdetails(String id) {
        farmer_list_sync_land.clear();
        String selectQuery = "SELECT * from " + DBTables.TABLE_ASSOCIATED_FARMERS + " as a left join " + DBTables.TABLE_ASSOCIATED_LAND_DETAILS + " as b on a.id = b.farmer_registration_id where a.farmer_code = '" + id + "' ";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        FarmerRegistrationDto list = new FarmerRegistrationDto();
        if (cursor.moveToFirst()) {
            List<FarmerApprovedLandDetailsDto> farmerApprovedLandDetailslist = new ArrayList<>();
            do {
                list.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                list.setFarmerName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_NAME)));
                list.setFarmerCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_CODE)));
                list.setFarmerLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_NAME)));
                list.setGuardian(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_GUARDIAN_TYPE)));
                list.setGuardianName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_GUARDIAN_NAME)));
                list.setGuardianLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_GUARDIAN_REGIONAL_NAME)));
                list.setRationCardNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_RATION_CARD_NUMBER)));
                list.setAddress1(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_ADDRESS_1)));
                list.setAddress2(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_ADDRESS_2)));
                DpcDistrictDto districtDto = new DpcDistrictDto();
                districtDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_DPC_DISTRICT_ID)));
                list.setDpcDistrictDto(districtDto);

                DpcTalukDto talukDto = new DpcTalukDto();
                talukDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_DPC_TALUK_ID)));
                list.setDpcTalukDto(talukDto);

                DpcVillageDto villageDto = new DpcVillageDto();
                villageDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_DPC_VILLAGE_ID)));
                list.setDpcVillageDto(villageDto);

                list.setMobileNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_MOBILE_NUMBER)));
                list.setAadhaarNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_AADHAAR_NUMBER)));
                BankDto bankDto = new BankDto();
                bankDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_BANK_ID)));
                list.setBankDto(bankDto);
                list.setAccountNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_ACCOUNT_NUMBER)));
                list.setBranchName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_BRANCH_NAME)));
                list.setIfscCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_IFSC_CODE)));
                //farmer land details
                FarmerApprovedLandDetailsDto farmerLandDetailsDto = new FarmerApprovedLandDetailsDto();
                farmerLandDetailsDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                farmerLandDetailsDto.setMainLandLordName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_MAIN_LOARD_NAME)));
                farmerLandDetailsDto.setMainLandLordLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_AREA)));
                farmerLandDetailsDto.setArea(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_AREA)));
                farmerLandDetailsDto.setSurveyNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_SURVEY_NUMBER)));
                farmerLandDetailsDto.setSowedAccumulated(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_SHOWED_ACCUMULATED)));
                farmerLandDetailsDto.setSowedNonAccumulated(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_SHOWED_NON_ACCUMULATED)));
                farmerLandDetailsDto.setExpectedAccumulated(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_EXPECTED_ACCUMULATED)));
                farmerLandDetailsDto.setExpectedNonAccumulated(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_EXPECTED_NON_ACCUMULATED)));
//                farmerLandDetailsDto.setCreatedBy(LoginData.getInstance().getResponseData().getUserDetailDto().getCreatedBy());
//                farmerLandDetailsDto.setModifiedBy(LoginData.getInstance().getResponseData().getUserDetailDto().getModifiedBy());
                farmerLandDetailsDto.setPattaNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_PATTA_NUMBER)));
//                Log.e("User Profile Dto", "user profile Dto" + `Data.getInstance().getResponseData().getDpcProfileDto());
                farmerLandDetailsDto.setExpectedTotal(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_EXPECTED_TOTAL)));
                farmerLandDetailsDto.setSowedTotal(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_SHOWED_TOTAL)));
                DpcDistrictDto districtDto2 = new DpcDistrictDto();
                districtDto2.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_DPC_DISTRICT_ID)));
                farmerLandDetailsDto.setDpcDistrictDto(getDistrictName_byid(districtDto2.getId(), districtDto2));
                DpcTalukDto talukDto2 = new DpcTalukDto();
                talukDto2.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_DPC_TALUK_ID)));
                farmerLandDetailsDto.setDpcTalukDto(getTalukName_byid(talukDto2.getId(), talukDto2));
                DpcVillageDto villageDto2 = new DpcVillageDto();
                villageDto2.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_DPC_VILLAGE_ID)));
                farmerLandDetailsDto.setDpcVillageDto(getVillageName_byid(villageDto2.getId(), villageDto2));
                LandTypeDto landTypeDto = new LandTypeDto();
                landTypeDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_LANDTYPE_ID)));
                farmerLandDetailsDto.setLandTypeDto(getLandtype_byid(landTypeDto.getId(), landTypeDto));
                PaddyCategoryDto paddyCategoryDto = new PaddyCategoryDto();
                paddyCategoryDto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_PADYCATEGORY_ID)));
                paddyCategoryDto.setName(getpaddy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_PADYCATEGORY_ID))));
                farmerLandDetailsDto.setPaddyCategoryDto(paddyCategoryDto);
                farmerLandDetailsDto.setPattaNumber(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_PATTA_NUMBER)));
                farmerLandDetailsDto.setExpectedProcuringDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_LAND_EXPECTED_PROCURING_DATE)));
                farmerApprovedLandDetailslist.add(farmerLandDetailsDto);
                list.setFarmerApprovedLandDetailsDtos(farmerApprovedLandDetailslist);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public void insertLoginHistory(LoginHistoryDto loginHistory) {
        try {
            ContentValues values = new ContentValues();
            values.put(DbConstants.KEY_LOGIN_TIME, loginHistory.getLoginTime());
            values.put(DbConstants.KEY_LOGIN_TYPE, loginHistory.getLoginType());
            values.put(DbConstants.KEY_USER_ID, loginHistory.getUserId());
            values.put(DbConstants.KEY_USER_DPC_ID, loginHistory.getDpcId());
            values.put(DbConstants.KEY_TRANSACTION_ID, loginHistory.getTransactionId());
            values.put(DbConstants.KEY_CREATED_TIME, new Date().getTime());
            values.put(DbConstants.KEY_IS_SYNC, 0);
            if (!database.isOpen()) {
                database = dbHelper.getWritableDatabase();
            }
            database.insert(DBTables.TABLE_LOGIN_HISTORY, null, values);
        } catch (Exception e) {
            Log.e("Login History", e.toString(), e);
        }
    }

    public int gettablecount(String tablename) {
        String count = "0";
        if (SyncPageActivity.optimize) {
            String selectQuery = "SELECT COUNT(*) AS CNT FROM " + tablename;
            Log.e("THE QUERY IS  :", selectQuery);
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    count = cursor.getString(cursor.getColumnIndex("CNT"));
                } while (cursor.moveToNext());
            }
            if (count == null)
                count = "0";
        }

        return Integer.parseInt(count);
    }

    public String getlastlogin(String userid) {
        String time = "";
        String selectQuery = "SELECT " + DbConstants.KEY_LOGIN_TIME + " AS TIME FROM " + DBTables.TABLE_LOGIN_HISTORY + " WHERE login_type = 'ONLINE_LOGIN' OR login_type = 'OFFLINE_LOGIN' ORDER BY " + DbConstants.KEY_LOGIN_TIME + " DESC LIMIT 1 ";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                time = cursor.getString(cursor.getColumnIndex("TIME"));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return time;
    }

    public DpcPaddyRateDto getRate(long paddy_grade_id) {
        String selectQuery = "SELECT " + DbConstants.KEY_ID_SERVER + "," + DbConstants.KEY_BONUS_RATE + "," + DbConstants.KEY_PURCHASE_RATE + "," + DbConstants.KEY_TOTAL_RATE + " FROM " + DBTables.TABLE_DPC_RATE + " WHERE " + DbConstants.KEY_RATE_DPC_PADDY_GRADE_ID + " = " + paddy_grade_id;
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        DpcPaddyRateDto paddy_rate_dto = null;
        if (cursor.moveToFirst()) {
            do {
                paddy_rate_dto = new DpcPaddyRateDto();
                paddy_rate_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                paddy_rate_dto.setBonusRate(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_BONUS_RATE)));
                paddy_rate_dto.setPurchaseRate(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PURCHASE_RATE)));
                paddy_rate_dto.setTotalRate(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_TOTAL_RATE)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return paddy_rate_dto;
    }

    public void Insert_Procurement(DPCProcurementDto procurement) {
        try {
            ContentValues values = new ContentValues();
            values.put(DbConstants.KEY_PROCUREMENT_ADDITIONAL_CUT, String.valueOf(procurement.getAdditionalCut()));
            values.put(DbConstants.KEY_CREATED_BY, String.valueOf(procurement.getCreatedBy()));
            values.put(DbConstants.KEY_CREATED_DATE, String.valueOf(procurement.getCreatedDate()));
            values.put(DbConstants.KEY_PROCUREMENT_GRADE_CUT, String.valueOf(procurement.getGradeCut()));
            values.put(DbConstants.KEY_PROCUREMENT_LOT_NUMBER, String.valueOf(procurement.getLotNumber()));
            values.put(DbConstants.KEY_MODIFIED_BY, String.valueOf(procurement.getModifiedBy()));
            values.put(DbConstants.KEY_MODIFIED_DATE, String.valueOf(procurement.getModifiedDate()));
            values.put(DbConstants.KEY_PROCUREMENT_MOISTURE_CONTENT, String.valueOf(procurement.getMoistureContent()));
            values.put(DbConstants.KEY_PROCUREMENT_MOISTURE_CUT, String.valueOf(procurement.getMoistureCut()));
            values.put(DbConstants.KEY_PROCUREMENT_NET_AMOUNT, String.valueOf(procurement.getNetAmount()));
            values.put(DbConstants.KEY_PROCUREMENT_NET_WEIGHT, String.valueOf(procurement.getNetWeight()));
            values.put(DbConstants.KEY_PROCUREMENT_NUMBER_OF_BAGS, String.valueOf(procurement.getNumberOfBags()));
            values.put(DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER, String.valueOf(procurement.getProcurementReceiptNo()));
            values.put(DbConstants.KEY_PROCUREMENT_SPILLAGE_QTY, String.valueOf(procurement.getSpillageQuantity()));
            values.put(DbConstants.KEY_PROCUREMENT_TOTAL_AMOUNT, String.valueOf(procurement.getTotalAmount()));
            values.put(DbConstants.KEY_PROCUREMENT_TOTAL_CUT, String.valueOf(procurement.getTotalCut()));
            values.put(DbConstants.KEY_PROCUREMENT_TOTAL_CUT_AMOUNT, String.valueOf(procurement.getTotalCutAmount()));
            values.put(DbConstants.KEY_PROCUREMENT_DEVICE_ID, String.valueOf(procurement.getDeviceDto().getDeviceNumber()));
            values.put(DbConstants.KEY_PROCUREMENT_PADDY_RATE_ID, String.valueOf(procurement.getDpcPaddyRateDto().getId()));
            values.put(DbConstants.KEY_PROCUREMENT_PROFILE_ID, String.valueOf(procurement.getDpcProfileDto().getId()));
            values.put(DbConstants.KEY_PROCUREMENT_FARMER_REG_ID, String.valueOf(procurement.getFarmerRegistrationDto().getId()));
            values.put(DbConstants.KEY_PROCUREMENT_PADDY_CATEGORY_ID, String.valueOf(procurement.getPaddyCategoryDto().getId()));
            values.put(DbConstants.KEY_PROCUREMENT_PADDY_GRADE_ID, String.valueOf(procurement.getPaddyGradeDto().getId()));
            values.put(DbConstants.KEY_PROCUREMENT_SYNC_STATUS, String.valueOf(procurement.getSyncStatus()));
            values.put(DbConstants.KEY_PROCUREMENT_MODE, String.valueOf(procurement.getMode()));
//            values.put(DbConstants.KEY_PROCUREMENT_SMS_REFERENCE_NUMBER, String.valueOf(procurement.getSmsRefNumber()));
            values.put(DbConstants.KEY_PROCUREMENT_LAST_SYNC_TIME, String.valueOf(procurement.getLastSyncTime()));
            values.put(DbConstants.KEY_PROCUREMENT_TRANSACTION_DATE, String.valueOf(procurement.getTxnDateTime()));
            values.put(DbConstants.KEY_PROCUREMENT_SPECIFICATION_ID, String.valueOf(procurement.getDpcSpecificationDto().getId()));
            database.insert(DBTables.TABLE_DPC_PROCUREMENT, null, values);
//            database.insertWithOnConflict(DBTables.TABLE_DPC_PROCUREMENT, DbConstants.KEY_ID_SERVER, values, SQLiteDatabase.CONFLICT_REPLACE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DPCProfileDto getDpcProfile() {
        String selectQuery = "SELECT * from " + DBTables.TABLE_DPC_PROFILE;
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        DPCProfileDto dpc_profile_dto = null;
        if (cursor.moveToFirst()) {
            do {
                dpc_profile_dto = new DPCProfileDto();
                dpc_profile_dto.setGeneratedCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_GENERATED_CODE)));
                dpc_profile_dto.setContactPerson(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_CONTACT_PERSON)));
                dpc_profile_dto.setContactNum(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_CONTACT_NUMBER)));
                dpc_profile_dto.setDpcCategory(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_CATEGORY)));
                dpc_profile_dto.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_NAME)));
                dpc_profile_dto.setDpcType(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_TYPE)));
                dpc_profile_dto.setStorageCapacity(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_STORAGE_CAPACITY)));
                dpc_profile_dto.setLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_REGIONAL_NAME)));
                dpc_profile_dto.setWeighingCapacity(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_WEIGHING_MACHINE)));
                DpcDistrictDto district = new DpcDistrictDto();
                district.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_DISTRICT_ID)));
                dpc_profile_dto.setDpcDistrictDto(district);
                DpcTalukDto taluk = new DpcTalukDto();
                taluk.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_TALUK_ID)));
                dpc_profile_dto.setDpcTalukDto(taluk);
                DpcVillageDto village = new DpcVillageDto();
                village.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_VILLAGE_ID)));
                dpc_profile_dto.setDpcVillageDto(village);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dpc_profile_dto;
    }

    public DPCProfileDto getDpcProfileById(long id) {
        String selectQuery = "SELECT * from " + DBTables.TABLE_DPC_PROFILE + " where " + DbConstants.KEY_ID_SERVER + " = " + id;
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        DPCProfileDto dpc_profile_dto = null;
        if (cursor.moveToFirst()) {
            do {
                dpc_profile_dto = new DPCProfileDto();
                dpc_profile_dto.setGeneratedCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_GENERATED_CODE)));
                dpc_profile_dto.setContactPerson(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_CONTACT_PERSON)));
                dpc_profile_dto.setContactNum(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_CONTACT_NUMBER)));
                dpc_profile_dto.setDpcCategory(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_CATEGORY)));
                dpc_profile_dto.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_NAME)));
                dpc_profile_dto.setDpcType(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_TYPE)));
                dpc_profile_dto.setStorageCapacity(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_STORAGE_CAPACITY)));
                dpc_profile_dto.setLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_REGIONAL_NAME)));
                dpc_profile_dto.setWeighingCapacity(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_WINNOING_MACHINE)));
                DpcDistrictDto district = new DpcDistrictDto();
                district.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_DISTRICT_ID)));
                dpc_profile_dto.setDpcDistrictDto(district);
                DpcTalukDto taluk = new DpcTalukDto();
                taluk.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_TALUK_ID)));
                dpc_profile_dto.setDpcTalukDto(taluk);
                DpcVillageDto village = new DpcVillageDto();
                village.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_VILLAGE_ID)));
                dpc_profile_dto.setDpcVillageDto(village);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dpc_profile_dto;
    }

    public DpcDistrictDto getDistrictName_byid_(long district_id) {
//        district_id = MySharedPreference.readString(context, MySharedPreference.DISTRICT_ID, "");
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_DPC_DISTRICT + " WHERE " + DbConstants.KEY_ID_SERVER + " = " + district_id + "";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        DpcDistrictDto dpcDistrictDto = null;
        if (cursor.moveToFirst()) {
            do {
                dpcDistrictDto = new DpcDistrictDto();
                dpcDistrictDto.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_DISTRICT_NAME)));
                dpcDistrictDto.setLdistrictName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_REGIONAL_DISTRICT_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dpcDistrictDto;
    }

    public DpcTalukDto getTalukName_byid_(long taluk_id) {
//        district_id = MySharedPreference.readString(context, MySharedPreference.DISTRICT_ID, "");
        String selectQuery = "SELECT  * FROM " + DBTables.TABLE_DPC_TALUK + "  where " + DbConstants.KEY_ID_SERVER + " =" + taluk_id + " ORDER BY " + DbConstants.KEY_TALUK_NAME + " ASC ";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        DpcTalukDto dpcTalukDto = null;
        if (cursor.moveToFirst()) {
            do {
                dpcTalukDto = new DpcTalukDto();
                dpcTalukDto.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TALUK_NAME)));
                dpcTalukDto.setLtalukName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_TALUK_REGIONAL_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dpcTalukDto;
    }

    public DpcVillageDto getVillageName_byid_(long village_id) {
        String selectQuery = "SELECT * FROM " + DBTables.TABLE_VILLAGE + " where " + DbConstants.KEY_ID_SERVER + " = " + village_id + " ORDER BY " + DbConstants.KEY_VILLAGE_NAME + " ASC ";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        DpcVillageDto dpcVillageDto = null;
        if (cursor.moveToFirst()) {
            do {
                dpcVillageDto = new DpcVillageDto();
                dpcVillageDto.setName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_VILLAGE_NAME)));
                dpcVillageDto.setLvillageName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_VILLAGE_LVILLAGE_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dpcVillageDto;
    }

    public List<DPCProcurementDto> getProcurement() {
        procurement_list_sync.clear();
        String selectQuery = "SELECT * from " + DBTables.TABLE_DPC_PROCUREMENT + " where " + DbConstants.KEY_PROCUREMENT_SYNC_STATUS + " = 'A'";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DPCProcurementDto procurement_list = new DPCProcurementDto();
                procurement_list.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                procurement_list.setAdditionalCut(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_ADDITIONAL_CUT)));
                procurement_list.setCreatedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_BY)));
                procurement_list.setCreatedDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_DATE)));
                procurement_list.setGradeCut(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_GRADE_CUT)));
                procurement_list.setLotNumber(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_LOT_NUMBER)));
                procurement_list.setModifiedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_BY)));
                procurement_list.setModifiedDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_DATE)));
                procurement_list.setMoistureContent(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_MOISTURE_CONTENT)));
                procurement_list.setMoistureCut(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_MOISTURE_CUT)));
                procurement_list.setNetAmount(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_NET_AMOUNT)));
                procurement_list.setNetWeight(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_NET_WEIGHT)));
                procurement_list.setNumberOfBags(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_NUMBER_OF_BAGS)));
                procurement_list.setProcurementReceiptNo(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER)));
                procurement_list.setSpillageQuantity(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SPILLAGE_QTY)));
                procurement_list.setTotalAmount(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TOTAL_AMOUNT)));
                procurement_list.setTotalCut(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TOTAL_CUT)));
                procurement_list.setTotalCutAmount(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TOTAL_CUT_AMOUNT)));
                DeviceDto device_dto = new DeviceDto();
                device_dto.setDeviceNumber(Util.device_number);
                procurement_list.setDeviceDto(device_dto);
                DpcPaddyRateDto rate_dto = new DpcPaddyRateDto();
                rate_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PADDY_RATE_ID)));
                procurement_list.setDpcPaddyRateDto(rate_dto);
                DPCProfileDto profile_dto = new DPCProfileDto();
                profile_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PROFILE_ID)));
                procurement_list.setDpcProfileDto(profile_dto);
                FarmerRegistrationDto farmer_reg_dto = new FarmerRegistrationDto();
                farmer_reg_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_FARMER_REG_ID)));
                procurement_list.setFarmerRegistrationDto(farmer_reg_dto);
                PaddyCategoryDto paddy_dto = new PaddyCategoryDto();
                paddy_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PADDY_CATEGORY_ID)));
                procurement_list.setPaddyCategoryDto(paddy_dto);
                PaddyGradeDto grade_dto = new PaddyGradeDto();
                grade_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PADDY_GRADE_ID)));
                procurement_list.setPaddyGradeDto(grade_dto);
                procurement_list.setSyncStatus(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SYNC_STATUS)));
                procurement_list.setMode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_MODE)));
                procurement_list.setSmsRefNumber(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SMS_REFERENCE_NUMBER)));
                procurement_list.setLastSyncTime(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_LAST_SYNC_TIME)));
                procurement_list.setTxnDateTime(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TRANSACTION_DATE)));
                DpcSpecificationDto spec = new DpcSpecificationDto();
                spec.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SPECIFICATION_ID)));
                procurement_list.setDpcSpecificationDto(spec);
                procurement_list_sync.add(procurement_list);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return procurement_list_sync;

    }

    public int getUnSyncedProcurementCountByCode(long id) {
        int count = 0;
        String selectQuery = "SELECT  count(*) FROM " + DBTables.TABLE_DPC_PROCUREMENT + " where " + DbConstants.KEY_PROCUREMENT_SYNC_STATUS + " = 'A' And " + DbConstants.KEY_PROCUREMENT_FARMER_REG_ID + " = " + id;
        Log.e("THE QUERY IS", "" + selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public int getUnSyncedProcurementCountByDate(String date) {
        int count = 0;
        String selectQuery = "SELECT  count(*) FROM " + DBTables.TABLE_DPC_PROCUREMENT + " where " + DbConstants.KEY_PROCUREMENT_SYNC_STATUS + " = 'A' AND date(" + DbConstants.KEY_PROCUREMENT_TRANSACTION_DATE + ") ='" + date + "'";
        Log.e("THE QUERY IS", "" + selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public int getUnSyncedProcurementCountByPaddy(long id) {
        int count = 0;
        String selectQuery = "SELECT  count(*) FROM " + DBTables.TABLE_DPC_PROCUREMENT + " where " + DbConstants.KEY_PROCUREMENT_SYNC_STATUS + " = 'A' AND " + DbConstants.KEY_PROCUREMENT_PADDY_CATEGORY_ID + " = " + id;
        Log.e("THE QUERY IS", "" + selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public int getUnSyncedTruckmemoCountByDate(String date) {
        /*String selectQuery = "SELECT  count(*) FROM " + DBTables.TABLE_DPC_TRUCK_MEMO + " where " + DbConstants.KEY_TRUCKMEMO_SYNC_STATUS + " = 'A'date(" + DbConstants.KEY_PROCUREMENT_TRANSACTION_DATE + ") ='" + date + "'"*/
        int count = 0;
        String selectQuery = "SELECT  count(*) FROM " + DBTables.TABLE_DPC_TRUCK_MEMO + " where " + DbConstants.KEY_TRUCKMEMO_SYNC_STATUS + " = 'A' AND date(" + DbConstants.KEY_PROCUREMENT_TRANSACTION_DATE + ") ='" + date + "'";
        Log.e("THE QUERY IS", "" + selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public int getUnSyncedTruckmemoCountByCap(Long id) {
        int count = 0;
        String selectQuery = "SELECT  count(*) FROM " + DBTables.TABLE_DPC_TRUCK_MEMO + " where " + DbConstants.KEY_TRUCKMEMO_SYNC_STATUS + " = 'A' AND " + DbConstants.KEY_TRUCKMEMO_DPC_CAP_ID + " = " + id;
        Log.e("THE QUERY IS", "" + selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public int getUnSyncedTruckmemoCountByPaddy(long id) {
        int count = 0;
        String selectQuery = "SELECT  count(*) FROM " + DBTables.TABLE_DPC_TRUCK_MEMO + " where " + DbConstants.KEY_TRUCKMEMO_SYNC_STATUS + " = 'A' AND " + DbConstants.KEY_TRUCKMEMO_PADDY_CATEGORY_ID + " = " + id;
        Log.e("THE QUERY IS", "" + selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public List<DPCProcurementDto> getProcurementByPaddy(long id) {
        procurement_list_sync.clear();
        String selectQuery = "SELECT * from " + DBTables.TABLE_DPC_PROCUREMENT + " where " + DbConstants.KEY_PROCUREMENT_PADDY_CATEGORY_ID + " = " + id + " ORDER BY " + DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER + " DESC";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DPCProcurementDto procurement_list = new DPCProcurementDto();
                procurement_list.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                procurement_list.setAdditionalCut(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_ADDITIONAL_CUT)));
                procurement_list.setCreatedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_BY)));
                procurement_list.setCreatedDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_DATE)));
                procurement_list.setGradeCut(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_GRADE_CUT)));
                procurement_list.setLotNumber(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_LOT_NUMBER)));
                procurement_list.setModifiedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_BY)));
                procurement_list.setModifiedDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_DATE)));
                procurement_list.setMoistureContent(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_MOISTURE_CONTENT)));
                procurement_list.setMoistureCut(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_MOISTURE_CUT)));
                procurement_list.setNetAmount(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_NET_AMOUNT)));
                procurement_list.setNetWeight(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_NET_WEIGHT)));
                procurement_list.setNumberOfBags(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_NUMBER_OF_BAGS)));
                procurement_list.setProcurementReceiptNo(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER)));
                procurement_list.setSpillageQuantity(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SPILLAGE_QTY)));
                procurement_list.setTotalAmount(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TOTAL_AMOUNT)));
                procurement_list.setTotalCut(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TOTAL_CUT)));
                procurement_list.setTotalCutAmount(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TOTAL_CUT_AMOUNT)));
                DeviceDto device_dto = new DeviceDto();
                device_dto.setDeviceNumber(Util.device_number);
                procurement_list.setDeviceDto(device_dto);
                DpcPaddyRateDto rate_dto = new DpcPaddyRateDto();
                rate_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PADDY_RATE_ID)));
                procurement_list.setDpcPaddyRateDto(rate_dto);
                DPCProfileDto profile_dto = new DPCProfileDto();
                profile_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PROFILE_ID)));
                procurement_list.setDpcProfileDto(profile_dto);
                FarmerRegistrationDto farmer_reg_dto = new FarmerRegistrationDto();
                farmer_reg_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_FARMER_REG_ID)));
                procurement_list.setFarmerRegistrationDto(farmer_reg_dto);
                PaddyCategoryDto paddy_dto = new PaddyCategoryDto();
                paddy_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PADDY_CATEGORY_ID)));
                procurement_list.setPaddyCategoryDto(paddy_dto);
                PaddyGradeDto grade_dto = new PaddyGradeDto();
                grade_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PADDY_GRADE_ID)));
                procurement_list.setPaddyGradeDto(grade_dto);
                procurement_list.setSyncStatus(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SYNC_STATUS)));
                procurement_list.setMode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_MODE)));
                procurement_list.setSmsRefNumber(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SMS_REFERENCE_NUMBER)));
                procurement_list.setLastSyncTime(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_LAST_SYNC_TIME)));
                procurement_list.setTxnDateTime(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TRANSACTION_DATE)));
                DpcSpecificationDto spec = new DpcSpecificationDto();
                spec.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SPECIFICATION_ID)));
                procurement_list.setDpcSpecificationDto(spec);
                procurement_list_sync.add(procurement_list);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return procurement_list_sync;
    }

    public List<DPCProcurementDto> getProcurementByDate(String date) {
        procurement_list_sync.clear();
        String selectQuery = "SELECT * from " + DBTables.TABLE_DPC_PROCUREMENT + " where date(" + DbConstants.KEY_PROCUREMENT_TRANSACTION_DATE + ") ='" + date + "' ORDER BY " + DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER + " DESC";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DPCProcurementDto procurement_list = new DPCProcurementDto();
                procurement_list.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                procurement_list.setAdditionalCut(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_ADDITIONAL_CUT)));
                procurement_list.setCreatedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_BY)));
                procurement_list.setCreatedDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_DATE)));
                procurement_list.setGradeCut(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_GRADE_CUT)));
                procurement_list.setLotNumber(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_LOT_NUMBER)));
                procurement_list.setModifiedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_BY)));
                procurement_list.setModifiedDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_DATE)));
                procurement_list.setMoistureContent(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_MOISTURE_CONTENT)));
                procurement_list.setMoistureCut(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_MOISTURE_CUT)));
                procurement_list.setNetAmount(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_NET_AMOUNT)));
                procurement_list.setNetWeight(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_NET_WEIGHT)));
                procurement_list.setNumberOfBags(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_NUMBER_OF_BAGS)));
                procurement_list.setProcurementReceiptNo(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER)));
                procurement_list.setSpillageQuantity(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SPILLAGE_QTY)));
                procurement_list.setTotalAmount(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TOTAL_AMOUNT)));
                procurement_list.setTotalCut(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TOTAL_CUT)));
                procurement_list.setTotalCutAmount(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TOTAL_CUT_AMOUNT)));
                DeviceDto device_dto = new DeviceDto();
                device_dto.setDeviceNumber(Util.device_number);
                procurement_list.setDeviceDto(device_dto);
                DpcPaddyRateDto rate_dto = new DpcPaddyRateDto();
                rate_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PADDY_RATE_ID)));
                procurement_list.setDpcPaddyRateDto(rate_dto);
                DPCProfileDto profile_dto = new DPCProfileDto();
                profile_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PROFILE_ID)));
                procurement_list.setDpcProfileDto(profile_dto);
                FarmerRegistrationDto farmer_reg_dto = new FarmerRegistrationDto();
                farmer_reg_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_FARMER_REG_ID)));
                procurement_list.setFarmerRegistrationDto(farmer_reg_dto);
                PaddyCategoryDto paddy_dto = new PaddyCategoryDto();
                paddy_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PADDY_CATEGORY_ID)));
                procurement_list.setPaddyCategoryDto(paddy_dto);
                PaddyGradeDto grade_dto = new PaddyGradeDto();
                grade_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PADDY_GRADE_ID)));
                procurement_list.setPaddyGradeDto(grade_dto);
                procurement_list.setSyncStatus(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SYNC_STATUS)));
                procurement_list.setMode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_MODE)));
                procurement_list.setSmsRefNumber(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SMS_REFERENCE_NUMBER)));
                procurement_list.setLastSyncTime(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_LAST_SYNC_TIME)));
                procurement_list.setTxnDateTime(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TRANSACTION_DATE)));
                DpcSpecificationDto spec = new DpcSpecificationDto();
                spec.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SPECIFICATION_ID)));
                procurement_list.setDpcSpecificationDto(spec);
                procurement_list_sync.add(procurement_list);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return procurement_list_sync;
    }

    public FarmerRegistrationDto getAssociatedFarmerCode(long id) {
        String selectQuery = "SELECT * from " + DBTables.TABLE_ASSOCIATED_FARMERS + " where " + DbConstants.KEY_ID_SERVER + " = " + id;
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        FarmerRegistrationDto list = new FarmerRegistrationDto();
        if (cursor.moveToFirst()) {
            do {
                list.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                list.setFarmerName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_NAME)));
                list.setFarmerCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_CODE)));
                list.setFarmerLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public FarmerRegistrationDto getAssociatedFarmerId(String code) {
        String selectQuery = "SELECT * from " + DBTables.TABLE_ASSOCIATED_FARMERS + " where " + DbConstants.KEY_ASSOCIATED_FARMER_CODE + " = " + code;
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        FarmerRegistrationDto list = new FarmerRegistrationDto();
        if (cursor.moveToFirst()) {
            do {
                list.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                list.setFarmerName(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_NAME)));
                list.setFarmerCode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_CODE)));
                list.setFarmerLname(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_ASSOCIATED_FARMER_NAME)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public List<DPCProcurementDto> getProcurementById(long id) {
        procurement_list_sync.clear();
        String selectQuery = "SELECT * from " + DBTables.TABLE_DPC_PROCUREMENT + " where " + DbConstants.KEY_PROCUREMENT_FARMER_REG_ID + " = " + id + " ORDER BY " + DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER + " DESC";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DPCProcurementDto procurement_list = new DPCProcurementDto();
                procurement_list.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                procurement_list.setAdditionalCut(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_ADDITIONAL_CUT)));
                procurement_list.setCreatedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_BY)));
                procurement_list.setCreatedDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_DATE)));
                procurement_list.setGradeCut(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_GRADE_CUT)));
                procurement_list.setLotNumber(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_LOT_NUMBER)));
                procurement_list.setModifiedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_BY)));
                procurement_list.setModifiedDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_DATE)));
                procurement_list.setMoistureContent(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_MOISTURE_CONTENT)));
                procurement_list.setMoistureCut(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_MOISTURE_CUT)));
                procurement_list.setNetAmount(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_NET_AMOUNT)));
                procurement_list.setNetWeight(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_NET_WEIGHT)));
                procurement_list.setNumberOfBags(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_NUMBER_OF_BAGS)));
                procurement_list.setProcurementReceiptNo(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER)));
                procurement_list.setSpillageQuantity(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SPILLAGE_QTY)));
                procurement_list.setTotalAmount(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TOTAL_AMOUNT)));
                procurement_list.setTotalCut(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TOTAL_CUT)));
                procurement_list.setTotalCutAmount(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TOTAL_CUT_AMOUNT)));
                DeviceDto device_dto = new DeviceDto();
                device_dto.setDeviceNumber(Util.device_number);
                procurement_list.setDeviceDto(device_dto);
                DpcPaddyRateDto rate_dto = new DpcPaddyRateDto();
                rate_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PADDY_RATE_ID)));
                procurement_list.setDpcPaddyRateDto(rate_dto);
                DPCProfileDto profile_dto = new DPCProfileDto();
                profile_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PROFILE_ID)));
                procurement_list.setDpcProfileDto(profile_dto);
                FarmerRegistrationDto farmer_reg_dto = new FarmerRegistrationDto();
                farmer_reg_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_FARMER_REG_ID)));
                procurement_list.setFarmerRegistrationDto(farmer_reg_dto);
                PaddyCategoryDto paddy_dto = new PaddyCategoryDto();
                paddy_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PADDY_CATEGORY_ID)));
                procurement_list.setPaddyCategoryDto(paddy_dto);
                PaddyGradeDto grade_dto = new PaddyGradeDto();
                grade_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PADDY_GRADE_ID)));
                procurement_list.setPaddyGradeDto(grade_dto);
                procurement_list.setSyncStatus(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SYNC_STATUS)));
                procurement_list.setMode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_MODE)));
                procurement_list.setSmsRefNumber(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SMS_REFERENCE_NUMBER)));
                procurement_list.setLastSyncTime(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_LAST_SYNC_TIME)));
                procurement_list.setTxnDateTime(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TRANSACTION_DATE)));
                DpcSpecificationDto spec = new DpcSpecificationDto();
                spec.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SPECIFICATION_ID)));
                procurement_list.setDpcSpecificationDto(spec);
                procurement_list_sync.add(procurement_list);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return procurement_list_sync;
    }

    public DPCProcurementDto getProcurement_history(String receiptNumber) {
        String selectQuery = "SELECT * from " + DBTables.TABLE_DPC_PROCUREMENT + " where " + DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER + " = '" + receiptNumber + "'";
        Log.e("THE QUERY IS  :", selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        DPCProcurementDto procurement_list = null;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                procurement_list = new DPCProcurementDto();
                procurement_list.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
                procurement_list.setAdditionalCut(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_ADDITIONAL_CUT)));
                procurement_list.setCreatedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_BY)));
                procurement_list.setCreatedDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_CREATED_DATE)));
                procurement_list.setGradeCut(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_GRADE_CUT)));
                procurement_list.setLotNumber(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_LOT_NUMBER)));
                procurement_list.setModifiedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_BY)));
                procurement_list.setModifiedDate(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_MODIFIED_DATE)));
                procurement_list.setMoistureContent(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_MOISTURE_CONTENT)));
                procurement_list.setMoistureCut(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_MOISTURE_CUT)));
                procurement_list.setNetAmount(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_NET_AMOUNT)));
                procurement_list.setNetWeight(cursor.getDouble(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_NET_WEIGHT)));
                procurement_list.setNumberOfBags(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_NUMBER_OF_BAGS)));
                procurement_list.setProcurementReceiptNo(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER)));
                procurement_list.setSpillageQuantity(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SPILLAGE_QTY)));
                procurement_list.setTotalAmount(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TOTAL_AMOUNT)));
                procurement_list.setTotalCut(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TOTAL_CUT)));
                procurement_list.setTotalCutAmount(cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TOTAL_CUT_AMOUNT)));
                DeviceDto device_dto = new DeviceDto();
                device_dto.setDeviceNumber(Util.device_number);
                procurement_list.setDeviceDto(device_dto);
                DpcPaddyRateDto rate_dto = new DpcPaddyRateDto();
                rate_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PADDY_RATE_ID)));
                procurement_list.setDpcPaddyRateDto(rate_dto);
                DPCProfileDto profile_dto = new DPCProfileDto();
                profile_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PROFILE_ID)));
                procurement_list.setDpcProfileDto(profile_dto);
                FarmerRegistrationDto farmer_reg_dto = new FarmerRegistrationDto();
                farmer_reg_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_FARMER_REG_ID)));
                procurement_list.setFarmerRegistrationDto(farmer_reg_dto);
                PaddyCategoryDto paddy_dto = new PaddyCategoryDto();
                paddy_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PADDY_CATEGORY_ID)));
                procurement_list.setPaddyCategoryDto(paddy_dto);
                PaddyGradeDto grade_dto = new PaddyGradeDto();
                grade_dto.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_PADDY_GRADE_ID)));
                procurement_list.setPaddyGradeDto(grade_dto);
                procurement_list.setSyncStatus(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SYNC_STATUS)));
                procurement_list.setMode(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_MODE)));
                procurement_list.setSmsRefNumber(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SMS_REFERENCE_NUMBER)));
                procurement_list.setLastSyncTime(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_LAST_SYNC_TIME)));
                procurement_list.setTxnDateTime(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_TRANSACTION_DATE)));
                DpcSpecificationDto spec = new DpcSpecificationDto();
                spec.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_PROCUREMENT_SPECIFICATION_ID)));
                procurement_list.setDpcSpecificationDto(spec);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return procurement_list;
    }

    public int getUnSyncedProcurementCount() {
        int count = 0;
        String selectQuery = "SELECT  count(*) FROM " + DBTables.TABLE_DPC_PROCUREMENT + " where " + DbConstants.KEY_PROCUREMENT_SYNC_STATUS + " = 'A' ";
        Log.e("THE QUERY IS", "" + selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }

    public int getUnSyncedTruckmemoCount() {
        /*String selectQuery = "SELECT  count(*) FROM " + DBTables.TABLE_DPC_TRUCK_MEMO + " where " + DbConstants.KEY_TRUCKMEMO_SYNC_STATUS + " = 'A'date(" + DbConstants.KEY_PROCUREMENT_TRANSACTION_DATE + ") ='" + date + "'"*/
        int count = 0;
        String selectQuery = "SELECT  count(*) FROM " + DBTables.TABLE_DPC_TRUCK_MEMO + " where " + DbConstants.KEY_TRUCKMEMO_SYNC_STATUS + " = 'A'";
        Log.e("THE QUERY IS", "" + selectQuery);
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }
        cursor.close();
        return count;
    }
//    public ResponseDto getUser() {
//        try {
//            String selectQuery = "SELECT  * FROM " + DBTables.TABLE_USER + " LIMIT 1";
//            SQLiteDatabase db = this.getReadableDatabase();
//            Cursor cursor = db.rawQuery(selectQuery, null);
//            UserDetailDto userDetail = null;
//            ResponseDto loginResponse = null;
//            if (cursor.moveToFirst()) {
//                userDetail = new UserDetailDto();
//                loginResponse = new ResponseDto();
//                userDetail.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
////                userDetail.setCreatedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
////                userDetail.setModifiedBy(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_ID_SERVER)));
//                userDetail.setUsername(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_ID)));
//                userDetail.setUserId(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USER_NAME)));
////                userDetail.setPassword(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_USERS_PASS_HASH)));
////                userDetail.setProfile(cursor.getString(cursor.getColumnIndex(DbConstants.KEY_PROFILE)));
////                int status = cursor.getInt(cursor.getColumnIndex(DbConstants.KEY_USER_ACTIVE));
////                if (status == 0) userDetail.setActive(false);
////                else userDetail.setActive(true);
//                DPCProfileDto dpc = new DPCProfileDto();
//                dpc.setId(cursor.getLong(cursor.getColumnIndex(DbConstants.KEY_USER_DPC_ID)));
//                loginResponse.setDpcProfileDto(dpc);
//                loginResponse.setUserDetailDto(userDetail);
//            } else {
//                System.out.println("Shiva enter the moveToFirst else method :::");
//            }
//            return loginResponse;
//        } catch (Exception e) {
//            Log.e("UserDetailDto", "Exception = " + e);
//        }
//        return null;
//    }
}
