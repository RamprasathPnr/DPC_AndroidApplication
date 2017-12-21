package com.omneagate.dpc.Service;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;


import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Utility.Constants;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.DownloadDataProcessor;

import java.util.HashMap;
import java.util.List;

/**
 * Created by user on 5/8/2016.
 */
public class ResReqController extends Controller implements ServiceResponseListener {

    private static final String TAG = ResReqController.class.getSimpleName();
    private HandlerThread workerThread;
    private Handler workHandler;
    private Context mContext;
    private List<Object> listModel;
    private String post = "POST";
    private String get = "GET";
    private String BASE_URL;
    //    private DpcDeviceDto listModel;
//    private Object detailsModel;
    private Object detailsModel;

    public static final int LOGIN = 2;
    public static final int LOGIN_SUCCESS = 203;
    public static final int LOGIN_FAILED = 204;

    public static final int DEVICE_REGISTRATION = 3;
    public static final int DEVICE_REGISTRATION_SUCCESS = 205;
    public static final int DEVICE_REGISTRATION_FAILED = 206;

    public static final int FIRST_SYNC_GET_DETAILS = 4;
    public static final int FIRST_SYNC_GET_DETAILS_SUCCESS = 207;
    public static final int FIRST_SYNC_GET_DETAILS_FAILED = 208;

    public static final int FIRST_SYNC_GET_TABLE_DETAIL = 5;
    public static final int FIRST_SYNC_GET_TABLE_DETAIL_SUCCESS = 209;
    public static final int FIRST_SYNC_GET_TABLE_DETAIL_FAILED = 210;

    public static final int FIRST_SYNC_COMPLETE = 6;
    public static final int FIRST_SYNC_COMPLETE_SUCCESS = 211;
    public static final int FIRST_SYNC_COMPLETE_FAILED = 212;


    public static final int RATION_CARD = 7;
    public static final int RATION_CARD_SUCCESS = 213;
    public static final int RATION_CARD_FAILED = 214;

    public static final int FARMER_REGISTRATION = 8;
    public static final int FARMER_REGISTRATION_SUCCESS = 215;
    public static final int FARMER_REGISTRATION_FAILED = 216;

    public static final int VERSION_UPGRADE = 9;
    public static final int VERSION_UPGRADE_SUCCESS = 217;
    public static final int VERSION_UPGRADE_FAILED = 218;

    public static final int UPGRADE_ADDDETAILS = 10;
    public static final int UPGRADE_ADDDETAILS_SUCCESS = 219;
    public static final int UPGRADE_ADDDETAILS_FAILED = 220;

    public static final int REGULARSYNC_GET_TABLE_DETAILS = 11;
    public static final int REGULARSYNC_GET_TABLE_DETAILS_SUCCESS = 221;
    public static final int REGULARSYNC_GET_TABLE_DETAILS_FAILED = 222;

    public static final int REGULARSYNC_GET_TABLE_DATA = 12;
    public static final int REGULARSYNC_GET_TABLE_DATA_SUCCESS = 223;
    public static final int REGULARSYNC_GET_TABLE_DATA_FAILED = 224;

    public static final int REGULARSYNC_COMPLETE = 13;
    public static final int REGULARSYNC_COMPLETE_SUCCESS = 225;
    public static final int REGULARSYNC_COMPLETE_FAILED = 226;

    public static final int DPC_POS_EXCEPTION = 14;
    public static final int DPC_POS_EXCEPTION_SUCCESS = 227;
    public static final int DPC_POS_EXCEPTION_FAILED = 228;

    public static final int OFFLINE_REGISTRATION = 15;
    public static final int OFFLINE_REGISTRATION_SUCCESS = 229;
    public static final int OFFLINE_REGISTRATION_FAILED = 230;

    public static final int BACKGROUND_LOGIN = 16;
    public static final int BACKGROUND_LOGIN_SUCCESS = 229;
    public static final int BACKGROUND_LOGIN_FAILED = 230;

    public static final int LOGOUT = 17;
    public static final int LOGOUT_SUCCESS = 231;
    public static final int LOGOUT_FAILED = 232;

    public static final int SEARCH_FARMER_ONLINE = 18;
    public static final int SEARCH_FARMER_ONLINE_SUCCESS = 233;
    public static final int SEARCH_FARMER_ONLINE_FAILED = 234;

    public static final int PROCUREMENT = 19;
    public static final int PROCUREMENT_SUCCESS = 235;
    public static final int PROCUREMENT_FAILED = 236;

    public static final int TRUCKMEMO = 20;
    public static final int TRUCKMEMO_SUCCESS = 235;
    public static final int TRUCKMEMO_FAILED = 236;

    public static final int OFFLINE_PROCUREMENT = 21;
    public static final int OFFLINE_PROCUREMENT_SUCCESS = 237;
    public static final int OFFLINE_PROCUREMENT_FAILED = 238;

    public static final int OFFLINE_TRUCKMEMO = 22;
    public static final int OFFLINE_TRUCKMEMO_SUCCESS = 239;
    public static final int OFFLINE_TRUCKMEMO_FAILED = 240;


    public ResReqController(Context mContext) {
        this.mContext = mContext;
        workerThread = new HandlerThread("worker Thread");
        workerThread.start();
        workHandler = new Handler(workerThread.getLooper());
    }

    public ResReqController(Context mContext, List<Object> listModel, Object detailsModel) {
        this.listModel = listModel;
        this.detailsModel = detailsModel;
        this.mContext = mContext;
        workerThread = new HandlerThread("worker Thread");
        workerThread.start();
        workHandler = new Handler(workerThread.getLooper());
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public void getListData(final String url, final HashMap<String, Object> inputParams,
                            final boolean showProgress, final String tag, final String url_enum, final String method) {
        try {

            workHandler.post(new Runnable() {
                @Override
                public void run() {
//                synchronized (listModel) {
                    Utilities.getInstance().makeServiceCall(mContext, showProgress, url, inputParams, tag, ResReqController.this, url_enum, method);
//                }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getListData_LoadMore(final String url, final HashMap<String, Object> inputParams,
                                     final boolean showProgress, final String tag, final String url_enum, final String method) {
        try {

            workHandler.post(new Runnable() {
                @Override
                public void run() {
//                synchronized (listModel) {
                    Utilities.getInstance().serviceCall_loadMore(mContext, showProgress, url, inputParams, tag, ResReqController.this, url_enum, method);
//                }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getListData_LoadMore_(final String url, final HashMap<String, Object> inputParams,
                                      final boolean showProgress, final String tag, final String url_enum, final String method) {
        try {

            workHandler.post(new Runnable() {
                @Override
                public void run() {
//                synchronized (listModel) {
                    DownloadDataProcessor controlll = new DownloadDataProcessor(mContext);
                    Utilities.getInstance().serviceCall_loadMore(mContext, showProgress, url, inputParams, tag, controlll, url_enum, method);
//                }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getListData_LoadMore_Receiver(final String url, final HashMap<String, Object> inputParams,
                                              final boolean showProgress, final String tag, final String url_enum, final String method) {
        try {

            workHandler.post(new Runnable() {
                @Override
                public void run() {
//                synchronized (listModel) {
                    ConnectivityReceiver receiver = new ConnectivityReceiver(mContext);
                    Utilities.getInstance().serviceCall_loadMore(mContext, showProgress, url, inputParams, tag, receiver, url_enum, method);
//                }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void submitData(final String url, final String inputParams, final boolean showProgress, final String tag, final String url_enum, final String method) {
        try {
            workHandler.post(new Runnable() {
                @Override
                public void run() {
//                synchronized (listModel) {
                    Utilities.getInstance().makeServiceCall_(mContext, showProgress, url, inputParams, tag, ResReqController.this, url_enum, method);
//                }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void submitData_(final String url, final String inputParams, final boolean showProgress, final String tag, final String url_enum, final String method) {
        try {
            workHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.e("THE INSIDE SUBMIT DATA", inputParams);
//                synchronized (listModel) {
                    DownloadDataProcessor controlll = new DownloadDataProcessor(mContext);
                    Utilities.getInstance().makeServiceCall__(mContext, showProgress, url, inputParams, tag, controlll, url_enum, method);
//                }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void submitData_Offline(final String url, final String inputParams, final boolean showProgress, final String tag, final String url_enum, final String method) {
        try {
            workHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.e("THE INSIDE SUBMIT DATA", inputParams);
//                synchronized (listModel) {
                    OffLineFarmerSyncService control_offline_service = new OffLineFarmerSyncService(mContext);
                    Utilities.getInstance().makeServiceCall__(mContext, showProgress, url, inputParams, tag, control_offline_service, url_enum, method);
                }
//                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void submit_Procurement(final String url, final String inputParams, final boolean showProgress, final String tag, final String url_enum, final String method) {
        try {
            workHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.e("THE INSIDE SUBMIT DATA", inputParams);
//                synchronized (listModel) {
                    OffLineProcurementSync control_offline_service = new OffLineProcurementSync(mContext);
                    Utilities.getInstance().makeServiceCall__(mContext, showProgress, url, inputParams, tag, control_offline_service, url_enum, method);
//                }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void submit_TruckMemo(final String url, final String inputParams, final boolean showProgress, final String tag, final String url_enum, final String method) {
        try {
            workHandler.post(new Runnable() {
                @Override
                public void run() {
                    Log.e("THE INSIDE SUBMIT DATA", inputParams);
//                synchronized (listModel) {
                    OfflineTruckMemoManager control_offline_service = new OfflineTruckMemoManager(mContext);
                    Utilities.getInstance().makeServiceCall__(mContext, showProgress, url, inputParams, tag, control_offline_service, url_enum, method);
//                }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean handleMessage(int what, final HashMap<String, Object> inputParams, final Object data) {
        getUrl(mContext);
        try {
            switch (what) {
                case LOGIN:
                    getListData(BASE_URL + Constants.LOGIN_URL + data.toString(), inputParams, false, "LOGIN NUMBER", Constants.LOGIN_URL, post);
                    break;

                case FIRST_SYNC_GET_DETAILS:
                    getListData(BASE_URL + Constants.FIRST_SYNC_GET_DETAILS + data.toString(), inputParams, false, "FIRST SYNC GET DETAILS", Constants.FIRST_SYNC_GET_DETAILS, post);
                    break;

                case FIRST_SYNC_GET_TABLE_DETAIL:
                    getListData_LoadMore(BASE_URL + Constants.FIRST_SYNC_GET_TABLE_DETAILL + data.toString(), inputParams, false, "FIRST SYNC GET TABLE DETAILS", Constants.FIRST_SYNC_GET_TABLE_DETAILL, post);
                    break;

                case FIRST_SYNC_COMPLETE:
                    getListData(BASE_URL + Constants.FIRST_SYNC_COMPLETE + data.toString(), inputParams, false, "FIRST SYNC COMPLETE", Constants.FIRST_SYNC_COMPLETE, post);
                    break;

                case RATION_CARD:
                    getListData(BASE_URL + Constants.RATION_CARD + data.toString(), inputParams, false, "RATION CARD", Constants.RATION_CARD, post);
                    break;

                case REGULARSYNC_GET_TABLE_DETAILS:
                    getListData_LoadMore_(BASE_URL + Constants.REGULARSYNC_GET_TABLE_DETAILS + data.toString(), inputParams, false, "REGULARSYNC_GET_TABLE_DETAILS", Constants.REGULARSYNC_GET_TABLE_DETAILS, post);
                    break;

                case REGULARSYNC_COMPLETE:
                    getListData_LoadMore_(BASE_URL + Constants.REGULARSYNC_COMPLETE + data.toString(), inputParams, false, "REGULARSYNC COMPLETE", Constants.REGULARSYNC_COMPLETE, post);
                    break;

                case BACKGROUND_LOGIN:
                    getListData_LoadMore_Receiver(BASE_URL + "/" + Constants.BACKGROUND_LOGIN + data.toString(), inputParams, false, "BACKGROUND LOGIN ", Constants.BACKGROUND_LOGIN, post);
                    break;

                case LOGOUT:
                    getListData(BASE_URL + Constants.LOGOUT_URL + data.toString(), inputParams, false, "LOGOUT ", Constants.LOGOUT_URL, get);
                    break;

                case SEARCH_FARMER_ONLINE:
                    getListData(BASE_URL + Constants.SEARCH_FARMER_ONLINE + data.toString(), inputParams, false, "SEARCH_FARMER_ONLINE", Constants.SEARCH_FARMER_ONLINE, post);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean handleMessage_(int what, final String inputParams, final Object data) {
        getUrl(mContext);
        try {
            switch (what) {

                case DEVICE_REGISTRATION:
                    submitData(BASE_URL + Constants.DEVICE_REGISTRATION, inputParams, false, "SUBMIT DEVICE_DETAILS", Constants.DEVICE_REGISTRATION, post);
                    break;

                case FARMER_REGISTRATION:
                    submitData(BASE_URL + Constants.FARMER_REGISTRATION, inputParams, false, "FARMER", Constants.FARMER_REGISTRATION, post);
                    break;

                case VERSION_UPGRADE:
                    submitData(BASE_URL + Constants.VERSION_UPGRADE, inputParams, false, "VERSION UPGRADE", Constants.VERSION_UPGRADE, post);
                    break;
                case UPGRADE_ADDDETAILS:
                    submitData(BASE_URL + Constants.UPGRADE_ADDDETAILS, inputParams, false, "UPGRADE ADDDETAILS", Constants.UPGRADE_ADDDETAILS, post);
                    break;

                case REGULARSYNC_GET_TABLE_DATA:
                    Log.e("inside handle message", "handle message");
                    submitData_(BASE_URL + Constants.REGULARSYNC_GET_TABLE_DATA, inputParams, false, "REGULARSYNC_GET_TABLE_DATA", Constants.REGULARSYNC_GET_TABLE_DATA, post);
                    break;

                case DPC_POS_EXCEPTION:
                    submitData_(BASE_URL + Constants.DPC_POS_EXCEPTION, inputParams, false, "DPC POS EXCEPTION", Constants.DPC_POS_EXCEPTION, post);
                    break;

                case OFFLINE_REGISTRATION:
                    submitData_Offline(BASE_URL + "/" + Constants.OFFLINE_REGISTRATION, inputParams, false, "OFFLINE_REGISTRATION", Constants.OFFLINE_REGISTRATION, post);
                    break;

                case PROCUREMENT:
                    submitData(BASE_URL + Constants.PROCUREMENT, inputParams, false, "PROCUREMENT", Constants.PROCUREMENT, post);
                    break;
                case TRUCKMEMO:
                    submitData(BASE_URL + Constants.TRUCKMEMO_URL, inputParams, false, "TRUCKMEMO", Constants.TRUCKMEMO_URL, post);
                    break;

                case OFFLINE_PROCUREMENT:
                    submit_Procurement(BASE_URL + "/" + Constants.OFFLINE_PROCUREMENT, inputParams, false, "OFFLINE_PROCUREMENT", Constants.OFFLINE_PROCUREMENT, post);
                    break;

                case OFFLINE_TRUCKMEMO:
                    submit_TruckMemo(BASE_URL + "/" + Constants.OFFLINE_TRUCKMEMO_URL, inputParams, false, "OFFLINE_TRUCKMEMO_URL", Constants.OFFLINE_TRUCKMEMO_URL, post);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void onSuccessResponse(String url, HashMap<String, Object> requestParams, String response) {
        try {
            Utilities.getInstance().dismissProgressBar();
            logLargeString(response);
//            Log.e(TAG + " Success Response", response);


            switch (url) {
                case Constants.LOGIN_URL:
                    notifyOutboxHandlers(LOGIN_SUCCESS, 0, 0, response);
                    break;

                case Constants.DEVICE_REGISTRATION:
                    notifyOutboxHandlers(DEVICE_REGISTRATION_SUCCESS, 0, 0, response);
                    break;

                case Constants.FIRST_SYNC_GET_DETAILS:
                    notifyOutboxHandlers(FIRST_SYNC_GET_DETAILS_SUCCESS, 0, 0, response);
                    break;

                case Constants.FIRST_SYNC_GET_TABLE_DETAILL:
                    notifyOutboxHandlers(FIRST_SYNC_GET_TABLE_DETAIL_SUCCESS, 0, 0, response);
                    break;

                case Constants.FIRST_SYNC_COMPLETE:
                    notifyOutboxHandlers(FIRST_SYNC_COMPLETE_SUCCESS, 0, 0, response);
                    break;

                case Constants.RATION_CARD:
                    notifyOutboxHandlers(RATION_CARD_SUCCESS, 0, 0, response);
                    break;

                case Constants.FARMER_REGISTRATION:
                    notifyOutboxHandlers(FARMER_REGISTRATION_SUCCESS, 0, 0, response);
                    break;

                case Constants.VERSION_UPGRADE:
                    notifyOutboxHandlers(VERSION_UPGRADE_SUCCESS, 0, 0, response);
                    break;

                case Constants.UPGRADE_ADDDETAILS:
                    notifyOutboxHandlers(UPGRADE_ADDDETAILS_SUCCESS, 0, 0, response);
                    break;

                case Constants.REGULARSYNC_GET_TABLE_DETAILS:
                    Log.e("notified", "notified");
                    notifyOutboxHandlers(REGULARSYNC_GET_TABLE_DETAILS_SUCCESS, 0, 0, response);
                    break;


                case Constants.REGULARSYNC_GET_TABLE_DATA:
                    notifyOutboxHandlers(REGULARSYNC_GET_TABLE_DATA_SUCCESS, 0, 0, response);
                    break;


                case Constants.REGULARSYNC_COMPLETE:
                    notifyOutboxHandlers(REGULARSYNC_COMPLETE_SUCCESS, 0, 0, response);
                    break;

                case Constants.DPC_POS_EXCEPTION:
                    notifyOutboxHandlers(DPC_POS_EXCEPTION_SUCCESS, 0, 0, response);
                    break;

                case Constants.OFFLINE_REGISTRATION:
                    notifyOutboxHandlers(OFFLINE_REGISTRATION_SUCCESS, 0, 0, response);
                    break;

                case Constants.BACKGROUND_LOGIN:
                    notifyOutboxHandlers(BACKGROUND_LOGIN_SUCCESS, 0, 0, response);
                    break;

                case Constants.SEARCH_FARMER_ONLINE:
                    notifyOutboxHandlers(SEARCH_FARMER_ONLINE_SUCCESS, 0, 0, response);
                    break;

                case Constants.PROCUREMENT:
                    notifyOutboxHandlers(PROCUREMENT_SUCCESS, 0, 0, response);
                    break;
                case Constants.TRUCKMEMO_URL:
                    notifyOutboxHandlers(TRUCKMEMO_SUCCESS, 0, 0, response);
                    break;

                case Constants.OFFLINE_PROCUREMENT:
                    notifyOutboxHandlers(OFFLINE_PROCUREMENT_SUCCESS, 0, 0, response);
                    break;

                case Constants.LOGOUT_URL:
                    notifyOutboxHandlers(LOGOUT_SUCCESS, 0, 0, response);
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
            Log.e(TAG + " Error Response", errorMessage);
            switch (url) {
                case Constants.LOGIN_URL:
                    notifyOutboxHandlers(LOGIN_FAILED, 0, 0, errorMessage);
                    break;

                case Constants.DEVICE_REGISTRATION:
                    notifyOutboxHandlers(DEVICE_REGISTRATION_FAILED, 0, 0, errorMessage);
                    break;

                case Constants.FIRST_SYNC_GET_DETAILS:
                    notifyOutboxHandlers(FIRST_SYNC_GET_DETAILS_FAILED, 0, 0, errorMessage);
                    break;

                case Constants.FIRST_SYNC_GET_TABLE_DETAILL:
                    notifyOutboxHandlers(FIRST_SYNC_GET_TABLE_DETAIL_FAILED, 0, 0, errorMessage);
                    break;

                case Constants.FIRST_SYNC_COMPLETE:
                    notifyOutboxHandlers(FIRST_SYNC_COMPLETE_FAILED, 0, 0, errorMessage);
                    break;

                case Constants.RATION_CARD:
                    notifyOutboxHandlers(RATION_CARD_FAILED, 0, 0, errorMessage);
                    break;

                case Constants.FARMER_REGISTRATION:
                    notifyOutboxHandlers(FARMER_REGISTRATION_FAILED, 0, 0, errorMessage);
                    break;

                case Constants.VERSION_UPGRADE:
                    notifyOutboxHandlers(VERSION_UPGRADE_FAILED, 0, 0, errorMessage);
                    break;

                case Constants.UPGRADE_ADDDETAILS:
                    notifyOutboxHandlers(UPGRADE_ADDDETAILS_FAILED, 0, 0, errorMessage);
                    break;

                case Constants.REGULARSYNC_GET_TABLE_DETAILS:
                    notifyOutboxHandlers(REGULARSYNC_GET_TABLE_DETAILS_FAILED, 0, 0, errorMessage);
                    break;


                case Constants.REGULARSYNC_GET_TABLE_DATA:
                    notifyOutboxHandlers(REGULARSYNC_GET_TABLE_DATA_FAILED, 0, 0, errorMessage);
                    break;

                case Constants.REGULARSYNC_COMPLETE:
                    notifyOutboxHandlers(REGULARSYNC_COMPLETE_FAILED, 0, 0, errorMessage);
                    break;


                case Constants.DPC_POS_EXCEPTION:
                    notifyOutboxHandlers(DPC_POS_EXCEPTION_FAILED, 0, 0, errorMessage);
                    break;

                case Constants.OFFLINE_REGISTRATION:
                    notifyOutboxHandlers(OFFLINE_REGISTRATION_FAILED, 0, 0, errorMessage);
                    break;

                case Constants.BACKGROUND_LOGIN:
                    notifyOutboxHandlers(BACKGROUND_LOGIN_FAILED, 0, 0, errorMessage);
                    break;

                case Constants.SEARCH_FARMER_ONLINE:
                    notifyOutboxHandlers(SEARCH_FARMER_ONLINE_FAILED, 0, 0, errorMessage);
                    break;

                case Constants.PROCUREMENT:
                    notifyOutboxHandlers(PROCUREMENT_FAILED, 0, 0, errorMessage);
                    break;
                case Constants.TRUCKMEMO_URL:
                    notifyOutboxHandlers(TRUCKMEMO_FAILED, 0, 0, errorMessage);
                    break;

                case Constants.OFFLINE_PROCUREMENT:
                    notifyOutboxHandlers(OFFLINE_PROCUREMENT_FAILED, 0, 0, errorMessage);
                    break;


                case Constants.LOGOUT_URL:
                    notifyOutboxHandlers(LOGOUT_FAILED, 0, 0, errorMessage);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNetWorkFailure(String url) {

        Utilities.getInstance().dismissProgressBar();

        try {
            Utilities.getInstance().dismissProgressBar();
            String errorNonetwork = "No NetWork";
            String syncUrl = BASE_URL + Constants.FIRST_SYNC_GET_DETAILS;
            String syncUrl_reg = BASE_URL + Constants.FIRST_SYNC_GET_TABLE_DETAILL;

            String syncomplete = BASE_URL + Constants.FIRST_SYNC_COMPLETE;

            if (url.equalsIgnoreCase(syncUrl)) {
                notifyOutboxHandlers(FIRST_SYNC_GET_DETAILS_FAILED, 0, 0, errorNonetwork);
            }

            if (url.equalsIgnoreCase(syncUrl_reg)) {
                notifyOutboxHandlers(FIRST_SYNC_GET_TABLE_DETAIL_FAILED, 0, 0, errorNonetwork);
            }


            if (url.equalsIgnoreCase(syncUrl_reg)) {
                notifyOutboxHandlers(FIRST_SYNC_COMPLETE_FAILED, 0, 0, errorNonetwork);
            }

            /*else if (url.equalsIgnoreCase(syncUrl_reg)) {
                notifyOutboxHandlers(REGULARSYNC_GET_TABLE_DETAILS_FAILED, 0, 0, errorNonetwork);

            }*/
            /*switch (url) {

                case syncUrl:
                    notifyOutboxHandlers(FIRST_SYNC_GET_TABLE_DETAIL_FAILED, 0, 0, errorNonetwork);
                    break;

            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getUrl(Context context) {
        BASE_URL = DBHelper.getInstance(context).getMasterData("serverUrl");
    }


    public void logLargeString(String str) {
        if (str.length() > 3000) {
            Log.e(TAG, str.substring(0, 3000));
            logLargeString(str.substring(3000));
        } else {
            Log.e(TAG, str); // continuation
        }
    }


}