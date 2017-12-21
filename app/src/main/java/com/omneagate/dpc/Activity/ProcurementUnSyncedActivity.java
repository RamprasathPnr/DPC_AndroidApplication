package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.omneagate.dpc.Adapter.ProcurementSearchByPaddyAdapter;
import com.omneagate.dpc.Model.DPCProcurementDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Service.ResReqController;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.NetworkConnection;

import java.util.List;

public class ProcurementUnSyncedActivity extends BaseActivity implements View.OnClickListener, Handler.Callback,ConnectivityReceiver.ConnectivityReceiverListener  {

    ResReqController sync_controller = new ResReqController(this);
    private ListView unsynced_list_view;
    private List<DPCProcurementDto> procurementList;
    private ProcurementSearchByPaddyAdapter adapter;
    private int syncedlist = 0;
    private Button btn_close;
    private TextView pro_syn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_synced_procurement);
        checkInternetConnection();
        sync_controller.addOutboxHandler(new Handler(this));
        setupview();
    }


    private void setupview() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.un_synced_Procurement));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        setUpPopUpPage();
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
        pro_syn=(TextView)findViewById(R.id.pro_syn);
        ((Button) findViewById(R.id.sync_btn)).setOnClickListener(this);
        unsynced_list_view = (ListView) findViewById(R.id.listViewUnsynced);
        if(procurementList!=null){
            procurementList.clear();
        }
        procurementList = DBHelper.getInstance(ProcurementUnSyncedActivity.this).getProcurement();
        adapter = new ProcurementSearchByPaddyAdapter(this, procurementList);
        unsynced_list_view.setAdapter(adapter);
        unsynced_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DPCProcurementDto procurement_dto = procurementList.get(position);
                Intent duplicate = new Intent(ProcurementUnSyncedActivity.this, ProcurementDuplicatePrintActivity.class);
                duplicate.putExtra("procurement_dto", procurement_dto);
                startActivity(duplicate);
                finish();
            }
        });
    }



    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.image_back:
                onBackPressed();
                break;
            case R.id.sync_btn:
                sync_procurement();
                break;
            case R.id.btn_close:
                onBackPressed();
                break;
        }

    }

    private void sync_procurement() {

        if (new NetworkConnection(this).isNetworkAvailable()) {
            syncedlist = 0;
            if (procurementList.size() > 0) {
                for (DPCProcurementDto procurementDto : procurementList) {
                    syncTruckMemoToServer(procurementDto);
                }
            } else {
                Toast.makeText(this, getResources().getString(R.string.sync_done), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.toast_network_error), Toast.LENGTH_SHORT).show();
        }
    }

    private void syncTruckMemoToServer(DPCProcurementDto procurementDto) {
        new OfflineDataSyncTask().execute(procurementDto);
    }


    private class OfflineDataSyncTask extends AsyncTask<DPCProcurementDto, String, String> {
        @Override
        protected String doInBackground(DPCProcurementDto... procurementDto) {
            try {
                String procurementDto_ = new Gson().toJson(procurementDto[0]);
                sync_controller.handleMessage_(ResReqController.PROCUREMENT, procurementDto_, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
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
                        DBHelper.getInstance(this).UpdateProcurementReceiptNumber(ReceiptNumber);
                    } else if (pro_res_dto.getStatusCode().equalsIgnoreCase("1600058")) {
                        DBHelper.getInstance(this).UpdateProcurementReceiptNumber(ReceiptNumber);
                        showToastMessage(getString(R.string.toast_duplicate_receipt_number), 3000);
                    }
                    syncedlist += 1;
                    if (syncedlist == procurementList.size())
                        pro_syn.setVisibility(View.VISIBLE);
                    return true;
                case ResReqController.PROCUREMENT_FAILED:
                    showToastMessage(getString(R.string.toast_server_unreachable), 3000);
                    Log.d("Error", "" + msg.obj.toString());
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        set_online_offline(isConnected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }
}
