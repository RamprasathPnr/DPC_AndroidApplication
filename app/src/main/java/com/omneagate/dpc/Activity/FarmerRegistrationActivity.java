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

import com.omneagate.dpc.Activity.Dialog.FarmerAlertDialog;
import com.omneagate.dpc.Adapter.GuardianAdapter;
import com.omneagate.dpc.Model.FarmerClassDto;
import com.omneagate.dpc.Model.FarmerRegistrationRequestDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.AadhaarVerhoeffAlgorithm;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.NoDefaultSpinner;
import com.omneagate.dpc.Utility.Util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 5/8/16.
 */
public class FarmerRegistrationActivity extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private Button btn_next, btn_calcel;
    private EditText farmer_name, guardian_name, ration_1, ration_2, ration_3;
    private NoDefaultSpinner spinner_community, spinner_guardian;
    private List<FarmerClassDto> farmer_class_list;
    private DBHelper db;
    private String farmer_class, guardian_typ;
    private long id_famer_class;
    private GuardianAdapter g_adapter;
    private List<String> guardian_type;
    private int PosGuaridianId, PosFarmerClassId;
    private EditText aadhaar1, aadhaar2, aadhaar3;
    private FarmerRegistrationRequestDto farmerRegistrationRequest;
    private String a_number;
    private String a_number2;
    private String a_number3;
    private String a_number1;
    private String cardNumber;
    private String cardNumber1;
    private String cardNumber2;
    private String cardNumber3;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_registration_basic);
        checkInternetConnection();
        setUpView();
    }

    private void setUpView() {
        setUpPopUpPage();
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_Farmer_Registration));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        view = this.getCurrentFocus();
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        btn_calcel = (Button) findViewById(R.id.btn_cancel);
        btn_calcel.setOnClickListener(this);
        TextView step1 = (TextView) findViewById(R.id.num_txt_1);
        step1.setBackground(getResources().getDrawable(R.drawable.shape_circle_steps_moved));
        farmerRegistrationRequest = new FarmerRegistrationRequestDto();
        farmerRegistrationRequest = (FarmerRegistrationRequestDto) getIntent().getSerializableExtra("FarmerRegistrationRequest");
        aadhaar1 = (EditText) findViewById(R.id.aadhaar_1);
        aadhaar2 = (EditText) findViewById(R.id.aadhaar_2);
        aadhaar3 = (EditText) findViewById(R.id.aadhaar_three);
        ration_1 = (EditText) findViewById(R.id.ration_1);
        ration_2 = (EditText) findViewById(R.id.ration_2);
        ration_3 = (EditText) findViewById(R.id.ration_3);

        ration_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (ration_1.getText().toString() != null) {

                        int ration_1_length = ration_1.getText().toString().length();
                        int number = Integer.parseInt(ration_1.getText().toString());
                        if (ration_1_length == 2) {
                            if (number < 01 || number > 33) {
                                showToastMessage(getString(R.string.toast_value_less), 3000);
                                return;
                            }

                            ration_2.requestFocus();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ration_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    Integer textlength1 = ration_2.getText().length();
                    if (textlength1 == 1) {
                        ration_3.requestFocus();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        db = new DBHelper(this);
        db.getWritableDatabase();
        farmer_name = (EditText) findViewById(R.id.ed_farmer_name);
        guardian_name = (EditText) findViewById(R.id.edit_text_gurdian);
        spinner_guardian = (NoDefaultSpinner) findViewById(R.id.spinner_guardian);
        guardian_type = new ArrayList<String>();
        guardian_type.add("FATHER");
        guardian_type.add("HUSBAND");
        g_adapter = new GuardianAdapter(this, guardian_type);
        spinner_guardian.setAdapter(g_adapter);
        spinner_guardian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                guardian_typ = guardian_type.get(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        aadhaar1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = aadhaar1.getText().length();
                if (textlength1 >= 4) {
                    aadhaar2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        aadhaar2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength2 = aadhaar2.getText().length();
                Log.e("Integer length", "" + textlength2);
                if (textlength2 >= 4) {
                    aadhaar3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        aadhaar3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength2 = aadhaar3.getText().length();
                if (textlength2 == 4) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(((EditText) findViewById(R.id.aadhaar_three)).getWindowToken(), 0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        checkifValuesExist();
    }

    private void checkifValuesExist() {
        try {
            if (farmerRegistrationRequest.getFarmerName() != null) {
                farmer_name.setText(farmerRegistrationRequest.getFarmerName());
            }
            if (farmerRegistrationRequest.getGuardian() != null) {
                GetFarmerPosition();
                spinner_guardian.setSelection(PosGuaridianId);
                guardian_typ = guardian_type.get(PosGuaridianId).toString();

            }
            if (farmerRegistrationRequest.getGuardianName() != null) {
                guardian_name.setText(farmerRegistrationRequest.getGuardianName());
            }
            if (farmerRegistrationRequest.getFarmerClassDto() != null) {
                if (farmerRegistrationRequest.getFarmerClassDto().getName() != null) {
                    GetFarmerClassPosition();
                }
            }
            if (!farmerRegistrationRequest.getAadhaarNumber().isEmpty() && farmerRegistrationRequest.getAadhaarNumber() != null) {
                String AadharNUmber1 = farmerRegistrationRequest.getAadhaarNumber().substring(0, 4);
                String AadharNUmber2 = farmerRegistrationRequest.getAadhaarNumber().substring(4, 8);
                String AadharNUmber3 = farmerRegistrationRequest.getAadhaarNumber().substring(8, 12);
                aadhaar1.setText(AadharNUmber1);
                aadhaar2.setText(AadharNUmber2);
                aadhaar3.setText(AadharNUmber3);
            }
            if (!farmerRegistrationRequest.getRationCardNumber().isEmpty() && farmerRegistrationRequest.getRationCardNumber() != null) {
                String rationNUmber1 = farmerRegistrationRequest.getRationCardNumber().substring(0, 2);
                String rationNUmber2 = farmerRegistrationRequest.getRationCardNumber().substring(2, 3);
                String rationNUmber3 = farmerRegistrationRequest.getRationCardNumber().substring(3, 10);
                ration_1.setText(rationNUmber1);
                ration_2.setText(rationNUmber2);
                ration_3.setText(rationNUmber3);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void GetFarmerPosition() {
        for (int i = 0; i < guardian_type.size(); i++) {
            String guardian = guardian_type.get(i);
            if (farmerRegistrationRequest.getGuardian().equals(guardian)) {
                PosGuaridianId = i;
            }
        }
    }

    private void GetFarmerClassPosition() {
        for (int i = 0; i < farmer_class_list.size(); i++) {
            String guardian = farmer_class_list.get(i).getName();
            if (farmerRegistrationRequest.getFarmerClassDto().getName().equals(guardian)) {
                PosFarmerClassId = i;
            }
        }
    }

    private void setFarmerRegistrationDetails() {
        farmerRegistrationRequest.setFarmerName(farmer_name.getText().toString());
        farmerRegistrationRequest.setFarmerLname(farmer_name.getText().toString());
        farmerRegistrationRequest.setChannel("DPC_POS_APP");
        farmerRegistrationRequest.setGuardian(guardian_typ);
        farmerRegistrationRequest.setGuardianName(guardian_name.getText().toString());
        farmerRegistrationRequest.setGuardianLname(guardian_name.getText().toString());
        a_number1 = aadhaar1.getText().toString();
        a_number2 = aadhaar2.getText().toString();
        a_number3 = aadhaar3.getText().toString();
        a_number = a_number1 + a_number2 + a_number3;
        farmerRegistrationRequest.setAadhaarNumber(a_number);
        if (cardNumber.length() < 10) {
            farmerRegistrationRequest.setRationCardNumber(null);
        } else {
            farmerRegistrationRequest.setRationCardNumber(cardNumber);
        }
        FarmerClassDto farmerClassDto = new FarmerClassDto();
        farmerClassDto.setName(farmer_class);
        farmerClassDto.setId(id_famer_class);
        farmerRegistrationRequest.setFarmerClassDto(farmerClassDto);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.image_back:

                try {
                    onBackPressed();
                /*if (Util.farmerLandDetailsDtoList != null) {
                    Util.farmerLandDetailsDtoList.clear();
                }
                Intent ii_ = new Intent(FarmerRegistrationActivity.this, FarmerListActivity.class);
                startActivity(ii_);
                finish();*/
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case R.id.btn_next:
                checkValues();
                break;
            case R.id.btn_cancel:
                try {
                    if (Util.farmerLandDetailsDtoList != null) {
                        Util.farmerLandDetailsDtoList.clear();
                    }
                    onBackPressed();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*Intent i_ = new Intent(FarmerRegistrationActivity.this, FarmerListActivity.class);
                startActivity(i_);
                finish();*/
                break;
            default:
                break;
        }

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        set_online_offline(isConnected);
    }

    @Override
    public void onBackPressed() {

        try {
            new FarmerAlertDialog(FarmerRegistrationActivity.this).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkValues() {
        try {
            a_number1 = aadhaar1.getText().toString();
            a_number2 = aadhaar2.getText().toString();
            a_number3 = aadhaar3.getText().toString();
            a_number = a_number1 + a_number2 + a_number3;
            AadhaarVerhoeffAlgorithm verhoeffAlgorithm = new AadhaarVerhoeffAlgorithm();
            Boolean isAadharNumber = verhoeffAlgorithm.validateVerhoeff(a_number);

            cardNumber1 = ration_1.getText().toString();
            cardNumber2 = ration_2.getText().toString();
            cardNumber3 = ration_3.getText().toString();
            cardNumber = cardNumber1 + cardNumber2 + cardNumber3;

            if (farmer_name.getText().toString().trim().equalsIgnoreCase("")) {
                showToastMessage(getString(R.string.toast_Please_enter_the_farmer_name), 3000);
                return;
            } else if (farmer_name.getText().toString().trim().length() < 2) {
                showToastMessage(getString(R.string.toast_fr_name), 3000);
                return;
            } else if (guardian_typ == null) {
                showToastMessage(getString(R.string.grr), 3000);
                return;
            } else if (guardian_name.getText().toString().trim().equalsIgnoreCase("")) {
                showToastMessage(getString(R.string.gr), 3000);
                return;
            } else if (guardian_name.getText().toString().trim().length() < 2) {
                showToastMessage(getString(R.string.toast_hus_nm), 3000);
                return;
            } /*else if (farmer_class == null) {
                Toast.makeText(FarmerRegistrationActivity.this, getString(R.string.toast_Please_Choose_community), Toast.LENGTH_SHORT).show();
                return;
            }*/ else if (a_number != null && (!a_number.equalsIgnoreCase("")) && a_number.length() != 12) {
                showToastMessage(getString(R.string.toast_aadhaar), 3000);
                return;
            } else if (a_number != null && (!a_number.equalsIgnoreCase("")) && a_number.length() == 12 && DBHelper.getInstance(this).CheckAadharNumberAlreadyExist(a_number)) {
                showToastMessage(getString(R.string.toast_aadhaar_already), 3000);
                return;
            } else if (a_number != null && (!a_number.equalsIgnoreCase("")) && a_number.length() == 12 && DBHelper.getInstance(this).CheckAadharNumberAlreadyExistinRegistration(a_number)) {
                showToastMessage(getString(R.string.toast_aadhaar_already), 3000);
                return;
            } else if (!isAadharNumber) {
                showToastMessage(getString(R.string.toast_invalid_aadhaar_num), 3000);
                return;
            } else if (cardNumber != null && (!cardNumber.equalsIgnoreCase("")) && cardNumber.length() != 10) {
                showToastMessage(getString(R.string.toast_invalid_ration_digit), 3000);
                return;
            } else if (cardNumber != null && (!cardNumber.equalsIgnoreCase("")) && cardNumber.length() == 10 && (StringUtils.isEmpty(cardNumber1) || Integer.parseInt(cardNumber1) > 33)) {
                showToastMessage(getString(R.string.toast_invalid_ration), 3000);
                return;
            }
            /*else if (cardNumber != null && (!cardNumber.equalsIgnoreCase("")) && cardNumber.length() == 10) {
                Toast.makeText(FarmerRegistrationActivity.this, "INVALID CArd", Toast.LENGTH_SHORT).show();
                return;
            }*/
            else {
                setFarmerRegistrationDetails();
                FarmerContactActivity.activityScreen = 1;
                Intent i = new Intent(FarmerRegistrationActivity.this, FarmerContactActivity.class);
                i.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                startActivity(i);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }
}
