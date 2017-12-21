package com.omneagate.dpc.Utility;

import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;

import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Model.FarmerLandDetailsDto;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Utility class for entire application
 */
public class Util {
    public static List<FarmerLandDetailsDto> farmerLandDetailsDtoList = new ArrayList<>();

    public static String device_number;
    private static NetworkConnection networkConnection;
    /**
     * Add log to queue
     *
     * @param context,errorType and error string
     *//*
    public static void LoggingQueue(Context context, String errorType, String error) {

        GlobalAppState appState = (GlobalAppState) context.getApplicationContext();
        if (GlobalAppState.isLoggingEnabled && NetworkUtil.getConnectivityStatus(context) != 0)
            appState.queue.enqueue(logging(context, errorType, error));

    }*/

    /**
     * Change language in android
     *
     * @param languageCode,context
     */
    public static void changeLanguage(Context context, String languageCode) {
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(languageCode);
        res.updateConfiguration(conf, dm);
        DBHelper.getInstance(context).updateMaserData("language", languageCode);
    }

    public static String getTransactionId(Context context) {
        String referenceNumber;
        String Dpcid = LoginData.getInstance().getResponseData().getDpcProfileDto().getCode();
        Log.e("UTil", "Dpcid" + Dpcid);
        Date todayDate = new Date();
        SimpleDateFormat toDate = new SimpleDateFormat("MMyy", Locale.getDefault());
        String lastReferenceNumber = DBHelper.getInstance(context).lastRegistrationToday(Dpcid);

        Log.e("Util", "lastReferenceNUmber" + lastReferenceNumber);
        String Date = toDate.format(todayDate);
        if (StringUtils.isNotEmpty(lastReferenceNumber)) {
            String timestamp = lastReferenceNumber.substring(0, lastReferenceNumber.length() - 4);
            Log.e("Util", "lastthreedigit" + timestamp);
            String lastthreedigit = lastReferenceNumber.substring(lastReferenceNumber.length() - 4);
            Log.e("Util", "lastthreedigit" + lastthreedigit);

            long record = Long.parseLong(lastthreedigit) + 1;
            String addedrecord = String.format("%04d", record);
            referenceNumber = timestamp + addedrecord + "";

        } else {
            referenceNumber = Dpcid + Date + "0001";
        }
        Log.e("Util ", "referenceNumber" + referenceNumber);
        return referenceNumber;
    }

    public static String getRegistrationResponseNumber(Context context) {
        String referenceNumber;
        String Dpcid;
//        String generated_code = MySharedPreference.readString(context, MySharedPreference.GENERATED_CODE, "");

        networkConnection = new NetworkConnection(context);

        if (networkConnection.isNetworkAvailable()) {
            Dpcid = LoginData.getInstance().getResponseData().getDpcProfileDto().getGeneratedCode();
            Log.e("UTil", "Dpcid" + Dpcid);
        } else {
            Dpcid = DBHelper.getInstance(context).getDpcGeneratedCode();
            Log.e("generated_code", "generated_code*****************" + Dpcid);

        }

//        String generated_code = DBHelper.getInstance(context).getDpcGeneratedCode();



        // String lastReferenceNumber = DBHelper.getInstance(context).lastRegistrationToday(Dpcid);
        String lastReferenceNumber = DBHelper.getInstance(context).lastRegistrationNumber();
        Log.e("Util", "lastReferenceNUmber-----------" + lastReferenceNumber);

        if (StringUtils.isNotEmpty(lastReferenceNumber)) {
            String dpc_id = lastReferenceNumber.substring(0, lastReferenceNumber.length() - 7);
            Log.e("Util", "lastthreedigit" + dpc_id);
            String lastthreedigit = lastReferenceNumber.substring(lastReferenceNumber.length() - 7);
            Log.e("Util", "lastthreedigit" + lastthreedigit);

            long record = Long.parseLong(lastthreedigit) + 1;
            String addedrecord = String.format("%07d", record);
            referenceNumber = dpc_id + addedrecord + "";

        } else {
            referenceNumber = Dpcid + "0000001";
        }
        Log.e("Util ", "referenceNumber-------------" + referenceNumber);
        return referenceNumber;
    }



    public static String generateReceiptNumber(Context context){

        Date todayDate = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyMM", Locale.getDefault());
        String lastProcurementNumber = DBHelper.getInstance(context).lastReceiptNumber_();
        Log.e("lastProcurementNumber server",lastProcurementNumber);
        String date = dateformat.format(todayDate);


        if (StringUtils.isNotEmpty(lastProcurementNumber) && ! lastProcurementNumber.equals("null")) {
            String year_month = lastProcurementNumber.substring(0, lastProcurementNumber.length() - 6);
            String last_running_number = lastProcurementNumber.substring(lastProcurementNumber.length() - 6);
            long record = Long.parseLong(last_running_number) + 1;
            String addedrecord = String.format("%06d", record);
            lastProcurementNumber = year_month + addedrecord + "";

        } else {
            lastProcurementNumber = date + "000001";
        }
        Log.e("Util", "lastProcurementNumber-----------" + lastProcurementNumber);

        return lastProcurementNumber;

    }


    public static String GetTruckMemoNumber(Context context){

           Date todayDate = new Date();
           SimpleDateFormat dateformat = new SimpleDateFormat("yyMM", Locale.getDefault());
           String lastTruckMemoNumber = DBHelper.getInstance(context).lastTruckMemo();
           String date = dateformat.format(todayDate);


           if (StringUtils.isNotEmpty(lastTruckMemoNumber)) {

               String year_month = lastTruckMemoNumber.substring(0, lastTruckMemoNumber.length() - 6);
               String last_running_number = lastTruckMemoNumber.substring(lastTruckMemoNumber.length() - 6);
               long record = Long.parseLong(last_running_number) + 1;
               String addedrecord = String.format("%06d", record);
               lastTruckMemoNumber = year_month + addedrecord + "";

           } else {
               lastTruckMemoNumber = date + "000001";
           }
        Log.e("Util", "lastTruckMemoNumber-----------" + lastTruckMemoNumber);

       return lastTruckMemoNumber;

   }
    public static String DecryptPassword(String encryptedString) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        BouncyCastleProvider bouncy = new BouncyCastleProvider();
        encryptor.setProvider(bouncy);
        encryptor.setAlgorithm("PBEWITHSHA-256AND256BITAES-CBC-BC");
        encryptor.setPassword("fpspos");
        return encryptor.decrypt(encryptedString);
    }

    public static String EncryptPassword(String password) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setProvider(new BouncyCastleProvider());
        encryptor.setAlgorithm("PBEWITHSHA-256AND256BITAES-CBC-BC");
        encryptor.setPassword("fpspos");
        return encryptor.encrypt(password);
    }


    public static String latLngRoundOffFormat(Double latLngValue) {
        Log.e("util", "latLngRoundOffFormat() called latLngValue = " + latLngValue);


        BigDecimal currQuantity = new BigDecimal(latLngValue);
        currQuantity.setScale(3, RoundingMode.HALF_EVEN);
        latLngValue = (double) Math.round(latLngValue * 1000);
        latLngValue = latLngValue / 1000;
        NumberFormat formatter = new DecimalFormat("#0.0000");
        String LatLng = formatter.format(latLngValue);

        Log.e("util", "latLngRoundOffFormat() called LatLng = " + LatLng);

//        Log.e("util", "after rounding quantityValue..." + qty);
        return LatLng;
    }

}
