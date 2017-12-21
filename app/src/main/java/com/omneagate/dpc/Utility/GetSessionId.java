package com.omneagate.dpc.Utility;

import android.content.Context;
import android.provider.Settings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omneagate.dpc.Model.EnumModel.ApplicationType;
import com.omneagate.dpc.Model.LoginDto;
import com.omneagate.dpc.Model.ResponseDto;
import com.omneagate.dpc.Model.UserDetailDto;
import com.omneagate.dpc.Service.StartService;

/**
 * Created by user1 on 22/11/16.
 */
public class GetSessionId implements ResultUpdatable {
    private static Context context;
    static GetSessionId getsessionid;
    private String _session = "session";
    private String sessionId;
    StartService servicestart;

    public GetSessionId(Context context) {
        this.context = context;
    }

    public static GetSessionId getInstance(Context con) {
        context = con;
        if (getsessionid == null) {
            getsessionid = new GetSessionId(context);
        }
        return getsessionid;
    }

    public void callServer_forSession(StartService service, int requestCode) {
        servicestart = service;
        NetworkConnection network = new NetworkConnection(context);
        if (network.isNetworkAvailable()) {
            String url = DBHelper.getInstance(context).getMasterData("serverUrl") + Constants.LOGIN_URL;
            LoginDto loginDto = setLoginDetails();
            if (loginDto != null) {
                String loginDetails = new Gson().toJson(loginDto);
                new Volley_service().execute(url, loginDetails, GetSessionId.this, Integer.toString(requestCode));
            }
        }
    }

    private LoginDto setLoginDetails() {
        UserDetailDto loginResponse = DBHelper.getInstance(context)
                .getBackgroundLogin(SessionId.getInstance().getUserId());
        if (loginResponse != null) {
            LoginDto loginDto = new LoginDto();
            loginDto.setUserName(loginResponse.getUsername());
            loginDto.setPassword(Util.DecryptPassword(loginResponse.getEncryptedPassword()));
            loginDto.setDeviceId(Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID).toUpperCase());
            loginDto.setAppType(ApplicationType.DPC);
            return loginDto;
        }
        return null;
    }

    @Override
    public void setResult(String result, String id) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        ResponseDto loginresponse_dto = gson.fromJson(result, ResponseDto.class);
        LoginData.getInstance().setResponseData(loginresponse_dto);
        if (loginresponse_dto.getStatusCode().equalsIgnoreCase("0")
                && loginresponse_dto.getDeviceStatus().toString().equalsIgnoreCase("ACTIVE")
                && loginresponse_dto.getUserDetailDto().getProfile().equalsIgnoreCase("DPC")
                || loginresponse_dto.getUserDetailDto().getProfile().equalsIgnoreCase("ADMIN")) {
            sessionId = loginresponse_dto.getSessionid();
            servicestart.callservice(Integer.parseInt(id));
        }
    }
}
