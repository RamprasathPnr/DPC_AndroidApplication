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

import com.omneagate.dpc.Adapter.ProcurementSearchByPaddyAdapter;
import com.omneagate.dpc.Adapter.TruckSearchPaddyAdapter;
import com.omneagate.dpc.Model.DPCProcurementDto;
import com.omneagate.dpc.Model.DpcTruckMemoDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class ProcurementSearchByPaddy extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private long paddyCategoryId;
    private String paddyCategoryName;
    private TextView category;
    private List<DPCProcurementDto> procurementList;
    private ListView pady_list_view;
    private ProcurementSearchByPaddyAdapter adapter;
    private Button btn_close;
    private TextView no_records;
    private TextView total_unSynced;
    private ImageView btn_unsynced;
    private int un_synced_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurement_search_by_paddy);
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
        no_records = (TextView) findViewById(R.id.no_records);
        paddyCategoryId = getIntent().getLongExtra("paddyCategoryId", 0);
        paddyCategoryName = getIntent().getStringExtra("paddyCategoryName");
        category = (TextView) findViewById(R.id.category);
        category.setText(paddyCategoryName);
        pady_list_view = (ListView) findViewById(R.id.listViewPaddy);
        un_synced_count = DBHelper.getInstance(ProcurementSearchByPaddy.this).getUnSyncedProcurementCountByPaddy(paddyCategoryId);
        total_unSynced = (TextView) findViewById(R.id.total_unSynced);
        total_unSynced.setText("" + un_synced_count);



        pady_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DPCProcurementDto procurement_dto = adapter.procurementList.get(position);
                Intent duplicate = new Intent(ProcurementSearchByPaddy.this, ProcurementDuplicatePrintActivity.class);
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
                    Intent i = new Intent(ProcurementSearchByPaddy.this, ProcurementUnSyncedActivity.class);
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
        procurementList = DBHelper.getInstance(ProcurementSearchByPaddy.this).getProcurementByPaddy(paddyCategoryId);
        if (procurementList.size() == 0) {
            no_records.setVisibility(View.VISIBLE);
            return;
        }
        adapter = new ProcurementSearchByPaddyAdapter(ProcurementSearchByPaddy.this, procurementList);
        pady_list_view.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent ii_ = new Intent(ProcurementSearchByPaddy.this, ProcurementHistory.class);
        startActivity(ii_);
        finish();
    }
}
