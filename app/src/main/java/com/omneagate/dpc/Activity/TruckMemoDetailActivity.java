package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omneagate.dpc.Activity.Dialog.SuccessTruckMemoDialog;
import com.omneagate.dpc.Model.DPCProfileDto;
import com.omneagate.dpc.Model.DpcDistrictDto;
import com.omneagate.dpc.Model.DpcTalukDto;
import com.omneagate.dpc.Model.DpcTruckMemoDto;
import com.omneagate.dpc.Model.ResponseDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Service.ResReqController;
import com.omneagate.dpc.Utility.BlueToothPrint;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.LoginData;
import com.omneagate.dpc.Utility.NetworkConnection;
import com.omneagate.dpc.Utility.SessionId;
import com.omneagate.dpc.Utility.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by root on 22/9/16.
 */
public class TruckMemoDetailActivity extends BaseActivity implements View.OnClickListener, Handler.Callback, ConnectivityReceiver.ConnectivityReceiverListener {
    private DpcTruckMemoDto dpcTruckMemoDto;
    final ResReqController controller = new ResReqController(this);
    private NetworkConnection networkConnection;
    private Button Btn_edit, btn_next;
    private DPCProfileDto dpc_profiledto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_memo_detail);
        checkInternetConnection();
        networkConnection = new NetworkConnection(this);
        controller.addOutboxHandler(new Handler(this));
        SetView();
    }


    private void SetView() {
        try {
            TextView title = (TextView) findViewById(R.id.title);
            title.setText(getString(R.string.TruckMemo));
            ImageView btn_back = (ImageView) findViewById(R.id.image_back);
            btn_back.setOnClickListener(this);
            setUpPopUpPage();
            dpcTruckMemoDto = new DpcTruckMemoDto();
            dpcTruckMemoDto = (DpcTruckMemoDto) getIntent().getSerializableExtra("TruckMemoDto");
            ResponseDto responsedata = LoginData.getInstance().getResponseData();
            dpc_profiledto = responsedata.getUserDetailDto().getDpcProfileDto();
            dpcTruckMemoDto.setDpcProfileDto(dpc_profiledto);
            Btn_edit = (Button) findViewById(R.id.btn_edit);
            btn_next = (Button) findViewById(R.id.btn_next);
            Btn_edit.setOnClickListener(this);

            btn_next.setOnClickListener(this);
            if (GlobalAppState.language.equals("ta")) {
                ((TextView) findViewById(R.id.txt_paddyCategory)).setText("" + dpcTruckMemoDto.getPaddyCategoryDto().getLname());
                ((TextView) findViewById(R.id.txt_grade)).setText("" + dpcTruckMemoDto.getPaddyGradeDto().getLname());
                ((TextView) findViewById(R.id.tv_dpc_code)).setText("" + dpcTruckMemoDto.getDpcProfileDto().getGeneratedCode());
                ((TextView) findViewById(R.id.truck_dpc_name)).setText("" + dpcTruckMemoDto.getDpcProfileDto().getLname());
            } else {
                ((TextView) findViewById(R.id.txt_paddyCategory)).setText("" + dpcTruckMemoDto.getPaddyCategoryDto().getName());
                ((TextView) findViewById(R.id.txt_grade)).setText("" + dpcTruckMemoDto.getPaddyGradeDto().getName());
                ((TextView) findViewById(R.id.tv_dpc_code)).setText("" + dpcTruckMemoDto.getDpcProfileDto().getGeneratedCode());
                ((TextView) findViewById(R.id.truck_dpc_name)).setText("" + dpcTruckMemoDto.getDpcProfileDto().getName());
            }
            ((TextView) findViewById(R.id.txt_Number_Bags)).setText("" + dpcTruckMemoDto.getNumberOfBags());
            ((TextView) findViewById(R.id.specification_truck)).setText("" + dpcTruckMemoDto.getDpcSpecificationDto().getSpecificationType());
            ((TextView) findViewById(R.id.txt_Lorry_number)).setText("" + dpcTruckMemoDto.getLorryNumber());
            ((TextView) findViewById(R.id.txt_ConditionofGunny)).setText("" + dpcTruckMemoDto.getConditionOfGunny());
            ((TextView) findViewById(R.id.txt_net_quan)).setText("" + dpcTruckMemoDto.getNetQuantity());
            ((TextView) findViewById(R.id.txt_Transporter_Name)).setText("" + dpcTruckMemoDto.getTransporterName());
            ((TextView) findViewById(R.id.txt_gunnyCapacity)).setText("" + dpcTruckMemoDto.getGunnyCapacity());
            ((TextView) findViewById(R.id.txt_moisture_content)).setText("" + dpcTruckMemoDto.getMoistureContent());
            ((TextView) findViewById(R.id.txt_transport_type)).setText("" + dpcTruckMemoDto.getTransportType());
            ((TextView) findViewById(R.id.txt_cap_code)).setText("" + dpcTruckMemoDto.getDpcCapDto().getGeneratedCode() + "/" + dpcTruckMemoDto.getDpcCapDto().getName());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("TruckMemoDetail", "Exception while getting" + e.toString());
        }
    }

    private void setTruckMemoNumer() {
        try {
            if (dpcTruckMemoDto.getTruckMemoNumber() == null) {
                String truckMemoNumber = Util.GetTruckMemoNumber(this);
                if (truckMemoNumber != null) {
                    dpcTruckMemoDto.setTruckMemoNumber(truckMemoNumber);
                    dpcTruckMemoDto.setCreatedBy(SessionId.getInstance().getUserId());
                    dpcTruckMemoDto.setModifiedBy(SessionId.getInstance().getUserId());
                    SimpleDateFormat regDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    dpcTruckMemoDto.setTxnDateTime(regDate.format(new Date()));
                    DBHelper.getInstance(this).InsertDPcTruckMemo(dpcTruckMemoDto);
                } else {
                    Toast.makeText(TruckMemoDetailActivity.this, "Internal Error", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        set_online_offline(isConnected);
    }

    @Override
    public boolean handleMessage(Message msg) {
        try {
            switch (msg.what) {
                case ResReqController.TRUCKMEMO_SUCCESS:
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    DpcTruckMemoDto truckMemo_response = gson.fromJson(msg.obj.toString(), DpcTruckMemoDto.class);
                    String Truck_Memo_Number = truckMemo_response.getTruckMemoNumber();
                    Log.e("truck memo number", Truck_Memo_Number);
                    new SuccessTruckMemoDialog(this, Truck_Memo_Number).show();
                    callprint();
                    break;
                case ResReqController.TRUCKMEMO_FAILED:

                    btn_next.setVisibility(View.VISIBLE);
                    showToastMessage(getString(R.string.toast_server_unreachable), 3000);
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        Intent imageback = new Intent(TruckMemoDetailActivity.this, TruckMemoActivity.class);
        imageback.putExtra("TruckMemoDto", dpcTruckMemoDto);
        startActivity(imageback);
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.image_back:
                onBackPressed();
                break;
            case R.id.btn_edit:
                onBackPressed();
                break;
            case R.id.btn_next:
                btn_next.setVisibility(View.GONE);
                if (networkConnection.isNetworkAvailable()) {
                    dpcTruckMemoDto.setSyncStatus("B");
                    setTruckMemoNumer();
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    String truckmemo = gson.toJson(dpcTruckMemoDto);
                    Log.e("TruckMemo", "Request : " + truckmemo);
                    controller.handleMessage_(ResReqController.TRUCKMEMO, truckmemo, null);
                } else {
                    dpcTruckMemoDto.setSyncStatus("A");
                    setTruckMemoNumer();
                    new SuccessTruckMemoDialog(TruckMemoDetailActivity.this, dpcTruckMemoDto.getTruckMemoNumber()).show();
                    callprint();
                }
                break;
            default:
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
        textData.append("             TNCSC\n");
        textData.append("          Truck Memo\n");
        textData.append("--------------------------------\n");
        if (dpcTruckMemoDto != null) {
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
            textData.append("DPC Name  : " + " " + dpcTruckMemoDto.getDpcProfileDto().getName() + "\n");
            textData.append("DPC Code  : " + " " + dpcTruckMemoDto.getDpcProfileDto().getGeneratedCode() + "\n");
            textData.append("--------------------------------\n");
            textData.append(getString(R.string.godowm) + "        " + dpcTruckMemoDto.getDpcCapDto().getName() + "\n");
            textData.append(getString(R.string.print_lorry_number) + "      " + dpcTruckMemoDto.getLorryNumber() + "\n");
            textData.append(getString(R.string.print_no_bags) + "    " + dpcTruckMemoDto.getNumberOfBags() + "\n");
            textData.append(getString(R.string.print_net_qty) + "       " + dpcTruckMemoDto.getNetQuantity() + "\n");
            textData.append(getString(R.string.print_variety) + "       " + dpcTruckMemoDto.getPaddyCategoryDto().getName() + "\n");
            textData.append(getString(R.string.print_gunny_type) + "    " + dpcTruckMemoDto.getGunnyCapacity() + "\n");
            textData.append(getString(R.string.print_g_sub_type) + "    " + dpcTruckMemoDto.getConditionOfGunny() + "\n");
            textData.append(getString(R.string.print_moisture) + "      " + dpcTruckMemoDto.getMoistureContent() + "\n");
            textData.append(getString(R.string.specification_) + " " + dpcTruckMemoDto.getDpcSpecificationDto().getSpecificationType() + "\n");
            textData.append(getString(R.string.print_tc) + "       " + dpcTruckMemoDto.getTransporterName() + "\n");
        }
        textData.append("\n");
        textData.append("--------------------------------\n\n\n\n");
        return textData.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }
}
