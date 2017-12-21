package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
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
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.BlueToothPrint;
import com.omneagate.dpc.Utility.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TruckMemoDuplicatePrintActivity extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {
    private DpcTruckMemoDto dpcTruckMemoDto;
    private Button btn_close, btn_print;
    private DpcCapDto cap;
    private DPCProfileDto dpc_dto;
    private String paddy_name;
    private PaddyGradeDto grade_dto;
    private String activity_name;
    private String specification_type;
    private String formatedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truckmemo_history_detail);
        checkInternetConnection();
        dpcTruckMemoDto = (DpcTruckMemoDto) getIntent().getSerializableExtra("DpcTruckMemoDto");
        setview(dpcTruckMemoDto);
    }


    private void setview(DpcTruckMemoDto dpcTruckMemoDto) {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.TruckMemo_details));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(TruckMemoDuplicatePrintActivity.this);
        setUpPopUpPage();
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_print = (Button) findViewById(R.id.btn_print);
        btn_close.setOnClickListener(this);
        btn_print.setOnClickListener(this);
        activity_name = getIntent().getStringExtra("activity");


        String oldFormat = "yyyy-MM-dd HH:mm:ss";
        String newFormat = "dd-MM-yyyy hh:mm:ss a";
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(dpcTruckMemoDto.getTxnDateTime());
            SimpleDateFormat timeFormat = new SimpleDateFormat(newFormat);
            formatedDate = timeFormat.format(myDate);
            Log.e("truckMemoDate", "output date " + formatedDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        Log.e("FORMAT", "" + dpcTruckMemoDto.getTxnDateTime());

        ((TextView) findViewById(R.id.tv_truck_number)).setText("" + dpcTruckMemoDto.getTruckMemoNumber());
        ((TextView) findViewById(R.id.tv_datetime)).setText("" + formatedDate);
        long id = dpcTruckMemoDto.getDpcProfileDto().getId();
        dpc_dto = DBHelper.getInstance(TruckMemoDuplicatePrintActivity.this).getDpcProfileById(id);
        ((TextView) findViewById(R.id.tv_dpc_code_his)).setText("" + dpc_dto.getGeneratedCode());
        long cap_id = dpcTruckMemoDto.getDpcCapDto().getId();
        cap = DBHelper.getInstance(TruckMemoDuplicatePrintActivity.this).getCapName(cap_id);
        long paddy_id = dpcTruckMemoDto.getPaddyCategoryDto().getId();
        paddy_name = DBHelper.getInstance(TruckMemoDuplicatePrintActivity.this).getpaddy(paddy_id);
        long grad_id = dpcTruckMemoDto.getPaddyGradeDto().getId();
        grade_dto = DBHelper.getInstance(TruckMemoDuplicatePrintActivity.this).getGradeById(grad_id);

        if (dpcTruckMemoDto != null) {
            long specification_id = dpcTruckMemoDto.getDpcSpecificationDto().getId();
            specification_type = DBHelper.getInstance(TruckMemoDuplicatePrintActivity.this).getSpecificationById(specification_id);
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
        ((TextView) findViewById(R.id.txt_net_quan)).setText("" + dpcTruckMemoDto.getNetQuantity());
        ((TextView) findViewById(R.id.specification)).setText(specification_type);

        ((TextView) findViewById(R.id.txt_Transporter_Name)).setText("" + dpcTruckMemoDto.getTransporterName());
        ((TextView) findViewById(R.id.txt_gunnyCapacity)).setText("" + dpcTruckMemoDto.getGunnyCapacity());
        ((TextView) findViewById(R.id.txt_moisture_content)).setText("" + dpcTruckMemoDto.getMoistureContent());
        ((TextView) findViewById(R.id.txt_transport_type)).setText("" + dpcTruckMemoDto.getTransportType());
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

            case R.id.btn_print:
                callprint();
                break;

            case R.id.btn_close:
                onBackPressed();
                break;
        }
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
