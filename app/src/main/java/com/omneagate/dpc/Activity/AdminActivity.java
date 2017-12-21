package com.omneagate.dpc.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.omneagate.dpc.Activity.Dialog.LogOutDialog;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.LocalDbRecoveryProcess;

/**
 * Created by root on 10/8/16.
 */
public class AdminActivity extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        checkInternetConnection();
        setUpView();
    }

    private void setUpView() {
        setUpPopUpPage();
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_dashboard));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
    }

    public void retrieve(View view) {
        LocalDbRecoveryProcess localDbRecoveryPro = new LocalDbRecoveryProcess(AdminActivity.this);
        localDbRecoveryPro.backupDb(DBHelper.DATABASE_NAME);
    }


    public void restore(View view) {
        LocalDbRecoveryProcess localDbRecoveryProcess = new LocalDbRecoveryProcess(AdminActivity.this);
        localDbRecoveryProcess.restoresDb();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        set_online_offline(isConnected);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        userLogoutResponse();
    }

    private void userLogoutResponse() {
        logout = new LogOutDialog(this);
        logout.show();
    }
}
