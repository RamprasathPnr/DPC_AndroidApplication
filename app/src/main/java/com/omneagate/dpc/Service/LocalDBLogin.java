package com.omneagate.dpc.Service;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.omneagate.dpc.Activity.AdminActivity;
import com.omneagate.dpc.Activity.DashBoardActivity;
import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Model.LoginHistoryDto;
import com.omneagate.dpc.Model.UserDetailDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.SessionId;
import com.omneagate.dpc.Utility.StringDigesterString;

import org.jasypt.digest.StringDigester;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Shanthakumar on 26-08-2016.
 */
public class LocalDBLogin {

    Activity context;
    UserDetailDto profile;

    public LocalDBLogin(Activity context, UserDetailDto profile) {
        this.context = context;
        this.profile = profile;
    }


    /**
     * async task for login
     *
     * @param passwordUser and hash
     */
    public void setLoginProcess(String passwordUser, String hashDbPassword) {
        new LocalLoginProcess(passwordUser, hashDbPassword).execute();

    }


    //Local login Process
    private class LocalLoginProcess extends AsyncTask<String, Void, Boolean> {

        // user password and local db password
        String passwordUser, hashDbPassword;

        // LocalLoginProcess Constructor
        LocalLoginProcess(String passwordUser, String hashDbPassword) {
            this.passwordUser = passwordUser;
            this.hashDbPassword = hashDbPassword;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                return localDbPassword(passwordUser, hashDbPassword);
            } catch (Exception e) {
                Log.e("loca lDb", "Interrupted", e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                GlobalAppState.localLogin = true;
                insertLoginHistoryDetails();
                if (profile.getProfile().equalsIgnoreCase("ADMIN")) {
                    context.startActivity(new Intent(context, AdminActivity.class));
                    context.finish();
                } else {
                    context.startActivity(new Intent(context, DashBoardActivity.class));
                    context.finish();
                }
                //Toast.makeText(context, "Offline Login successfully ", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context,context.getString(R.string.toast_userName_or_Password_is_incorrect), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void insertLoginHistoryDetails() {
        LoginHistoryDto loginHistoryDto = new LoginHistoryDto();
        if (profile.getDpcProfileDto() != null)
            loginHistoryDto.setDpcId(profile.getDpcProfileDto().getId());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        loginHistoryDto.setLoginTime(df.format(new Date()));
        loginHistoryDto.setLoginType("OFFLINE_LOGIN");
        loginHistoryDto.setUserId(profile.getId());
        df = new SimpleDateFormat("ddMMyyHHmmss", Locale.getDefault());
        String transactionId = df.format(new Date());
        loginHistoryDto.setTransactionId(transactionId);
        SessionId.getInstance().setTransactionId(transactionId);
        DBHelper.getInstance(context).insertLoginHistory(loginHistoryDto);
    }
    private boolean localDbPassword(String passwordUser, String passwordDbHash) {

        StringDigester stringDigester = StringDigesterString.getPasswordHash(context);

        return stringDigester.matches(passwordUser, passwordDbHash);
    }
}
