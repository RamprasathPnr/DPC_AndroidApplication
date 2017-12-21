package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import android.os.Handler;
import android.widget.Toast;

import com.omneagate.dpc.Activity.Dialog.LogOutDialog;
import com.omneagate.dpc.Model.DPCProfileDto;
import com.omneagate.dpc.Model.LoginHistoryDto;
import com.omneagate.dpc.Model.ResponseDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.ResReqController;
import com.omneagate.dpc.Service.Utilities;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.LoginData;
import com.omneagate.dpc.Utility.NetworkConnection;
import com.omneagate.dpc.Utility.SessionId;
import com.omneagate.dpc.Utility.Util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by user on 27/6/16.
 */
public class BaseActivity extends AppCompatActivity {
    View layoutOfPopup;
    PopupWindow popupMessage;
    private Button menu_button;
    NetworkConnection networkConnection;
    final ResReqController controller = new ResReqController(this);
    LogOutDialog logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String languageCode = DBHelper.getInstance(this).getMasterData("language");
        if (languageCode == null) {
            languageCode = "ta";
        }
        Util.changeLanguage(this, languageCode);
        GlobalAppState.language = languageCode;
    }

    /*
    * on create to check the internet connection for each activity.
    * */
    public void checkInternetConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        set_online_offline(isConnected);
    }

    public boolean checkInternet() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        return isConnected;
    }

    public void set_online_off(boolean isConnected) {
        if (!isConnected) {
        }
    }

    /*
    * check internet connection and set online or offline indicator in footer
    * */
    public void set_online_offline(boolean isConnected) {
        if (isConnected) {
            View viewOnline = findViewById(R.id.onLineOffline);
            TextView textViewOnline = (TextView) findViewById(R.id.textOnline);
            viewOnline.setBackgroundResource(R.drawable.rounded_circle_green);
            textViewOnline.setTextColor(Color.parseColor("#038203"));
            textViewOnline.setText(getString(R.string.txt_Online));
        } else {
            View viewOnline = findViewById(R.id.onLineOffline);
            TextView textViewOnline = (TextView) findViewById(R.id.textOnline);
            viewOnline.setBackgroundResource(R.drawable.rounded_circle_red);
            textViewOnline.setTextColor(Color.parseColor("#FFFF0000"));
            textViewOnline.setText(getString(R.string.txt_Offline));
        }
    }

    public void setUpPopUpPage() {
        try {
            layoutOfPopup = LayoutInflater.from(this).inflate(R.layout.popup_user, new LinearLayout(this), false);
            popupMessage = new PopupWindow(layoutOfPopup, LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            popupMessage.setContentView(layoutOfPopup);
            popupMessage.setBackgroundDrawable(new BitmapDrawable());
            popupMessage.setOutsideTouchable(true);

            if (StringUtils.isNotEmpty(SessionId.getInstance().getUserName()))
                if (SessionId.getInstance().getLastLoginTime() != null)
                    popupMessage.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            menu_button = (Button) findViewById(R.id.menu_button);
            menu_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupMessage.showAsDropDown(menu_button, 0, 0);
                }
            });
            layoutOfPopup.findViewById(R.id.button_logout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupMessage.dismiss();
                    userLogoutResponse( );
                }
            });

            layoutOfPopup.findViewById(R.id.text_dpc).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), DpcProfileActivity.class);
                    startActivity(i);
                    popupMessage.dismiss();
                }
            });

            ((TextView) layoutOfPopup.findViewById(R.id.text_last_login)).setText(convert_date(DBHelper.getInstance(this).getlastlogin("")));

//            ResponseDto what = LoginData.getInstance().getResponseData();
            if (LoginData.getInstance().getResponseData().getUserDetailDto().getProfile().equalsIgnoreCase("DPC")) {
                ((TextView) layoutOfPopup.findViewById(R.id.text_dpc_user)).setText(LoginData.getInstance().getResponseData().getUserDetailDto().getUsername());
            } else {
                ((TextView) layoutOfPopup.findViewById(R.id.text_dpc_user)).setText("ADMIN");
            }
            DPCProfileDto dpc_dto = DBHelper.getInstance(this).getDpcProfile();
            ((TextView) layoutOfPopup.findViewById(R.id.tV_code)).setText(dpc_dto.getGeneratedCode());
        } catch (Exception e) {
            Log.e("BaseActivity", e.toString(), e);
        }
    }

    private String convert_date(String date) {
        DateFormat informat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        DateFormat outformat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        Date date1 = null;
        try {
            date1 = informat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(outformat.format(date1));
        return outformat.format(date1);
    }

    private void userLogoutResponse( ) {
        logout = new LogOutDialog(BaseActivity.this);
        logout.show();
    }

    public void logoutSuccess() {

        try {
            String type = "OFFLINE_LOGOUT";
            networkConnection = new NetworkConnection(this);
            if (networkConnection.isNetworkAvailable()) {
                type = "ONLINE_LOGOUT";
                try {
                    HashMap<String, Object> inputParams = Utilities.getInstance().getRequestParams();
                    inputParams.put("", "");
                    Object data = SessionId.getInstance().getSessionId();
                    Log.e("Logout", "session id " + SessionId.getInstance().getSessionId());
                    controller.handleMessage(ResReqController.LOGOUT, inputParams, data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            insertLoginHistoryDetails(type);
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToastMessage(String text, int duration) {
        final Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.show();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, duration);
    }

    private void insertLoginHistoryDetails(String type) {
        LoginHistoryDto loginHistoryDto = new LoginHistoryDto();
        ResponseDto loginresponse_dto = LoginData.getInstance().getResponseData();

        if (loginresponse_dto.getUserDetailDto() != null && loginresponse_dto.getUserDetailDto().getDpcProfileDto() != null)
            loginHistoryDto.setDpcId(loginresponse_dto.getUserDetailDto().getDpcProfileDto().getId());

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        loginHistoryDto.setLoginTime(df.format(new Date()));
        loginHistoryDto.setLoginType(type);
        loginHistoryDto.setUserId(loginresponse_dto.getUserDetailDto().getId());
        df = new SimpleDateFormat("ddMMyyHHmmss", Locale.getDefault());
        String transactionId = df.format(new Date());
        loginHistoryDto.setTransactionId(transactionId);
        DBHelper.getInstance(this).insertLoginHistory(loginHistoryDto);
    }
}
