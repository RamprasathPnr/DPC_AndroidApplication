package com.omneagate.dpc.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;

import com.omneagate.dpc.R;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.MySharedPreference;
import com.omneagate.dpc.Utility.Util;


public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 2000;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Util.farmerLandDetailsDtoList != null) {
            Util.farmerLandDetailsDtoList.clear();
        }
        context = this;
        Util.device_number = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase();
        SQLiteDatabase db = new DBHelper(this).getWritableDatabase();
        Log.e("splash activity........", "db.getVersion()...." + db.getVersion());
//        int oldVersion = MySharedPreference.readInteger(getApplicationContext(), MySharedPreference.VERSION, db.getVersion());
        String languageCode = DBHelper.getInstance(this).getMasterData("language");
        if (languageCode == null || languageCode.isEmpty()) {
            DBHelper.getInstance(this).insertValues();
        }
        if (languageCode == null) {
            languageCode = "ta";
        }
        Util.changeLanguage(this, languageCode);
        GlobalAppState.language = languageCode;

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}