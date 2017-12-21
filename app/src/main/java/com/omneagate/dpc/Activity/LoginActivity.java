package com.omneagate.dpc.Activity;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omneagate.dpc.Activity.Dialog.BluetoothDialog;
import com.omneagate.dpc.Activity.Dialog.ChangeUrlDialog;
import com.omneagate.dpc.Activity.Dialog.DateChangeDialog;
import com.omneagate.dpc.Activity.Dialog.DeviceDialog;
import com.omneagate.dpc.Activity.Dialog.LanguageSelectionDialog;
import com.omneagate.dpc.ActivityBusinessClass.Call_service;
import com.omneagate.dpc.ActivityBusinessClass.LoginCheck;
import com.omneagate.dpc.Adapter.MenuAdapter;
import com.omneagate.dpc.Model.DPCDeviceRegRequestDto;
import com.omneagate.dpc.Model.EnumModel.ApplicationType;
import com.omneagate.dpc.Model.LoginDto;
import com.omneagate.dpc.Model.LoginHistoryDto;
import com.omneagate.dpc.Model.LoginRequest;
import com.omneagate.dpc.Model.MenuDataDto;
import com.omneagate.dpc.Model.ResponseDto;
import com.omneagate.dpc.Model.UserDetailDto;
import com.omneagate.dpc.Model.VersionUpgradeDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Service.FirstSyncExceptionService;
import com.omneagate.dpc.Service.OffLineFarmerSyncService;
import com.omneagate.dpc.Service.OffLineProcurementSync;
import com.omneagate.dpc.Service.OfflineTruckMemoManager;
import com.omneagate.dpc.Service.ResReqController;
import com.omneagate.dpc.Service.UpdateDataService;
import com.omneagate.dpc.Service.Utilities;
import com.omneagate.dpc.Utility.AndroidDeviceProperties;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.LoginData;
import com.omneagate.dpc.Utility.MySharedPreference;
import com.omneagate.dpc.Utility.NetworkConnection;
import com.omneagate.dpc.Utility.SessionId;
import com.omneagate.dpc.Utility.Util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, Handler.Callback, ConnectivityReceiver.ConnectivityReceiverListener {
    private ListPopupWindow popupWindow;
    private Context context;
    private EditText username;
    private EditText password_edtxt;
    private Button btn_login;
    private String device_id;
    final ResReqController controller = new ResReqController(this);
    private LoginDto login_dto;
    private ResponseDto loginresponse_dto;
    private AndroidDeviceProperties DeviceDetailsDto;
    private NetworkConnection networkConnection;
    private String syncTime;
    private BluetoothAdapter mBluetoothAdapter;
    private List<BluetoothDevice> mDeviceList;
    private Dialog dialog;
    private BluetoothDialog bluetoothDialog;

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                final int state = intent.getIntExtra(
                        BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                if (state == BluetoothAdapter.STATE_ON) {
                    mBluetoothAdapter.startDiscovery();
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                mDeviceList = new ArrayList<>();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED
                    .equals(action)) {
                dismissProgressBar();
                if (mDeviceList == null) {
                    mDeviceList = new ArrayList<>();
                }
                if (mDeviceList.size() > 0) {
                    bluetoothDialog = new BluetoothDialog(com.omneagate.dpc.Activity.LoginActivity.this, mDeviceList);
                    bluetoothDialog.show();
                } else {
                }
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                if (mDeviceList == null) {
                    mDeviceList = new ArrayList<>();
                }
                mDeviceList.add((BluetoothDevice) intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DeviceDetailsDto = new AndroidDeviceProperties(this);
        System.out.println("DEVICE DETAILS" + DeviceDetailsDto.getDeviceProperties());
        controller.addOutboxHandler(new Handler(this));
        networkConnection = new NetworkConnection(this);
        setUpView();
        checkInternet();
        context = this;
    }

    public void setUpView() {
        findViewById(R.id.onLineOffline).setVisibility(View.GONE);
        findViewById(R.id.image_white).setVisibility(View.GONE);
        username = (EditText) findViewById(R.id.edit_text_username);
        password_edtxt = (EditText) findViewById(R.id.edit_text_password);
        device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase();
        if (device_id.equalsIgnoreCase("79EBD3D9E8AE44")) {
            username.setText("dpcuser55");
            password_edtxt.setText("alluser");
        } else if (device_id.equalsIgnoreCase("8C8004C0CCD7525F")) {
            username.setText("dpcuser41");
            password_edtxt.setText("alluser");
        } else if (device_id.equalsIgnoreCase("66E6C1D62CE916BC")) {
            username.setText("dpcuser51");
            password_edtxt.setText("alluser");
        }
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                checkLoginCredentials();
                break;
        }
    }

    /**
     * check log in
     */
    public void checkLoginCredentials() {
        String user_nm = username.getText().toString().trim();
        String password = password_edtxt.getText().toString().trim();
        login_dto = new LoginDto();
        login_dto.setUserName(user_nm);
        login_dto.setPassword(password);
        login_dto.setAppType(ApplicationType.DPC);
        String apptype = String.valueOf(ApplicationType.DPC);
        if (user_nm.isEmpty()) {
            showToastMessage(getString(R.string.toast_Please_Enter_the_User_Name), 3000);
        } else if (password.isEmpty()) {
            showToastMessage(getString(R.string.toast_Please_Enter_the_Password), 3000);
        } else {
            overridePendingTransition(R.anim.right_in, R.anim.left_out);
            HashMap<String, Object> inputParams = Utilities.getInstance().getRequestParams();
            inputParams.put("userName", user_nm);
            inputParams.put("password", password);
            inputParams.put("deviceId", device_id);
            inputParams.put("appType", apptype);
            Object data = "";
            networkConnection = new NetworkConnection(this);
            if (networkConnection.isNetworkAvailable()) {
                controller.handleMessage(ResReqController.LOGIN, inputParams, data);
            } else {
                if (MySharedPreference.readBoolean(LoginActivity.this,
                        MySharedPreference.SYNC_COMPLETE, false)) {
                    LoginCheck loginLocal = new LoginCheck(this);
                    LoginRequest loginCredentials = new LoginRequest();
                    loginCredentials.setUserName(user_nm);
                    loginCredentials.setPassword(password);
                    loginCredentials.setDeviceId(device_id);
                    loginLocal.localLoginoffline(loginCredentials);
                } else {
                    showToastMessage(getString(R.string.internetnotavailable), 3000);
                }
            }
        }
    }

    /**
     * Menu creation
     * Used to change language
     */
    public void showPopupMenu(View v) {
        List<MenuDataDto> menuDto = new ArrayList<>();
        menuDto.add(new MenuDataDto("Language", R.drawable.ic_language, "மொழி"));
        menuDto.add(new MenuDataDto("Change URL", R.drawable.ic_server, "யுஆர்யல் மாற்று"));
        menuDto.add(new MenuDataDto("Device Details", R.drawable.ic_device_details, "சாதனத் தகவல்"));
        menuDto.add(new MenuDataDto("Printer", R.drawable.ic_printer, "அச்சுப் பொறி"));
        popupWindow = new ListPopupWindow(this);
        ListAdapter adapter = new MenuAdapter(this, menuDto); // The view ids to map the data to
        popupWindow.setAnchorView(v);
        popupWindow.setAdapter(adapter);
        popupWindow.setWidth(400); // note: don't use pixels, use a dimen resource
        popupWindow.setOnItemClickListener(this); // the callback for when a list item is selected
        popupWindow.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        popupWindow.dismiss();
        switch (position) {
            case 0:
                new LanguageSelectionDialog(this).show();
                break;
            case 1:
                new ChangeUrlDialog(this).show();
                break;
            case 2:
                new DeviceDialog(this).show();
                break;
            case 3:
                printerConfiguration();
                break;
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case ResReqController.LOGIN_SUCCESS:
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                Log.d("Check", "" + msg.obj.toString());
                loginresponse_dto = gson.fromJson(msg.obj.toString(), ResponseDto.class);
                LoginData.getInstance().setResponseData(loginresponse_dto);
                syncTime = DBHelper.getInstance(this).getMasterData("syncTime");
                boolean passwordstatus = true;
                UserDetailDto userdto = DBHelper.getInstance(LoginActivity.this).checkuserdetails(username.getText().toString());
                if (loginresponse_dto.getStatusCode().equalsIgnoreCase("160003")
                        || loginresponse_dto.getStatusCode().equalsIgnoreCase("1001")
                        || loginresponse_dto.getStatusCode().equalsIgnoreCase("1000")) {
                    if (userdto != null)
                        passwordstatus = password_edtxt.getText().toString().equals(Util.DecryptPassword(userdto.getEncryptedPassword()));
                    showToastMessage(getString(R.string.toast_userName_or_Password_is_incorrect), 3000);
                } else if (loginresponse_dto.getStatusCode().equalsIgnoreCase("10102") || loginresponse_dto.getStatusCode().equalsIgnoreCase("2000")) {
                    showToastMessage(getString(R.string.toast_userName_or_Password_is_incorrect), 3000);
                } else if (loginresponse_dto.getStatusCode().equalsIgnoreCase("105") || loginresponse_dto.getStatusCode().equalsIgnoreCase("2000")) {
                    showToastMessage(getString(R.string.toast_Device_inactive_contact_Help_Desk), 3000);
                } else if (loginresponse_dto.getStatusCode().equalsIgnoreCase("202") && loginresponse_dto.getDeviceStatus().toString().equalsIgnoreCase("INACTIVE")) {
                    showToastMessage(getString(R.string.toast_device_not_reg), 3000);
                } else if (loginresponse_dto.getStatusCode().equalsIgnoreCase("160005")) {
                    showToastMessage(getString(R.string.toast_Device_request_already_sent), 3000);
                } else if (loginresponse_dto.getStatusCode().equalsIgnoreCase("1600068")) {
                    showToastMessage(getString(R.string.toast_dpc_inactive), 3000);
                } else if (loginresponse_dto.getStatusCode().equalsIgnoreCase("1600069")) {
                    showToastMessage(getString(R.string.toast_app_type), 3000);
                } else if (loginresponse_dto.getStatusCode().equalsIgnoreCase("160008")) {
                    showToastMessage(getString(R.string.toast_device_not_associated), 3000);
                } else if (loginresponse_dto.getUserDetailDto() != null && !loginresponse_dto.getUserDetailDto().isActive()) {
                    showToastMessage(getString(R.string.toast_user_inactive), 3000);
                } else if (loginresponse_dto.getStatusCode().equalsIgnoreCase("10100")) {
                    showToastMessage(getString(R.string.internalError_Server), 3000);
                } else if (loginresponse_dto.getDeviceStatus().toString().equalsIgnoreCase("UNASSOCIATED")) {
                    showToastMessage(getString(R.string.toast_device_register), 3000);
                    deviceRegistration();
                } else if (loginresponse_dto.getStatusCode().equalsIgnoreCase("11012")) {
                    showToastMessage(getString(R.string.toast_User_is_not_associated), 3000);
                } else if (loginresponse_dto.getStatusCode().equalsIgnoreCase("0")
                        && loginresponse_dto.getDeviceStatus().toString().equalsIgnoreCase("ACTIVE")
                        && loginresponse_dto.getUserDetailDto().getProfile().equalsIgnoreCase("DPC")
                        || loginresponse_dto.getUserDetailDto().getProfile().equalsIgnoreCase("ADMIN")) {

                    long diff = new Date().getTime() - new Date(loginresponse_dto.getServerCurrentDateTime()).getTime();//as given
                    long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);

                    if (seconds < 300 && seconds > -300) {
                        DBHelper.getInstance(LoginActivity.this).dpcprofile(loginresponse_dto.getDpcProfileDto());
                        MySharedPreference.writeBoolean(LoginActivity.this,
                                MySharedPreference.DEVICE_STATUS, true);
                        String session_id = loginresponse_dto.getSessionid();
                        Log.e("Login", "Session Id " + loginresponse_dto.getSessionid());
                        SessionId.getInstance().setSessionId(session_id);
                        SessionId.getInstance().setUserId(loginresponse_dto.getUserDetailDto().getId());
                        CheckUserApk();
                        insertLoginHistoryDetails();
                    } else {
                        DateChangeDialog dateChangeDialog = new DateChangeDialog(LoginActivity.this, loginresponse_dto.getServerCurrentDateTime());
                        dateChangeDialog.show();
                    }
                    new Call_service().startservices(this);
                }
                if (loginresponse_dto != null) {
                    if (loginresponse_dto.getDeviceStatus() != null) {
                        MySharedPreference.writeString(LoginActivity.this,
                                MySharedPreference.STRING_DEVICE_STATUS, loginresponse_dto.getDeviceStatus().toString());
                    }

                    if (userdto == null && loginresponse_dto.getUserDetailDto() != null) {
                        DBHelper.getInstance(LoginActivity.this).
                                insertUserDetails(loginresponse_dto.getUserDetailDto(), password_edtxt.getText().toString(), loginresponse_dto.getStatusCode());
                    } else {
                        if (passwordstatus) {
                            UserDetailDto userdetaildto = new UserDetailDto();
                            userdetaildto.setUserId(username.getText().toString());
                            userdetaildto.setStatusCode(loginresponse_dto.getStatusCode());
                            DBHelper.getInstance(LoginActivity.this).
                                    updatetUserDetails(userdetaildto, "");
                        }
                    }
                }
                return true;
            case ResReqController.LOGIN_FAILED:
                showToastMessage(getString(R.string.toast_server_unreachable), 3000);
                Log.d("Error", "" + msg.obj.toString());
                return true;
            case ResReqController.DEVICE_REGISTRATION_SUCCESS:
                GsonBuilder gsonBuilder_ = new GsonBuilder();
                Gson gson_ = gsonBuilder_.create();
                Log.d("Check", "" + msg.obj.toString());
                ResponseDto login_dto_ = gson_.fromJson(msg.obj.toString(), ResponseDto.class);
                if (login_dto_.getStatusCode().equalsIgnoreCase("160007")) {
                    showToastMessage(getString(R.string.toast_device_not_register), 3000);
                } else if (login_dto_.getStatusCode().equalsIgnoreCase("201")) {
                    showToastMessage(getString(R.string.toast_admin_credentials), 3000);
                }
                return true;
            case ResReqController.DEVICE_REGISTRATION_FAILED:
                showToastMessage(getString(R.string.toast_server_unreachable), 3000);
                Log.d("Error", "" + msg.obj.toString());
                return true;
            case ResReqController.VERSION_UPGRADE_SUCCESS:
                GsonBuilder gsonBuilder1 = new GsonBuilder();
                Gson gson1 = gsonBuilder1.create();
                VersionUpgradeDto versionUpgradeDto = gson1.fromJson(msg.obj.toString(), VersionUpgradeDto.class);
                if (versionUpgradeDto.getStatusCode().equalsIgnoreCase("424")) {
                    navigateToAdmin();
                } else {
                    if (versionUpgradeDto == null || versionUpgradeDto.getVersion() == 0 || StringUtils.isEmpty(versionUpgradeDto.getLocation())) {
                        showToastMessage(getString(R.string.toast_error_upgrade), 3000);
                    } else {
                        if (Integer.parseInt(versionUpgradeDto.getStatusCode()) == 0) {
                            PackageInfo pInfo = null;
                            try {
                                pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                            if (versionUpgradeDto.getVersion() > pInfo.versionCode) {
                                Log.e("error versionCode", "error if--------------------");
                                Intent intent = new Intent(this, AutoUpgrationActivity.class);
                                intent.putExtra("downloadPath", versionUpgradeDto.getLocation());
                                intent.putExtra("newVersion", versionUpgradeDto.getVersion());
                                startActivity(intent);
                                finish();
                            } else {
                                navigateToAdmin();
                            }
                        } else {
                            showToastMessage(getString(R.string.toast_error_upgrade), 3000);
                        }
                    }
                }
                return true;
            case ResReqController.VERSION_UPGRADE_FAILED:
                showToastMessage(getString(R.string.toast_server_unreachable), 3000);
                Log.e("ERROR", "" + msg.obj.toString());
                return true;
        }
        return false;
    }

    private void navigateToAdmin() {
        if (loginresponse_dto.getStatusCode().equalsIgnoreCase("0") && loginresponse_dto.getDeviceStatus().toString().equalsIgnoreCase("ACTIVE") && loginresponse_dto.getUserDetailDto().getProfile().equalsIgnoreCase("DPC")) {
            if (syncTime == null) {
                showToastMessage(getString(R.string.toast_Please_user), 3000);
//                Toast.makeText(LoginActivity.this, getString(R.string.toast_Please_user), Toast.LENGTH_SHORT).show();
                return;
            }
            String session_id = loginresponse_dto.getSessionid();
            SessionId.getInstance().setSessionId(session_id);
            startServices();
            Intent i = new Intent(LoginActivity.this, DashBoardActivity.class);
            startActivity(i);
            String genereted_code = LoginData.getInstance().getResponseData().getDpcProfileDto().getGeneratedCode();
            MySharedPreference.writeString(LoginActivity.this, MySharedPreference.GENERATED_CODE, genereted_code);
            String language = MySharedPreference.readString(getApplicationContext(), MySharedPreference.DISTRICT_ID, "");
        } else if (loginresponse_dto.getStatusCode().equalsIgnoreCase("0") && loginresponse_dto.getDeviceStatus().toString().equalsIgnoreCase("ACTIVE") && loginresponse_dto.getUserDetailDto().getProfile().equalsIgnoreCase("ADMIN")) {
            if (syncTime == null) {
                startActivity(new Intent(this, SyncPageActivity.class));
            } else {
                Intent in = new Intent(LoginActivity.this, AdminActivity.class);
                startActivity(in);
            }
        }
    }

    public void startServices() {
        startService(new Intent(this, UpdateDataService.class));
        startService(new Intent(this, OffLineFarmerSyncService.class));
        startService(new Intent(this, OffLineProcurementSync.class));
        startService(new Intent(this, OfflineTruckMemoManager.class));
    }

    private void CheckUserApk() {
        try {
            if (networkConnection.isNetworkAvailable()) {
                VersionUpgradeDto version = new VersionUpgradeDto();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                String versionUpgrade = gson.toJson(version);
                controller.handleMessage_(ResReqController.VERSION_UPGRADE, versionUpgrade, null);
            } else {
                showToastMessage(getString(R.string.toast_connection_error), 3000);
            }
        } catch (Exception e) {
            showToastMessage(getString(R.string.toast_connection_error), 3000);
        }
    }

    public void deviceRegistration() {
        try {
            DPCDeviceRegRequestDto dpc_device_dto = new DPCDeviceRegRequestDto();
            dpc_device_dto.setDpcDeviceDetailsDto(DeviceDetailsDto.getAllDeviceDetails());
            dpc_device_dto.setLoginDto(login_dto);
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            String login = gson.toJson(dpc_device_dto);
            controller.handleMessage_(ResReqController.DEVICE_REGISTRATION, login, null);
        } catch (Exception e) {
            Log.e("LoginActivity", e.toString(), e);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        set_online_off(isConnected);
    }

    @Override
    protected void onStart() {
        super.onStart();
        removeAllServices();
    }

    private void removeAllServices() {
        stopService(new Intent(this, UpdateDataService.class));
        stopService(new Intent(this, FirstSyncExceptionService.class));
//        stopService(new Intent(this, OffLineFarmerSyncService.class));
        stopService(new Intent(this, OffLineProcurementSync.class));
        stopService(new Intent(this, OfflineTruckMemoManager.class));
    }

    private void insertLoginHistoryDetails() {
        LoginHistoryDto loginHistoryDto = new LoginHistoryDto();
        if (loginresponse_dto.getUserDetailDto().getDpcProfileDto() != null)
            loginHistoryDto.setDpcId(loginresponse_dto.getUserDetailDto().getDpcProfileDto().getId());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        loginHistoryDto.setLoginTime(df.format(new Date()));
        loginHistoryDto.setLoginType("ONLINE_LOGIN");
        loginHistoryDto.setUserId(loginresponse_dto.getUserDetailDto().getId());
        df = new SimpleDateFormat("ddMMyyHHmmss", Locale.getDefault());
        String transactionId = df.format(new Date());
        loginHistoryDto.setTransactionId(transactionId);
        SessionId.getInstance().setTransactionId(transactionId);
        DBHelper.getInstance(this).insertLoginHistory(loginHistoryDto);
    }

    private void printerConfiguration() {
        showProgressBar(LoginActivity.this);
        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, filter);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
        }
        mBluetoothAdapter.startDiscovery();
    }

    public void bluetoothRegister(BluetoothDevice device) {
        try {
            unregisterReceiver(mReceiver);
            MySharedPreference.writeString(LoginActivity.this, MySharedPreference.DEVICE_ADDRESS, device.getAddress());
            Toast.makeText(LoginActivity.this, getString(R.string.device_connected), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, getString(R.string.device_not_connected), Toast.LENGTH_SHORT).show();
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

}


