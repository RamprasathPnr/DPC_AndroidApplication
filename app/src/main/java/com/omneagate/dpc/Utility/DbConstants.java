package com.omneagate.dpc.Utility;

/**
 * Created by user on 19/7/16.
 */
public class DbConstants {

    /* Common */
    public static final String KEY_CREATED_BY = "created_by";
    public static final String KEY_CREATED_DATE = "created_date";
    public static final String KEY_MODIFIED_BY = "modified_by";
    public static final String KEY_MODIFIED_DATE = "modified_date";
    public static final String KEY_CREATED_BY_ID = "created_by_id";
    public static final String KEY_MODIFIED_BY_ID = "modified_by_id";
    public static final String KEY_STATUS = "status";
    public static final String KEY_ID_SERVER = "id";
    public static final String KEY_CODE = "code";

    /*Table User*/
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_PROFILE = "user_profile";
    public static final String KEY_PASSWORD = "user_password";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USERS_PASS_HASH = "user_password_hash";
    public static final String KEY_DPC_CONTACT_PERSON = "user_contact_person";
    public static final String KEY_CONTACT_NUMBER = "user_contact_number";
    public static final String KEY_USER_ACTIVE = "user_active";
    public static final String KEY_USER_ENCRYPTED_PASSWORD = "user_encrypted_password";


    //dpc_profile
    public static final String KEY_USER_DPC_ID = "dpc_id";
    public static final String KEY_USER_DPC_ACTIVE = "dpc_active";
    public static final String KEY_USER_DPC_ADDRESS_LINE_1 = "address_line1";
    public static final String KEY_USER_DPC_ADDRESS_LINE_2 = "address_line2";
    public static final String KEY_USER_DPC_ADDRESS_LINE_3 = "address_line3";
    public static final String KEY_USER_DPC_CODE = "dpc_code";
    public static final String KEY_USER_DPC_CONTACT_NUMBER = "contact_number";
    public static final String KEY_USER_DPC_CONTACT_PERSON = "contact_person";
    public static final String KEY_USER_DPC_CREATED_BY = "created_by";
    public static final String KEY_USER_DPC_CREATED_DATE = "created_date";
    public static final String KEY_USER_DPC_GEOFENCING = "geofencing";
    public static final String KEY_USER_DPC_LATITUDE = "latitude";
    public static final String KEY_USER_DPC_LONGITUDE = "longitude";
    public static final String KEY_USER_DPC_MODIFIED_BY = "modified_by";
    public static final String KEY_USER_DPC_MODIFIED_DATE = "modified_date";
    public static final String KEY_USER_DPC_NAME = "name";
    public static final String KEY_USER_DPC_PINCODE = "pin_code";
    public static final String KEY_USER_DPC_VERSION = "version";
    public static final String KEY_USER_DPC_DEVICE_ID = "device_id";
    public static final String KEY_USER_DPC_ADDRESS = "address";
    public static final String KEY_USER_DPC_CATEGORY = "dpc_category";
    public static final String KEY_USER_DPC_TYPE = "dpc_type";
    public static final String KEY_USER_DPC_EMAIL_ADDRESS = "email_address";
    public static final String KEY_USER_DPC_GENERATED_CODE = "generated_code";
    public static final String KEY_USER_DPC_NUMBER_OF_PEOPLE = "number_of_people";
    public static final String KEY_USER_DPC_REMOTE_LOG = "remote_log_enabled";
    public static final String KEY_USER_DPC_STORAGE_CAPACITY = "storage_capacity";
    public static final String KEY_USER_DPC_WEIGHING_MACHINE = "weighing_machine_capacity";
    public static final String KEY_USER_DPC_STATUS = "status";
    public static final String KEY_USER_DPC_REGIONAL_NAME = "l_dpc_name";
    public static final String KEY_USER_DPC_MOISTURE_METER = "moisture_meter";
    public static final String KEY_USER_DPC_PLATFORM_SCALES = "platform_scales";
    public static final String KEY_USER_DPC_WINNOING_MACHINE = "winnowing_machine";
    public static final String KEY_USER_DPC_DISTRICT_ID = "dpc_district_id";
    public static final String KEY_USER_DPC_TALUK_ID = "dpc_taluk_id";
    public static final String KEY_USER_DPC_VILLAGE_ID = "dpc_village_id";


    /* Bank Table fields */
    public static final String KEY_BANK_NAME = "name";
    //public static final String KEY_BANK_CODE = "bank_code";
    public static final String KEY_BANK_REGIONAL_NAME = "lname";
    public static final String KEY_BRANCH_NAME = "branch";
    public static final String KEY_CONTACT_PERSON = "contact_person";
    public static final String KEY_MOBILE_NUMBER = "mobile_no";
    public static final String KEY_BANK_DISTRICT_ID = "district_id";
    public static final String KEY_BANK_TALUK_ID = "taluk_id";


    /* Taluk */
    public static final String KEY_TALUK_REGIONAL_NAME = "l_taluk_name";
    public static final String KEY_TALUK_NAME = "name";
    public static final String KEY_TALUK_DISTRICT_ID = "district_id";

    /* Village */
    public static final String KEY_VILLAGE_LVILLAGE_NAME = "l_village_name";
    public static final String KEY_VILLAGE_NAME = "name";
    public static final String KEY_VILLAGE_TALUK_ID = "dpc_taluk_id";
    public static final String KEY_NEW_VILLAGE_CODE = "new_village_code";


    /* Crop */
    /*public static final String KEY_CROP_ID = "id_crop";
    public static final String KEY_CROP_NAME = "crop_name";*/

    /* Land */
    // public static final String KEY_LAND_ID = "id_land";
    public static final String KEY_LAND_TYPE = "name";


    /* District */
    public static final String KEY_DISTRICT_NAME = "name";
    public static final String KEY_REGIONAL_DISTRICT_NAME = "l_district_name";
    public static final String KEY_DISTRICT_STATE_NAME = "district_sname";
    public static final String KEY_DISTRICT_STATE_ID = "dpc_state_id";
    public static final String KEY_DISTRICT_REGION_CODE = "region_code";
    public static final String KEY_DELTA_REGION = "delta_region";


    /*public static final String KEY_DISTRICT_ID = "id_district";
    public static final String KEY_DISTRICT_CODE = "district_code";
    public static final String KEY_DISTRICT_STATE_ID = "state_id";
    public static final String KEY_DISTRICT_STATE_NAME = "district_state_name";*/

    /* SOCIETY  */
    public static final String KEY_SOCIETY_ID = "id_society";
    public static final String KEY_SOCIETY_CODE = "society_code";
    public static final String KEY_SOCIETY_NAME = "society_name";
    public static final String KEY_SOCIETY_REGIONAL_NAME = "society_regional_name";
    public static final String KEY_SOCIETY_ADDRESS = "society_address";

    /* Farmer class   */
    public static final String KEY_FARMER_CLASS_ID = "id_farmer_class";
    public static final String KEY_FARMER_CLASS_CODE = "farmer_class_code";
    public static final String KEY_FARMER_CLASS_NAME = "farmer_class_name";
    public static final String KEY_FARMER_CLASS_REGIONAL_NAME = "farmer_class_regional_name";

    /* Paddy category */
    // public static final String KEY_PADDY_CATEGORY_ID = "id_paddy_category";
    //    public static final String KEY_PADDY_CATEGORY_CODE = "paddy_category_code";
    public static final String KEY_PADDY_CATEGORY_NAME = "name";
    public static final String KEY_PADDY_CATEGORY_REGIONAL_NAME = "lname";
    public static final String KEY_BONOUS_RATE_PER_QUINTAL = "bonus_rate_per_quintal";
    public static final String KEY_PURCHASE_RATE_PER_QUINTAL = "purchase_rate_per_qunital";
    public static final String KEY_TOTAL_RATE_PER_QUINTAL = "total_rate_per_quintal";
    public static final String KEY_DPC_GRADE_ID = "dpc_grade_id";

    /* state */
    public static final String KEY_STATE_NAME = "name";
    public static final String KEY_STATE_REGIONAL_NAME = "l_state_name";

    /* Grade */
    public static final String KEY_GRADE_NAME = "name";
    public static final String KEY_GRADE_REGIONAL_NAME = "lname";
    public static final String KEY_GRADE_CODE = "code";

    /* farmer */
    public static final String KEY_FARMER_AADHAR_NUMBER = "aadhaar_number";
    public static final String KEY_FARMER_ACCOUNT_NUMBER = "account_number";
    public static final String KEY_FARMER_ADDRESS1 = "address1";
    public static final String KEY_FARMER_ADDRESS2 = "address2";
    public static final String KEY_FARMER_BRANCH_NAME = "branch_name";
    public static final String KEY_FARMER_LOCAL_NAME = "farmer_lname";
    public static final String KEY_FARMER_NAME = "farmer_name";
    public static final String KEY_GUARDIAN_TYPE = "guardian_type";
    public static final String KEY_FARMER_FATHER_HUSBAND_NAME_LOCAL = "guardian_lname";
    public static final String KEY_FARMER_FATHER_HUSBAND_NAME = "guardian_name";
    public static final String KEY_FARMER_IFSC_CODE = "ifse_code";
    public static final String KEY_FARMER_MOBILE_NUMBER = "mobile_number";
    public static final String KEY_FARMER_PINCODE = "pin_code";
    public static final String KEY_FARMER_BANK_ID = "bank_id";
    public static final String KEY_FARMER_DISTRICT_ID = "dpc_district_id";
    public static final String KEY_FARMER_DPC_ID = "dpc_profile_id";
    public static final String KEY_FARMER_TALUK_ID = "dpc_taluk_id";
    public static final String KEY_FARMER_VILLAGE_ID = "dpc_village_id";
    public static final String KEY_REFERENCE_NUMBER = "reference_number";
    public static final String KEY_REGISTERED_DATE_TIME = "registered_date";
    public static final String KEY_DATE_OF_BIRTH = "date_of_birth";
    public static final String KEY_FARMER_COMMUNITY_ID = "community_id";
    public static final String KEY_FARMER_CROP_NAME_ID = "crop_name_id";
    public static final String KEY_FARMER_LOAN_BOOK_NUMBER = "loan_book_number";
    public static final String KEY_IS_SERVER_ADDED = "is_server_added";
    public static final String KEY_FARMER_RATION_CARD_NUMBER = "ration_card_number";


    //Request land table
    public static final String KEY_LAND_FARMER_ID = "farmer_id";
    public static final String KEY_LAND_VILLAGE_ID = "dpc_village_id";
    public static final String KEY_LAND_TALUK_ID = "dpc_taluk_id";
    public static final String KEY_LAND_DISTRICT_ID = "dpc_district_id";
    public static final String KEY_LAND_TYPE_ID = "land_type_id";
    public static final String KEY_FARMER_AREA = "farmer_area";
    public static final String KEY_LAND_MAIN_LORD_NAME = "main_lord_name";
    public static final String KEY_LAND_LOAN_BOOK_NUMBER = "loan_book_number";
    public static final String KEY_LAND_SURVEY_NUMBER = "survey_number";
    public static final String KEY_LAND_SEED_ACCUMULATED_KG = "area_of_seed_sowed_Accumulated_Kg";
    public static final String KEY_LAND_SEED_NON_ACCUMULATED_KG = "area_of_seed_sowed_non_Accumulated_Kg";
    public static final String KEY_LAND_SEED_SHOWED_TOTAL_KG = "area_of_seed_sowed_total_kg";
    public static final String KEY_LAND_SEED_ACCUMULATED_QUINTAL = "expected_procurement_accumulated_quintal ";
    public static final String KEY_LAND_SEED_NON_ACCUMULATED_QUINTAL = "expected_procurement_non_accumulated_quintal";
    public static final String KEY_LAND_SEED_EXCEPTED_TOTAL_QUINTAL = "area_of_seed_excepted_total_quintal";

    public static final String KEY_LAND_PATTA_NUMBER = "patta_number";
    public static final String KEY_LAND_PROCUREMENT_EXPECTED_DATE = "procurement_expected_date";
//    public static final String KEY_LAND_REVENUE_VILLAGE_ID = "revenue_village_id";

    /*//Revenue Table
    public static final String KEY_REVRNUE_VILLAGE_ID = "id_village";
    public static final String KEY_REVRNUE_VILLAGE_NAME = "rev_village_name";
    public static final String KEY_REVRNUE_VILLAGE_CODE = "rev_village_code";
    public static final String KEY_REVRNUE_VILLAGE_TALUK_ID = "taluk_id";
    public static final String KEY_REVRNUE_VILLAGE_LVILLAGE_NAME = "rev_village_lname";*/

    //Associated Farmer's table
    public static final String KEY_ASSOCIATED_FARMER_AADHAAR_NUMBER = "aadhaar_number";
    public static final String KEY_ASSOCIATED_FARMER_ACCOUNT_NUMBER = "account_number";
    public static final String KEY_ASSOCIATED_FARMER_ADDRESS_1 = "address1";
    public static final String KEY_ASSOCIATED_FARMER_ADDRESS_2 = "address2";
    public static final String KEY_ASSOCIATED_FARMER_BRANCH_NAME = "branch_name";
    public static final String KEY_ASSOCIATED_FARMER_DATE_OF_BIRTH = "date_of_birth";
    public static final String KEY_ASSOCIATED_FARMER_CODE = "farmer_code";
    public static final String KEY_ASSOCIATED_FARMER_REGIONAL_NAME = "farmer_lname";
    public static final String KEY_ASSOCIATED_FARMER_NAME = "farmer_name";
    public static final String KEY_ASSOCIATED_FARMER_GUARDIAN_TYPE = "guardian_type";
    public static final String KEY_ASSOCIATED_FARMER_GUARDIAN_REGIONAL_NAME = "guardian_lname";
    public static final String KEY_ASSOCIATED_FARMER_GUARDIAN_NAME = "guardian_name";
    public static final String KEY_ASSOCIATED_FARMER_IFSC_CODE = "ifsc_code";
    public static final String KEY_ASSOCIATED_FARMER_MOBILE_NUMBER = "mobile_number";
    public static final String KEY_ASSOCIATED_FARMER_PIN_CODE = "pin_code";
    public static final String KEY_ASSOCIATED_FARMER_BANK_ID = "bank_id";
    public static final String KEY_ASSOCIATED_FARMER_DPC_DISTRICT_ID = "dpc_district_id";
    public static final String KEY_ASSOCIATED_FARMER_DPC_PROFILE = "dpc_profile_id";
    public static final String KEY_ASSOCIATED_FARMER_DPC_TALUK_ID = "dpc_taluk_id";
    public static final String KEY_ASSOCIATED_FARMER_DPC_VILLAGE_ID = "dpc_village_id";
    public static final String KEY_ASSOCIATED_FARMER_RATION_CARD_NUMBER = "ration_card_number";
//    public static final String KEY_ASSOCOATED_FARMER_SOCITY_ID = "society_id";

    //Associated Land Details table
    public static final String KEY_ASSOCIATED_LAND_AREA = "area";
    public static final String KEY_ASSOCIATED_LAND_EXPECTED_ACCUMULATED = "expected_accumulated";
    public static final String KEY_ASSOCIATED_LAND_EXPECTED_NON_ACCUMULATED = "expected_non_accumulated";
    public static final String KEY_ASSOCIATED_LAND_EXPECTED_PROCURING_DATE = "expected_procuring_date";
    public static final String KEY_ASSOCIATED_LAND_EXPECTED_TOTAL = "expected_total";
    public static final String KEY_ASSOCIATED_LAND_MAIN_LOARD_REGIONAL_NAME = "main_land_lord_lname";
    public static final String KEY_ASSOCIATED_LAND_MAIN_LOARD_NAME = "main_land_lord_name";
    public static final String KEY_ASSOCIATED_LAND_PATTA_NUMBER = "patta_number";
    public static final String KEY_ASSOCIATED_LAND_SHOWED_ACCUMULATED = "sowed_accumulated";
    public static final String KEY_ASSOCIATED_LAND_SHOWED_NON_ACCUMULATED = "sowed_non_accumulated";
    public static final String KEY_ASSOCIATED_LAND_SHOWED_TOTAL = "sowed_total";
    public static final String KEY_ASSOCIATED_LAND_SURVEY_NUMBER = "survey_number";
    public static final String KEY_ASSOCIATED_LAND_DPC_DISTRICT_ID = "dpc_district_id_land";
    public static final String KEY_ASSOCIATED_LAND_FARMER_REGISTRATION_ID = "farmer_registration_id";
    public static final String KEY_ASSOCIATED_LAND_LANDTYPE_ID = "land_type_id";
    public static final String KEY_ASSOCIATED_LAND_PADYCATEGORY_ID = "paddy_category_id";
    public static final String KEY_ASSOCIATED_LAND_DPC_TALUK_ID = "dpc_taluk_id_land";
    public static final String KEY_ASSOCIATED_LAND_DPC_VILLAGE_ID = "dpc_village_id_land";

    //Login history  table column  name
    public static final String KEY_LOGIN_TIME = "login_time";
    public static final String KEY_LOGIN_TYPE = "login_type";
    public static final String KEY_LOGOUT_TIME = "logout_time";
    public static final String KEY_LOGOUT_TYPE = "logout_type";
    public static final String KEY_TRANSACTION_ID = "transaction_id";
    public static final String KEY_CREATED_TIME = "created_time";
    public static final String KEY_IS_SYNC = "is_sync";
    public static final String KEY_IS_LOGOUT_SYNC = "is_logout_sync";

    //Dpc_rate
    public static final String KEY_BONUS_RATE = "bonus_rate";
    public static final String KEY_PURCHASE_RATE = "purchase_rate";
    public static final String KEY_TOTAL_RATE = "total_rate";
    public static final String KEY_VALIDITY_DATE_FROM = "validity_date_from";
    public static final String KEY_VALIDITY_DATE_TO = "validity_date_to";
    public static final String KEY_RATE_DPC_PADDY_GRADE_ID = "dpc_paddy_grade_id";

//    public static final String KEY_RATE_DPC_PADDY_CATEGORY = "dpc_paddy_category_id";


    //Dpc_PROCUREMENT
    public static final String KEY_PROCUREMENT_ID = "_id";
    public static final String KEY_PROCUREMENT_ADDITIONAL_CUT = "additional_cut";
    public static final String KEY_PROCUREMENT_GRADE_CUT = "grade_cut";
    public static final String KEY_PROCUREMENT_LOT_NUMBER = "lot_number";
    public static final String KEY_PROCUREMENT_MOISTURE_CONTENT = "moisture_content";
    public static final String KEY_PROCUREMENT_MOISTURE_CUT = "moisture_cut";
    public static final String KEY_PROCUREMENT_NET_AMOUNT = "net_amount";
    public static final String KEY_PROCUREMENT_NET_WEIGHT = "net_weight";
    public static final String KEY_PROCUREMENT_NUMBER_OF_BAGS = "number_of_bags";
    public static final String KEY_PROCUREMENT_RECEIPT_NUMBER = "procurement_receipt_no";
    public static final String KEY_PROCUREMENT_SPILLAGE_QTY = "spillage_quantity";
    public static final String KEY_PROCUREMENT_TOTAL_AMOUNT = "total_amount";
    public static final String KEY_PROCUREMENT_TOTAL_CUT = "total_cut";
    public static final String KEY_PROCUREMENT_TOTAL_CUT_AMOUNT = "total_cut_amount";
    public static final String KEY_PROCUREMENT_DEVICE_ID = "device_id";
    public static final String KEY_PROCUREMENT_PADDY_RATE_ID = "dpc_paddy_rate_id";
    public static final String KEY_PROCUREMENT_FARMER_REG_ID = "dpc_farmer_registration_id";
    public static final String KEY_PROCUREMENT_PROFILE_ID = "dpc_profile_id";
    public static final String KEY_PROCUREMENT_PADDY_CATEGORY_ID = "dpc_paddy_category_id";
    public static final String KEY_PROCUREMENT_PADDY_GRADE_ID = "dpc_grade_id";
    public static final String KEY_PROCUREMENT_SERVER_BILL_NO = "server_procurement_bill_no";
    public static final String KEY_PROCUREMENT_LAST_SYNC_TIME = "last_sync_time";
    public static final String KEY_PROCUREMENT_TRANSACTION_DATE = "tnx_date_time";
    public static final String KEY_PROCUREMENT_MODE = "mode";
    public static final String KEY_PROCUREMENT_SMS_REFERENCE_NUMBER = "sms_ref_number";
    public static final String KEY_PROCUREMENT_SYNC_STATUS = "sync_status";
    public static final String KEY_PROCUREMENT_SPECIFICATION_ID = "dpc_specification_id";


    //DPC_cap
    public static final String KEY_GOWNDOWN_ID = "_id";
    public static final String KEY_GOWNDOWN_ADDRESS = "address";
    public static final String KEY_GOWNDOWN_CONTACT_NUMBER = "contact_number";
    public static final String KEY_GOWNDOWN_LATITUDE = "latitude";
    public static final String KEY_GOWNDOWN_LONGITIUDE = "longitude";
    public static final String KEY_GOWNDOWN_NAME = "name";
    public static final String KEY_GOWNDOWN_DISTRICT_ID = "dpc_district_id";
    public static final String KEY_GOWNDOWN_TALUK_ID = "dpc_taluk_id";
    public static final String KEY_GOWNDOWN_CONTACT_PERSON_NAME = "contact_person";
    public static final String KEY_GOWNDOWN_EMAIL_ADDRESS = "email_address";
    public static final String KEY_GOWNDOWN_GENERATED_CAP_CODE = "generated_code";
    public static final String KEY_GOWNDOWN_GEOFENCING = "geofencing";
    public static final String KEY_GOWNDOWN_MOISTURE_METER = "moisture_meter";
    public static final String KEY_GOWNDOWN_NUMBER_PEOPLE = "number_of_people";
    public static final String KEY_GOWNDOWN_PINCODE = "pincode";
    public static final String KEY_GOWNDOWN_PLATFORM_SCALE = "platform_scales";
    public static final String KEY_GOWNDOWN_REMOTE_LOG = "remote_log_enabled";
    public static final String KEY_GOWNDOWN_VERSION = "version";
    public static final String KEY_GOWNDOWN_WEIGHING_MACHINE = "weighing_machine_capacity";
    public static final String KEY_GOWNDOWN_WINNOWING_MACHINE = "winnowing_machine";
    public static final String KEY_GOWNDOWN_DEVICE_ID = "device_id";
    public static final String KEY_GOWNDOWN_DPC_VILLAGE_ID = "dpc_village_id";
    public static final String KEY_GOWNDOWN_CAP_REGIONAL_NAME = "l_cap_name";
    public static final String KEY_GOWNDOWN_CAP_CATEGORY = "cap_category";
    public static final String KEY_GOWNDOWN_CAP_TYPE = "cap_type";
    public static final String KEY_GOWNDOWN_STORAGE_CAPACITY = "storage_capacity";




    //DPC_Truck_MEMO
    public static final String KEY_TRUCKMEMO_ID = "_id";
    public static final String KEY_TRUCKMEMO_CONDITION_OF_GUNNY = "condition_of_gunny";
    public static final String KEY_TRUCKMEMO_GUNNY_CAPACITY = "gunny_capacity";
    public static final String KEY_TRUCKMEMO_LORRY_NUMBR = "lorry_number";
    public static final String KEY_TRUCKMEMO_MOISTURE_CONTENT = "moisture_content";
    public static final String KEY_TRUCKMEMO_NET_QUANTITY = "net_quantity";
    public static final String KEY_TRUCKMEMO_NUMBER_OF_BAGS = "number_of_bags";
    public static final String KEY_TRUCKMEMO_TRANSPORT_TYPE = "transport_type";
    public static final String KEY_TRUCKMEMO_TRANSPORTER_NAME = "transporter_name";
    public static final String KEY_TRUCKMEMO_TRUCK_MEMO_NUMBER = "truck_memo_number";
    public static final String KEY_TRUCKMEMO_DPC_CAP_ID = "dpc_cap_id";
    public static final String KEY_TRUCKMEMO_PADDY_CATEGORY_ID = "dpc_paddy_category_id";
    public static final String KEY_TRUCKMEMO_GRADE_ID = "dpc_grade_id";
    public static final String KEY_TRUCKMEMO_PROFILE_ID = "dpc_profile_id";
    public static final String KEY_TRUCKMEMO_SYNC_STATUS = "sync_status";
    public static final String KEY_TRUCKMEMO_SERVER_TRUCK_MEMO_NO = "server_truck_memo_no";
    public static final String KEY_TRUCKMEMO_TXN_DATE_TIME = "tnx_date_time";
    public static final String KEY_TRUCKMEMO_SPECIFICATION_ID = "dpc_specification_id";

    //Specification
    public static final String KEY_SPECIFICATION_START_DATE = "start_date";
    public static final String KEY_SPECIFICATION_END_DATE = "end_date";
    public static final String KEY_SPECIFICATION_MOISTURE_FROM = "moisture_percentage_from";
    public static final String KEY_SPECIFICATION_MOISTURE_TO = "moisture_percentage_to";
    public static final String KEY_SPECIFICATION_TYPE= "specification_type";
    public static final String KEY_SPECIFICATION_CODE= "specification_code";

}
