package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.omneagate.dpc.Model.DPCProfileDto;
import com.omneagate.dpc.Model.DpcCapDto;
import com.omneagate.dpc.Model.DpcDistrictDto;
import com.omneagate.dpc.Model.DpcTalukDto;
import com.omneagate.dpc.Model.DpcTruckMemoDto;
import com.omneagate.dpc.Model.PaddyGradeDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Utility.BlueToothPrint;
import com.omneagate.dpc.Utility.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 5/10/16.
 */
public class TruckSearchNumber extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {
    private TextView number;
    private Button Btn_edit, Btn_cancel;
    private TextView total_unSynced, no_records;
    private String truckMemoNumber;
    private DpcTruckMemoDto dpcTruckMemoDto;
    private DPCProfileDto dpc_dto;
    private Button btn_close;
    private Button btn_print;
    private DpcCapDto cap;
    private String paddy_name;
    private PaddyGradeDto grade_dto;
    private String specification_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truckmemo_history_detail);
        checkInternetConnection();
        setUpView();
    }

    private void setUpView() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_truck_memo_history));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        setUpPopUpPage();
        TextView no_records = (TextView) findViewById(R.id.no_records);
        truckMemoNumber = getIntent().getStringExtra("truck_memo_number");
        dpcTruckMemoDto = DBHelper.getInstance(TruckSearchNumber.this).getTruckMemoByNumber(truckMemoNumber);
        Log.e("dpcTruckMemoDto", "**********" + dpcTruckMemoDto + "*******");
        if (dpcTruckMemoDto != null) {
            setview(dpcTruckMemoDto);
        } else {
            no_records.setVisibility(View.VISIBLE);
        }

    }

    private void setview(DpcTruckMemoDto dpcTruckMemoDto) {
        try {
            TextView title = (TextView) findViewById(R.id.title);
            title.setText(getString(R.string.TruckMemo_details));

            ImageView btn_back = (ImageView) findViewById(R.id.image_back);
            btn_back.setOnClickListener(TruckSearchNumber.this);
            setUpPopUpPage();
            btn_close = (Button) findViewById(R.id.btn_close);
            btn_print = (Button) findViewById(R.id.btn_print);
            btn_close.setOnClickListener(this);
            btn_print.setOnClickListener(this);
            ((TextView) findViewById(R.id.tv_truck_number)).setText("" + dpcTruckMemoDto.getTruckMemoNumber());


            String truckDate = dpcTruckMemoDto.getTxnDateTime();
            String formatedDate = "";
            String oldFormat = "yyyy-MM-dd HH:mm:ss";
            String newFormat = "dd-MM-yyyy hh:mm:ss a";
            SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
            Date myDate = null;
            try {
                myDate = dateFormat.parse(truckDate);
                SimpleDateFormat timeFormat = new SimpleDateFormat(newFormat);
                formatedDate = timeFormat.format(myDate);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }


            ((TextView) findViewById(R.id.tv_datetime)).setText("" + formatedDate);
            if (dpcTruckMemoDto != null) {
                long id = dpcTruckMemoDto.getDpcProfileDto().getId();
                dpc_dto = DBHelper.getInstance(TruckSearchNumber.this).getDpcProfileById(id);
                if (dpc_dto != null) {
                    ((TextView) findViewById(R.id.tv_dpc_code_his)).setText("" + dpc_dto.getGeneratedCode());
                }

            }
            if (dpcTruckMemoDto != null) {
                long cap_id = dpcTruckMemoDto.getDpcCapDto().getId();
                cap = DBHelper.getInstance(TruckSearchNumber.this).getCapName(cap_id);
            }
            if (dpcTruckMemoDto != null) {
                long paddy_id = dpcTruckMemoDto.getPaddyCategoryDto().getId();
                paddy_name = DBHelper.getInstance(TruckSearchNumber.this).getpaddy(paddy_id);
            }
            if (dpcTruckMemoDto != null) {
                long grad_id = dpcTruckMemoDto.getPaddyGradeDto().getId();
                grade_dto = DBHelper.getInstance(TruckSearchNumber.this).getGradeById(grad_id);
            }

            if (dpcTruckMemoDto != null) {
                long specification_id = dpcTruckMemoDto.getDpcSpecificationDto().getId();
                specification_type = DBHelper.getInstance(TruckSearchNumber.this).getSpecificationById(specification_id);
            }

            if (GlobalAppState.language.equals("ta")) {
                ((TextView) findViewById(R.id.txt_paddyCategory)).setText(paddy_name);
                ((TextView) findViewById(R.id.txt_grade)).setText(grade_dto.getLname());
                ((TextView) findViewById(R.id.tv_dpc_name_his)).setText("" + dpc_dto.getLname());
                ((TextView) findViewById(R.id.txt_cap_code)).setText("" + cap.getGeneratedCode() + "/" + cap.getLname());
            } else {
                ((TextView) findViewById(R.id.txt_paddyCategory)).setText(paddy_name);
                ((TextView) findViewById(R.id.txt_grade)).setText(grade_dto.getName());
                ((TextView) findViewById(R.id.tv_dpc_name_his)).setText("" + dpc_dto.getName());
                ((TextView) findViewById(R.id.txt_cap_code)).setText("" + cap.getGeneratedCode() + "/" + cap.getName());
            }
            ((TextView) findViewById(R.id.txt_Number_Bags)).setText("" + dpcTruckMemoDto.getNumberOfBags());
            ((TextView) findViewById(R.id.txt_Lorry_number)).setText("" + dpcTruckMemoDto.getLorryNumber());
            ((TextView) findViewById(R.id.txt_ConditionofGunny)).setText("" + dpcTruckMemoDto.getConditionOfGunny());
            ((TextView) findViewById(R.id.specification)).setText(specification_type);
            ((TextView) findViewById(R.id.txt_net_quan)).setText("" + dpcTruckMemoDto.getNetQuantity());
            ((TextView) findViewById(R.id.txt_Transporter_Name)).setText("" + dpcTruckMemoDto.getTransporterName());
            ((TextView) findViewById(R.id.txt_gunnyCapacity)).setText("" + dpcTruckMemoDto.getGunnyCapacity());
            ((TextView) findViewById(R.id.txt_moisture_content)).setText("" + dpcTruckMemoDto.getMoistureContent());
            ((TextView) findViewById(R.id.txt_transport_type)).setText("" + dpcTruckMemoDto.getTransportType());
        } catch (Exception e) {
            e.printStackTrace();
        }

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
    public void onBackPressed() {
        Intent imageback = new Intent(TruckSearchNumber.this, TruckMemoHistoryActivity.class);
        startActivity(imageback);
        finish();
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
        textData.append("          Truck Memo\n");
        textData.append("--------------------------------\n");

        if (dpcTruckMemoDto != null) {
            textData.append("       Duplicate Copy\n");
            textData.append("BILL NO : " + "  " + dpcTruckMemoDto.getTruckMemoNumber() + "\n");
            textData.append(new SimpleDateFormat("dd/MM/yyyy  hh:mm:ss a").format(new Date()) + "\n");
            textData.append("--------------------------------\n");
            DpcDistrictDto dpcdistrictdto = dpcTruckMemoDto.getDpcProfileDto().getDpcDistrictDto();
            if (dpcdistrictdto != null) {
                long districtid = dpcTruckMemoDto.getDpcProfileDto().getDpcDistrictDto().getId();
                dpcdistrictdto = DBHelper.getInstance(this).getDistrictName_byid(districtid, dpcdistrictdto);
                textData.append("District  : " + " " + dpcdistrictdto.getName().trim() + "\n");
            }
            DpcTalukDto dpctalukdto = dpcTruckMemoDto.getDpcProfileDto().getDpcTalukDto();
            if (dpctalukdto != null) {
                long talukid = dpcTruckMemoDto.getDpcProfileDto().getDpcTalukDto().getId();
                dpctalukdto = DBHelper.getInstance(this).getTalukName_byid(talukid, dpctalukdto);
                textData.append("Taluk     : " + " " + dpctalukdto.getName() + "\n");
            }
            textData.append("DPC Name  : " + " " + dpc_dto.getName() + "\n");
            textData.append("DPC Code  : " + " " + dpc_dto.getGeneratedCode() + "\n");
            textData.append("--------------------------------\n");
            textData.append(getString(R.string.godowm) + "        " + cap.getName() + "\n");
            textData.append(getString(R.string.print_lorry_number) + "      " + dpcTruckMemoDto.getLorryNumber() + "\n");
            textData.append(getString(R.string.print_no_bags) + "    " + dpcTruckMemoDto.getNumberOfBags() + "\n");
            textData.append(getString(R.string.print_net_qty) + "       " + dpcTruckMemoDto.getNetQuantity() + "\n");
            textData.append(getString(R.string.print_variety) + "       " + paddy_name + "\n");
            textData.append(getString(R.string.print_gunny_type) + "    " + dpcTruckMemoDto.getGunnyCapacity() + "\n");
            textData.append(getString(R.string.print_g_sub_type) + "    " + dpcTruckMemoDto.getConditionOfGunny() + "\n");
            textData.append(getString(R.string.print_moisture) + "      " + dpcTruckMemoDto.getMoistureContent() + "\n");
            textData.append(getString(R.string.specification_) + " " + specification_type + "\n");
            textData.append(getString(R.string.print_tc) + "       " + dpcTruckMemoDto.getTransporterName() + "\n");
        }
        textData.append("\n");
        textData.append("--------------------------------\n\n\n\n");
        return textData.toString();
    }
}
