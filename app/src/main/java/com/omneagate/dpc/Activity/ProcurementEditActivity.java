package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omneagate.dpc.Activity.Dialog.SuccessProcurementDialog;
import com.omneagate.dpc.Model.DPCProcurementDto;
import com.omneagate.dpc.Model.DPCProfileDto;
import com.omneagate.dpc.Model.DeviceDto;
import com.omneagate.dpc.Model.DpcDistrictDto;
import com.omneagate.dpc.Model.DpcTalukDto;
import com.omneagate.dpc.Model.FarmerRegistrationDto;
import com.omneagate.dpc.Model.ResponseDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Service.ResReqController;
import com.omneagate.dpc.Utility.BlueToothPrint;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.EnglishNumberToWords;
import com.omneagate.dpc.Utility.LoginData;
import com.omneagate.dpc.Utility.NetworkConnection;
import com.omneagate.dpc.Utility.Util;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ProcurementEditActivity extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener, Handler.Callback {
    private FarmerRegistrationDto farmer_registration_dto;
    private DPCProcurementDto dpcProcurementDto;
    private Button btn_confirm, btn_edit;
    final ResReqController controller = new ResReqController(this);
    private NetworkConnection networkConnection;
    private DPCProfileDto dpc_profiledto;
    private DecimalFormat formater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurement_edit);
        checkInternetConnection();
        networkConnection = new NetworkConnection(this);
        controller.addOutboxHandler(new Handler(this));
        formater = new DecimalFormat("#.00");
        setUpView();
        set_details();
    }

    private void set_details() {
        DecimalFormat net_formater = new DecimalFormat("#.000");
        ResponseDto response_data = LoginData.getInstance().getResponseData();
        dpc_profiledto = response_data.getUserDetailDto().getDpcProfileDto();
        dpcProcurementDto.setDpcProfileDto(dpc_profiledto);
        if (GlobalAppState.language.equals("ta")) {
            ((TextView) findViewById(R.id.fm_name)).setText(farmer_registration_dto.getFarmerLname());
            ((TextView) findViewById(R.id.paddy_category)).setText(dpcProcurementDto.getPaddyCategoryDto().getLname());
            ((TextView) findViewById(R.id.grade)).setText(dpcProcurementDto.getPaddyGradeDto().getLname());
            ((TextView) findViewById(R.id.tv_dpc_name)).setText(dpc_profiledto.getLname());
            ((TextView) findViewById(R.id.fm_bank_name)).setText(farmer_registration_dto.getBankDto().getLname());
        } else {
            ((TextView) findViewById(R.id.fm_name)).setText(farmer_registration_dto.getFarmerName());
            ((TextView) findViewById(R.id.paddy_category)).setText(dpcProcurementDto.getPaddyCategoryDto().getName());
            ((TextView) findViewById(R.id.grade)).setText(dpcProcurementDto.getPaddyGradeDto().getName());
            ((TextView) findViewById(R.id.tv_dpc_name)).setText(dpc_profiledto.getName());
            ((TextView) findViewById(R.id.fm_bank_name)).setText(farmer_registration_dto.getBankDto().getBankName());
        }
        ((TextView) findViewById(R.id.fm_code)).setText(farmer_registration_dto.getFarmerCode());
        ((TextView) findViewById(R.id.fm_branch_name)).setText(farmer_registration_dto.getBranchName());
        ((TextView) findViewById(R.id.fm_mob_number)).setText(farmer_registration_dto.getMobileNumber());
        ((TextView) findViewById(R.id.fm_ac_number)).setText(farmer_registration_dto.getAccountNumber());
        ((TextView) findViewById(R.id.fm_ifsc)).setText(farmer_registration_dto.getIfscCode());
        ((TextView) findViewById(R.id.ed_lnum)).setText("" + dpcProcurementDto.getLotNumber());
        ((TextView) findViewById(R.id.spillage_qty)).setText("" + net_formater.format(dpcProcurementDto.getSpillageQuantity()));
        ((TextView) findViewById(R.id.no_bags)).setText("" + dpcProcurementDto.getNumberOfBags());
        ((TextView) findViewById(R.id.moisture_content)).setText("" + dpcProcurementDto.getMoistureContent());
        ((TextView) findViewById(R.id.tv_dpc_code)).setText(dpc_profiledto.getGeneratedCode());
        ((TextView) findViewById(R.id.ed_netweight)).setText("" + String.format("%.3f",dpcProcurementDto.getNetWeight()));
        ((TextView) findViewById(R.id.purchaserate)).setText("" + formater.format(dpcProcurementDto.getDpcPaddyRateDto().getPurchaseRate()));
        ((TextView) findViewById(R.id.specification)).setText("" + dpcProcurementDto.getDpcSpecificationDto().getSpecificationType());
        ((TextView) findViewById(R.id.bonusrate)).setText("" + formater.format(dpcProcurementDto.getDpcPaddyRateDto().getBonusRate()));
        ((TextView) findViewById(R.id.totalrate)).setText("" + formater.format(dpcProcurementDto.getDpcPaddyRateDto().getTotalRate()));
        ((TextView) findViewById(R.id.totalamount)).setText("" + formater.format(dpcProcurementDto.getTotalAmount()));
        ((TextView) findViewById(R.id.grade_cut)).setText("" + String.format("%.2f", dpcProcurementDto.getGradeCut()));
        ((TextView) findViewById(R.id.moisture_cut)).setText("" + String.format("%.2f", dpcProcurementDto.getMoistureCut()));
        ((TextView) findViewById(R.id.additional_cut)).setText("" + String.format("%.2f", dpcProcurementDto.getAdditionalCut()));
        ((TextView) findViewById(R.id.total_cut)).setText("" + String.format("%.2f", dpcProcurementDto.getTotalCut()));
        ((TextView) findViewById(R.id.total_cut_amount)).setText("" + String.format("%.2f", dpcProcurementDto.getTotalCutAmount()));
        ((TextView) findViewById(R.id.totalamount_calc)).setText("(" + String.format("%.2f", dpcProcurementDto.getTotalAmount()) + ")");
        ((TextView) findViewById(R.id.totalcut_amount_calc)).setText("(" + String.format("%.2f", dpcProcurementDto.getTotalCutAmount()) + ")");
        ((TextView) findViewById(R.id.netamount)).setText("" + formater.format(dpcProcurementDto.getNetAmount()));
    }

    private void setUpView() {
        setUpPopUpPage();
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_Farmer_Procurement));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(this);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_edit.setOnClickListener(this);

        farmer_registration_dto = (FarmerRegistrationDto) getIntent().getSerializableExtra("farmerRegistrationDto");
        dpcProcurementDto = (DPCProcurementDto) getIntent().getSerializableExtra("dpcProcurementDto");
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
            case R.id.btn_confirm:
                btn_confirm.setVisibility(View.GONE);
                setValues();
                generateReceptNumber();
                Log.e("THE JSON",""+dpcProcurementDto);
                Log.e("FINAL DTO", "***********" + dpcProcurementDto.toString() + "********");
                break;
            case R.id.btn_edit:
                Intent edit_intent = new Intent(ProcurementEditActivity.this, ProcurementDetailsActivity.class);
                edit_intent.putExtra("farmerRegistrationDto", farmer_registration_dto);
                edit_intent.putExtra("dpcProcurementDto", dpcProcurementDto);
                startActivity(edit_intent);
                finish();
                break;
            default:
                break;
        }
    }

    private void generateReceptNumber() {
        if (networkConnection.isNetworkAvailable()) {
            try {
                dpcProcurementDto.setSyncStatus("B");
                SetReceiptNumber();
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();
                String dpcProcurementDto_ = gson.toJson(dpcProcurementDto);
                Log.e("the request", dpcProcurementDto_);
                controller.handleMessage_(ResReqController.PROCUREMENT, dpcProcurementDto_, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            dpcProcurementDto.setSyncStatus("A");
            SetReceiptNumber();
            String ReceiptNumber = dpcProcurementDto.getProcurementReceiptNo();
            new SuccessProcurementDialog(this, ReceiptNumber, false).show();
            callprint();
        }
    }

    private void SetReceiptNumber() {
        if (dpcProcurementDto.getProcurementReceiptNo() == null) {
            dpcProcurementDto.setProcurementReceiptNo(Util.generateReceiptNumber(this));
        }
        DBHelper.getInstance(this).Insert_Procurement(dpcProcurementDto);
    }

    private void setValues() {
        String device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID).toUpperCase();
        DeviceDto device_dto = new DeviceDto();
        device_dto.setDeviceNumber(device_id);
        dpcProcurementDto.setDeviceDto(device_dto);
        Log.e("DEVICE DTO",""+device_dto);
        long dpc_id = LoginData.getInstance().getResponseData().getUserDetailDto().getDpcProfileDto().getId();
        long mo_cr_id = LoginData.getInstance().getResponseData().getUserDetailDto().getId();
        Log.e("siva dpc id in pro", "****^^^^^^^^^^" + dpc_id);
        dpcProcurementDto.setModifiedBy(mo_cr_id);
        dpcProcurementDto.setCreatedBy(mo_cr_id);
        SimpleDateFormat regDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        dpcProcurementDto.setTxnDateTime(regDate.format(new Date()));
        dpcProcurementDto.setCreatedBy(mo_cr_id);
    }

    @Override
    public void onBackPressed() {
        Intent ii_ = new Intent(ProcurementEditActivity.this, ProcurementCalculationActivity.class);
        ii_.putExtra("farmerRegistrationDto", farmer_registration_dto);
        ii_.putExtra("dpcProcurementDto", dpcProcurementDto);
        startActivity(ii_);
        finish();
    }

    @Override
    public boolean handleMessage(Message msg) {
        try {
            switch (msg.what) {
                case ResReqController.PROCUREMENT_SUCCESS:
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    Log.d("Check", "" + msg.obj.toString());
                    DPCProcurementDto pro_res_dto = gson.fromJson(msg.obj.toString(), DPCProcurementDto.class);
                    String ReceiptNumber = pro_res_dto.getProcurementReceiptNo();
                    if (pro_res_dto.getStatusCode().equalsIgnoreCase("0")) {
                        new SuccessProcurementDialog(this, ReceiptNumber, true).show();
                        callprint();
                    } else if (pro_res_dto.getStatusCode().equalsIgnoreCase("1600058")) {
                        btn_confirm.setVisibility(View.VISIBLE);
                        showToastMessage(getString(R.string.toast_duplicate_receipt_number), 3000);
                        DBHelper.getInstance(this).RemoveDuplicateReceiptNumber(dpcProcurementDto.getProcurementReceiptNo());
                        dpcProcurementDto.setProcurementReceiptNo(null);
                    }
                    return true;
                case ResReqController.PROCUREMENT_FAILED:
                    btn_confirm.setVisibility(View.VISIBLE);
                    showToastMessage(getString(R.string.toast_server_unreachable), 3000);
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void callprint() {
        String printdata = BluetoothPrintData();
        Log.e("printdata", printdata);
        BlueToothPrint printing = new BlueToothPrint(this);
        printing.opendialog(printdata);
    }

    private String BluetoothPrintData() {
        StringBuilder textData = new StringBuilder();
        textData.append("    ");
        textData.append("--------------------------------\n");
        textData.append("            TNCSC \n");
        textData.append("       DPC Purchase Bill\n");
        textData.append("--------------------------------\n");
        if (dpcProcurementDto != null && farmer_registration_dto != null) {
            textData.append("BILL NO : " + "  " + dpcProcurementDto.getProcurementReceiptNo() + "\n");
            textData.append(new SimpleDateFormat("dd/MM/yyyy  hh:mm:ss a").format(new Date()) + "\n");
            textData.append("--------------------------------\n");
            DpcDistrictDto dpcdistrictdto = dpcProcurementDto.getDpcProfileDto().getDpcDistrictDto();
            if (dpcdistrictdto != null) {
                long districtid = dpcProcurementDto.getDpcProfileDto().getDpcDistrictDto().getId();
                dpcdistrictdto = DBHelper.getInstance(this).getDistrictName_byid(districtid, dpcdistrictdto);
                textData.append("District   : " + " " + dpcdistrictdto.getName().trim() + "\n");
            }
            DpcTalukDto dpctalukdto = dpcProcurementDto.getDpcProfileDto().getDpcTalukDto();
            if (dpctalukdto != null) {
                long talukid = dpcProcurementDto.getDpcProfileDto().getDpcTalukDto().getId();
                dpctalukdto = DBHelper.getInstance(this).getTalukName_byid(talukid, dpctalukdto);
                textData.append("Taluk      : " + " " + dpctalukdto.getName() + "\n");
            }
            textData.append("DPC Name   : " + " " + dpcProcurementDto.getDpcProfileDto().getName() + "\n");
            textData.append("DPC Code   : " + " " + dpcProcurementDto.getDpcProfileDto().getGeneratedCode() + "\n");
            textData.append("--------------------------------\n");
            textData.append(getstring(R.string.name) + "           " + farmer_registration_dto.getFarmerName() + "\n");
            textData.append(getstring(R.string.address) + "        " + farmer_registration_dto.getAddress1() + "\n");
            textData.append(getString(R.string.txt_mob_no) + "         " + farmer_registration_dto.getMobileNumber() + "\n");
            textData.append(getString(R.string.bank) + "           " + farmer_registration_dto.getBankDto().getBankName() + "\n");
            textData.append(getString(R.string.acc) + "         " + farmer_registration_dto.getAccountNumber() + "\n");
            textData.append(getstring(R.string.gradename) + "          " + dpcProcurementDto.getPaddyGradeDto().getName() + "\n");
            textData.append(getstring(R.string.txt_Paddy_Category) + "        " + dpcProcurementDto.getPaddyCategoryDto().getName() + "\n");
            textData.append(getstring(R.string.specification_) + "  " + dpcProcurementDto.getDpcSpecificationDto().getSpecificationType() + "\n");
            textData.append(getstring(R.string.text_lot_number) + "     " + dpcProcurementDto.getLotNumber() + "\n");
            textData.append(getstring(R.string.text_moistureonly) + "       " + dpcProcurementDto.getMoistureContent() + "\n");
            textData.append(getstring(R.string.print_Number_of_Bags) + "     " + dpcProcurementDto.getNumberOfBags() + "\n");
            textData.append(getstring(R.string.print_netwt) + "         " + String.format("%.3f", dpcProcurementDto.getNetWeight()) + "\n");
            textData.append(getstring(R.string.print_pur_rate) + "   " + String.format("%.2f",dpcProcurementDto.getDpcPaddyRateDto().getPurchaseRate()) + "\n");
            textData.append(getstring(R.string.print_Bon_rate) + "   " + String.format("%.2f",dpcProcurementDto.getDpcPaddyRateDto().getBonusRate()) + "\n");
            textData.append(getstring(R.string.print_total) + "   " + String.format("%.2f", dpcProcurementDto.getTotalAmount()) + "\n");
            textData.append(getstring(R.string.print_gradecut) + "      " + String.format("%.2f", dpcProcurementDto.getGradeCut()) + "\n");
            textData.append(getstring(R.string.print_moisturecut) + "   " + String.format("%.2f", dpcProcurementDto.getMoistureCut()) + "\n");
            textData.append(getstring(R.string.print_totalcutamount) + "  " + String.format("%.2f", dpcProcurementDto.getTotalCutAmount()) + "\n");
            textData.append(getstring(R.string.print_netamt) + "        " + String.format("%.2f", dpcProcurementDto.getNetAmount()) + "\n");
            split_text(textData, dpcProcurementDto.getNetAmount());
        }
        textData.append("\n");
        textData.append("--------------------------------\n\n\n\n");
        textData.append("SIGN OF THE  FARMER\n\n\n\n");
        textData.append("SIGN OF THE BILL CLERK \n");
        textData.append("--------------------------------\n");
        textData.append("\n");
        textData.append("\n");
        return textData.toString();
    }

    private void split_text(StringBuilder textData, double number) {
        int total = 33;
        String text = EnglishNumberToWords.convert(number);
        text = text + " Only";
        if (text.length() > total) {
            String[] splitter = text.split(" ");
            for (int i = 0; i < splitter.length; i++) {
                if ((total -= splitter[i].length() + 1) > 0) {
                    textData.append(splitter[i] + " ");
                } else {
                    total = 33;
                    textData.append(" \n" + splitter[i] + " ");
                }
            }
        } else {
            textData.append(text + " ");
        }
        Log.e("Print text", textData.toString());
    }

    private String getstring(int i) {
        return getResources().getString(i);
    }
}
