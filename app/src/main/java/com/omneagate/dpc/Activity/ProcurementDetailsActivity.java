package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.omneagate.dpc.Adapter.GradeAdapter;
import com.omneagate.dpc.Adapter.PaddyCategoryAdapter;
import com.omneagate.dpc.Adapter.SpecificationAdapter;
import com.omneagate.dpc.Model.DPCProcurementDto;
import com.omneagate.dpc.Model.DpcSpecificationDto;
import com.omneagate.dpc.Model.FarmerRegistrationDto;
import com.omneagate.dpc.Model.PaddyCategoryDto;
import com.omneagate.dpc.Model.PaddyGradeDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.NoDefaultSpinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProcurementDetailsActivity extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {
    private Button btn_next, btn_cancel;
    private NoDefaultSpinner spinner_paddycategory, spinner_grade, spinner_specification;
    private PaddyCategoryAdapter paddy_category_adapter;
    private GradeAdapter paddy_grade_adapter;
    private List<PaddyCategoryDto> paddy_category_list;
    private List<PaddyGradeDto> paddy_grade_list;

    private List<DpcSpecificationDto> specification_list;

    private FarmerRegistrationDto farmer_registration_dto;
    private EditText edit_text_moisture, edit_text_lot_number, edit_text_spillage, edit_text_num_bags;
    private String paddyCategoryName;
    private String gradeName;
    private Double moisture;
    private Double spillageqty;
    private TextView step_2;
    DPCProcurementDto dpcProcurementDto;
    private long paddyCategoryId;
    private long gradeId;
    private int pre_paddy_id;
    private String paddy_name = null;
    private int pre_grade_id;
    private SpecificationAdapter specification_adapter;
    private Long farmer_id;
    private String specification_type;

    private Long specification_id;

    private int specfication_id;

    InputFilter filter1 = new InputFilter() {
        final int maxDigitsBeforeDecimalPoint = 2;
        final int maxDigitsAfterDecimalPoint = 1;

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
    InputFilter filter2 = new InputFilter() {
        final int maxDigitsBeforeDecimalPoint = 1;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurement_details);
        checkInternetConnection();
        setUpView();
        settext();

        /* Double d = new Double(dpcTruckMemoDto.getGunnyCapacity());
                int i = d.intValue();
                edt_gunny_capacity.setText("" + i); */
    }

    private void settext() {
        if (GlobalAppState.language.equalsIgnoreCase("ta")) {
            ((TextView) findViewById(R.id.farmer_name)).setText(farmer_registration_dto.getFarmerLname());
        } else {
            ((TextView) findViewById(R.id.farmer_name)).setText(farmer_registration_dto.getFarmerName());
        }
        ((TextView) findViewById(R.id.farmer_code)).setText(farmer_registration_dto.getFarmerCode());
        ((TextView) findViewById(R.id.farmer_mobile_num)).setText(farmer_registration_dto.getMobileNumber());
    }

    private void setUpView() {
        farmer_registration_dto = (FarmerRegistrationDto) getIntent().getSerializableExtra("farmerRegistrationDto");
        farmer_id = farmer_registration_dto.getId();
        Log.e("farmer_id", "" + farmer_id);
        dpcProcurementDto = (DPCProcurementDto) getIntent().getSerializableExtra("dpcProcurementDto");
        if (farmer_registration_dto != null) {
            settext();
        }
        paddy_category_list = new ArrayList<PaddyCategoryDto>();
        paddy_grade_list = new ArrayList<PaddyGradeDto>();
        specification_list = new ArrayList<DpcSpecificationDto>();

        spinner_paddycategory = (NoDefaultSpinner) findViewById(R.id.spinner_proc_paddy_category);
        paddy_category_list = DBHelper.getInstance(this).getPaddyCategory();
        spinner_grade = (NoDefaultSpinner) findViewById(R.id.spinner_grade);

        paddy_grade_list = DBHelper.getInstance(this).getpaddygrade();

        specification_list = DBHelper.getInstance(this).getSpecification();

        edit_text_moisture = (EditText) findViewById(R.id.edit_text_moisture);
        edit_text_moisture.setFilters(new InputFilter[]{filter1});
        edit_text_lot_number = (EditText) findViewById(R.id.edit_text_lot_number);
        edit_text_spillage = (EditText) findViewById(R.id.edit_text_spillage);
        edit_text_spillage.setFilters(new InputFilter[]{filter2});
        edit_text_num_bags = (EditText) findViewById(R.id.edit_text_num_bags);

        spinner_specification = (NoDefaultSpinner) findViewById(R.id.spinner_specification);
        spinner_paddycategory();
        spinner_grade();
        spinner_specification_();


        if (dpcProcurementDto != null) {
            setPreviousValues();
        } else {
            dpcProcurementDto = new DPCProcurementDto();
        }
        step_2 = (TextView) findViewById(R.id.num_txt_2);
        setUpPopUpPage();
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_Farmer_Procurement_details));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_next.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        step_2.setOnClickListener(this);
//        tempsettext();
    }

    private void tempsettext() {
        edit_text_num_bags.setText("5");
        edit_text_spillage.setText("2");
        edit_text_lot_number.setText("5");
        edit_text_moisture.setText("11");
        spinner_grade.setSelection(0);
        for (int i = 0; i < paddy_category_list.size(); i++) {
            if (paddy_category_list.get(i).getName().contains("I.R.50")) {
                spinner_paddycategory.setSelection(i);
                break;
            }
        }
    }

    private void spinner_paddycategory() {
        paddy_category_adapter = new PaddyCategoryAdapter(this, paddy_category_list);
        spinner_paddycategory.setAdapter(paddy_category_adapter);
        spinner_paddycategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (GlobalAppState.language.equals("ta")) {
                    paddyCategoryName = paddy_category_list.get(position).getLname();
                } else {
                    paddyCategoryName = paddy_category_list.get(position).getName();
                }
                paddyCategoryId = paddy_category_list.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void spinner_grade() {
        paddy_grade_adapter = new GradeAdapter(this, paddy_grade_list);
        spinner_grade.setAdapter(paddy_grade_adapter);
        spinner_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (GlobalAppState.language.equals("ta")) {
                    gradeName = paddy_grade_list.get(position).getLname();
                } else {
                    gradeName = paddy_grade_list.get(position).getName();
                }
                gradeId = paddy_grade_list.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void spinner_specification_() {
        specification_adapter = new SpecificationAdapter(this, specification_list);
        spinner_specification.setAdapter(specification_adapter);
        spinner_specification.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                specification_type = specification_list.get(position).getSpecificationType();
                specification_id = specification_list.get(position).getId();
                Log.e("specification_type   ", specification_type);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                onBackPressed();
                break;
            case R.id.btn_next:
                checkValues();
                break;
            case R.id.btn_cancel:
                onBackPressed();
                break;
            case R.id.num_txt_2:
                checkValues();
                break;
            default:
                return;
        }
    }

    private void checkValues() {
        double from_moisture = 0;
        double to_moisture = 0;
        long from_date;
        long to_date;
        Date dff = null;
        Date dt = null;
        Date cr = null;


        DpcSpecificationDto specification_dto = DBHelper.getInstance(this).getMoistureContent(specification_type);
        if (specification_dto != null) {
            from_moisture = specification_dto.getMoisturePercentageFrom();
            to_moisture = specification_dto.getMoisturePercentageTo();
            from_date = specification_dto.getStartDate();
            to_date = specification_dto.getEndDate();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String from = df.format(from_date);
            String to = df.format(to_date);
            Log.e("from_moisture ", "" + from_moisture + "to_moisture    :" + to_moisture);
            SimpleDateFormat regDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            String current = regDate.format(new Date());
            Log.e("from_date ", "" + from + "to_date    :" + to + "current    :" + current);
            try {
                dff = regDate.parse(from);
                dt = regDate.parse(to);
                cr = regDate.parse(current);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (!edit_text_moisture.getText().toString().trim().isEmpty()) {
            moisture = Double.parseDouble(edit_text_moisture.getText().toString());
        }
        if (!edit_text_spillage.getText().toString().trim().isEmpty()) {
            spillageqty = Double.parseDouble(edit_text_spillage.getText().toString().trim());
        }
        if (paddyCategoryName == null) {
            showToastMessage(getString(R.string.toast_paddy_category), 3000);
            return;
        } else if (edit_text_moisture.getText().toString().trim() == null || edit_text_moisture.getText().toString().isEmpty()) {
            showToastMessage(getString(R.string.enter_moisture), 3000);
            return;
        } else if (gradeName == null) {
            showToastMessage(getString(R.string.toast_select_grade), 3000);
            return;
        } else if (edit_text_lot_number.getText().toString().trim() == null
                || edit_text_lot_number.getText().toString().trim().isEmpty()) {
            showToastMessage(getString(R.string.toast_enter_lot_number), 3000);
            return;
        } else if (!edit_text_spillage.getText().toString().trim().equalsIgnoreCase("")
                && (spillageqty < 0.500 || spillageqty > 2.500)) {
//            Toast.makeText(ProcurementDetailsActivity.this, getString(R.string.spillage_less), Toast.LENGTH_SHORT).show();
            showToastMessage(getString(R.string.spillage_less), 3000);
            return;
        } else if (edit_text_num_bags.getText().toString().trim() == null || edit_text_num_bags.getText().toString().isEmpty()) {
            showToastMessage(getString(R.string.toast_enter_number_bags), 3000);
            return;
        } else if (Integer.parseInt(edit_text_num_bags.getText().toString().trim()) < 1) {
            showToastMessage(getString(R.string.toast_enter_number), 3000);
            return;
        } else if (specification_type == null) {
            showToastMessage(getString(R.string.toast_select_specfication), 3000);
            return;
        } else if ((moisture < from_moisture || moisture > to_moisture)) {
            showToastMessage(getString(R.string.please_select_valid_specification), 3000);
            return;
        } else if (cr.before(dff) || cr.after(dt)) {
            showToastMessage(getString(R.string.please_select_valid_specification), 3000);
            return;
        } else {
            setValues();
            Intent i = new Intent(ProcurementDetailsActivity.this, ProcurementCalculationActivity.class);
            i.putExtra("farmerRegistrationDto", farmer_registration_dto);
            i.putExtra("dpcProcurementDto", dpcProcurementDto);
            startActivity(i);
            finish();
        }
    }

    private void setValues() {
        PaddyCategoryDto pdy_cat = new PaddyCategoryDto();
        pdy_cat.setId(paddyCategoryId);
        if (GlobalAppState.language.equals("ta")) {
            pdy_cat.setLname(paddyCategoryName);
        } else {
            pdy_cat.setName(paddyCategoryName);
        }
        dpcProcurementDto.setPaddyCategoryName(paddyCategoryName);
        dpcProcurementDto.setPaddyCategoryDto(pdy_cat);
        PaddyGradeDto gd_dto = new PaddyGradeDto();
        gd_dto.setId(gradeId);
        if (GlobalAppState.language.equals("ta")) {
            gd_dto.setLname(gradeName);
        } else {
            gd_dto.setName(gradeName);
        }
        dpcProcurementDto.setGradeName(gradeName);
        if (ProcurementActivity.aadhaar.equalsIgnoreCase("t")) {
            dpcProcurementDto.setMode("H");
        } else if (ProcurementActivity.aadhaar.equalsIgnoreCase("f")) {
            dpcProcurementDto.setMode("G");
        }
        if (ProcurementActivity.farmer_code_.equalsIgnoreCase("t")) {
            dpcProcurementDto.setMode("A");
        } else if (ProcurementActivity.farmer_code_.equalsIgnoreCase("f")) {
            dpcProcurementDto.setMode("E");
        }
        FarmerRegistrationDto frm_dto = new FarmerRegistrationDto();
        frm_dto.setId(farmer_id);
        dpcProcurementDto.setFarmerRegistrationDto(frm_dto);
        dpcProcurementDto.setPaddyGradeDto(gd_dto);
        dpcProcurementDto.setMoistureContent(moisture);
        dpcProcurementDto.setLotNumber(Integer.parseInt(edit_text_lot_number.getText().toString()));
        if (!edit_text_spillage.getText().toString().isEmpty()) {
            dpcProcurementDto.setSpillageQuantity(Double.parseDouble(edit_text_spillage.getText().toString()));
        } else {
            dpcProcurementDto.setSpillageQuantity(0.0d);
        }
        dpcProcurementDto.setNumberOfBags(Integer.parseInt(edit_text_num_bags.getText().toString()));
        dpcProcurementDto.setNetWeight(Double.parseDouble(edit_text_num_bags.getText().toString()) * 40 / 100);

        DpcSpecificationDto spec = new DpcSpecificationDto();
        spec.setId(specification_id);
        spec.setSpecificationType(specification_type);
        dpcProcurementDto.setDpcSpecificationDto(spec);


        /* dpcProcurementDto.setNumberOfBags(Integer.parseInt(edit_text_num_bags.getText().toString()));
        double net = Double.parseDouble(edit_text_num_bags.getText().toString()) * 40 / 100;
        dpcProcurementDto.setNetWeight(Double.parseDouble(String.format("%.3f", net)));*/
    }

    @Override
    public void onBackPressed() {
        Intent ii_ = new Intent(ProcurementDetailsActivity.this, ProcurementActivity.class);
        ii_.putExtra("farmerRegistrationDto", farmer_registration_dto);
        startActivity(ii_);
        finish();
    }

    private void setPreviousValues() {
        if (dpcProcurementDto.getPaddyGradeDto() != null) {
            if (dpcProcurementDto.getPaddyGradeDto().getName() != null || dpcProcurementDto.getPaddyGradeDto().getLname() != null) {
                GetPaddyPosition();
                spinner_paddycategory.setSelection(pre_paddy_id);
                if (GlobalAppState.language.equalsIgnoreCase("ta")) {
                    paddyCategoryName = paddy_category_list.get(pre_paddy_id).getLname();
                } else {
                    paddyCategoryName = paddy_category_list.get(pre_paddy_id).getName();
                }
                paddyCategoryId = paddy_category_list.get(pre_paddy_id).getId();
            }
        }
        if (dpcProcurementDto.getPaddyGradeDto() != null) {
            if (dpcProcurementDto.getPaddyGradeDto().getName() != null || dpcProcurementDto.getPaddyGradeDto().getLname() != null) {
                GetGradePosition();
                spinner_grade.setSelection(pre_grade_id);
                if (GlobalAppState.language.equalsIgnoreCase("ta")) {
                    gradeName = paddy_grade_list.get(pre_grade_id).getLname();
                } else {
                    gradeName = paddy_grade_list.get(pre_grade_id).getName();
                }
                gradeId = paddy_grade_list.get(pre_grade_id).getId();
            }
        }


        if (dpcProcurementDto.getDpcSpecificationDto() != null) {
            if (dpcProcurementDto.getDpcSpecificationDto().getSpecificationType() != null) {
                getSpecificationPosition();
                spinner_specification.setSelection(specfication_id);
                specification_type = specification_list.get(specfication_id).getSpecificationType();
                specification_id = specification_list.get(specfication_id).getId();
            }
        }

        edit_text_moisture.setText("" + dpcProcurementDto.getMoistureContent());

//        Double d = new Double(dpcProcurementDto.getMoistureContent());
//        int i = d.intValue();
//        edit_text_moisture.setText("" + i);

        edit_text_lot_number.setText("" + dpcProcurementDto.getLotNumber());
        edit_text_spillage.setText("" + dpcProcurementDto.getSpillageQuantity());
        edit_text_num_bags.setText("" + dpcProcurementDto.getNumberOfBags());
    }

    private void GetPaddyPosition() {
        String paddy_category;
        for (int i = 0; i < paddy_category_list.size(); i++) {
            if (GlobalAppState.language.equalsIgnoreCase("ta")) {
                paddy_category = paddy_category_list.get(i).getLname();
            } else {
                paddy_category = paddy_category_list.get(i).getName();
            }
            if (GlobalAppState.language.equalsIgnoreCase("ta")) {
                if (dpcProcurementDto.getPaddyCategoryDto().getLname().equals(paddy_category)) {
                    pre_paddy_id = i;
                }
            } else {
                if (dpcProcurementDto.getPaddyCategoryDto().getName().equals(paddy_category)) {
                    pre_paddy_id = i;
                }
            }
        }
    }

    private void GetGradePosition() {
        String paddy_category;
        for (int i = 0; i < paddy_grade_list.size(); i++) {
            if (GlobalAppState.language.equalsIgnoreCase("ta")) {
                paddy_category = paddy_grade_list.get(i).getLname();
            } else {
                paddy_category = paddy_grade_list.get(i).getName();
            }
            if (GlobalAppState.language.equalsIgnoreCase("ta")) {
                if (dpcProcurementDto.getPaddyGradeDto().getLname().equals(paddy_category)) {
                    pre_grade_id = i;
                }
            } else {
                if (dpcProcurementDto.getPaddyGradeDto().getName().equals(paddy_category)) {
                    pre_grade_id = i;
                }
            }
        }
    }


    private void getSpecificationPosition() {
        String specification;
        for (int i = 0; i < specification_list.size(); i++) {
            specification = specification_list.get(i).getSpecificationType();
            if (dpcProcurementDto.getDpcSpecificationDto().getSpecificationType().equals(specification)) {
                specfication_id = i;
            }
        }
    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setTextSize(10);
    }
}
