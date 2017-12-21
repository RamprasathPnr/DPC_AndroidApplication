package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.omneagate.dpc.Activity.Dialog.LogOutDialog;
import com.omneagate.dpc.Model.DpcTruckMemoDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;

/**
 * Created by user on 28/6/16.
 */
public class DashBoardActivity extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {
    private RelativeLayout layout_procurement_history, layout_truck_memo_history,
            layout_former_list, layout_reports, layout_procurement,
            layout_truck_memo, layout_daily_wages, layout_others;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        checkInternetConnection();
        setUpView();
    }

    public void setUpView() {
        setUpPopUpPage();
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_dashboard));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        layout_procurement_history = (RelativeLayout) findViewById(R.id.relay_procurement_history);
        layout_truck_memo_history = (RelativeLayout) findViewById(R.id.relay_truck_memo_history);
        layout_former_list = (RelativeLayout) findViewById(R.id.relay_former_list);
        layout_reports = (RelativeLayout) findViewById(R.id.relay_reports);
        layout_procurement = (RelativeLayout) findViewById(R.id.relay_procurement);
        layout_truck_memo = (RelativeLayout) findViewById(R.id.relay_truck_memo);
        layout_daily_wages = (RelativeLayout) findViewById(R.id.relay_daily_wages);
        layout_others = (RelativeLayout) findViewById(R.id.relay_others);
        layout_procurement_history.setOnClickListener(this);
        layout_truck_memo_history.setOnClickListener(this);
        layout_former_list.setOnClickListener(this);
        layout_reports.setOnClickListener(this);
        layout_procurement.setOnClickListener(this);
        layout_truck_memo.setOnClickListener(this);
        layout_daily_wages.setOnClickListener(this);
        layout_others.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                userLogoutResponse();
                break;
            case R.id.relay_procurement_history:
                Intent procurement_history = new Intent(DashBoardActivity.this, ProcurementHistory.class);
                startActivity(procurement_history);
                finish();
                break;
            case R.id.relay_truck_memo_history:
                Intent truck_memo_history = new Intent(DashBoardActivity.this, TruckMemoHistoryActivity.class);
                startActivity(truck_memo_history);
                finish();
                break;
            case R.id.relay_former_list:
                Intent former_intent = new Intent(DashBoardActivity.this, FarmerListActivity.class);
                startActivity(former_intent);
                finish();
                break;
            case R.id.relay_reports:
                break;
            case R.id.relay_procurement:
                Intent procurement_intent = new Intent(DashBoardActivity.this, ProcurementActivity.class);
                startActivity(procurement_intent);
                finish();
                break;
            case R.id.relay_truck_memo:
                Intent truckmemo_Intent = new Intent(DashBoardActivity.this, TruckMemoActivity.class);
                DpcTruckMemoDto dpcTruckMemoDto = new DpcTruckMemoDto();
                truckmemo_Intent.putExtra("TruckMemoDto", dpcTruckMemoDto);
                startActivity(truckmemo_Intent);
                finish();
                break;
            case R.id.relay_daily_wages:
                break;
            case R.id.relay_others:
                Intent others_intent = new Intent(DashBoardActivity.this, OthersActivity.class);
                startActivity(others_intent);
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }

    private void userLogoutResponse() {
        logout = new LogOutDialog(this);
        logout.show();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        set_online_offline(isConnected);
    }

    @Override
    public void onBackPressed() {
        userLogoutResponse();
    }
}
