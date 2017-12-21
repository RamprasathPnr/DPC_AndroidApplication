package com.omneagate.dpc.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.omneagate.dpc.Activity.Dialog.DateSelectionDialog;
import com.omneagate.dpc.Activity.Dialog.DialogProcurementDateSelection;
import com.omneagate.dpc.Adapter.DistrictAdapter;
import com.omneagate.dpc.Adapter.LandTypeAdapter;
import com.omneagate.dpc.Adapter.PaddyCategoryAdapter;
import com.omneagate.dpc.Adapter.TalukAdapter;
import com.omneagate.dpc.Adapter.VillageAdapter;
import com.omneagate.dpc.Model.DpcDistrictDto;
import com.omneagate.dpc.Model.DpcTalukDto;
import com.omneagate.dpc.Model.DpcVillageDto;
import com.omneagate.dpc.Model.FarmerLandDetailsDto;
import com.omneagate.dpc.Model.FarmerRegistrationRequestDto;
import com.omneagate.dpc.Model.FirstSyncDistrictDto;
import com.omneagate.dpc.Model.FirstSyncTalukDto;
import com.omneagate.dpc.Model.LandTypeDto;
import com.omneagate.dpc.Model.PaddyCategoryDto;
import com.omneagate.dpc.Model.VillageDtoId;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.LoginData;
import com.omneagate.dpc.Utility.NoDefaultSpinner;
import com.omneagate.dpc.Utility.Util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddLandDetailsActivity extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {
    private Button btn_next, btn_calcel;
    private NoDefaultSpinner spinner_taluk, spinner_village, spinner_crop_name, spinner_land_type, spinner_district;
    private DBHelper db;
    private List<FirstSyncTalukDto> taluk_list;
    private List<FirstSyncDistrictDto> district_list;
    private List<LandTypeDto> land_type_list;
    private List<PaddyCategoryDto> paddy_category_list;
    private List<VillageDtoId> Village_List;
    private TalukAdapter taluk_adapter;
    private PaddyCategoryAdapter paddy_category_adapter;
    private LandTypeAdapter land_type_adapter;
    private VillageAdapter village_adapter;
    private EditText etLandLordName, etLoanBookNUmber, etArea, etSurveyNumber, etAccumulatedKgs, etNonAccumulatedKgs, etToatalKgs,
            etAccumulatedQuintal, etNonAccumulatedQuintal, etToatalQuintal;
    private String TalukName, VillageName, CropName, LandType;
    FarmerRegistrationRequestDto farmerRegistrationRequest;
    private long taluk_id;
    private long Villageid;
    private long crop_id;
    private long LandType_id;
    private DistrictAdapter district_adapter;
    private String district_nm;
    private long district_id;
    private TextView tex_expected_date;
    private String dis_flag;
    private Calendar myCalendar;
    private List<VillageDtoId> village_name_list = null;
    private EditText patta_number;
    private ImageView date_selection;
    private DatePickerDialog.OnDateSetListener date;

    public static boolean IsClickedDate;

    InputFilter filter = new InputFilter() {
        final int maxDigitsBeforeDecimalPoint = 5;
        final int maxDigitsAfterDecimalPoint = 3;

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            StringBuilder builder = new StringBuilder(dest);
            builder.replace(dstart, dend, source
                    .subSequence(start, end).toString());
            if (!builder.toString().matches(
                    "(([1-9]{1})([0-9]{0," + (maxDigitsBeforeDecimalPoint - 1) + "})?)?(\\.[0-9]{0," + maxDigitsAfterDecimalPoint + "})?"
            )) {
                if (source.length() == 0)
                    return dest.subSequence(dstart, dend);
                return "";
            }
            return null;
        }
    };
    InputFilter filter1 = new InputFilter() {
        final int maxDigitsBeforeDecimalPoint = 6;
        final int maxDigitsAfterDecimalPoint = 6;

        @Override
        public CharSequence filter(CharSequence source, int start, int end,
                                   Spanned dest, int dstart, int dend) {
            StringBuilder builder = new StringBuilder(dest);
            builder.replace(dstart, dend, source
                    .subSequence(start, end).toString());
            if (!builder.toString().matches(
                    "(([1-9]{1})([0-9]{0," + (maxDigitsBeforeDecimalPoint - 1) + "})?)?(\\.[0-9]{0," + maxDigitsAfterDecimalPoint + "})?"
            )) {
                if (source.length() == 0)
                    return dest.subSequence(dstart, dend);
                return "";
            }
            return null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_land_details);
        checkInternet();
        setUpView();
    }

    private void setUpView() {
        dis_flag = "no";
        myCalendar = Calendar.getInstance();
        farmerRegistrationRequest = new FarmerRegistrationRequestDto();
        farmerRegistrationRequest = (FarmerRegistrationRequestDto) getIntent().getSerializableExtra("FarmerRegistrationRequest");
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        spinner_taluk = (NoDefaultSpinner) findViewById(R.id.spinner_taluk_name);
        spinner_crop_name = (NoDefaultSpinner) findViewById(R.id.spinner_crop_name);
        spinner_land_type = (NoDefaultSpinner) findViewById(R.id.spinner_land_type);
        spinner_village = (NoDefaultSpinner) findViewById(R.id.spinner_village);
        spinner_district = (NoDefaultSpinner) findViewById(R.id.spinner_district);
        etLandLordName = (EditText) findViewById(R.id.edit_text_lord_land);
        etLandLordName.requestFocus();
        etArea = (EditText) findViewById(R.id.edit_tex_area);
        etArea.setFilters(new InputFilter[]{filter1});
        etSurveyNumber = (EditText) findViewById(R.id.edit_text_survey_number);
        patta_number = (EditText) findViewById(R.id.edit_text_patta_number);
        tex_expected_date = (TextView) findViewById(R.id.tex_expected_date);
        tex_expected_date.setOnClickListener(this);

        etSurveyNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength1 = etSurveyNumber.getText().length();
                if (textlength1 >= 100) {
                    etArea.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etAccumulatedKgs = (EditText) findViewById(R.id.edit_text_accumulated_kgs);
        etAccumulatedKgs.setFilters(new InputFilter[]{filter});
        etNonAccumulatedKgs = (EditText) findViewById(R.id.edit_text_non_accumulated_kgs);
        etNonAccumulatedKgs.setFilters(new InputFilter[]{filter});
        etToatalKgs = (EditText) findViewById(R.id.edit_text_total_kgs);
        etAccumulatedQuintal = (EditText) findViewById(R.id.edit_text_accumulated_quintal);
        etAccumulatedQuintal.setFilters(new InputFilter[]{filter});
        etNonAccumulatedQuintal = (EditText) findViewById(R.id.edit_text_non_accumulated_quintal);
        etNonAccumulatedQuintal.setFilters(new InputFilter[]{filter});
        etToatalQuintal = (EditText) findViewById(R.id.edit_text_total_quintal);
        btn_calcel = (Button) findViewById(R.id.btn_cancel);
        btn_calcel.setOnClickListener(this);
        etToatalQuintal.setEnabled(false);
        etToatalQuintal.setFocusable(false);
        etToatalKgs.setEnabled(false);
        etToatalKgs.setFocusable(false);
        db = new DBHelper(this);
        db.getWritableDatabase();
        taluk_list = new ArrayList<FirstSyncTalukDto>();
        district_list = new ArrayList<FirstSyncDistrictDto>();
        district_list = db.getDistrictName();
        district_adapter = new DistrictAdapter(this, district_list);
        spinner_district.setAdapter(district_adapter);

        spinner_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (dis_flag.equalsIgnoreCase("yes")) {
                    TalukName = null;
                    VillageName = null;
                }
                dis_flag = "yes";

                if (GlobalAppState.language.equalsIgnoreCase("ta")) {
                    district_nm = district_list.get(position).getLdistrictName();
                } else {
                    district_nm = district_list.get(position).getName();
                }

                district_id = district_list.get(position).getId();
                Log.e("district_nm", district_nm);
                Log.e("district_id", "" + district_id);
                taluk_list = db.getTalukName(district_id);
                setTaluks(taluk_list);
                List<VillageDtoId> Village_List = new ArrayList<VillageDtoId>();
                village_adapter = new VillageAdapter(AddLandDetailsActivity.this, Village_List);
                spinner_village.setAdapter(village_adapter);

                /* List<VillageDtoId> Village_List =new ArrayList<VillageDtoId>();
                spinner_village.setAdapter(AddLandDetailsActivity.this,Village_List);*/

                /*if(spinner1== 0) {
                    spinner2List.clear();
                    ArrayAdapter<String> adapterEmpty = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinner2List);
                    adapterEmpty.setDropDownViewResource(R.layout.spinner_layout);
                    spinner2.setAdapter(adapterEmpty);
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        paddy_category_list = new ArrayList<PaddyCategoryDto>();
        paddy_category_list = db.getPaddyCategory();
        paddy_category_adapter = new PaddyCategoryAdapter(this, paddy_category_list);
        spinner_crop_name.setAdapter(paddy_category_adapter);
        land_type_list = new ArrayList<LandTypeDto>();
        land_type_list = db.getLand();
        land_type_adapter = new LandTypeAdapter(this, land_type_list);
        spinner_land_type.setAdapter(land_type_adapter);
        etAccumulatedKgs.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double value1, value2;
                try {
                    if (!etAccumulatedKgs.getText().toString().equalsIgnoreCase("")
                            && !etNonAccumulatedKgs.getText().toString().equalsIgnoreCase("")) {
                        value1 = Double.parseDouble(etAccumulatedKgs.getText().toString().trim());
                        value2 = Double.parseDouble(etNonAccumulatedKgs.getText().toString().trim());
                        Double total = value1 + value2;
                        etToatalKgs.setText(total.toString());
                    } else {
                        etToatalKgs.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etNonAccumulatedKgs.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double value1, value2;
                try {
                    if (!etAccumulatedKgs.getText().toString().equalsIgnoreCase("")
                            && !etNonAccumulatedKgs.getText().toString().equalsIgnoreCase("")) {
                        value1 = Double.parseDouble(etAccumulatedKgs.getText().toString().trim());
                        value2 = Double.parseDouble(etNonAccumulatedKgs.getText().toString().trim());
                        Double total = value1 + value2;
                        etToatalKgs.setText(total.toString());
                    } else {
                        etToatalKgs.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etAccumulatedQuintal.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double value1, value2;
                try {
                    if (!etAccumulatedQuintal.getText().toString().equalsIgnoreCase("")
                            && !etNonAccumulatedQuintal.getText().toString().equalsIgnoreCase("")) {
                        value1 = Double.parseDouble(etAccumulatedQuintal.getText().toString().trim());
                        value2 = Double.parseDouble(etNonAccumulatedQuintal.getText().toString().trim());
                        Double total = value1 + value2;
                        etToatalQuintal.setText(total.toString());
                    } else {
                        etToatalQuintal.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etNonAccumulatedQuintal.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Double value1, value2;
                try {
                    if (!etAccumulatedQuintal.getText().toString().equalsIgnoreCase("")
                            && !etNonAccumulatedQuintal.getText().toString().equalsIgnoreCase("")) {
                        value1 = Double.parseDouble(etAccumulatedQuintal.getText().toString().trim());
                        value2 = Double.parseDouble(etNonAccumulatedQuintal.getText().toString().trim());
                        Double total = value1 + value2;
                        etToatalQuintal.setText(total.toString());
                    } else {
                        etToatalQuintal.setText("");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        spinner_taluk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TalukName = taluk_list.get(position).getName();
                taluk_id = taluk_list.get(position).getId();

                String taluk_id = db.getTalukId(TalukName);
                if (village_name_list != null) {
                    village_name_list.clear();
                }
                if (village_adapter != null) {
                    village_adapter.notifyDataSetChanged();
                }
                village_name_list = db.getvillsges(taluk_id);
                if (village_name_list != null) {
                    getvillages(village_name_list);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (GlobalAppState.language.equalsIgnoreCase("ta")) {
                    VillageName = Village_List.get(position).getVillagelname();
                } else {
                    VillageName = Village_List.get(position).getName();
                }
                Villageid = Village_List.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

       /* spinner_village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                VillageName = Village_List.get(position).getName();
                Villageid = Village_List.get(position).getId();
                Log.e("village name", VillageName);
                Log.e("village id", "" + Villageid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
        spinner_crop_name.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!GlobalAppState.language.equalsIgnoreCase("ta")) {
                    CropName = paddy_category_list.get(position).getName();
                } else {
                    CropName = paddy_category_list.get(position).getLname();
                }
                crop_id = paddy_category_list.get(position).getId();
                Log.e("crop_id", "" + crop_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinner_land_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                LandType = land_type_list.get(position).getName();
                LandType_id = land_type_list.get(position).getId();
                Log.e("LandType_id", "" + LandType_id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setFarmerLandDetail() {

        try {
            FarmerLandDetailsDto farmerLandDetailsDto = new FarmerLandDetailsDto();
            farmerLandDetailsDto.setMainLandLordName(etLandLordName.getText().toString());
            farmerLandDetailsDto.setMainLandLordLname(etLandLordName.getText().toString());
            farmerLandDetailsDto.setArea(Double.parseDouble(etArea.getText().toString().trim()));
            farmerLandDetailsDto.setSurveyNumber(etSurveyNumber.getText().toString());
            if (etAccumulatedKgs.getText().toString().trim().equalsIgnoreCase("")) {
                farmerLandDetailsDto.setSowedAccumulated(0.0d);
            } else {
                farmerLandDetailsDto.setSowedAccumulated(Double.parseDouble(etAccumulatedKgs.getText().toString().trim()));
            }
            if (etNonAccumulatedKgs.getText().toString().trim().equalsIgnoreCase("")) {
                farmerLandDetailsDto.setSowedNonAccumulated(0.0d);
            } else {
                farmerLandDetailsDto.setSowedNonAccumulated(Double.parseDouble(etNonAccumulatedKgs.getText().toString().trim()));
            }
            if (etToatalKgs.getText().toString().trim().equalsIgnoreCase("")) {
                farmerLandDetailsDto.setSowedTotal(0.0d);
            } else {
                farmerLandDetailsDto.setSowedTotal(Double.parseDouble(etToatalKgs.getText().toString().trim()));
            }
            if (etAccumulatedQuintal.getText().toString().trim().equalsIgnoreCase("")) {
                farmerLandDetailsDto.setExpectedAccumulated(0.0d);
            } else {
                farmerLandDetailsDto.setExpectedAccumulated(Double.parseDouble(etAccumulatedQuintal.getText().toString().trim()));
            }
            if (etNonAccumulatedQuintal.getText().toString().trim().equalsIgnoreCase("")) {
                farmerLandDetailsDto.setExpectedNonAccumulated(0.0d);
            } else {
                farmerLandDetailsDto.setExpectedNonAccumulated(Double.parseDouble(etNonAccumulatedQuintal.getText().toString().trim()));
            }
            if (etToatalQuintal.getText().toString().trim().equalsIgnoreCase("")) {
                farmerLandDetailsDto.setExpectedTotal(0.0d);
            } else {
                farmerLandDetailsDto.setExpectedTotal(Double.parseDouble(etToatalQuintal.getText().toString().trim()));
            }
            farmerLandDetailsDto.setCreatedBy(LoginData.getInstance().getResponseData().getUserDetailDto().getCreatedBy());
            farmerLandDetailsDto.setModifiedBy(LoginData.getInstance().getResponseData().getUserDetailDto().getModifiedBy());
            DpcDistrictDto districtDto = new DpcDistrictDto();
            if (GlobalAppState.language.equalsIgnoreCase("ta")) {
                districtDto.setLdistrictName(district_nm);
            } else {
                districtDto.setName(district_nm);
            }
            districtDto.setId(district_id);
            farmerLandDetailsDto.setDpcDistrictDto(districtDto);
            DpcTalukDto talukDto = new DpcTalukDto();
            talukDto.setDpcDistrictDto(districtDto);

            if (GlobalAppState.language.equalsIgnoreCase("ta")) {
                talukDto.setLtalukName(TalukName);
            } else {
                talukDto.setName(TalukName);
            }
            talukDto.setId(taluk_id);
            farmerLandDetailsDto.setDpcTalukDto(talukDto);
            DpcVillageDto villageDto = new DpcVillageDto();
            if (GlobalAppState.language.equalsIgnoreCase("ta")) {
                villageDto.setLvillageName(VillageName);
            } else {
                villageDto.setName(VillageName);
            }
            villageDto.setId(Villageid);

            farmerLandDetailsDto.setDpcVillageDto(villageDto);
            LandTypeDto landTypeDto = new LandTypeDto();
            landTypeDto.setName(LandType);
            landTypeDto.setId(LandType_id);

            farmerLandDetailsDto.setLandTypeDto(landTypeDto);
            PaddyCategoryDto paddyCategoryDto = new PaddyCategoryDto();
            if (!GlobalAppState.language.equalsIgnoreCase("ta")) {
                paddyCategoryDto.setName(CropName);
            }else {
                paddyCategoryDto.setLname(CropName);
            }
            paddyCategoryDto.setId(crop_id);

            farmerLandDetailsDto.setPaddyCategoryDto(paddyCategoryDto);
            farmerLandDetailsDto.setPattaNumber(patta_number.getText().toString());

            String exp_date = tex_expected_date.getText().toString();
            DateTimeFormatter inputFormat = DateTimeFormat.forPattern("dd-MM-yyyy");
            DateTimeFormatter outputFormat = DateTimeFormat.forPattern("yyyy-MM-dd");

            if (!exp_date.equalsIgnoreCase("")) {
                DateTime date = inputFormat.parseDateTime(exp_date);
                String outputDate_ = date.toString(outputFormat);
                farmerLandDetailsDto.setExpectedProcuringDate(outputDate_);
            }

            Util.farmerLandDetailsDtoList.add(farmerLandDetailsDto);
            farmerRegistrationRequest.setFarmerLandDetailsDtoList(Util.farmerLandDetailsDtoList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tex_expected_date:
                if (IsClickedDate) {
                    return;
                } else {
                    IsClickedDate = true;
                    new DateSelectionDialog(this).show();
                }
                break;
            case R.id.btn_next:
                btn_next.setVisibility(View.GONE);
                if (district_nm == null) {
                    btn_next.setVisibility(View.VISIBLE);
                    showToastMessage(getString(R.string.toast_server_di), 3000);
                    return;
                } else if (TalukName == null) {
                    btn_next.setVisibility(View.VISIBLE);
                    showToastMessage(getString(R.string.toast_server_tk), 3000);
                    return;
                } else if (VillageName == null) {
                    btn_next.setVisibility(View.VISIBLE);
                    showToastMessage(getString(R.string.toast_Please_select_village), 3000);
                    return;
                } else if (CropName == null) {
                    btn_next.setVisibility(View.VISIBLE);
                    showToastMessage(getString(R.string.toast_Please_Select_Crop_Name), 3000);
                    return;
                } else if (LandType == null) {
                    btn_next.setVisibility(View.VISIBLE);
                    showToastMessage(getString(R.string.toast_Please_Select_Land_Type), 3000);
                    return;
                } else if (etLandLordName.getText().toString().trim().equalsIgnoreCase("")) {
                    btn_next.setVisibility(View.VISIBLE);
                    showToastMessage(getString(R.string.toast_Please_Enter_Land_Lord_Name), 3000);
                    return;
                } else if (etLandLordName.getText().toString().trim().length() < 2) {
                    btn_next.setVisibility(View.VISIBLE);
                    showToastMessage(getString(R.string.toast_lord), 3000);
                    return;
                } /*else if (etLoanBookNUmber.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(AddLandDetailsActivity.this, getString(R.string.toast_Please_Enter_Loan_Book_Numbere), Toast.LENGTH_SHORT).show();
                    return;
                }*/ /*else if (etLoanBookNUmber.getText().toString().length() < 2) {
                    Toast.makeText(AddLandDetailsActivity.this, getString(R.string.toast_loan_book_number_min), Toast.LENGTH_SHORT).show();
                    return;
                } else if (etLoanBookNUmber.getText().toString().length() < 8) {
                    Toast.makeText(AddLandDetailsActivity.this, getString(R.string.toast_loan_book), Toast.LENGTH_SHORT).show();
                    return;
                }*/ else if (etSurveyNumber.getText().toString().trim().equalsIgnoreCase("")) {
                    btn_next.setVisibility(View.VISIBLE);
                    showToastMessage(getString(R.string.toast_Please_Enter_Survey_Number), 3000);
                    return;
                } else if (etSurveyNumber.getText().toString().trim().length() < 1) {
                    btn_next.setVisibility(View.VISIBLE);
                    showToastMessage(getString(R.string.toast_survey), 3000);
                    return;
                } else if (etArea.getText().toString().trim().equalsIgnoreCase("")) {
                    btn_next.setVisibility(View.VISIBLE);
                    showToastMessage(getString(R.string.toast_Please_Enter_Area), 3000);
                    return;
                } else if (patta_number.getText().toString().trim().equalsIgnoreCase("")) {
                    btn_next.setVisibility(View.VISIBLE);
                    showToastMessage(getString(R.string.toast_Please_patta), 3000);
                    return;
                }
                setFarmerLandDetail();
                Intent i = new Intent(AddLandDetailsActivity.this, LandDetailsActivity.class);
                i.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                startActivity(i);
                finish();
                break;
            //back up
            /*else if (tex_expected_date.getText().toString().trim().equalsIgnoreCase("")) {
                    showToastMessage(getString(R.string.toast_expected_date), 3000);
//                    Toast.makeText(AddLandDetailsActivity.this, getString(R.string.toast_expected_date), Toast.LENGTH_SHORT).show();
                    return;
                }*/ /*else if (etAccumulatedKgs.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(AddLandDetailsActivity.this, getString(R.string.toast_Please_Enter_Accumulated_in_Kgs), Toast.LENGTH_SHORT).show();
                    return;
                } else if (etNonAccumulatedKgs.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(AddLandDetailsActivity.this, getString(R.string.toast_Please_Enter_Non_Accumulated_in_Kgs), Toast.LENGTH_SHORT).show();
                    return;
                } else if (etAccumulatedQuintal.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(AddLandDetailsActivity.this, getString(R.string.toast_Please_Enter_Accumulated_in_Quintal), Toast.LENGTH_SHORT).show();
                    return;
                } else if (etNonAccumulatedQuintal.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(AddLandDetailsActivity.this, getString(R.string.toast_Please_Enter_Non_Accumulated_in_Quintal), Toast.LENGTH_SHORT).show();
                    return;
                }*/
            case R.id.btn_cancel:
                Intent cancel_intent = new Intent(AddLandDetailsActivity.this, LandDetailsActivity.class);
                cancel_intent.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                startActivity(cancel_intent);
                finish();
                break;
        }
    }

    public void getvillages(List<VillageDtoId> village_nm) {
        Log.e("INSIDE VILAGES", "" + village_nm);
        Village_List = village_nm;
        village_adapter = new VillageAdapter(this, Village_List);
        spinner_village.setAdapter(village_adapter);
    }

    public void setTaluks(List<FirstSyncTalukDto> taluk_nm) {
        Log.e("INSIDE VILAGES", "" + taluk_nm);
        taluk_list = taluk_nm;
        taluk_adapter = new TalukAdapter(this, taluk_list);
        spinner_taluk.setAdapter(taluk_adapter);
    }

    @Override
    public void onBackPressed() {
        Intent cancel_intent = new Intent(AddLandDetailsActivity.this, LandDetailsActivity.class);
        cancel_intent.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
        startActivity(cancel_intent);
        finish();
    }

    public void setTextDate(String textDate) {
        tex_expected_date.setText(textDate);
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        set_online_offline(isConnected);
    }
}
