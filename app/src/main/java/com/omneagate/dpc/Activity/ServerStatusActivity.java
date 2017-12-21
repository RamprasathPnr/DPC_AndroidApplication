package com.omneagate.dpc.Activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.omneagate.dpc.Activity.Dialog.ServerStatusDialog;
import com.omneagate.dpc.R;

public class ServerStatusActivity extends BaseActivity implements View.OnClickListener {

    Button status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_status);
        setUpView();

    }

    private void setUpView() {
        status = (Button) findViewById(R.id.btn_status);
        status.setOnClickListener(this);
        LoginActivity login= new LoginActivity();
        login.deviceRegistration();

}

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_status:
                new ServerStatusDialog(this).show();
                break;
        }

    }
}
