package com.omneagate.dpc.Activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.omneagate.dpc.Adapter.Approvedlandadapter;
import com.omneagate.dpc.Model.BankDto;
import com.omneagate.dpc.Model.DpcDistrictDto;
import com.omneagate.dpc.Model.DpcTalukDto;
import com.omneagate.dpc.Model.DpcVillageDto;
import com.omneagate.dpc.Model.FarmerRegistrationDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.DBHelper;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class AssociatedFarmerViewDetails extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {
    private String farmer_code;
    RecyclerView recyclerView;
    ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associatedfarmerviewdetails);
        imageBack =(ImageView) findViewById(R.id.image_back);
        imageBack.setOnClickListener(this);

        ((Button) findViewById(R.id.btn_cancel)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_lands);
        farmer_code = getIntent().getStringExtra("farmer_code");
        checkInternetConnection();
        setUpView();
    }

    private void setUpView() {
        setUpPopUpPage();
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_associate_farmer_detailview));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        FarmerRegistrationDto list = DBHelper.getInstance(this).getAssociatedFarmerviewdetails(farmer_code);
        settextview(list);
        if(list.getFarmerApprovedLandDetailsDtos().get(0).getId()!=0)
        setrecylerview(list);
    }

    private void setrecylerview(FarmerRegistrationDto list) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Approvedlandadapter lands_adapter = new Approvedlandadapter(this, list.getFarmerApprovedLandDetailsDtos(), list);
        recyclerView.setAdapter(lands_adapter);
    }

    private void settextview(FarmerRegistrationDto list) {
        ((TextView) findViewById(R.id.fm_code)).setText(list.getFarmerCode());
        if (GlobalAppState.language.equalsIgnoreCase("ta")) {
            ((TextView) findViewById(R.id.fm_name)).setText(list.getFarmerLname());
            ((TextView) findViewById(R.id.fm_hus_name)).setText(list.getGuardianLname());
            ((TextView) findViewById(R.id.fm_district)).setText(get_districtname(list).getLdistrictName());
            ((TextView) findViewById(R.id.fm_taluk)).setText(get_talukname(list).getLtalukName());
            ((TextView) findViewById(R.id.fm_village)).setText(get_villagename(list).getLvillageName());
            ((TextView) findViewById(R.id.fm_bank_name)).setText(getBankName_id(list).getLname());
        } else {
            ((TextView) findViewById(R.id.fm_name)).setText(list.getFarmerName());
            ((TextView) findViewById(R.id.fm_hus_name)).setText(list.getGuardianName());
            ((TextView) findViewById(R.id.fm_district)).setText(get_districtname(list).getName());
            ((TextView) findViewById(R.id.fm_taluk)).setText(get_talukname(list).getName());
            ((TextView) findViewById(R.id.fm_village)).setText(get_villagename(list).getName());
            ((TextView) findViewById(R.id.fm_bank_name)).setText(getBankName_id(list).getBankName());
        }
        String ration = list.getRationCardNumber();
        String aadhaar = list.getAadhaarNumber();

        if (ration == null || ration.equalsIgnoreCase("null") || ration.isEmpty()) {
            ((TextView) findViewById(R.id.fm_rationcardnumber)).setText("");
        } else {
            ((TextView) findViewById(R.id.fm_rationcardnumber)).setText(list.getRationCardNumber());
        }

        if (aadhaar == null || aadhaar.equalsIgnoreCase("null") ||  aadhaar.isEmpty() ) {
            ((TextView) findViewById(R.id.fm_aadhaar_number)).setText("");
        } else {
            ((TextView) findViewById(R.id.fm_aadhaar_number)).setText(list.getAadhaarNumber());
        }
        ((TextView) findViewById(R.id.fm_mobile_number)).setText(list.getMobileNumber());
        ((TextView) findViewById(R.id.fm_address_1)).setText(list.getAddress1());
        ((TextView) findViewById(R.id.fm_address_2)).setText(list.getAddress2());
        ((TextView) findViewById(R.id.fm_branch_name)).setText(list.getBranchName());
        ((TextView) findViewById(R.id.fm_ac_number)).setText(list.getAccountNumber());
        ((TextView) findViewById(R.id.fm_ifsc)).setText(list.getIfscCode());
    }

    private DpcDistrictDto get_districtname(FarmerRegistrationDto list) {
        return DBHelper.getInstance(this).getDistrictName_byid(list.getDpcDistrictDto().getId(), list.getDpcDistrictDto());
    }

    private DpcTalukDto get_talukname(FarmerRegistrationDto list) {
        return DBHelper.getInstance(this).getTalukName_byid(list.getDpcTalukDto().getId(), list.getDpcTalukDto());
    }

    private DpcVillageDto get_villagename(FarmerRegistrationDto list) {
        return DBHelper.getInstance(this).getVillageName_byid(list.getDpcVillageDto().getId(), list.getDpcVillageDto());
    }

    private BankDto getBankName_id(FarmerRegistrationDto list) {
        return DBHelper.getInstance(this).getBankName_id(list.getBankDto().getId(), list.getBankDto());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                onBackPressed();
                finish();
                break;
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        set_online_offline(isConnected);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }
}
