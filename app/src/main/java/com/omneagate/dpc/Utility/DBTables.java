package com.omneagate.dpc.Utility;

/**
 * Created by user on 27/6/16.
 */
public class DBTables {
    //Key for id in tables
    public final static String KEY_ID = "_id";
    public static final String TABLE_USER = "table_user";
    public static final String TABLE_DPC_PROFILE = "dpc_profile";
    public static final String TABLE_CONFIG_TABLE = "configuration";
    public static final String TABLE_VILLAGE = "dpc_village";
    public static final String TABLE_BANK = "dpc_bank";
    public static final String TABLE_DPC_TALUK = "dpc_taluk";
    public static final String TABLE_LAND_TYPE = "dpc_land_type";
    public static final String TABLE_DPC_DISTRICT = "dpc_district";
    public static final String TABLE_PADDY_CATEGORY = "dpc_paddy_category";
    public static final String TABLE_DPC_STATE = "dpc_state";
    public static final String TABLE_GRADE = "dpc_grade";
    public static final String TABLE_UPGRADE = "table_upgrade";
    public static final String TABLE_FARMER = "dpc_farmer_registration_request";
    public static final String TABLE_LAND_DETAILS = "dpc_farmer_land_details";
    public static final String TABLE_ASSOCIATED_FARMERS = "dpc_farmer_registration";
    public static final String TABLE_ASSOCIATED_LAND_DETAILS = "dpc_farmer_approved_land_details";
    public static final String TABLE_LOGIN_HISTORY = "login_history";
    public static final String TABLE_DPC_RATE = "dpc_paddy_rate";
    public static final String TABLE_DPC_PROCUREMENT = "dpc_procurement";
    public static final String TABLE_DPC_CAB = "dpc_cap";
    public static final String TABLE_DPC_TRUCK_MEMO = "dpc_truck_memo";
    public static final String TABLE_DPC_SPECIFICATION = "dpc_specification";

    //        public static final String TABLE_REVENVE_VILLAGE = "dpc_revenue_village";
//        public static final String TABLE_DPC_SOCIETY = "TABLE_DPC_SOCIETY";
//        public static final String TABLE_FARMER_CLASS = "TABLE_FARMER_CLASS";
//        public static final String TABLE_CROP_NAME = "TABLE_CROP_NAME";
    // Configuration table
    public static final String CREATE_MASTER_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONFIG_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + "name VARCHAR(50) NOT NULL UNIQUE,value VARCHAR(150)  UNIQUE" + " )";
    public static final String CREATE_TABLE_USERS = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + DbConstants.KEY_ID_SERVER + " INTEGER, "
            + DbConstants.KEY_USER_NAME + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_PROFILE + " VARCHAR(255) NOT NULL, "
            + DbConstants.KEY_USER_ID + " VARCHAR(150) NOT NULL,"
            + DbConstants.KEY_USERS_PASS_HASH + " VARCHAR(150), "
            + DbConstants.KEY_USER_ACTIVE + " INTEGER, "
            + DbConstants.KEY_DPC_CONTACT_PERSON + " VARCHAR(150) NOT NULL, "
            + DbConstants.KEY_CONTACT_NUMBER + " VARCHAR(150) NOT NULL,"
            + DbConstants.KEY_CREATED_DATE + " INTEGER, "
            + DbConstants.KEY_CREATED_BY + " INTEGER, "
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER, "
            + DbConstants.KEY_USER_ENCRYPTED_PASSWORD + " VARCHAR(250), "
            + DbConstants.KEY_STATUS + " INTEGER, "
            + DbConstants.KEY_MODIFIED_BY + " INTEGER, "
            + DbConstants.KEY_USER_DPC_ID + " INTEGER " +
            " )";
    public static final String CREATE_TABLE_DPC_PROFILE = "CREATE TABLE IF NOT EXISTS " + TABLE_DPC_PROFILE + "("
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE, "
            + DbConstants.KEY_USER_DPC_ID + " INTEGER, "
            + DbConstants.KEY_USER_DPC_ACTIVE + " INTEGER, "
            + DbConstants.KEY_USER_DPC_ADDRESS_LINE_1 + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_USER_DPC_ADDRESS_LINE_2 + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_USER_DPC_ADDRESS_LINE_3 + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_USER_DPC_CODE + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_USER_DPC_CONTACT_NUMBER + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_USER_DPC_CONTACT_PERSON + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_USER_DPC_CREATED_BY + " INTEGER, "
            + DbConstants.KEY_USER_DPC_CREATED_DATE + " INTEGER, "
            + DbConstants.KEY_USER_DPC_GEOFENCING + " INTEGER, "
            + DbConstants.KEY_USER_DPC_LATITUDE + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_USER_DPC_LONGITUDE + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_USER_DPC_MODIFIED_BY + " INTEGER, "
            + DbConstants.KEY_USER_DPC_MODIFIED_DATE + " INTEGER, "
            + DbConstants.KEY_USER_DPC_NAME + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_USER_DPC_PINCODE + " INTEGER, "
            + DbConstants.KEY_USER_DPC_VERSION + " INTEGER, "
            + DbConstants.KEY_USER_DPC_DEVICE_ID + " INTEGER, "
            + DbConstants.KEY_USER_DPC_ADDRESS + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_USER_DPC_CATEGORY + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_USER_DPC_TYPE + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_USER_DPC_EMAIL_ADDRESS + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_USER_DPC_GENERATED_CODE + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_USER_DPC_NUMBER_OF_PEOPLE + " INTEGER, "
            + DbConstants.KEY_USER_DPC_REMOTE_LOG + " INTEGER, "
            + DbConstants.KEY_USER_DPC_STORAGE_CAPACITY + " INTEGER, "
            + DbConstants.KEY_USER_DPC_WEIGHING_MACHINE + " INTEGER, "
            + DbConstants.KEY_USER_DPC_STATUS + " INTEGER, "
            + DbConstants.KEY_USER_DPC_REGIONAL_NAME + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_USER_DPC_MOISTURE_METER + " INTEGER, "
            + DbConstants.KEY_USER_DPC_PLATFORM_SCALES + " INTEGER, "
            + DbConstants.KEY_USER_DPC_WINNOING_MACHINE + " INTEGER, "
            + DbConstants.KEY_USER_DPC_DISTRICT_ID + " INTEGER, "
            + DbConstants.KEY_USER_DPC_TALUK_ID + " INTEGER, "
            + DbConstants.KEY_USER_DPC_VILLAGE_ID + " INTEGER " +
            " )";
    public static final String CREATE_TABLE_LOGIN_HISTORY = "CREATE TABLE IF NOT EXISTS " + TABLE_LOGIN_HISTORY + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DbConstants.KEY_LOGIN_TIME + " VARCHAR(60),"
            + DbConstants.KEY_LOGIN_TYPE + " VARCHAR(50),"
            + DbConstants.KEY_USER_ID + " INTEGER,"
            + DbConstants.KEY_LOGOUT_TIME + " VARCHAR(60),"
            + DbConstants.KEY_LOGOUT_TYPE + " VARCHAR(50),"
            + DbConstants.KEY_USER_DPC_ID + " INTEGER,"
            + DbConstants.KEY_TRANSACTION_ID + " VARCHAR(50),"
            + DbConstants.KEY_CREATED_TIME + " INTEGER,"
            + DbConstants.KEY_IS_SYNC + " INTEGER,"
            + DbConstants.KEY_IS_LOGOUT_SYNC + " INTEGER)";
    // Table Bank details
    public static final String CREATE_TABLE_BANK = "CREATE TABLE IF NOT EXISTS " + TABLE_BANK + "("
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE, "
            + DbConstants.KEY_BANK_NAME + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_BRANCH_NAME + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_CODE + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_CONTACT_PERSON + " VARCHAR(225) NOT NULL,"
            + DbConstants.KEY_CREATED_BY + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_CREATED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_BANK_REGIONAL_NAME + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_MOBILE_NUMBER + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_MODIFIED_BY + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_STATUS + " INTEGER, "
            + DbConstants.KEY_BANK_DISTRICT_ID + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_BANK_TALUK_ID + " INTEGER DEFAULT NULL " +
            " )";
    // Table Taluk
    public static final String CREATE_TABLE_TALUK = "CREATE TABLE IF NOT EXISTS " + TABLE_DPC_TALUK + "("
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE, "
            + DbConstants.KEY_CODE + " VARCHAR(255) NOT NULL, "
            + DbConstants.KEY_CREATED_BY_ID + " INTEGER  DEFAULT NULL, "
            + DbConstants.KEY_CREATED_DATE + " INTEGER  DEFAULT NULL, "
            + DbConstants.KEY_TALUK_REGIONAL_NAME + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_MODIFIED_BY_ID + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_TALUK_NAME + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_STATUS + " INTEGER, "
            + DbConstants.KEY_TALUK_DISTRICT_ID + " INTEGER " +
            " )";
    // Table Village
    public static final String CREATE_TABLE_VILLAGE = "CREATE TABLE IF NOT EXISTS " + TABLE_VILLAGE + "("
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE,"
            + DbConstants.KEY_CODE + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_CREATED_BY_ID + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_CREATED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_VILLAGE_LVILLAGE_NAME + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_MODIFIED_BY_ID + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_VILLAGE_NAME + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_STATUS + " INTEGER, "
            + DbConstants.KEY_VILLAGE_TALUK_ID + " INTEGER, "
            + DbConstants.KEY_NEW_VILLAGE_CODE + " INTEGER " +
            " )";
    /*// Table Crop
    public static final String CREATE_TABLE_CROP = "CREATE TABLE " + TABLE_CROP_NAME + "("
            + DbConstants.KEY_CROP_ID + " INTEGER PRIMARY KEY,"
            + DbConstants.KEY_CROP_NAME + " VARCHAR(150) NOT NULL,"
            + DbConstants.KEY_CREATED_DATE + " INTEGER, "
            + DbConstants.KEY_CREATED_BY + " INTEGER,"
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER, "
            + DbConstants.KEY_MODIFIED_BY + " INTEGER " +
            " )";*/
    // Table Land
    public static final String CREATE_TABLE_LAND = "CREATE TABLE IF NOT EXISTS " + TABLE_LAND_TYPE + "("
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE, "
            + DbConstants.KEY_CREATED_BY + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_CREATED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_MODIFIED_BY + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_LAND_TYPE + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_STATUS + " INTEGER " +
            " )";

    //Table District
    public static final String CREATE_TABLE_DISTRICT = "CREATE TABLE IF NOT EXISTS " + TABLE_DPC_DISTRICT + "("
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE,"
            + DbConstants.KEY_CODE + " VARCHAR(225) NOT NULL,"
            + DbConstants.KEY_CREATED_BY_ID + " INTEGER,"
            + DbConstants.KEY_CREATED_DATE + " INTEGER,"
            + DbConstants.KEY_DISTRICT_STATE_NAME + " VARCHAR(225) NOT NULL,"
            + DbConstants.KEY_REGIONAL_DISTRICT_NAME + " VARCHAR(225) NOT NULL UNIQUE,"
            + DbConstants.KEY_MODIFIED_BY_ID + " INTEGER,"
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER,"
            + DbConstants.KEY_DISTRICT_NAME + " VARCHAR(225) NOT NULL,"
            + DbConstants.KEY_STATUS + " INTEGER, "
            + DbConstants.KEY_DISTRICT_REGION_CODE + " VARCHAR(225) NOT NULL,"
            + DbConstants.KEY_DELTA_REGION + " INTEGER, "
            + DbConstants.KEY_DISTRICT_STATE_ID + " INTEGER DEFAULT NULL " +
            " )";
    /* + DbConstants.KEY_DISTRICT_STATE_NAME + " VARCHAR(150) NOT NULL " +
    *   + DbConstants.KEY_DISTRICT_CODE + " VARCHAR(225) NOT NULL UNIQUE " +
    *     + DbConstants.KEY_DISTRICT_STATE_ID + " INTEGER NOT NULL,"
    *     + DbConstants.KEY_DISTRICT_ID + " INTEGER PRIMARY KEY, " + */
    /*//Table society
    public static final String CREATE_TABLE_SOCIETY = "CREATE TABLE IF NOT EXISTS " + TABLE_DPC_SOCIETY + "("
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE, "
            + DbConstants.KEY_SOCIETY_ID + " INTEGER PRIMARY KEY, "
            + DbConstants.KEY_SOCIETY_CODE + " VARCHAR(150) NOT NULL, "
            + DbConstants.KEY_SOCIETY_NAME + " VARCHAR(150) NOT NULL, "
            + DbConstants.KEY_STATUS + " INTEGER, "
            + DbConstants.KEY_SOCIETY_REGIONAL_NAME + " VARCHAR(150) NOT NULL, "
            + DbConstants.KEY_SOCIETY_ADDRESS + " VARCHAR(150) NOT NULL, "
            + DbConstants.KEY_CREATED_DATE + " INTEGER, "
            + DbConstants.KEY_CREATED_BY + " INTEGER, "
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER, "
            + DbConstants.KEY_MODIFIED_BY + " INTEGER " +
            " )";
*/
    /*//Table farmer_class
    public static final String CREATE_TABLE_FARMER_CLASS = "CREATE TABLE IF NOT EXISTS " + TABLE_FARMER_CLASS + "("
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE,"
            + DbConstants.KEY_FARMER_CLASS_ID + " INTEGER PRIMARY KEY,"
            + DbConstants.KEY_FARMER_CLASS_CODE + " VARCHAR(150) NOT NULL,"
            + DbConstants.KEY_STATUS + " INTEGER, "
            + DbConstants.KEY_FARMER_CLASS_NAME + " VARCHAR(150) NOT NULL,"
            + DbConstants.KEY_FARMER_CLASS_REGIONAL_NAME + " VARCHAR(150) NOT NULL ,"
            + DbConstants.KEY_CREATED_DATE + " INTEGER, "
            + DbConstants.KEY_CREATED_BY + " INTEGER,"
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER, "
            + DbConstants.KEY_MODIFIED_BY + " INTEGER " +
            " )";*/
    //Table paddy_category
    public static final String CREATE_TABLE_PADDY_CATEGORY = "CREATE TABLE IF NOT EXISTS " + TABLE_PADDY_CATEGORY + "("
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE,"
            + DbConstants.KEY_CODE + " VARCHAR(225) NOT NULL,"
            + DbConstants.KEY_CREATED_BY + " INTEGER DEFAULT 0,"
            + DbConstants.KEY_CREATED_DATE + " INTEGER DEFAULT NULL,"
            + DbConstants.KEY_PADDY_CATEGORY_REGIONAL_NAME + " VARCHAR(225) NOT NULL,"
            + DbConstants.KEY_MODIFIED_BY + " INTEGER DEFAULT 0,"
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER DEFAULT NULL,"
            + DbConstants.KEY_PADDY_CATEGORY_NAME + " VARCHAR(225) NOT NULL,"
            + DbConstants.KEY_STATUS + " INTEGER " +
            " )";
    /*+ DbConstants.KEY_BONOUS_RATE_PER_QUINTAL + " DOUBLE NOT NULL,"
            + DbConstants.KEY_PURCHASE_RATE_PER_QUINTAL + " DOUBLE NOT NULL,"
            + DbConstants.KEY_TOTAL_RATE_PER_QUINTAL + " DOUBLE DEFAULT NULL,"
            + DbConstants.KEY_DPC_GRADE_ID + " INTEGER,"*/
    //Table state
    public static final String CREATE_TABLE_STATE = "CREATE TABLE IF NOT EXISTS " + TABLE_DPC_STATE + "("
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE, "
            + DbConstants.KEY_CODE + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_CREATED_BY_ID + " INTEGER DEFAULT NULL,"
            + DbConstants.KEY_CREATED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_STATE_REGIONAL_NAME + " VARCHAR(255) NOT NULL UNIQUE, "
            + DbConstants.KEY_MODIFIED_BY_ID + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_STATE_NAME + " VARCHAR(150) NOT NULL UNIQUE, "
            + DbConstants.KEY_STATUS + " INTEGER " +
            " )";
    //Table Grade
    public static final String CREATE_TABLE_GRADE = "CREATE TABLE IF NOT EXISTS " + TABLE_GRADE + "("
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE,"
            + DbConstants.KEY_CODE + " VARCHAR(225) NOT NULL,"
            + DbConstants.KEY_CREATED_BY + " INTEGER DEFAULT 0,"
            + DbConstants.KEY_CREATED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_GRADE_REGIONAL_NAME + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_MODIFIED_BY + " INTEGER DEFAULT 0, "
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_GRADE_NAME + " VARCHAR(255) NOT NULL, "
            + DbConstants.KEY_STATUS + " INTEGER " +
            " )";

    public static final String CREATE_TABLE_UPGRADE = "CREATE TABLE if not exists " + TABLE_UPGRADE + "(_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ref_id VARCHAR(30),android_old_version INTEGER,android_new_version INTEGER,"
            + "state VARCHAR(30),description VARCHAR(250),status VARCHAR(20),refer_id VARCHAR(30),created_date VARCHAR(30),server_status INTEGER,execution_time VARCHAR(30))";

    //Table FARMER
    public static final String CREATE_TABLE_FARMER = "CREATE TABLE IF NOT EXISTS " + TABLE_FARMER + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE,"
            + DbConstants.KEY_REFERENCE_NUMBER + " VARCHAR(150) UNIQUE,"
            + DbConstants.KEY_REGISTERED_DATE_TIME + " INTEGER, "
            + DbConstants.KEY_FARMER_NAME + " VARCHAR(75),"
            + DbConstants.KEY_FARMER_LOCAL_NAME + " VARCHAR(75),"
            + DbConstants.KEY_GUARDIAN_TYPE + " VARCHAR(75),"
            + DbConstants.KEY_FARMER_FATHER_HUSBAND_NAME + " VARCHAR(75),"
            + DbConstants.KEY_FARMER_FATHER_HUSBAND_NAME_LOCAL + " VARCHAR(75),"
            + DbConstants.KEY_DATE_OF_BIRTH + " INTEGER,"
            + DbConstants.KEY_FARMER_MOBILE_NUMBER + " VARCHAR(10) NOT NULL UNIQUE,"
            + DbConstants.KEY_FARMER_ADDRESS1 + " VARCHAR(225), "
            + DbConstants.KEY_FARMER_ADDRESS2 + " VARCHAR(225), "
            + DbConstants.KEY_FARMER_VILLAGE_ID + " INTEGER, "
            + DbConstants.KEY_FARMER_TALUK_ID + " INTEGER,"
            + DbConstants.KEY_FARMER_DISTRICT_ID + " INTEGER, "
            + DbConstants.KEY_FARMER_PINCODE + " VARCHAR(6),"
            + DbConstants.KEY_FARMER_COMMUNITY_ID + " INTEGER, "
            + DbConstants.KEY_FARMER_AADHAR_NUMBER + " VARCHAR(12),"
            + DbConstants.KEY_FARMER_BANK_ID + " VARCHAR(75), "
            + DbConstants.KEY_FARMER_BRANCH_NAME + " VARCHAR(75), "
            + DbConstants.KEY_FARMER_ACCOUNT_NUMBER + " VARCHAR(15) NOT NULL, "
            + DbConstants.KEY_FARMER_IFSC_CODE + " VARCHAR(5), "
            + DbConstants.KEY_FARMER_DPC_ID + " INTEGER,"
            + DbConstants.KEY_FARMER_RATION_CARD_NUMBER + "  VARCHAR(10) DEFAULT NULL,"
            + DbConstants.KEY_IS_SERVER_ADDED + " VARCHAR(1)" +
            " )";
    //land details
    public static final String CREATE_TABLE_LAND_DETAILS = "CREATE TABLE IF NOT EXISTS " + TABLE_LAND_DETAILS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE,"
            + DbConstants.KEY_LAND_TYPE_ID + " INTEGER,"
            + DbConstants.KEY_LAND_VILLAGE_ID + " INTEGER,"
            + DbConstants.KEY_LAND_TALUK_ID + " INTEGER,"
            + DbConstants.KEY_LAND_DISTRICT_ID + " INTEGER,"
            + DbConstants.KEY_LAND_FARMER_ID + " INTEGER,"
            + DbConstants.KEY_LAND_MAIN_LORD_NAME + " VARCHAR(75) ,"
            + DbConstants.KEY_FARMER_CROP_NAME_ID + " INTEGER, "
            + DbConstants.KEY_LAND_SURVEY_NUMBER + " VARCHAR(100), "
            + DbConstants.KEY_LAND_LOAN_BOOK_NUMBER + " VARCHAR(50),"
            + DbConstants.KEY_FARMER_AREA + " VARCHAR(10),"
            + DbConstants.KEY_LAND_SEED_ACCUMULATED_KG + " DOUBLE,"
            + DbConstants.KEY_LAND_SEED_NON_ACCUMULATED_KG + " DOUBLE, "
            + DbConstants.KEY_LAND_SEED_ACCUMULATED_QUINTAL + " DOUBLE,"
            + DbConstants.KEY_LAND_SEED_SHOWED_TOTAL_KG + " DOUBLE,"
            + DbConstants.KEY_LAND_PATTA_NUMBER + " VARCHAR(225) ,"
            + DbConstants.KEY_LAND_PROCUREMENT_EXPECTED_DATE + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_LAND_SEED_EXCEPTED_TOTAL_QUINTAL + " DOUBLE,"
            + DbConstants.KEY_LAND_SEED_NON_ACCUMULATED_QUINTAL + " DOUBLE" +
            " )";
    //SyncException table
    public static final String CREATE_TABLE_SYNC_EXCEPTION = "CREATE TABLE IF NOT EXISTS syncException ("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + "dpcId INTEGER, "
            + "device_id VARCHAR(150), "
            + "syncMode VARCHAR(150), "
            + "tableName VARCHAR(150), "
            + "action VARCHAR(150), "
            + "recordId VARCHAR(150), "
            + "lastSyncTime VARCHAR(150), "
            + "rawData VARCHAR(150), "
            + "errorDescription VARCHAR(150), " +
            "isSynced VARCHAR(150)) ";

    //Associated Farmers table
    public static final String CREATE_TABLE_ASSOCIATED_FARMER = "CREATE TABLE IF NOT EXISTS " + TABLE_ASSOCIATED_FARMERS + "("
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE,"
            + DbConstants.KEY_ASSOCIATED_FARMER_AADHAAR_NUMBER + " VARCHAR(12) DEFAULT NULL,"
            + DbConstants.KEY_ASSOCIATED_FARMER_ACCOUNT_NUMBER + " VARCHAR(15) NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_FARMER_ADDRESS_1 + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_FARMER_ADDRESS_2 + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_ASSOCIATED_FARMER_BRANCH_NAME + " VARCHAR(75) NOT NULL, "
            + DbConstants.KEY_CREATED_BY + " INTEGER DEFAULT NULL,"
            + DbConstants.KEY_CREATED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_ASSOCIATED_FARMER_DATE_OF_BIRTH + " INTEGER, "
            + DbConstants.KEY_ASSOCIATED_FARMER_CODE + " VARCHAR(150) NOT NULL UNIQUE, "
            + DbConstants.KEY_ASSOCIATED_FARMER_REGIONAL_NAME + " VARCHAR(75) NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_FARMER_NAME + " VARCHAR(75) NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_FARMER_GUARDIAN_TYPE + " VARCHAR(75) NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_FARMER_GUARDIAN_REGIONAL_NAME + " VARCHAR(75) NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_FARMER_GUARDIAN_NAME + " VARCHAR(75) NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_FARMER_IFSC_CODE + " VARCHAR(75) NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_FARMER_MOBILE_NUMBER + " VARCHAR(10) NOT NULL,"
            + DbConstants.KEY_MODIFIED_BY + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_ASSOCIATED_FARMER_PIN_CODE + " VARCHAR(6) NOT NULL,"
            + DbConstants.KEY_ASSOCIATED_FARMER_BANK_ID + " INTEGER NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_FARMER_DPC_DISTRICT_ID + " INTEGER NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_FARMER_DPC_PROFILE + " INTEGER NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_FARMER_DPC_TALUK_ID + " INTEGER NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_FARMER_DPC_VILLAGE_ID + " INTEGER NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_FARMER_RATION_CARD_NUMBER + "  VARCHAR(10) DEFAULT NULL " +
            " )";
    //Associated Land table
    public static final String CREATE_TABLE_ASSOCIATED_LAND_DETAILS = "CREATE TABLE IF NOT EXISTS " + TABLE_ASSOCIATED_LAND_DETAILS + "("
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE, "
            + DbConstants.KEY_ASSOCIATED_LAND_AREA + " VARCHAR(10) NOT NULL, "
            + DbConstants.KEY_CREATED_BY + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_CREATED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_ASSOCIATED_LAND_EXPECTED_ACCUMULATED + " DOUBLE DEFAULT NULL, "
            + DbConstants.KEY_ASSOCIATED_LAND_EXPECTED_NON_ACCUMULATED + " DOUBLE DEFAULT NULL, "
            + DbConstants.KEY_ASSOCIATED_LAND_EXPECTED_PROCURING_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_ASSOCIATED_LAND_EXPECTED_TOTAL + " DOUBLE DEFAULT NULL, "
            + DbConstants.KEY_ASSOCIATED_LAND_MAIN_LOARD_REGIONAL_NAME + " VARCHAR(75) NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_LAND_MAIN_LOARD_NAME + " VARCHAR(75) NOT NULL, "
            + DbConstants.KEY_MODIFIED_BY + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_ASSOCIATED_LAND_PATTA_NUMBER + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_ASSOCIATED_LAND_SHOWED_ACCUMULATED + " DOUBLE DEFAULT NULL, "
            + DbConstants.KEY_ASSOCIATED_LAND_SHOWED_NON_ACCUMULATED + " DOUBLE DEFAULT NULL, "
            + DbConstants.KEY_ASSOCIATED_LAND_SHOWED_TOTAL + " DOUBLE DEFAULT NULL, "
            + DbConstants.KEY_ASSOCIATED_LAND_SURVEY_NUMBER + " VARCHAR(100) NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_LAND_DPC_DISTRICT_ID + " INTEGER NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_LAND_FARMER_REGISTRATION_ID + " INTEGER NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_LAND_LANDTYPE_ID + " INTEGER NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_LAND_PADYCATEGORY_ID + " INTEGER NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_LAND_DPC_TALUK_ID + " INTEGER NOT NULL, "
            + DbConstants.KEY_ASSOCIATED_LAND_DPC_VILLAGE_ID + " INTEGER NOT NULL " +
            " )";

    //DPC_RATE_TABLE
    public static final String CREATE_TABLE_DPC_RATE = "CREATE TABLE IF NOT EXISTS " + TABLE_DPC_RATE + "("
            + DbConstants.KEY_ID_SERVER + " INTEGER NOT NULL UNIQUE,"
            + DbConstants.KEY_BONUS_RATE + " DOUBLE NOT NULL,"
            + DbConstants.KEY_CREATED_BY_ID + " INTEGER DEFAULT NULL,"
            + DbConstants.KEY_CREATED_DATE + " INTEGER DEFAULT NULL,"
            + DbConstants.KEY_MODIFIED_BY_ID + " INTEGER DEFAULT NULL,"
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER DEFAULT NULL,"
            + DbConstants.KEY_PURCHASE_RATE + " DOUBLE NOT NULL,"
            + DbConstants.KEY_STATUS + " INTEGER, "
            + DbConstants.KEY_TOTAL_RATE + " DOUBLE NOT NULL,"
            + DbConstants.KEY_VALIDITY_DATE_FROM + " INTEGER NOT NULL,"
            + DbConstants.KEY_VALIDITY_DATE_TO + " INTEGER,"
            + DbConstants.KEY_RATE_DPC_PADDY_GRADE_ID + " INTEGER NOT NULL" +
            " )";

    //Dpc_Procurement
    public static final String CREATE_TABLE_DPC_PROCUREMENT = "CREATE TABLE IF NOT EXISTS " + TABLE_DPC_PROCUREMENT + "("
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE,"
            + DbConstants.KEY_PROCUREMENT_ID + " INTEGER PRIMARY KEY,"
            + DbConstants.KEY_PROCUREMENT_ADDITIONAL_CUT + " DOUBLE NOT NULL,"
            + DbConstants.KEY_CREATED_BY + " INTEGER DEFAULT NULL,"
            + DbConstants.KEY_CREATED_DATE + " INTEGER DEFAULT NULL,"
            + DbConstants.KEY_PROCUREMENT_GRADE_CUT + " DOUBLE NOT NULL,"
            + DbConstants.KEY_PROCUREMENT_LOT_NUMBER + " INTEGER NOT NULL,"
            + DbConstants.KEY_MODIFIED_BY + " INTEGER DEFAULT NULL,"
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER DEFAULT NULL,"
            + DbConstants.KEY_PROCUREMENT_MOISTURE_CONTENT + " DOUBLE NOT NULL,"
            + DbConstants.KEY_PROCUREMENT_MOISTURE_CUT + " DOUBLE NOT NULL, "
            + DbConstants.KEY_PROCUREMENT_NET_AMOUNT + " DOUBLE NOT NULL,"
            + DbConstants.KEY_PROCUREMENT_NET_WEIGHT + " DOUBLE NOT NULL,"
            + DbConstants.KEY_PROCUREMENT_NUMBER_OF_BAGS + " INTEGER NOT NULL,"
            + DbConstants.KEY_PROCUREMENT_RECEIPT_NUMBER + " VARCHAR(225) NOT NULL,"
            + DbConstants.KEY_PROCUREMENT_SPILLAGE_QTY + " DOUBLE NOT NULL,"
            + DbConstants.KEY_PROCUREMENT_TOTAL_AMOUNT + " DOUBLE NOT NULL, "
            + DbConstants.KEY_PROCUREMENT_TOTAL_CUT + " DOUBLE NOT NULL,"
            + DbConstants.KEY_PROCUREMENT_TOTAL_CUT_AMOUNT + " DOUBLE NOT NULL,"
            + DbConstants.KEY_PROCUREMENT_DEVICE_ID + " INTEGER,"
            + DbConstants.KEY_PROCUREMENT_PADDY_RATE_ID + " INTEGER NOT NULL,"
            + DbConstants.KEY_PROCUREMENT_PROFILE_ID + " INTEGER NOT NULL,"
            + DbConstants.KEY_PROCUREMENT_FARMER_REG_ID + " INTEGER NOT NULL,"
            + DbConstants.KEY_PROCUREMENT_PADDY_CATEGORY_ID + " INTEGER NOT NULL, "
            + DbConstants.KEY_PROCUREMENT_SERVER_BILL_NO + " VARCHAR(225), "
            + DbConstants.KEY_PROCUREMENT_LAST_SYNC_TIME + " VARCHAR(225), "
            + DbConstants.KEY_PROCUREMENT_TRANSACTION_DATE + " VARCHAR(225), "
            + DbConstants.KEY_PROCUREMENT_PADDY_GRADE_ID + " INTEGER NOT NULL,"
            + DbConstants.KEY_PROCUREMENT_SPECIFICATION_ID + " INTEGER,"
            + DbConstants.KEY_PROCUREMENT_MODE + " VARCHAR(1), "
            + DbConstants.KEY_PROCUREMENT_SMS_REFERENCE_NUMBER + " INTEGER, "
            + DbConstants.KEY_PROCUREMENT_SYNC_STATUS + " VARCHAR(1)" +
            " )";
    //Dpc_Cap
    public static final String CREATE_TABLE_DPC_CAP = " CREATE TABLE IF NOT EXISTS " + TABLE_DPC_CAB + "("
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE, "
            + DbConstants.KEY_GOWNDOWN_ADDRESS + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_GOWNDOWN_CONTACT_NUMBER + " VARCHAR(225), "
            + DbConstants.KEY_CREATED_BY + " INTEGER DEFAULT NULL , "
            + DbConstants.KEY_CREATED_DATE + " INTEGER DEFAULT NULL , "
            + DbConstants.KEY_GOWNDOWN_LATITUDE + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_GOWNDOWN_LONGITIUDE + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_MODIFIED_BY + " INTEGER DEFAULT NULL , "
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER DEFAULT NULL , "
            + DbConstants.KEY_GOWNDOWN_NAME + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_STATUS + " INTEGER NOT NULL , "
            + DbConstants.KEY_GOWNDOWN_DISTRICT_ID + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_GOWNDOWN_TALUK_ID + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_GOWNDOWN_CONTACT_PERSON_NAME + " VARCHAR(225), "
            + DbConstants.KEY_GOWNDOWN_EMAIL_ADDRESS + " VARCHAR(225), "
            + DbConstants.KEY_GOWNDOWN_GENERATED_CAP_CODE + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_GOWNDOWN_GEOFENCING + " INTEGER NOT NULL , "
            + DbConstants.KEY_GOWNDOWN_MOISTURE_METER + " INTEGER, "
            + DbConstants.KEY_GOWNDOWN_NUMBER_PEOPLE + " INTEGER, "
            + DbConstants.KEY_GOWNDOWN_PINCODE + " VARCHAR(225) DEFAULT NULL, "
            + DbConstants.KEY_GOWNDOWN_PLATFORM_SCALE + " INTEGER, "
            + DbConstants.KEY_GOWNDOWN_REMOTE_LOG + " INTEGER, "
            + DbConstants.KEY_GOWNDOWN_VERSION + " INTEGER, "
            + DbConstants.KEY_GOWNDOWN_WEIGHING_MACHINE + " INTEGER, "
            + DbConstants.KEY_GOWNDOWN_WINNOWING_MACHINE + " INTEGER, "
            + DbConstants.KEY_GOWNDOWN_DEVICE_ID + " INTEGER, "
            + DbConstants.KEY_GOWNDOWN_DPC_VILLAGE_ID + " INTEGER, "
            + DbConstants.KEY_GOWNDOWN_CAP_REGIONAL_NAME + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_GOWNDOWN_CAP_CATEGORY + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_GOWNDOWN_CAP_TYPE + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_GOWNDOWN_STORAGE_CAPACITY + " INTEGER NOT NULL "
            + ")";

    public static final String CREATE_TABLE_TRUCK_MEMO = " CREATE TABLE IF NOT EXISTS " + TABLE_DPC_TRUCK_MEMO + " ( "
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE,"
            + DbConstants.KEY_TRUCKMEMO_ID + " INTEGER PRIMARY KEY, "
            + DbConstants.KEY_TRUCKMEMO_CONDITION_OF_GUNNY + " VARCHAR(225) NOT NULL , "
            + DbConstants.KEY_TRUCKMEMO_GUNNY_CAPACITY + " DOUBLE NOT NULL, "
            + DbConstants.KEY_TRUCKMEMO_LORRY_NUMBR + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_TRUCKMEMO_MOISTURE_CONTENT + " DOUBLE NOT NULL, "
            + DbConstants.KEY_TRUCKMEMO_NET_QUANTITY + " DOUBLE NOT NULL, "
            + DbConstants.KEY_TRUCKMEMO_NUMBER_OF_BAGS + " DOUBLE NOT NULL, "
            + DbConstants.KEY_TRUCKMEMO_TRANSPORT_TYPE + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_TRUCKMEMO_TRANSPORTER_NAME + " VARCHAR(225) NOT NULL,"
            + DbConstants.KEY_TRUCKMEMO_TRUCK_MEMO_NUMBER + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_TRUCKMEMO_DPC_CAP_ID + " INTEGER NOT NULL, "
            + DbConstants.KEY_TRUCKMEMO_PADDY_CATEGORY_ID + " INTEGER NOT NULL, "
            + DbConstants.KEY_TRUCKMEMO_GRADE_ID + " INTEGER NOT NULL, "
            + DbConstants.KEY_CREATED_BY + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_CREATED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_MODIFIED_BY + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_TRUCKMEMO_TXN_DATE_TIME + " INTEGER,"
            + DbConstants.KEY_TRUCKMEMO_PROFILE_ID + " INTEGER NOT NULL,"
            + DbConstants.KEY_TRUCKMEMO_SERVER_TRUCK_MEMO_NO + " VARCHAR(225),"
            + DbConstants.KEY_TRUCKMEMO_SPECIFICATION_ID + " INTEGER,"
            + DbConstants.KEY_TRUCKMEMO_SYNC_STATUS + " VARCHAR(1)"
            + ")";

    //Specification
    public static final String CREATE_TABLE_SPECIFICATION = " CREATE TABLE IF NOT EXISTS " + TABLE_DPC_SPECIFICATION + " ( "
            + DbConstants.KEY_ID_SERVER + " INTEGER UNIQUE,"
            + DbConstants.KEY_SPECIFICATION_START_DATE + " INTEGER NOT NULL, "
            + DbConstants.KEY_SPECIFICATION_END_DATE + " INTEGER NOT NULL, "
            + DbConstants.KEY_SPECIFICATION_MOISTURE_FROM + " DOUBLE NOT NULL, "
            + DbConstants.KEY_SPECIFICATION_MOISTURE_TO + " DOUBLE NOT NULL, "
            + DbConstants.KEY_SPECIFICATION_TYPE + " VARCHAR(225) NOT NULL, "
            + DbConstants.KEY_SPECIFICATION_CODE + " INTEGER NOT NULL, "
            + DbConstants.KEY_CREATED_BY + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_CREATED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_MODIFIED_BY + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_MODIFIED_DATE + " INTEGER DEFAULT NULL, "
            + DbConstants.KEY_STATUS + " INTEGER "
            + ")";
}
