package com.omneagate.dpc.ActivityBusinessClass;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.omneagate.dpc.Model.LoginRequest;
import com.omneagate.dpc.Model.ResponseDto;
import com.omneagate.dpc.Model.UserDetailDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Service.LocalDBLogin;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.LoginData;
import com.omneagate.dpc.Utility.MySharedPreference;
import com.omneagate.dpc.Utility.SessionId;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by user on 26-08-2016.
 */
public class LoginCheck {
    Activity mContext;
    Boolean isShopInactive;
    Dialog dialog;

    public LoginCheck(Activity context) {
        this.mContext = context;
    }

    /**
     * IF NO NETWORK AVAILABLE LOGIN WILL DONE USING LOCAL DATABASE
     */
    public void localLogin(LoginRequest loginCredentials) {
        System.out.println("Shiva enter the localLogin method :::");
        try {
            if (!MySharedPreference.readBoolean(mContext,
                    MySharedPreference.DEVICE_STATUS, false)) {
                Toast.makeText(mContext, "DEVICE_STATUS ",
                        Toast.LENGTH_SHORT).show();
                System.out.println("Shiva enter the DEVICE_STATUS method :::");
                return;
            }
//            showProgressBar(mContext);
            ResponseDto loginResponse = DBHelper.getInstance(mContext)
                    .getUserDetails(loginCredentials.getUserName());
            if (loginResponse == null) {
                System.out.println("Shiva enter the loginResponse null method :::");
                Toast.makeText(mContext, mContext.getString(R.string.toast_userName_or_Password_is_incorrect),
                        Toast.LENGTH_SHORT).show();
//                dismissProgressBar();
                return;
            }
            LoginData.getInstance().setResponseData(loginResponse);
            String hashPassword = loginResponse.getUserDetailDto().getPassword();

            /*if (hashPassword == null) {
                Toast.makeText(mContext, mContext.getString(R.string.toast_userName_or_Password_is_incorrect),
                        Toast.LENGTH_SHORT).show();
//                Util.messageBar(context, context.getString(R.string.inCorrectUnamePword));

                return;
            }*/
            if (StringUtils.isNotEmpty(hashPassword)) {
                try {
                    if (!loginResponse.getUserDetailDto().isActive()) {
                        Toast.makeText(mContext, mContext.getString(R.string.toast_Device_inactive_contact_Help_Desk),
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    return;
                }
                SessionId.getInstance().setUserId(loginResponse.getUserDetailDto().getId());
                SessionId.getInstance().setUserName(loginResponse.getUserDetailDto().getUserId());
                LocalDBLogin localDBLogin = new LocalDBLogin(mContext, loginResponse.getUserDetailDto());
                localDBLogin.setLoginProcess(loginCredentials.getPassword(), hashPassword);
            } else {
                Toast.makeText(mContext, mContext.getString(R.string.toast_network_error),
                        Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
            Log.e("LoginActivity", "Error in Local Login", e);
        }
    }

    public void showProgressBar(Context ctx) {
        dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.loader);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void dismissProgressBar() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    public void localLoginoffline(LoginRequest loginCredentials) {
        if (!MySharedPreference.readBoolean(mContext,
                MySharedPreference.DEVICE_STATUS, false)) {
            Toast.makeText(mContext, "DEVICE_STATUS ",
                    Toast.LENGTH_SHORT).show();
            System.out.println("Shiva enter the DEVICE_STATUS method :::");
            return;
        }
        ResponseDto loginResponse = DBHelper.getInstance(mContext).getUserDetails(loginCredentials.getUserName());

        LoginData.getInstance().setResponseData(loginResponse);
        String devicestatus = MySharedPreference.readString(mContext,
                MySharedPreference.STRING_DEVICE_STATUS, "");
        UserDetailDto userdto = DBHelper.getInstance(mContext).checkuserdetails(loginCredentials.getUserName());
        if (userdto != null) {
            String status = userdto.getStatusCode();
            if (status.equalsIgnoreCase("160003") || status.equalsIgnoreCase("1001") || status.equalsIgnoreCase("1000")) {
                Toast.makeText(mContext, mContext.getString(R.string.toast_userName_or_Password_is_incorrect), Toast.LENGTH_SHORT).show();
            } else if (status.equalsIgnoreCase("105") || status.equalsIgnoreCase("2000")) {
                Toast.makeText(mContext, mContext.getString(R.string.toast_Device_inactive_contact_Help_Desk), Toast.LENGTH_SHORT).show();
            } else if (status.equalsIgnoreCase("202") && devicestatus.equalsIgnoreCase("INACTIVE")) {
                Toast.makeText(mContext, mContext.getString(R.string.toast_device_not_reg), Toast.LENGTH_SHORT).show();
            } else if (status.equalsIgnoreCase("160005")) {
                Toast.makeText(mContext, mContext.getString(R.string.toast_Device_request_already_sent), Toast.LENGTH_SHORT).show();
            } else if (status.equalsIgnoreCase("160008")) {
                Toast.makeText(mContext, mContext.getString(R.string.toast_device_not_associated), Toast.LENGTH_SHORT).show();
            } else if (status.equalsIgnoreCase("10100")) {
                Toast.makeText(mContext, mContext.getString(R.string.internalError_Server), Toast.LENGTH_SHORT).show();
            } else if (devicestatus.equalsIgnoreCase("UNASSOCIATED")) {
                Toast.makeText(mContext, mContext.getString(R.string.toast_device_register), Toast.LENGTH_SHORT).show();
            } else if (status.equalsIgnoreCase("11012")) {
                Toast.makeText(mContext, mContext.getString(R.string.toast_User_is_not_associated), Toast.LENGTH_SHORT).show();
            } else if (status.equalsIgnoreCase("0")
                    && devicestatus.equalsIgnoreCase("ACTIVE")
                    && userdto.getProfile().equalsIgnoreCase("DPC")
                    || userdto.getProfile().equalsIgnoreCase("ADMIN")) {
                SessionId.getInstance().setUserId(userdto.getId());
                SessionId.getInstance().setUserName(userdto.getUsername());
                LocalDBLogin localDBLogin = new LocalDBLogin(mContext, userdto);
                localDBLogin.setLoginProcess(loginCredentials.getPassword(), userdto.getPassword());
                new Call_service().startservices(mContext);
            }
        } else {
            Toast.makeText(mContext, mContext.getString(R.string.toast_userName_or_Password_is_incorrect), Toast.LENGTH_SHORT).show();
        }
    }
}
