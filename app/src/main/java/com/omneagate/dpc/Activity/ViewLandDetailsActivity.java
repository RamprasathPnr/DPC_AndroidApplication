package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.omneagate.dpc.Model.FarmerLandDetailsDto;
import com.omneagate.dpc.Model.FarmerRegistrationRequestDto;
import com.omneagate.dpc.R;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class ViewLandDetailsActivity extends BaseActivity implements View.OnClickListener {
    private FarmerLandDetailsDto land_details;
    private FarmerRegistrationRequestDto farmerRegistrationRequest;
    private Button btn_cancel;
    private EditText edit_text_taluk, edit_text_village, edit_crop_name, edit_text_land_type, edit_text_master_land, edit_text_district;
    private EditText txt_loan_number, edit_text_survey_umber, edit_text_area, edit_text_Accumulated_kgs, edit_text_non_Accumulated_kgs;
    private EditText edit_text_total_kgs, edit_text_Accumulated_quintal, edit_text_non_Accumulated_quintal,
            edit_text_total_quintal, expdate;
    private String where;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_land_details);
        edit_text_taluk = (EditText) findViewById(R.id.edit_text_taluk);
        edit_text_village = (EditText) findViewById(R.id.edit_text_village);
        edit_crop_name = (EditText) findViewById(R.id.edit_crop_name);
        expdate = (EditText) findViewById(R.id.expdate);
        edit_text_land_type = (EditText) findViewById(R.id.edit_text_land_type);
        edit_text_master_land = (EditText) findViewById(R.id.edit_text_master_land);
        txt_loan_number = (EditText) findViewById(R.id.edit_text_patta_number);
        edit_text_survey_umber = (EditText) findViewById(R.id.edit_text_survey_umber);
        edit_text_area = (EditText) findViewById(R.id.edit_text_area);
        edit_text_Accumulated_kgs = (EditText) findViewById(R.id.edit_text_Accumulated_kgs);
        edit_text_non_Accumulated_kgs = (EditText) findViewById(R.id.edit_text_non_Accumulated_kgs);
        edit_text_total_kgs = (EditText) findViewById(R.id.edit_text_total_kgs);
        edit_text_Accumulated_quintal = (EditText) findViewById(R.id.edit_text_Accumulated_quintal);
        edit_text_non_Accumulated_quintal = (EditText) findViewById(R.id.edit_text_non_Accumulated_quintal);
        edit_text_total_quintal = (EditText) findViewById(R.id.edit_text_total_quintal);
        edit_text_district = (EditText) findViewById(R.id.edit_text_district);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);
        land_details = new FarmerLandDetailsDto();
        where = getIntent().getStringExtra("classname");
        land_details = (FarmerLandDetailsDto) getIntent().getSerializableExtra("farmer_lands");
        farmerRegistrationRequest = (FarmerRegistrationRequestDto) getIntent().getSerializableExtra("FarmerRegistrationRequest");
        edit_text_land_type.setText(land_details.getLandTypeDto().getName());

        if (GlobalAppState.language.equalsIgnoreCase("ta")) {
            edit_text_district.setText(land_details.getDpcDistrictDto().getLdistrictName());
            edit_text_taluk.setText(land_details.getDpcTalukDto().getLtalukName());
            edit_text_village.setText(land_details.getDpcVillageDto().getLvillageName());
            edit_crop_name.setText(land_details.getPaddyCategoryDto().getLname());

        } else {
            edit_text_district.setText(land_details.getDpcDistrictDto().getName());
            edit_text_taluk.setText(land_details.getDpcTalukDto().getName());
            edit_text_village.setText(land_details.getDpcVillageDto().getName());
            edit_crop_name.setText(land_details.getPaddyCategoryDto().getName());
        }


//        edit_crop_name.setText(land_details.getPaddyCategoryDto().getName());
        edit_text_master_land.setText(land_details.getMainLandLordName());
        String patta_number = land_details.getPattaNumber();
        txt_loan_number.setText(land_details.getPattaNumber());

        String exp = land_details.getExpectedProcuringDate();
        Log.e("exp DATEEEE","exp");

        try {
            DateTimeFormatter inputFormat = DateTimeFormat.forPattern("yyyy-MM-dd");
            DateTimeFormatter outputFormat = DateTimeFormat.forPattern("dd-MM-yyyy");
            if (exp != null) {
                DateTime date = inputFormat.parseDateTime(exp);
                String outputDate_ = date.toString(outputFormat);
                expdate.setText(outputDate_);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        edit_text_survey_umber.setText(land_details.getSurveyNumber());
        edit_text_area.setText("" + land_details.getArea());
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
                if (!where.contains("ViewFarmerDetailsActivity")) {
                    Intent cancel = new Intent(ViewLandDetailsActivity.this, LandDetailsActivity.class);
                    cancel.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                    startActivity(cancel);
                }
                finish();
                break;
        }
    }
}
