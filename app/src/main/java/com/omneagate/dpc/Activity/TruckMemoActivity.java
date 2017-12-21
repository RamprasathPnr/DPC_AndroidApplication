package com.omneagate.dpc.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.omneagate.dpc.Adapter.CapAdapter;
import com.omneagate.dpc.Adapter.ConditionofGunnyAdapter;
import com.omneagate.dpc.Adapter.GradeAdapter;
import com.omneagate.dpc.Adapter.PaddyCategoryAdapter;
import com.omneagate.dpc.Adapter.SpecificationAdapter;
import com.omneagate.dpc.Adapter.TransportTypeAdapter;
import com.omneagate.dpc.Model.DPCProcurementDto;
import com.omneagate.dpc.Model.DpcCapDto;
import com.omneagate.dpc.Model.DeviceDto;
import com.omneagate.dpc.Model.DpcSpecificationDto;
import com.omneagate.dpc.Model.DpcTruckMemoDto;
import com.omneagate.dpc.Model.FarmerRegistrationRequestDto;
import com.omneagate.dpc.Model.PaddyCategoryDto;
import com.omneagate.dpc.Model.PaddyGradeDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.EditboxFilter;
import com.omneagate.dpc.Utility.NoDefaultSpinner;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TruckMemoActivity extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private NoDefaultSpinner spinner_paddycategory, spinner_grade, spinner_TransportType,
            spinner_Conditionofgunny, spinner_CapCodeName, spinner_taluk, spinner_specification;
    private EditText edt_moisture_content, edt_number_bags, edt_net_quantity,
            edt_gunny_capacity, edt_lorry_number, edt_Transporter_Name;
    private Button btn_next, btn_cancel;
    private String s_paddyCategoryName, s_GradeName,
            s_TransportType, s_ConditionOfGunny, s_CapCode, s_CapName;
    private long l_PaddtCategoryId, l_GradeNameId, l_CapId;
    private DpcTruckMemoDto dpcTruckMemoDto;
    private int paddyCategoryPosId, PaddyGradePosId,
            TransportTypeId, CapCodeId, CondiionOfGunnyId;
    private PaddyCategoryAdapter Paddy_Category_Adapter;
    private GradeAdapter Paddy_Grade_Adapter;
    private TransportTypeAdapter Transport_Adapter;
    private ConditionofGunnyAdapter Condition_of_Gunny_Adapter;
    private CapAdapter cap_adapter;
    private List<PaddyCategoryDto> Paddy_Category_List;
    private List<PaddyGradeDto> Paddy_Grade_List;
    private List<String> Transport_Type_List;
    private List<String> Condition_Of_Gunny_List;
    private List<DpcCapDto> Cap_List;
    private SpecificationAdapter specification_adapter;
    private List<DpcSpecificationDto> specification_list;
    private String specification_type;
    private Long specification_id;
    private DPCProcurementDto dpcProcurementDto;
    private int specfication_id;
    private Double from_moisture;
    private Double to_moisture;
    private long from_date;
    private long to_date;
    private Date dff;
    private Date dt;
    private Date cr;
    private Double moisture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_memo);
        checkInternetConnection();
        setUpView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }

    private void setUpView() {

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.TruckMemo));

        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        setUpPopUpPage();

        Paddy_Category_List = new ArrayList<PaddyCategoryDto>();
        Paddy_Grade_List = new ArrayList<PaddyGradeDto>();
        Cap_List = new ArrayList<DpcCapDto>();
        specification_list = new ArrayList<DpcSpecificationDto>();
        Transport_Type_List = new ArrayList<String>();
        Transport_Type_List.add(getString(R.string.own));
        Transport_Type_List.add(getString(R.string.Private));
        Condition_Of_Gunny_List = new ArrayList<String>();
        Condition_Of_Gunny_List.add(getString(R.string.new_bale));
        Condition_Of_Gunny_List.add(getString(R.string.sound_serviceable));
        Condition_Of_Gunny_List.add(getString(R.string.serviceable_patches));
        dpcTruckMemoDto = new DpcTruckMemoDto();
        dpcTruckMemoDto = (DpcTruckMemoDto) getIntent().getSerializableExtra("TruckMemoDto");
        Paddy_Category_List = DBHelper.getInstance(this).getPaddyCategory();
        Paddy_Grade_List = DBHelper.getInstance(this).getpaddygrade();
        Cap_List = DBHelper.getInstance(this).getCap();
        spinner_paddycategory = (NoDefaultSpinner) findViewById(R.id.paddy_category);
        spinner_grade = (NoDefaultSpinner) findViewById(R.id.spinner_grade);
        spinner_TransportType = (NoDefaultSpinner) findViewById(R.id.spinner_Transport_type);
        spinner_Conditionofgunny = (NoDefaultSpinner) findViewById(R.id.spinner_condition_of_gunny);
        spinner_CapCodeName = (NoDefaultSpinner) findViewById(R.id.spinner_Gowdown_Code_Name);
        spinner_specification = (NoDefaultSpinner) findViewById(R.id.spinner_specification);

        edt_moisture_content = (EditText) findViewById(R.id.edit_text_Moisture_content);
        edt_moisture_content.setFilters(new InputFilter[]{new EditboxFilter(2, 1)});
        edt_number_bags = (EditText) findViewById(R.id.edit_text_Number_of_bags);
        edt_net_quantity = (EditText) findViewById(R.id.edit_text_Net_quantity);
        edt_net_quantity.setFilters(new InputFilter[]{new EditboxFilter(5, 3)});
        edt_gunny_capacity = (EditText) findViewById(R.id.edit_text_Gunny_capacity);
        edt_lorry_number = (EditText) findViewById(R.id.edit_text_Lorry_Number);
        edt_Transporter_Name = (EditText) findViewById(R.id.edit_text_Transporter_Name);
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_next.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        specification_list = DBHelper.getInstance(this).getSpecification();
        Spinner_PaddyCategory();
        Spinner_TransportType();
        Spinner_ConditionofGunny();
        Spinner_Grade();
        Spinner_CapNameCode();
        spinner_specification_();
        CheckPreviousValues();
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        set_online_offline(isConnected);
    }

    private void CheckPreviousValues() {
        try {
            if (dpcTruckMemoDto.getMoistureContent() != null) {
                edt_moisture_content.setText("" + dpcTruckMemoDto.getMoistureContent());
            }
            if (String.valueOf(dpcTruckMemoDto.getNumberOfBags()) != null && !String.valueOf(dpcTruckMemoDto.getNumberOfBags()).equalsIgnoreCase("0")) {
                edt_number_bags.setText("" + dpcTruckMemoDto.getNumberOfBags());
            }
            if (String.valueOf(dpcTruckMemoDto.getNetQuantity()) != null && !String.valueOf(dpcTruckMemoDto.getNetQuantity()).equalsIgnoreCase("0.0")) {
                edt_net_quantity.setText("" + dpcTruckMemoDto.getNetQuantity());
            }
            if (dpcTruckMemoDto.getLorryNumber() != null) {
                edt_lorry_number.setText("" + dpcTruckMemoDto.getLorryNumber());
            }
            if (dpcTruckMemoDto.getTransporterName() != null) {
                edt_Transporter_Name.setText("" + dpcTruckMemoDto.getTransporterName());
            }
            if (String.valueOf(dpcTruckMemoDto.getGunnyCapacity()) != null && !String.valueOf(dpcTruckMemoDto.getGunnyCapacity()).equalsIgnoreCase("0.0")) {
                Double d = new Double(dpcTruckMemoDto.getGunnyCapacity());
                int i = d.intValue();
                edt_gunny_capacity.setText("" + i);

            }
            if (dpcTruckMemoDto.getPaddyCategoryDto() != null) {
                if (dpcTruckMemoDto.getPaddyCategoryDto().getName() != null || dpcTruckMemoDto.getPaddyCategoryDto().getLname() != null) {
                    GetPaddyCategoryPosition();
                    spinner_paddycategory.setSelection(paddyCategoryPosId);
                    if (GlobalAppState.language.equals("ta")) {
                        s_paddyCategoryName = Paddy_Category_List.get(paddyCategoryPosId).getLname();
                    } else {
                        s_paddyCategoryName = Paddy_Category_List.get(paddyCategoryPosId).getName();
                    }
                    l_PaddtCategoryId = Paddy_Category_List.get(paddyCategoryPosId).getId();
                }
            }
            if (dpcTruckMemoDto.getPaddyGradeDto() != null) {
                if (dpcTruckMemoDto.getPaddyGradeDto().getName() != null || dpcTruckMemoDto.getPaddyGradeDto().getLname() != null) {
                    GetPaddyGradePosition();
                    spinner_grade.setSelection(PaddyGradePosId);
                    if (GlobalAppState.language.equals("ta")) {
                        s_GradeName = Paddy_Grade_List.get(PaddyGradePosId).getLname();
                    } else {
                        s_GradeName = Paddy_Grade_List.get(PaddyGradePosId).getName();
                    }
                    l_GradeNameId = Paddy_Grade_List.get(PaddyGradePosId).getId();
                }
            }
            if (dpcTruckMemoDto.getTransportType() != null) {
                GetTransPortTypePosition();
                spinner_TransportType.setSelection(TransportTypeId);
                s_TransportType = Transport_Type_List.get(TransportTypeId).toString();
            }
            if (dpcTruckMemoDto.getConditionOfGunny() != null) {
                GetConditionOfGunnyPosition();
                spinner_Conditionofgunny.setSelection(CondiionOfGunnyId);
                s_ConditionOfGunny = Condition_Of_Gunny_List.get(CondiionOfGunnyId).toString();

            }
            if (dpcTruckMemoDto.getDpcCapDto() != null) {
                if (dpcTruckMemoDto.getDpcCapDto().getName() != null) {
                    GetCapCodePosition();
                    spinner_CapCodeName.setSelection(CapCodeId);
                    s_CapName = Cap_List.get(CapCodeId).getName();
                    s_CapCode = Cap_List.get(CapCodeId).getGeneratedCode();
                }
            }

            if (dpcTruckMemoDto.getDpcSpecificationDto() != null) {
                if (dpcTruckMemoDto.getDpcSpecificationDto().getSpecificationType() != null) {
                    getSpecificationPosition();
                    spinner_specification.setSelection(specfication_id);
                    specification_type = specification_list.get(specfication_id).getSpecificationType();
                    specification_id = specification_list.get(specfication_id).getId();
                }
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Spinner_PaddyCategory() {
        try {
            Paddy_Category_Adapter = new PaddyCategoryAdapter(this, Paddy_Category_List);
            spinner_paddycategory.setAdapter(Paddy_Category_Adapter);
            spinner_paddycategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (GlobalAppState.language.equals("ta")) {
                        s_paddyCategoryName = Paddy_Category_List.get(position).getLname();
                    } else {
                        s_paddyCategoryName = Paddy_Category_List.get(position).getName();
                    }
                    l_PaddtCategoryId = Paddy_Category_List.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Spinner_TransportType() {
        try {
            Transport_Adapter = new TransportTypeAdapter(this, Transport_Type_List);
            spinner_TransportType.setAdapter(Transport_Adapter);
            spinner_TransportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    s_TransportType = Transport_Type_List.get(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Spinner_ConditionofGunny() {
        try {
            Condition_of_Gunny_Adapter = new ConditionofGunnyAdapter(this, Condition_Of_Gunny_List);
            spinner_Conditionofgunny.setAdapter(Condition_of_Gunny_Adapter);
            spinner_Conditionofgunny.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    s_ConditionOfGunny = Condition_Of_Gunny_List.get(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Spinner_Grade() {
        try {
            Paddy_Grade_Adapter = new GradeAdapter(this, Paddy_Grade_List);
            spinner_grade.setAdapter(Paddy_Grade_Adapter);
            spinner_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (GlobalAppState.language.equals("ta")) {
                        s_GradeName = Paddy_Grade_List.get(position).getLname();
                    } else {
                        s_GradeName = Paddy_Grade_List.get(position).getName();
                    }
                    l_GradeNameId = Paddy_Grade_List.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void Spinner_CapNameCode() {
        try {
            cap_adapter = new CapAdapter(this, Cap_List);
            spinner_CapCodeName.setAdapter(cap_adapter);
            spinner_CapCodeName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    s_CapCode = Cap_List.get(position).getGeneratedCode();
                    s_CapName = Cap_List.get(position).getName();
                    l_CapId = Cap_List.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

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

    private void GetPaddyCategoryPosition() {
        for (int i = 0; i < Paddy_Category_List.size(); i++) {
            String category_name;
            if (GlobalAppState.language.equals("ta")) {
                category_name = Paddy_Category_List.get(i).getLname();
            } else {
                category_name = Paddy_Category_List.get(i).getName();
            }

            if (GlobalAppState.language.equals("ta")) {
                if (dpcTruckMemoDto.getPaddyCategoryDto().getLname().equals(category_name)) {
                    paddyCategoryPosId = i;
                }
            } else {
                if (dpcTruckMemoDto.getPaddyCategoryDto().getName().equals(category_name)) {
                    paddyCategoryPosId = i;
                }
            }
        }

    }

    private void GetPaddyGradePosition() {
        for (int i = 0; i < Paddy_Grade_List.size(); i++) {
            String grade_name;
            if (GlobalAppState.language.equals("ta")) {
                grade_name = Paddy_Grade_List.get(i).getLname();
            } else {
                grade_name = Paddy_Grade_List.get(i).getName();
            }

            if (GlobalAppState.language.equals("ta")) {
                if (dpcTruckMemoDto.getPaddyGradeDto().getLname().equals(grade_name)) {
                    PaddyGradePosId = i;
                }
            } else {
                if (dpcTruckMemoDto.getPaddyGradeDto().getName().equals(grade_name)) {
                    PaddyGradePosId = i;
                }
            }
        }
    }

    private void GetTransPortTypePosition() {
        for (int i = 0; i < Transport_Type_List.size(); i++) {
            String trasport_type = Transport_Type_List.get(i).toString();
            if (dpcTruckMemoDto.getTransportType().equals(trasport_type)) {
                TransportTypeId = i;
            }
        }

    }

    private void GetCapCodePosition() {
        for (int i = 0; i < Cap_List.size(); i++) {
            String cap_name = Cap_List.get(i).getName();
            if (dpcTruckMemoDto.getDpcCapDto().getName().equals(cap_name)) {
                CapCodeId = i;
            }
        }

    }

    private void GetConditionOfGunnyPosition() {
        for (int i = 0; i < Condition_Of_Gunny_List.size(); i++) {
            String condition_of_gunny = Condition_Of_Gunny_List.get(i).toString();
            if (dpcTruckMemoDto.getConditionOfGunny().equals(condition_of_gunny)) {
                CondiionOfGunnyId = i;
            }
        }

    }

    private void getSpecificationPosition() {
        String specification;
        for (int i = 0; i < specification_list.size(); i++) {
            specification = specification_list.get(i).getSpecificationType();
            if (dpcTruckMemoDto.getDpcSpecificationDto().getSpecificationType().equals(specification)) {
                specfication_id = i;
            }
        }
    }

    private void CheckValues() {
        try {
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

            if (!edt_moisture_content.getText().toString().trim().isEmpty()) {
                moisture = Double.parseDouble(edt_moisture_content.getText().toString().trim());
            }


            Log.e("s_paddyCategoryName", "" + s_paddyCategoryName);
            if (s_paddyCategoryName == null) {
                showToastMessage(getString(R.string.Select_paddyVariety), 3000);
                return;
            } else if (s_GradeName == null) {
                showToastMessage(getString(R.string.SelectGrade), 3000);
                return;
            } else if (edt_moisture_content.getText().toString().trim().equalsIgnoreCase("")) {
                showToastMessage(getString(R.string.SelectMoistureContent), 3000);
                return;
            } /*else if (Double.parseDouble(edt_moisture_content.getText().toString().trim()) < 10.0 || Double.parseDouble(edt_moisture_content.getText().toString().trim()) > 20.0) {
                showToastMessage(getString(R.string.toast_moisture_content), 3000);
                return;
            }*/ else if (edt_number_bags.getText().toString().trim().equalsIgnoreCase("")) {
                showToastMessage(getString(R.string.SelectNumberOfBags), 3000);
                return;
            } else if (edt_number_bags.getText().toString().trim().equalsIgnoreCase("0")) {
                showToastMessage(getString(R.string.toast_enter_number), 3000);
                return;
            } else if (edt_net_quantity.getText().toString().trim().equalsIgnoreCase("")) {
                showToastMessage(getString(R.string.SelectNetquantity), 3000);
                return;
            } else if (s_TransportType == null) {
                showToastMessage(getString(R.string.SelectTransportType), 3000);
                return;
            } else if (edt_lorry_number.getText().toString().trim().equalsIgnoreCase("")) {
                showToastMessage(getString(R.string.SelectLorryNumber), 3000);
                return;
            } else if (edt_lorry_number.getText().toString().trim().length() < 7) {
                showToastMessage(getString(R.string.toast_lorry_numer), 3000);
                return;
            } else if (edt_Transporter_Name.getText().toString().trim().equalsIgnoreCase("")) {
                showToastMessage(getString(R.string.SelectTransporterName), 3000);
                return;
            } else if (edt_Transporter_Name.getText().toString().trim().length() < 2) {
                showToastMessage(getString(R.string.toast_transporter_name), 3000);
                return;
            } else if (s_CapName == null) {
                showToastMessage(getString(R.string.SelectCapCodeName), 3000);
                return;
            } else if (s_ConditionOfGunny == null) {
                showToastMessage(getString(R.string.SelectConditionOfGunny), 3000);
                return;
            } else if (edt_gunny_capacity.getText().toString().trim().equalsIgnoreCase("")) {
                showToastMessage(getString(R.string.SelectGunnyCapacity), 3000);
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
            } else if (!(edt_gunny_capacity.getText().toString().trim().equalsIgnoreCase("50")
                    || edt_gunny_capacity.getText().toString().trim().equalsIgnoreCase("75")
                    || edt_gunny_capacity.getText().toString().trim().equalsIgnoreCase("100")
                    || edt_gunny_capacity.getText().toString().trim().equalsIgnoreCase("125")
                    || edt_gunny_capacity.getText().toString().trim().equalsIgnoreCase("50.0")
                    || edt_gunny_capacity.getText().toString().trim().equalsIgnoreCase("75.0")
                    || edt_gunny_capacity.getText().toString().trim().equalsIgnoreCase("100.0")
                    || edt_gunny_capacity.getText().toString().trim().equalsIgnoreCase("125.0"))) {
                showToastMessage(getString(R.string.toast_moisture_value), 3000);
                return;
            } else {
                SetTruckMemoDetails();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Truckmemo", "Exception while validation" + e.toString());
        }
    }

    private void SetTruckMemoDetails() {
        try {
            PaddyCategoryDto paddyCategoryDto = new PaddyCategoryDto();
            paddyCategoryDto.setId(l_PaddtCategoryId);
            if (GlobalAppState.language.equals("ta")) {
                paddyCategoryDto.setLname(s_paddyCategoryName);
            } else {
                paddyCategoryDto.setName(s_paddyCategoryName);
            }

            dpcTruckMemoDto.setPaddyCategoryDto(paddyCategoryDto);

            PaddyGradeDto paddyGradeDto = new PaddyGradeDto();
            paddyGradeDto.setId(l_GradeNameId);
            if (GlobalAppState.language.equals("ta")) {
                paddyGradeDto.setLname(s_GradeName);
            } else {
                paddyGradeDto.setName(s_GradeName);
            }

            dpcTruckMemoDto.setPaddyGradeDto(paddyGradeDto);
            dpcTruckMemoDto.setMoistureContent(Double.parseDouble(edt_moisture_content.getText().toString().trim()));
            dpcTruckMemoDto.setNumberOfBags(Integer.parseInt(edt_number_bags.getText().toString().trim()));
            dpcTruckMemoDto.setNetQuantity(Double.parseDouble(edt_net_quantity.getText().toString().trim()));
            dpcTruckMemoDto.setTransportType(s_TransportType);
            dpcTruckMemoDto.setLorryNumber(edt_lorry_number.getText().toString().trim());
            dpcTruckMemoDto.setTransporterName(edt_Transporter_Name.getText().toString().trim());
            dpcTruckMemoDto.setConditionOfGunny(s_ConditionOfGunny);
            dpcTruckMemoDto.setGunnyCapacity(Double.parseDouble(edt_gunny_capacity.getText().toString().trim()));

            DpcCapDto dpcCapDto = new DpcCapDto();
            dpcCapDto.setName(s_CapName);
            dpcCapDto.setGeneratedCode(s_CapCode);
            dpcCapDto.setId(l_CapId);
            dpcTruckMemoDto.setDpcCapDto(dpcCapDto);

            String device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase();
            DeviceDto deviceDto = new DeviceDto();
            deviceDto.setDeviceNumber(device_id);
            dpcTruckMemoDto.setDeviceDto(deviceDto);

            DpcSpecificationDto spec = new DpcSpecificationDto();
            spec.setId(specification_id);
            spec.setSpecificationType(specification_type);
            dpcTruckMemoDto.setDpcSpecificationDto(spec);

            Log.e("TruckMemo", "print TruckMemo " + dpcTruckMemoDto.toString());
            Intent truckmemodetailIntent = new Intent(TruckMemoActivity.this, TruckMemoDetailActivity.class);
            truckmemodetailIntent.putExtra("TruckMemoDto", dpcTruckMemoDto);
            startActivity(truckmemodetailIntent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TruckMemo", "Exception while submit" + e.toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                onBackPressed();
                break;
            case R.id.btn_cancel:
                Intent cancelIntent = new Intent(TruckMemoActivity.this, DashBoardActivity.class);
                startActivity(cancelIntent);
                finish();
                break;
            case R.id.btn_next:
                CheckValues();
                break;
        }

    }




    @Override
    public void onBackPressed() {
        Intent backIntent = new Intent(TruckMemoActivity.this, DashBoardActivity.class);
        startActivity(backIntent);
        finish();
    }
}
