package com.omneagate.dpc.Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

/**
 * Created by user on 5/8/15.
 */
public class MySharedPreference {

    public static final String PREF_NAME = "dpc";
    public static final int MODE = Context.MODE_PRIVATE;
    public static final String DISTRICT_ID = "district_id";
    public static final String SCAN_OPTION = "scan_option";
    public static final String SYNC_COMPLETE = "sync_complete";
    public static final String DEVICE_STATUS = "device_status";
    public static final String GENERATED_CODE = "dpc_generated_code";
    public static final String STRING_DEVICE_STATUS = "string_device_status";

    public static final String DEVICE_NAME = "device_name";

    public static final String VERSION = "version";




//    public static final String USER_DETAILS = "user_details";
//    public static final String LANGUAGE_SELECT = "language";


    private static final String TAG = MySharedPreference.class.getCanonicalName();
    public static final String DEVICE_ADDRESS = "device_address";


    public static void writeBoolean(Context context, String key, boolean value) {
        try {
            getEditor(context).putBoolean(key, value).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean readBoolean(Context context, String key,
                                      boolean defValue) {
        try {
            return getPreferences(context).getBoolean(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void writeInteger(Context context, String key, int value) {
        try {
            getEditor(context).putInt(key, value).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static int readInteger(Context context, String key, int defValue) {
        try {
            return getPreferences(context).getInt(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void writeString(Context context, String key, String value) {
        try {
            getEditor(context).putString(key, value).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readString(Context context, String key, String defValue) {
        try {
            return getPreferences(context).getString(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

  /*  public static void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    public static float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }  */

    public static void writeLong(Context context, String key, long value) {
        try {
            getEditor(context).putLong(key, value).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static long readLong(Context context, String key, long defValue) {
        try {
            return getPreferences(context).getLong(key, defValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static SharedPreferences getPreferences(Context context) {
        try {
            if (context == null) {
                Log.e(TAG, "My shared preference context is null : ");
            }
            Log.d(TAG, "My shared preference context is not null : ");
            return context.getSharedPreferences(PREF_NAME, MODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Editor getEditor(Context context) {
        try {
            return getPreferences(context).edit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
