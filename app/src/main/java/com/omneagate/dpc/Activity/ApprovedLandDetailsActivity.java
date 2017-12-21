package com.omneagate.dpc.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.omneagate.dpc.Model.FarmerApprovedLandDetailsDto;
import com.omneagate.dpc.Model.FarmerRegistrationDto;
import com.omneagate.dpc.R;


import java.text.SimpleDateFormat;

public class ApprovedLandDetailsActivity extends BaseActivity implements View.OnClickListener {
    private FarmerApprovedLandDetailsDto land_details;
    private FarmerRegistrationDto farmerRegistrationRequest;
    private Button btn_cancel;
    private EditText edit_text_taluk, edit_text_village, edit_crop_name, edit_text_land_type, edit_text_master_land, edit_text_district;
    private EditText txt_loan_number, edit_text_survey_umber, edit_text_area, edit_text_Accumulated_kgs, edit_text_non_Accumulated_kgs;
    private EditText edit_text_total_kgs, edit_text_Accumulated_quintal,
            edit_text_non_Accumulated_quintal, edit_text_total_quintal, expdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_landdetails_actiivity);
        edit_text_taluk = (EditText) findViewById(R.id.edit_text_taluk);
        edit_text_village = (EditText) findViewById(R.id.edit_text_village);
        edit_crop_name = (EditText) findViewById(R.id.edit_crop_name);
        edit_text_land_type = (EditText) findViewById(R.id.edit_text_land_type);
        edit_text_master_land = (EditText) findViewById(R.id.edit_text_master_land);
        txt_loan_number = (EditText) findViewById(R.id.edit_text_patta_number);
        edit_text_survey_umber = (EditText) findViewById(R.id.edit_text_survey_umber);
        edit_text_area = (EditText) findViewById(R.id.edit_text_area);
        expdate = (EditText) findViewById(R.id.expdate);
        edit_text_Accumulated_kgs = (EditText) findViewById(R.id.edit_text_Accumulated_kgs);
        edit_text_non_Accumulated_kgs = (EditText) findViewById(R.id.edit_text_non_Accumulated_kgs);
        edit_text_total_kgs = (EditText) findViewById(R.id.edit_text_total_kgs);
        edit_text_Accumulated_quintal = (EditText) findViewById(R.id.edit_text_Accumulated_quintal);
        edit_text_non_Accumulated_quintal = (EditText) findViewById(R.id.edit_text_non_Accumulated_quintal);
        edit_text_total_quintal = (EditText) findViewById(R.id.edit_text_total_quintal);
        edit_text_district = (EditText) findViewById(R.id.edit_text_district);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

        land_details = (FarmerApprovedLandDetailsDto) getIntent().getSerializableExtra("farmer_lands");
        farmerRegistrationRequest = (FarmerRegistrationDto) getIntent().getSerializableExtra("FarmerRegistrationRequest");
        edit_text_taluk.setText(land_details.getDpcTalukDto().getName());
        edit_text_village.setText(land_details.getDpcVillageDto().getName());
        edit_crop_name.setText(land_details.getPaddyCategoryDto().getName());
        edit_text_land_type.setText(land_details.getLandTypeDto().getName());
        edit_text_district.setText(land_details.getDpcDistrictDto().getName());
        edit_text_master_land.setText(land_details.getMainLandLordName());
        String patta = land_details.getPattaNumber();
        if (!patta.equalsIgnoreCase("null")) {
            txt_loan_number.setText(land_details.getPattaNumber());
        } else {
            txt_loan_number.setText("-");
        }
        edit_text_survey_umber.setText(land_details.getSurveyNumber());
        edit_text_area.setText("" + land_details.getArea());
        if (land_details.getExpectedProcuringDate() > 0) {
            Long exp1 = land_details.getExpectedProcuringDate();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.format(exp1);
            if (exp1 > 0) {
                expdate.setText(sdf.format(exp1));
            } else {
                expdate.setText("");
            }
        }
        edit_text_Accumulated_kgs.setText("" + land_details.getSowedAccumulated());
        edit_text_non_Accumulated_kgs.setText("" + land_details.getSowedNonAccumulated());
        edit_text_total_kgs.setText("" + land_details.getSowedTotal());
        edit_text_Accumulated_quintal.setText("" + land_details.getExpectedAccumulated());
        edit_text_non_Accumulated_quintal.setText("" + land_details.getExpectedNonAccumulated());
        edit_text_total_quintal.setText("" + land_details.getExpectedTotal());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cancel:
                finish();
                break;
        }
    }
}
