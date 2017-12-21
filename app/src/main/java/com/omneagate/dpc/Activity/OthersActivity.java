package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;

public class OthersActivity extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private RelativeLayout layout_former_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        checkInternetConnection();
        setUpView();
    }

    private void setUpView() {
        setUpPopUpPage();
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_others));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        layout_former_list = (RelativeLayout) findViewById(R.id.relay_former_list);
        layout_former_list.setOnClickListener(this);
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
            case R.id.relay_former_list:
                Intent former_intent = new Intent(OthersActivity.this, AssociatedFarmersListActivity.class);
                startActivity(former_intent);
                finish();
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(OthersActivity.this, DashBoardActivity.class);
        startActivity(i);
        finish();
    }
}
