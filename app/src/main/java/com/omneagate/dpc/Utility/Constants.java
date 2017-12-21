package com.omneagate.dpc.Utility;


import com.omneagate.dpc.Model.DPCSyncExceptionDto;
import com.omneagate.dpc.Model.FarmerRegistrationRequestDto;
import com.omneagate.dpc.Model.FistSyncInputDto;

import java.util.List;

/**
 * Created by user1 on 23/5/16.
 */
public class Constants {

    public static final String LOGIN_URL = "/dpc/login";
    public static final String BACKGROUND_LOGIN = "dpc/login";
    public static final String DEVICE_REGISTRATION = "/dpc/deviceRegistration";
    public static final String FIRST_SYNC_GET_DETAILS = "/dpcFirstSync/getdetails";
    public static final String FIRST_SYNC_GET_TABLE_DETAILL = "/dpcFirstSync/gettabledetails";
    public static final String FIRST_SYNC_COMPLETE = "/dpcFirstSync/completesynch";
    public static final String FARMER_REGISTRATION = "/dpcDashboard/farmerRegistration";
    public static final String OFFLINE_REGISTRATION = "dpcDashboard/farmerRegistration";
    public static final String RATION_CARD = "/dpcDashboard/getByRationCard";
    public static final String VERSION_UPGRADE = "/dpcVersionUpgrade/view";
    public static final String UPGRADE_ADDDETAILS = "/dpcVersionUpgrade/adddetails";
    public static final String REGULARSYNC_GET_TABLE_DETAILS = "/dpcRegularSync/getSyncedTableList";
    public static final String REGULARSYNC_GET_TABLE_DATA = "/dpcRegularSync/getTableData";
    public static final String REGULARSYNC_COMPLETE = "/dpcRegularSync/makeCompleteSync";
    public static final String DPC_POS_EXCEPTION = "/dpcPosSyncException/addSyncException";
    public static final String LOGOUT_URL = "/dpc/logmeout?sessionId=";
    //    public static final String LOGOUT_URL = "/dpc/logmeout";
    public static final String SEARCH_FARMER_ONLINE = "/dpc/searchFarmer";
    public static final String PROCUREMENT = "/dpc/createProcurement";
    public static final String TRUCKMEMO_URL = "/dpc/createTruckMemo";
    public static final String OFFLINE_PROCUREMENT = "dpc/createProcurement";
    public static final String OFFLINE_TRUCKMEMO_URL = "dpc/createTruckMemo";


    public static List<FistSyncInputDto> firstSync;
    public static List<DPCSyncExceptionDto> dpcSyncExceptionDtoList;
}
