package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omneagate.dpc.Activity.Dialog.SuccessRegistrationDialog;
import com.omneagate.dpc.Adapter.LandsAdapter;

import com.omneagate.dpc.Model.DPCProfileDto;
import com.omneagate.dpc.Model.DPCResponseDto;
import com.omneagate.dpc.Model.FarmerLandDetailsDto;
import com.omneagate.dpc.Model.FarmerRegistrationRequestDto;

import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.ResReqController;

import com.omneagate.dpc.Utility.DBHelper;

import com.omneagate.dpc.Utility.NetworkConnection;
import com.omneagate.dpc.Utility.Util;


import java.util.List;

/**
 * Created by user on 18/7/16.
 */
public class
LandDetailsActivity extends BaseActivity implements View.OnClickListener, Handler.Callback, ConnectivityReceiver.ConnectivityReceiverListener {

    private LinearLayout add_land_details;
    private Button btn_next, btn_cancel;
    final ResReqController controller = new ResReqController(this);
    private FarmerRegistrationRequestDto farmerRegistrationRequest;
    private RecyclerView recyclerView;
    private List<FarmerLandDetailsDto> list_lands;
    private LandsAdapter lands_adapter;
    private TextView step1, step2, step3;
    private NetworkConnection networkConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_land_details);
        checkInternetConnection();
        networkConnection = new NetworkConnection(this);
        controller.addOutboxHandler(new Handler(this));
        setUpView();
    }

    private void setUpView() {
        FarmerBankDetailsActivity.screen = 2;
        setUpPopUpPage();
        farmerRegistrationRequest = new FarmerRegistrationRequestDto();
        farmerRegistrationRequest = (FarmerRegistrationRequestDto) getIntent().getSerializableExtra("FarmerRegistrationRequest");
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_Farmer_Registration));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        View v1, v2, v3;
        v1 = (View) findViewById(R.id.view_txt_1);
        v1.setBackgroundColor(getResources().getColor(R.color.step_moved));
        v2 = (View) findViewById(R.id.view_txt_2);
        v2.setBackgroundColor(getResources().getColor(R.color.step_moved));
        v3 = (View) findViewById(R.id.view_txt_3);
        v3.setBackgroundColor(getResources().getColor(R.color.step_moved));
        step1 = (TextView) findViewById(R.id.num_txt_1);
        step1.setBackground(getResources().getDrawable(R.drawable.shape_circle_steps_moved));
        step1.setOnClickListener(this);
        step2 = (TextView) findViewById(R.id.num_txt_2);
        step2.setBackground(getResources().getDrawable(R.drawable.shape_circle_steps_moved));
        step2.setOnClickListener(this);
        step3 = (TextView) findViewById(R.id.num_txt_3);
        step3.setBackground(getResources().getDrawable(R.drawable.shape_circle_steps_moved));
        step3.setOnClickListener(this);
        TextView step4 = (TextView) findViewById(R.id.num_txt_4);
        step4.setBackground(getResources().getDrawable(R.drawable.shape_circle_steps_moved));
        add_land_details = (LinearLayout) findViewById(R.id.btn_add_land_details);
        add_land_details.setOnClickListener(this);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_lands);
        recyclerView.setLayoutManager(new LinearLayoutManager(LandDetailsActivity.this));
        list_lands = Util.farmerLandDetailsDtoList;
        lands_adapter = new LandsAdapter(this, list_lands, farmerRegistrationRequest);
        recyclerView.setAdapter(lands_adapter);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
    }

    private void SetReferenceNumber() {
        String Dpccode;
        if (farmerRegistrationRequest.getRequestedReferenceNumber() == null) {
            farmerRegistrationRequest.setRequestedReferenceNumber(Util.getRegistrationResponseNumber(this));
            /*if (!networkConnection.isNetworkAvailable()) {
                Dpccode = DBHelper.getInstance(LandDetailsActivity.this).getDpcCode();
                DPCProfileDto dto = new DPCProfileDto();
                dto.setGeneratedCode(Dpccode);
                farmerRegistrationRequest.setDpcProfileDto(dto);
            }*/
            DBHelper.getInstance(this).insertFarmerRegistrationDetails(farmerRegistrationRequest);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.image_back:
                Intent image_back = new Intent(LandDetailsActivity.this, FarmerBankDetailsActivity.class);
                image_back.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                startActivity(image_back);
                finish();
                break;

            case R.id.btn_add_land_details:
                try {
                    Intent i = new Intent(LandDetailsActivity.this, AddLandDetailsActivity.class);
                    i.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                    startActivity(i);
                    finish();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();

                }

            case R.id.btn_next:
                btn_next.setVisibility(View.GONE);
                if (farmerRegistrationRequest.getFarmerLandDetailsDtoList() == null) {
                    showToastMessage(getString(R.string.toast_land), 3000);
                    btn_next.setVisibility(View.VISIBLE);
                    return;
                }

                if (networkConnection.isNetworkAvailable()) {
                    try {
                        SetReferenceNumber();
                        GsonBuilder gsonBuilder = new GsonBuilder();
                        Gson gson = gsonBuilder.create();
                        String registration = gson.toJson(farmerRegistrationRequest);
                        controller.handleMessage_(ResReqController.FARMER_REGISTRATION, registration, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    SetReferenceNumber();
                    String ReferenceNumber = farmerRegistrationRequest.getRequestedReferenceNumber();
                    new SuccessRegistrationDialog(this, ReferenceNumber, false).show();
                }
                break;

            case R.id.num_txt_1:
                Intent number1 = new Intent(LandDetailsActivity.this, FarmerRegistrationActivity.class);
                number1.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                startActivity(number1);
                finish();
                break;

            case R.id.num_txt_2:
                Intent number2 = new Intent(LandDetailsActivity.this, FarmerContactActivity.class);
                number2.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                startActivity(number2);
                finish();
                break;

            case R.id.num_txt_3:
                Intent number3 = new Intent(LandDetailsActivity.this, FarmerBankDetailsActivity.class);
                number3.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                startActivity(number3);
                finish();
                break;

            case R.id.btn_cancel:
                Intent cancel_intent = new Intent(LandDetailsActivity.this, FarmerBankDetailsActivity.class);
                cancel_intent.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                startActivity(cancel_intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent image_back = new Intent(LandDetailsActivity.this, FarmerBankDetailsActivity.class);
        image_back.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
        startActivity(image_back);
        finish();
    }

    /*@Override
    public void onDestroy() {
        super.onDestroy();
        if (Util.farmerLandDetailsDtoList != null) {
            Util.farmerLandDetailsDtoList.clear();
        }
    }*/


    @Override
    public boolean handleMessage(Message msg) {
        try {
            switch (msg.what) {
                case ResReqController.FARMER_REGISTRATION_SUCCESS:
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    Log.d("Check", "" + msg.obj.toString());
                    DPCResponseDto reg_res_dto = gson.fromJson(msg.obj.toString(), DPCResponseDto.class);
                    String ReferenceNumber = reg_res_dto.getReferenceNumber();
                    if (reg_res_dto.getStatusCode().equalsIgnoreCase("0")) {
                        if (Util.farmerLandDetailsDtoList != null) {
                            Util.farmerLandDetailsDtoList.clear();
                        }
                        new SuccessRegistrationDialog(this, ReferenceNumber, true).show();
                    } else if (reg_res_dto.getStatusCode().equalsIgnoreCase("95853161")) {
                        btn_next.setVisibility(View.VISIBLE);
                        Log.e("referencenumber", "" + reg_res_dto.getReferenceNumber());
                        DBHelper.getInstance(this).RemoveDuplicateNumber(farmerRegistrationRequest.getRequestedReferenceNumber());
                        Toast.makeText(LandDetailsActivity.this, getString(R.string.toast_aadhaar_already), Toast.LENGTH_SHORT).show();
                        farmerRegistrationRequest.setRequestedReferenceNumber(null);
                    } else if (reg_res_dto.getStatusCode().equalsIgnoreCase("1600061")) {
                        btn_next.setVisibility(View.VISIBLE);
                        Log.e("referencenumber", "" + reg_res_dto.getReferenceNumber());
                        DBHelper.getInstance(this).RemoveDuplicateNumber(farmerRegistrationRequest.getRequestedReferenceNumber());
                        showToastMessage(getString(R.string.toast_invalid_aadhaar_num), 3000);
                        farmerRegistrationRequest.setRequestedReferenceNumber(null);
                    } else if (reg_res_dto.getStatusCode().equalsIgnoreCase("95853160")) {
                        btn_next.setVisibility(View.VISIBLE);
                        Log.e("referencenumber", "" + reg_res_dto.getReferenceNumber());
                        DBHelper.getInstance(this).RemoveDuplicateNumber(farmerRegistrationRequest.getRequestedReferenceNumber());
                        Toast.makeText(LandDetailsActivity.this, getString(R.string.toast_mbl_already), Toast.LENGTH_SHORT).show();
                        farmerRegistrationRequest.setRequestedReferenceNumber(null);
                    } else if (reg_res_dto.getStatusCode().equalsIgnoreCase("1600054")) {
                        btn_next.setVisibility(View.VISIBLE);
                        showToastMessage(getString(R.string.toast_register_already), 3000);
                        DBHelper.getInstance(this).RemoveDuplicateNumber(farmerRegistrationRequest.getRequestedReferenceNumber());
                        farmerRegistrationRequest.setRequestedReferenceNumber(null);
                    } else if (reg_res_dto.getStatusCode().equalsIgnoreCase("500")) {
                        btn_next.setVisibility(View.VISIBLE);
                        showToastMessage(getString(R.string.internalError_Server), 3000);
                        DBHelper.getInstance(this).RemoveDuplicateNumber(farmerRegistrationRequest.getRequestedReferenceNumber());
                        farmerRegistrationRequest.setRequestedReferenceNumber(null);
                    } /*else if (reg_res_dto.getStatusCode().equalsIgnoreCase("95853162")) {
                        Toast.makeText(LandDetailsActivity.this, getString(R.string.toast_Account_error), Toast.LENGTH_SHORT).show();
                        DBHelper.getInstance(this).RemoveDuplicateNumber(farmerRegistrationRequest.getRequestedReferenceNumber());
                        farmerRegistrationRequest.setRequestedReferenceNumber(null);
                    }*/ else if (reg_res_dto.getStatusCode().equalsIgnoreCase("95853156")) {
                        btn_next.setVisibility(View.VISIBLE);
                        Toast.makeText(LandDetailsActivity.this, getString(R.string.toast_mobile_aadhar_error), Toast.LENGTH_SHORT).show();
                        DBHelper.getInstance(this).RemoveDuplicateNumber(farmerRegistrationRequest.getRequestedReferenceNumber());
                        farmerRegistrationRequest.setRequestedReferenceNumber(null);
                    }
                    return true;

                case ResReqController.FARMER_REGISTRATION_FAILED:
                    btn_next.setVisibility(View.VISIBLE);
                    showToastMessage(getString(R.string.toast_server_unreachable), 3000);
                    return true;
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        set_online_offline(isConnected);
    }
}
