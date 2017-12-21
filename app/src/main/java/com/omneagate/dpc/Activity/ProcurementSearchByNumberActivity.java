package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.omneagate.dpc.Model.DPCProcurementDto;
import com.omneagate.dpc.Model.DPCProfileDto;
import com.omneagate.dpc.Model.DpcDistrictDto;
import com.omneagate.dpc.Model.DpcPaddyRateDto;
import com.omneagate.dpc.Model.DpcTalukDto;
import com.omneagate.dpc.Model.FarmerRegistrationDto;
import com.omneagate.dpc.Model.PaddyGradeDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.BlueToothPrint;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.EnglishNumberToWords;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProcurementSearchByNumberActivity extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private String procurementNumber;
    String TAG = "ProcurementSearchByNumberActivity";
    TextView tv_procurement_receipt_number, no_records;
    DPCProcurementDto dpcProcurementDto;
    //    FarmerRegistrationDto farmer_registration_dto;
    private DPCProfileDto dpc_dto;
    private String paddy_name;
    private PaddyGradeDto grade_dto;
    private DpcPaddyRateDto rate_dto;
    Button btn_print, btn_lose;
    private FarmerRegistrationDto farmer_registration_dto;
    private String specification_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurement_search_by_number);
        checkInternetConnection();
        setUpView();
        procurementNumber = getIntent().getStringExtra("ProcurementNumber");
        Log.e(TAG, "The Number is :::::::" + procurementNumber);
        tv_procurement_receipt_number = (TextView) findViewById(R.id.tv_procurement_receipt_number);
        tv_procurement_receipt_number.setText(procurementNumber);
        dpcProcurementDto = DBHelper.getInstance(ProcurementSearchByNumberActivity.this).getProcurement_history(procurementNumber);
        if (dpcProcurementDto != null) {
            setdetails();
        } else {
            no_records.setVisibility(View.VISIBLE);
        }
    }


    private void setUpView() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_truck_procurement_details));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        setUpPopUpPage();
        no_records = (TextView) findViewById(R.id.no_records);
        btn_print = (Button) findViewById(R.id.btn_print);
        btn_print.setOnClickListener(this);
        btn_lose = (Button) findViewById(R.id.btn_close);
        btn_lose.setOnClickListener(this);

    }

    private void setdetails() {
        if (dpcProcurementDto != null) {
            long id = dpcProcurementDto.getDpcProfileDto().getId();
            dpc_dto = DBHelper.getInstance(ProcurementSearchByNumberActivity.this).getDpcProfileById(id);
            if (dpc_dto != null) {
                dpcProcurementDto.setDpcProfileDto(dpc_dto);
            }
        }
        if (dpcProcurementDto != null) {
            long paddy_id = dpcProcurementDto.getPaddyCategoryDto().getId();
            paddy_name = DBHelper.getInstance(ProcurementSearchByNumberActivity.this).getpaddy(paddy_id);
        }

        if (dpcProcurementDto != null) {
            long grad_id = dpcProcurementDto.getPaddyGradeDto().getId();
            grade_dto = DBHelper.getInstance(ProcurementSearchByNumberActivity.this).getGradeById(grad_id);
        }

        if (dpcProcurementDto != null) {
            rate_dto = DBHelper.getInstance(ProcurementSearchByNumberActivity.this).getRate(dpcProcurementDto.getPaddyGradeDto().getId());
        }

        if (dpcProcurementDto != null) {
            Long farmer_id = dpcProcurementDto.getFarmerRegistrationDto().getId();
            Log.e("FARMER ID", "" + farmer_id);
            farmer_registration_dto = DBHelper.getInstance(ProcurementSearchByNumberActivity.this).getFarmerDetailsProcurementDuplicate(farmer_id);
        }

        if (dpcProcurementDto != null) {
            long specification_id = dpcProcurementDto.getDpcSpecificationDto().getId();
            specification_type = DBHelper.getInstance(ProcurementSearchByNumberActivity.this).getSpecificationById(specification_id);
        }


        if (GlobalAppState.language.equals("ta")) {
            ((TextView) findViewById(R.id.fm_name)).setText(farmer_registration_dto.getFarmerLname());
            ((TextView) findViewById(R.id.paddy_category)).setText(paddy_name);
            ((TextView) findViewById(R.id.grade)).setText(grade_dto.getLname());
//            ((TextView) findViewById(R.id.tv_dpc_name)).setText(dpc_profiledto.getLname());
            ((TextView) findViewById(R.id.fm_bank_name)).setText(farmer_registration_dto.getBankDto().getLname());
        } else {
            ((TextView) findViewById(R.id.fm_name)).setText(farmer_registration_dto.getFarmerName());
            ((TextView) findViewById(R.id.paddy_category)).setText(paddy_name);
            ((TextView) findViewById(R.id.grade)).setText(grade_dto.getName());
//            ((TextView) findViewById(R.id.tv_dpc_name)).setText(dpc_profiledto.getName());
            ((TextView) findViewById(R.id.fm_bank_name)).setText(farmer_registration_dto.getBankDto().getBankName());
        }
        ((TextView) findViewById(R.id.fm_code)).setText(farmer_registration_dto.getFarmerCode());
        ((TextView) findViewById(R.id.fm_branch_name)).setText(farmer_registration_dto.getBranchName());
        ((TextView) findViewById(R.id.fm_mob_number)).setText(farmer_registration_dto.getMobileNumber());
        ((TextView) findViewById(R.id.fm_ac_number)).setText(farmer_registration_dto.getAccountNumber());
        ((TextView) findViewById(R.id.fm_ifsc)).setText(farmer_registration_dto.getIfscCode());
        ((TextView) findViewById(R.id.ed_lnum)).setText("" + dpcProcurementDto.getLotNumber());
        ((TextView) findViewById(R.id.spillage_qty)).setText("" + dpcProcurementDto.getSpillageQuantity());

        ((TextView) findViewById(R.id.no_bags)).setText("" + dpcProcurementDto.getNumberOfBags());

//        Double d = new Double(dpcProcurementDto.getMoistureContent());
//        int i = d.intValue();
        ((TextView) findViewById(R.id.moisture_content)).setText("" + dpcProcurementDto.getMoistureContent());
//        ((TextView) findViewById(R.id.tv_dpc_code)).setText(dpc_profiledto.getGeneratedCode());
        ((TextView) findViewById(R.id.ed_netweight)).setText("" + String.format("%.3f",dpcProcurementDto.getNetWeight()));


        ((TextView) findViewById(R.id.specification)).setText(specification_type);

        ((TextView) findViewById(R.id.purchaserate)).setText("" + String.format("%.2f",rate_dto.getPurchaseRate()));
        ((TextView) findViewById(R.id.bonusrate)).setText("" + String.format("%.2f",rate_dto.getBonusRate()));
        ((TextView) findViewById(R.id.totalrate)).setText("" + String.format("%.2f",rate_dto.getTotalRate()));

        ((TextView) findViewById(R.id.totalamount)).setText("" + String.format("%.2f",dpcProcurementDto.getTotalAmount()));
        ((TextView) findViewById(R.id.grade_cut)).setText("" + String.format("%.2f",dpcProcurementDto.getGradeCut()));
        ((TextView) findViewById(R.id.moisture_cut)).setText("" + String.format("%.2f",dpcProcurementDto.getMoistureCut()));
        ((TextView) findViewById(R.id.additional_cut)).setText("" + String.format("%.2f",dpcProcurementDto.getAdditionalCut()));
        ((TextView) findViewById(R.id.total_cut)).setText("" + String.format("%.2f",dpcProcurementDto.getTotalCut()));
        ((TextView) findViewById(R.id.total_cut_amount)).setText("" +String.format("%.2f", dpcProcurementDto.getTotalCutAmount()));
        ((TextView) findViewById(R.id.totalamount_calc)).setText("" + String.format("%.2f",dpcProcurementDto.getTotalAmount()));
        ((TextView) findViewById(R.id.totalcut_amount_calc)).setText("" + String.format("%.2f",dpcProcurementDto.getTotalCutAmount()));
        ((TextView) findViewById(R.id.netamount)).setText("" + String.format("%.2f",dpcProcurementDto.getNetAmount()));

        String procurementDate = dpcProcurementDto.getTxnDateTime();
        String formatedDate = "";
        String oldFormat = "yyyy-MM-dd HH:mm:ss";
        String newFormat = "dd-MM-yyyy hh:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(procurementDate);
            SimpleDateFormat timeFormat = new SimpleDateFormat(newFormat);
            formatedDate = timeFormat.format(myDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        ((TextView) findViewById(R.id.date_time_pro)).setText(formatedDate);
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
        textData.append("            TNCSC\n");
        textData.append("       DPC Purchase Bill\n");
        textData.append("--------------------------------\n");
        if (dpcProcurementDto != null && farmer_registration_dto != null) {
            textData.append("         Duplicate Copy\n");
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
            textData.append("DPC Name   : " + " " + dpc_dto.getName() + "\n");
            textData.append("DPC Code   : " + " " + dpc_dto.getGeneratedCode() + "\n");
            textData.append("--------------------------------\n");

            textData.append(getString(R.string.name) + "           " + farmer_registration_dto.getFarmerName() + "\n");
            textData.append(getString(R.string.address) + "        " + farmer_registration_dto.getAddress1() + "\n");
            textData.append(getString(R.string.txt_mob_no) + "         " + farmer_registration_dto.getMobileNumber() + "\n");
            textData.append(getString(R.string.bank) + "           " + farmer_registration_dto.getBankDto().getBankName() + "\n");
            textData.append(getString(R.string.acc) + "         " + farmer_registration_dto.getAccountNumber() + "\n");
            textData.append(getString(R.string.gradename) + "          " + grade_dto.getName() + "\n");
            textData.append(getString(R.string.txt_Paddy_Category) + "         " + paddy_name + "\n");

            textData.append(getString(R.string.specification_) + "  " + specification_type + "\n");

            textData.append(getString(R.string.text_lot_number) + "     " + dpcProcurementDto.getLotNumber() + "\n");
//            Double d = new Double(dpcProcurementDto.getMoistureContent());
//            int i = d.intValue();
            textData.append(getString(R.string.text_moistureonly) + "       " +  dpcProcurementDto.getMoistureContent() + "\n");
            textData.append(getString(R.string.print_Number_of_Bags) + "     " + dpcProcurementDto.getNumberOfBags() + "\n");
            textData.append(getString(R.string.print_netwt) + "         " + String.format("%.3f", dpcProcurementDto.getNetWeight()) + "\n");
            textData.append(getString(R.string.print_pur_rate) + "   " + String.format("%.2f",rate_dto.getPurchaseRate()) + "\n");
            textData.append(getString(R.string.print_Bon_rate) + "   " + String.format("%.2f",rate_dto.getBonusRate()) + "\n");
            textData.append(getString(R.string.print_total) + "   " + String.format("%.2f", dpcProcurementDto.getTotalAmount()) + "\n");
            textData.append(getString(R.string.print_gradecut) + "      " + String.format("%.2f", dpcProcurementDto.getGradeCut()) + "\n");
            textData.append(getString(R.string.print_moisturecut) + "   " + String.format("%.2f", dpcProcurementDto.getMoistureCut()) + "\n");
            textData.append(getString(R.string.print_totalcutamount) + "  " + String.format("%.2f", dpcProcurementDto.getTotalCutAmount()) + "\n");
            textData.append(getString(R.string.print_netamt) + "        " + String.format("%.2f", dpcProcurementDto.getNetAmount()) + "\n");
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

            case R.id.btn_print:
                callprint();
                break;

            case R.id.btn_close:
                onBackPressed();
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }


    @Override
    public void onBackPressed() {
        Intent ii_ = new Intent(ProcurementSearchByNumberActivity.this, ProcurementHistory.class);
        startActivity(ii_);
        finish();
    }
}
