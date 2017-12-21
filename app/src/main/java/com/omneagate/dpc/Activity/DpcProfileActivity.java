package com.omneagate.dpc.Activity;


import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.omneagate.dpc.Model.DPCProfileDto;
import com.omneagate.dpc.Model.DpcDistrictDto;
import com.omneagate.dpc.Model.DpcTalukDto;
import com.omneagate.dpc.Model.DpcVillageDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.DBHelper;


public class DpcProfileActivity extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private TextView code, name, district, village, taluk, type,
            weighing_machine, procuring_day, sim_number, category;
    private DPCProfileDto dpcProfileDtos;
    private Button btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dpc_profile);
        checkInternetConnection();
        setUpView();
    }

    private void setUpView() {
        setUpPopUpPage();
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_Dpc_profile));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);

        code = (TextView) findViewById(R.id.tV_dpc_code);
        name = (TextView) findViewById(R.id.tV_dpc_name);
        district = (TextView) findViewById(R.id.tV_dpc_district);
        village = (TextView) findViewById(R.id.tV_dpc_village);
        taluk = (TextView) findViewById(R.id.tV_dpc_taluk);
        type = (TextView) findViewById(R.id.tV_dpc_type);
        weighing_machine = (TextView) findViewById(R.id.tV_weighing_machine);
        procuring_day = (TextView) findViewById(R.id.tV_procuringCapacity);
        sim_number = (TextView) findViewById(R.id.tV_sim_number);
        category = (TextView) findViewById(R.id.tV_dpc_category);
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
        dpcProfileDtos = DBHelper.getInstance(this).getDpcProfile();
        if (dpcProfileDtos != null) {
            setValues();
        }
    }

    private void setValues() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String sim_number_ = telephonyManager.getSimSerialNumber();
        code.setText(dpcProfileDtos.getGeneratedCode());
        DpcDistrictDto district_dto = DBHelper.getInstance(this).getDistrictName_byid_(dpcProfileDtos.getDpcDistrictDto().getId());
        DpcTalukDto talukt_dto = DBHelper.getInstance(this).getTalukName_byid_(dpcProfileDtos.getDpcTalukDto().getId());
        DpcVillageDto village_dto = DBHelper.getInstance(this).getVillageName_byid_(dpcProfileDtos.getDpcVillageDto().getId());

        if (GlobalAppState.language.equalsIgnoreCase("ta")) {
            name.setText(dpcProfileDtos.getLname());
            district.setText("" + district_dto.getLdistrictName());
            village.setText("" + village_dto.getLvillageName());
            taluk.setText("" + talukt_dto.getLtalukName());
        } else {
            name.setText(dpcProfileDtos.getName());
            district.setText("" + district_dto.getName());
            village.setText("" + village_dto.getName());
            taluk.setText("" + talukt_dto.getName());
        }

        type.setText(dpcProfileDtos.getDpcType());
        weighing_machine.setText("" + dpcProfileDtos.getWeighingCapacity());
        procuring_day.setText("" + dpcProfileDtos.getStorageCapacity());

        if (sim_number_ == null || (sim_number_.equalsIgnoreCase("null") || sim_number_.isEmpty())) {
            sim_number.setText("-");
        } else {
            sim_number.setText(sim_number_);
        }
        category.setText(dpcProfileDtos.getDpcCategory());
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

            case R.id.btn_close:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }
}
