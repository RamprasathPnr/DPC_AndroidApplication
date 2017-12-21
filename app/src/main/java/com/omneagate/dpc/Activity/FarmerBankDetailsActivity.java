package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.omneagate.dpc.Adapter.BankAdapter;
import com.omneagate.dpc.Model.BankDto;
import com.omneagate.dpc.Model.DPCProfileDto;
import com.omneagate.dpc.Model.FarmerRegistrationRequestDto;
import com.omneagate.dpc.Model.ResponseDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.LoginData;
import com.omneagate.dpc.Utility.NoDefaultSpinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 16/7/16.
 */
public class FarmerBankDetailsActivity extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private Button next, btn_cancel;
    private NoDefaultSpinner spinner_bank;
    private DBHelper db;
    private BankAdapter bank_adapter;
    private List<BankDto> bank_list;
    private String bank_name = null;
    private EditText branch_name, account_number, ifsc_code;
    private FarmerRegistrationRequestDto farmerRegistrationRequest;
    private long bank_id;
    private int farmerBankPosid;
    private TextView step1, step2;
    private String device_id;
    public static int screen = 1;
    private DPCProfileDto dpc_profiledto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_bank_details);
        checkInternetConnection();
        setUpView();
    }

    private void setUpView() {
        FarmerContactActivity.activityScreen = 1;
        setUpPopUpPage();
        farmerRegistrationRequest = new FarmerRegistrationRequestDto();
        farmerRegistrationRequest = (FarmerRegistrationRequestDto) getIntent().getSerializableExtra("FarmerRegistrationRequest");
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_Farmer_Registration));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        View v1, v2;
        v1 = (View) findViewById(R.id.view_txt_1);
        v1.setBackgroundColor(getResources().getColor(R.color.step_moved));
        v2 = (View) findViewById(R.id.view_txt_2);
        v2.setBackgroundColor(getResources().getColor(R.color.step_moved));
        step1 = (TextView) findViewById(R.id.num_txt_1);
        step1.setBackground(getResources().getDrawable(R.drawable.shape_circle_steps_moved));
        step1.setOnClickListener(this);
        step2 = (TextView) findViewById(R.id.num_txt_2);
        step2.setBackground(getResources().getDrawable(R.drawable.shape_circle_steps_moved));
        step2.setOnClickListener(this);
        TextView step3 = (TextView) findViewById(R.id.num_txt_3);
        step3.setBackground(getResources().getDrawable(R.drawable.shape_circle_steps_moved));
        next = (Button) findViewById(R.id.btn_next);
        next.setOnClickListener(this);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        branch_name = (EditText) findViewById(R.id.edit_text_branch_name);
        branch_name.requestFocus();
        account_number = (EditText) findViewById(R.id.edit_text_account_number);
        ifsc_code = (EditText) findViewById(R.id.edit_text_ifsc_code);
        farmerRegistrationRequest = (FarmerRegistrationRequestDto) getIntent().getSerializableExtra("FarmerRegistrationRequest");
        spinner_bank = (NoDefaultSpinner) findViewById(R.id.spinner_bank);
        db = new DBHelper(this);
        db.getWritableDatabase();
        bank_list = new ArrayList<BankDto>();
        bank_list = db.getBankName();
        bank_adapter = new BankAdapter(this, bank_list);
        spinner_bank.setAdapter(bank_adapter);

        spinner_bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bank_name = bank_list.get(position).getBankName();
                bank_id = bank_list.get(position).getId();
                Log.e("bank id", "" + bank_id);
                if (screen == 1) {
                    branch_name.setText("");
                    account_number.setText("");
                    ifsc_code.setText("");
                } else {
                    screen = 1;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ChecKIfValuesExist();
    }

    private void ChecKIfValuesExist() {
        if (farmerRegistrationRequest.getBranchName() != null) {
            branch_name.setText(farmerRegistrationRequest.getBranchName());
        }
        if (farmerRegistrationRequest.getAccountNumber() != null) {
            account_number.setText(farmerRegistrationRequest.getAccountNumber());
        }
        if (farmerRegistrationRequest.getIfscCode() != null) {
            ifsc_code.setText(farmerRegistrationRequest.getIfscCode());
        }
        if (farmerRegistrationRequest.getBankDto() != null) {
            if (farmerRegistrationRequest.getBankDto().getBankName() != null) {
                GetFarmerbankPostion();
                spinner_bank.setSelection(farmerBankPosid);
                bank_name = bank_list.get(farmerBankPosid).getBankName();
                bank_id = bank_list.get(farmerBankPosid).getId();
            }
        }
    }

    private void GetFarmerbankPostion() {
        for (int i = 0; i < bank_list.size(); i++) {
            String bank_name = bank_list.get(i).getBankName();
            if (farmerRegistrationRequest.getBankDto().getBankName().equals(bank_name)) {
                farmerBankPosid = i;
            }
        }
    }

    private void SetRegistrationBankDetails() {
        farmerRegistrationRequest.setBranchName(branch_name.getText().toString());
        farmerRegistrationRequest.setAccountNumber(account_number.getText().toString());
        farmerRegistrationRequest.setIfscCode(ifsc_code.getText().toString());
        BankDto bankDto = new BankDto();
        bankDto.setBankName(bank_name);
        bankDto.setId(bank_id);
        farmerRegistrationRequest.setBankDto(bankDto);
        device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase();
        farmerRegistrationRequest.setDeviceNumber(device_id);


        ResponseDto responsedata = LoginData.getInstance().getResponseData();
        dpc_profiledto = responsedata.getUserDetailDto().getDpcProfileDto();
        farmerRegistrationRequest.setDpcProfileDto(dpc_profiledto);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.image_back:
                Intent image_back = new Intent(FarmerBankDetailsActivity.this, FarmerContactActivity.class);
                image_back.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                startActivity(image_back);
                finish();
                break;

            case R.id.btn_next:
                if (bank_name == null) {
                    showToastMessage(getString(R.string.toast_Please_select_bank), 3000);
                    return;
                } else if (branch_name.getText().toString().trim().equalsIgnoreCase("")) {
                    showToastMessage(getString(R.string.toast_Please_enter_the_branch), 3000);
                    return;
                } else if (branch_name.getText().toString().trim().length() < 2) {
                    showToastMessage(getString(R.string.toast_bank), 3000);
                    return;
                } else if (account_number.getText().toString().trim().equalsIgnoreCase("")) {
                    showToastMessage(getString(R.string.toast_Please_enter_the_account_number), 3000);
                    return;
                } else if (account_number.getText().toString().trim().length() < 8) {
                    showToastMessage(getString(R.string.toast_account), 3000);
                    return;
                } else if (account_number.getText().toString().trim().equalsIgnoreCase("00000000")) {
                    showToastMessage(getString(R.string.toast_server_unreachable), 3000);
                    return;
                } else if (account_number.getText().toString().trim().equalsIgnoreCase("000000000000000000")) {
                    showToastMessage(getString(R.string.toast_valid_account_number), 3000);
                    return;
                }/*else if (DBHelper.getInstance(this).checkaccountNumberRequeat(account_number.getText().toString().trim())) {
                    showToastMessage(getString(R.string.toast_valid_account_number), 3000);
                    return;
                }*/ /*else if (DBHelper.getInstance(this).checkaccountNumberRegistration(account_number.getText().toString().trim())) {
                    showToastMessage(getString(R.string.toast_valid_account_number), 3000);
                    return;
                }*/ else if (ifsc_code.getText().toString().trim().equalsIgnoreCase("")) {
                    showToastMessage(getString(R.string.toast_Please_enter_the_IFSC_number), 3000);
                    return;
                } else if (ifsc_code.getText().toString().trim().length() < 11) {
                    showToastMessage(getString(R.string.toast_ifsc), 3000);
                    return;
                } else if (ifsc_code.getText().toString().trim().equalsIgnoreCase("00000000000")) {
                    showToastMessage(getString(R.string.toast_server_unreachable), 3000);
                    return;
                } else {
                    SetRegistrationBankDetails();
                    Intent i = new Intent(FarmerBankDetailsActivity.this, LandDetailsActivity.class);
                    i.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                    startActivity(i);
                    finish();
                }
                break;

            case R.id.num_txt_1:
                Intent number1 = new Intent(FarmerBankDetailsActivity.this, FarmerRegistrationActivity.class);
                number1.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                startActivity(number1);
                finish();
                break;

            case R.id.num_txt_2:
                Intent number2 = new Intent(FarmerBankDetailsActivity.this, FarmerContactActivity.class);
                number2.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                startActivity(number2);
                finish();
                break;

            case R.id.btn_cancel:
                Intent cancel_intent = new Intent(FarmerBankDetailsActivity.this, FarmerContactActivity.class);
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
        Intent image_back = new Intent(FarmerBankDetailsActivity.this, FarmerContactActivity.class);
        image_back.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
        startActivity(image_back);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        set_online_offline(isConnected);
    }

}
