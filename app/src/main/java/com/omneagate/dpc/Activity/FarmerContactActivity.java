package com.omneagate.dpc.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.omneagate.dpc.Adapter.DistrictAdapter;
import com.omneagate.dpc.Adapter.TalukAdapter;
import com.omneagate.dpc.Adapter.VillageAdapter;
import com.omneagate.dpc.Model.DpcDistrictDto;
import com.omneagate.dpc.Model.DpcTalukDto;
import com.omneagate.dpc.Model.DpcVillageDto;
import com.omneagate.dpc.Model.FarmerRegistrationRequestDto;
import com.omneagate.dpc.Model.FirstSyncDistrictDto;
import com.omneagate.dpc.Model.FirstSyncTalukDto;
import com.omneagate.dpc.Model.VillageDtoId;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.LoginData;
import com.omneagate.dpc.Utility.NoDefaultSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 5/8/16.
 */
public class FarmerContactActivity extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private Button btn_next, btn_calcel;
    private NoDefaultSpinner spinner_district;
    private NoDefaultSpinner spinner_village;
    private NoDefaultSpinner spinner_land_type;
    private NoDefaultSpinner spinner_taluk;
    private DBHelper db;
    private List<FirstSyncDistrictDto> district_list;
    private DistrictAdapter district_adapter;
    private String district_nm;
    private long district_id;
    private List<FirstSyncTalukDto> taluk_list;
    private TalukAdapter taluk_adapter;
    private String VillageName;
    private long Villageid;
    private List<VillageDtoId> Village_List;
    private String TalukName;
    private long taluk_id;
    private VillageAdapter village_adapter;
    private View v1;
    private FarmerRegistrationRequestDto farmerRegistrationRequest;
    public static int activityScreen;
    private List<VillageDtoId> village_name_list;
    private EditText mobile_nuber, addressLine1, addressLine2, pinCode;
    private int farmerDistrictid, farmerTalukid, farmerVillageid;
    private String dis_flag, tal_flag;
    private TextView step1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        checkInternetConnection();
        setUpView();
    }

    private void setUpView() {
        FarmerBankDetailsActivity.screen = 2;
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_Farmer_Registration));
        dis_flag = "no";
        tal_flag = "no";
        setUpPopUpPage();
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        btn_calcel = (Button) findViewById(R.id.btn_cancel);
        btn_calcel.setOnClickListener(this);
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        step1 = (TextView) findViewById(R.id.num_txt_1);
        step1.setOnClickListener(this);
        step1.setBackground(getResources().getDrawable(R.drawable.shape_circle_steps_moved));
        TextView step2 = (TextView) findViewById(R.id.num_txt_2);
        step2.setBackground(getResources().getDrawable(R.drawable.shape_circle_steps_moved));
        v1 = (View) findViewById(R.id.view_txt_1);
        v1.setBackgroundColor(getResources().getColor(R.color.step_moved));
        village_name_list = new ArrayList<>();
        taluk_list = new ArrayList<>();
        farmerRegistrationRequest = new FarmerRegistrationRequestDto();
        farmerRegistrationRequest = (FarmerRegistrationRequestDto) getIntent().getSerializableExtra("FarmerRegistrationRequest");
        mobile_nuber = (EditText) findViewById(R.id.ed_mobile_number);
        mobile_nuber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mobile_nuber.getText().toString().length() > 0) {
                    if (mobile_nuber.getText().toString().length() == 10) {
                      /*  InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(((EditText) findViewById(R.id.ed_mobile_number)).getWindowToken(), 0);*/
                        addressLine1.requestFocus();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        addressLine1 = (EditText) findViewById(R.id.edit_text_address_1);
        addressLine2 = (EditText) findViewById(R.id.edit_text_address_2);
        pinCode = (EditText) findViewById(R.id.edit_text_pin_code);
        pinCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (pinCode.getText().toString().length() > 0) {
                    if (pinCode.getText().toString().length() == 6) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(((EditText) findViewById(R.id.edit_text_pin_code)).getWindowToken(), 0);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        db = new DBHelper(this);
        db.getWritableDatabase();
        spinner_taluk = (NoDefaultSpinner) findViewById(R.id.spinner_taluk_name);
        taluk_list = new ArrayList<FirstSyncTalukDto>();
        spinner_land_type = (NoDefaultSpinner) findViewById(R.id.spinner_land_type);
        spinner_village = (NoDefaultSpinner) findViewById(R.id.spinner_village);
        spinner_district = (NoDefaultSpinner) findViewById(R.id.spinner_district);
        district_list = new ArrayList<FirstSyncDistrictDto>();
        district_list = db.getDistrictName();
        district_adapter = new DistrictAdapter(this, district_list);
        spinner_district.setAdapter(district_adapter);

        spinner_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                taluk_list.clear();
                setTaluks(taluk_list);

                village_name_list.clear();
                getvillages(village_name_list);

                district_nm = district_list.get(position).getName();
                district_id = district_list.get(position).getId();
                Log.e("district_nm", district_nm);
                Log.e("district_id", "" + district_id);

                if (dis_flag.equalsIgnoreCase("yes")) {
                    TalukName = null;
                    VillageName = null;
                }

                dis_flag = "yes";

                if (farmerRegistrationRequest.getDpcDistrictDto() == null || dis_flag.equalsIgnoreCase("yes")) {
                    taluk_list = db.getTalukName(district_id);
                    setTaluks(taluk_list);
                }
                //============================
                if (farmerRegistrationRequest.getDpcTalukDto() != null && activityScreen == 1) {


                    if (farmerRegistrationRequest.getDpcTalukDto().getName() != null) {
                        GetFarmerTalukPostion();
                        spinner_taluk.setSelection(farmerTalukid);
                        activityScreen = 1;
                        Log.e("position", "famerTalukid" + taluk_id);
                    } else {
                        activityScreen = 2;
                    }
                } else {
                    activityScreen = 2;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinner_taluk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                village_name_list.clear();
                getvillages(village_name_list);

                TalukName = taluk_list.get(position).getName();
                taluk_id = taluk_list.get(position).getId();

                String taluk_id = db.getTalukId(TalukName);
                tal_flag = "yes";

                if (farmerRegistrationRequest.getDpcDistrictDto() == null || tal_flag.equalsIgnoreCase("yes")) {
                    village_name_list = db.getvillsges(taluk_id);
                    getvillages(village_name_list);
                }

                //===========================

                if (farmerRegistrationRequest.getDpcVillageDto() != null && activityScreen == 1) {
                    if (farmerRegistrationRequest.getDpcVillageDto().getName() != null) {
                        GetFarmerVillagePostion();
                        spinner_village.setSelection(farmerVillageid);
                        activityScreen = 1;
                    } else {
                        activityScreen = 2;
                    }
                } else {
                    activityScreen = 2;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                activityScreen = 2;

                VillageName = Village_List.get(position).getName();
                Villageid = Village_List.get(position).getId();
                Log.e("village name", VillageName);
                Log.e("village id", "" + Villageid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        CheckifvaluesExist();

    }

    private void CheckifvaluesExist() {
        if (farmerRegistrationRequest.getMobileNumber() != null) {
            mobile_nuber.setText(farmerRegistrationRequest.getMobileNumber());
        }
        if (farmerRegistrationRequest.getAddress1() != null) {
            addressLine1.setText(farmerRegistrationRequest.getAddress1());
        }
        if (farmerRegistrationRequest.getAddress2() != null) {
            addressLine2.setText(farmerRegistrationRequest.getAddress2());
        }
        if (farmerRegistrationRequest.getPincode() != null) {
            pinCode.setText(farmerRegistrationRequest.getPincode());
        }

        if (farmerRegistrationRequest.getDpcDistrictDto() != null) {
            if (farmerRegistrationRequest.getDpcDistrictDto().getName() != null) {
                GetFarmerDistrictPostion();
                spinner_district.setSelection(farmerDistrictid);
                district_nm = district_list.get(farmerDistrictid).getName();
                district_id = district_list.get(farmerDistrictid).getId();
            }
        }
    }

    private void GetFarmerDistrictPostion() {
        for (int i = 0; i < district_list.size(); i++) {
            String district = district_list.get(i).getName();
            if (farmerRegistrationRequest.getDpcDistrictDto().getName().equals(district)) {
                farmerDistrictid = i;
            }
        }
    }

    private void GetFarmerTalukPostion() {
        for (int i = 0; i < taluk_list.size(); i++) {
            String taluk = taluk_list.get(i).getName();
            if (farmerRegistrationRequest.getDpcTalukDto().getName().equals(taluk)) {
                farmerTalukid = i;
            }
        }
    }

    private void GetFarmerVillagePostion() {
        for (int i = 0; i < Village_List.size(); i++) {
            String village = Village_List.get(i).getName();
            if (farmerRegistrationRequest.getDpcVillageDto().getName().equals(village)) {
                farmerVillageid = i;
            }
        }
    }

    public void getvillages(List<VillageDtoId> village_nm) {
        Village_List = village_nm;
        village_adapter = new VillageAdapter(this, Village_List);
        spinner_village.setAdapter(village_adapter);
    }

    public void setTaluks(List<FirstSyncTalukDto> taluk_nm) {
        taluk_list = taluk_nm;
        taluk_adapter = new TalukAdapter(this, taluk_list);
        spinner_taluk.setAdapter(taluk_adapter);
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        set_online_offline(isConnected);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.image_back:
                Intent ii_ = new Intent(FarmerContactActivity.this, FarmerRegistrationActivity.class);
                ii_.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                startActivity(ii_);
                activityScreen = 1;
                finish();
                break;

            case R.id.btn_next:
                checkValues();
                break;
            case R.id.num_txt_1:
                Intent numberbutton = new Intent(FarmerContactActivity.this, FarmerRegistrationActivity.class);
                numberbutton.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                startActivity(numberbutton);
                finish();
                break;

            case R.id.btn_cancel:
                Intent cancel = new Intent(FarmerContactActivity.this, FarmerRegistrationActivity.class);
                cancel.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                startActivity(cancel);
                break;
            default:
                break;
        }

    }

    private void checkValues() {

        boolean mobile_return = DBHelper.getInstance(this).chechMobileNumber(mobile_nuber.getText().toString());
        boolean mobile_return_request = DBHelper.getInstance(this).chechMobileNumberRequest(mobile_nuber.getText().toString());
        if (mobile_nuber.getText().toString().trim().equalsIgnoreCase("")) {
            showToastMessage(getString(R.string.toast_Please_enter_the_mobile_number), 3000);
            return;
        } else if (mobile_nuber.getText().toString().trim().length() < 10) {
            showToastMessage(getString(R.string.toast_mbl), 3000);
            return;
        } else if (!(mobile_nuber.getText().toString().trim().startsWith("9") || mobile_nuber.getText().toString().startsWith("8") || mobile_nuber.getText().toString().startsWith("7"))) {
            showToastMessage(getString(R.string.toast_mbl_num), 3000);
            return;
        } else if (addressLine1.getText().toString().trim().equalsIgnoreCase("")) {
            showToastMessage(getString(R.string.toast_Please_address), 3000);
            return;
        } else if (addressLine1.getText().toString().trim().length() < 2) {
            showToastMessage(getString(R.string.toast_ad1), 3000);
            return;
        } else if (district_nm == null) {
            showToastMessage(getString(R.string.toast_Please_dis), 3000);
            return;
        } else if (TalukName == null) {
            showToastMessage(getString(R.string.toast_Please_taluk), 3000);
            return;
        } else if (VillageName == null) {
            showToastMessage(getString(R.string.toast_Please_select_village), 3000);
            return;
        } else if (pinCode.getText().toString().trim().equalsIgnoreCase("")) {
            showToastMessage(getString(R.string.toast_Please_pin), 3000);
            return;
        } else if (pinCode.getText().toString().trim().length() < 6) {
            showToastMessage(getString(R.string.toast_pin_cd), 3000);
            return;
        } else if (!pinCode.getText().toString().trim().startsWith("6")) {
            showToastMessage(getString(R.string.toast_pin_six), 3000);
            return;
        } else if (mobile_return == false) {
            showToastMessage(getString(R.string.toast_mbl_already), 3000);
            return;
        } else if (mobile_return_request == false) {
            showToastMessage(getString(R.string.toast_mbl_already), 3000);
            return;
        } else {
            String spinnerValue = spinner_district.getSelectedItem().toString().trim();
            Log.e("Check Spinner value", spinnerValue);
            setValues();
            Intent i_ = new Intent(FarmerContactActivity.this, FarmerBankDetailsActivity.class);
            i_.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
            startActivity(i_);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent ii_ = new Intent(FarmerContactActivity.this, FarmerRegistrationActivity.class);
        ii_.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
        startActivity(ii_);
        activityScreen = 1;
        finish();
    }

    private void setValues() {
        if (mobile_nuber.getText().toString() != null) {
            farmerRegistrationRequest.setMobileNumber(mobile_nuber.getText().toString());
        }
        farmerRegistrationRequest.setAddress1(addressLine1.getText().toString());
        if (addressLine2 != null) {
            farmerRegistrationRequest.setAddress2(addressLine2.getText().toString());
        }

        DpcDistrictDto districtDto = new DpcDistrictDto();
        districtDto.setName(district_nm);
        districtDto.setId(district_id);
        farmerRegistrationRequest.setDpcDistrictDto(districtDto);

        DpcTalukDto talukDto = new DpcTalukDto();
        talukDto.setName(TalukName);
        talukDto.setId(taluk_id);
        farmerRegistrationRequest.setDpcTalukDto(talukDto);

        DpcVillageDto village = new DpcVillageDto();
        village.setName(VillageName);
        village.setId(Villageid);
        farmerRegistrationRequest.setDpcVillageDto(village);

        farmerRegistrationRequest.setPincode(pinCode.getText().toString());
        farmerRegistrationRequest.setStatus(true);
        farmerRegistrationRequest.setCreatedBy(LoginData.getInstance().getResponseData().getUserDetailDto().getCreatedBy());
        farmerRegistrationRequest.setModifiedBy(LoginData.getInstance().getResponseData().getUserDetailDto().getModifiedBy());
//        farmerRegistrationRequest.setDpcProfileDto(LoginData.getInstance().getResponseData().getDpcProfileDto());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }
}
