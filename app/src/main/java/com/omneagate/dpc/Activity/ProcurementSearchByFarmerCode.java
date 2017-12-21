package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.omneagate.dpc.Adapter.ProcurementSearchByDateAdapter;
import com.omneagate.dpc.Adapter.ProcurementSearchByFarmerCodeAdapter;
import com.omneagate.dpc.Model.DPCProcurementDto;
import com.omneagate.dpc.Model.FarmerRegistrationDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ProcurementSearchByFarmerCode extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private String farmer_code;
    private List<DPCProcurementDto> procurementList;
    private ListView listViewFarmerCode;
    private TextView total_unSynced, farmer_Code_Tv, no_records;
    private ProcurementSearchByFarmerCodeAdapter adapter;
    private Button btn_close;
    private long farmer_id;
    private ImageView btn_unsynced;
    private int un_synced_count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurement_search_by_farmer_code);
        checkInternetConnection();
        setUpView();
    }


    private void setUpView() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_procurements));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        setUpPopUpPage();
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
        btn_unsynced = (ImageView) findViewById(R.id.btn_unsynced);
        btn_unsynced.setOnClickListener(this);
        farmer_Code_Tv = (TextView) findViewById(R.id.farmer_code);
        farmer_code = getIntent().getStringExtra("farmer_code");
        farmer_Code_Tv.setText(farmer_code);
        listViewFarmerCode = (ListView) findViewById(R.id.listViewFarmerCode);
        no_records = (TextView) findViewById(R.id.no_records);

        FarmerRegistrationDto farmer_dto = DBHelper.getInstance(ProcurementSearchByFarmerCode.this).getAssociatedFarmerId(farmer_code);
        if (farmer_dto != null && farmer_dto.getId() != null) {
            farmer_id = farmer_dto.getId();
        } else {
            no_records.setVisibility(View.VISIBLE);
            return;
        }
        un_synced_count = DBHelper.getInstance(ProcurementSearchByFarmerCode.this).getUnSyncedProcurementCountByCode(farmer_id);
        total_unSynced = (TextView) findViewById(R.id.total_unSynced);
        total_unSynced.setText("" + un_synced_count);


        listViewFarmerCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DPCProcurementDto procurement_dto = adapter.procurementList.get(position);
                Intent duplicate = new Intent(ProcurementSearchByFarmerCode.this, ProcurementDuplicatePrintActivity.class);
                duplicate.putExtra("procurement_dto", procurement_dto);
                startActivity(duplicate);
            }
        });
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
            case R.id.btn_close:
                onBackPressed();
                break;
            case R.id.btn_unsynced:
                if (un_synced_count > 0) {
                    Intent i = new Intent(ProcurementSearchByFarmerCode.this, ProcurementUnSyncedActivity.class);
                    startActivity(i);
                }
                break;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);

        procurementList = new ArrayList<>();
        if (procurementList != null) {
            procurementList.clear();
        }
        procurementList = DBHelper.getInstance(ProcurementSearchByFarmerCode.this).getProcurementById(farmer_id);
        adapter = new ProcurementSearchByFarmerCodeAdapter(ProcurementSearchByFarmerCode.this, procurementList);
        listViewFarmerCode.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent ii_ = new Intent(ProcurementSearchByFarmerCode.this, ProcurementHistory.class);
        startActivity(ii_);
        finish();
    }
}
