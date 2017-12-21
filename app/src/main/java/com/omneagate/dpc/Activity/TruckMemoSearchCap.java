package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.omneagate.dpc.Adapter.TruckMemoCapAdapter;
import com.omneagate.dpc.Model.DpcTruckMemoDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class TruckMemoSearchCap extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private TextView cap;
    private List<DpcTruckMemoDto> truckMemoList;
    private TextView total_unSynced;
    private TruckMemoCapAdapter adapter;
    private ListView listViewCap;
    private ImageView btn_unsynced;
    private TextView no_records;
    private Button btn_close;
    private int un_synced_count;
    private Long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_memo_search_cap);
        checkInternetConnection();
        setUpView();
    }

    private void setUpView() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_truck_memo_history));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        setUpPopUpPage();
        cap = (TextView) findViewById(R.id.cap);
        btn_unsynced = (ImageView) findViewById(R.id.btn_unsynced);
        btn_unsynced.setOnClickListener(this);
        no_records = (TextView) findViewById(R.id.no_records);
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
        total_unSynced = (TextView) findViewById(R.id.total_unSynced);

        Bundle bdl = getIntent().getExtras();
        id = bdl.getLong("capId");
        Log.e("capId", "capId" + id);
        String capCode = bdl.getString("s_CapCode");

        un_synced_count = DBHelper.getInstance(TruckMemoSearchCap.this).getUnSyncedTruckmemoCountByCap(id);
        total_unSynced.setText("" + un_synced_count);
        total_unSynced.setOnClickListener(this);

        cap.setText(capCode);
        listViewCap = (ListView) findViewById(R.id.listViewCap);
        listViewCap.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DpcTruckMemoDto truck_dto = truckMemoList.get(position);
                Intent duplicate = new Intent(TruckMemoSearchCap.this, TruckMemoDuplicatePrintActivity.class);
                duplicate.putExtra("DpcTruckMemoDto", truck_dto);
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
                    Intent i = new Intent(TruckMemoSearchCap.this, TruckMemoUnSyncedActivity.class);
                    startActivity(i);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent imageback = new Intent(TruckMemoSearchCap.this, TruckMemoHistoryActivity.class);
        startActivity(imageback);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
        truckMemoList = new ArrayList<>();
        truckMemoList = DBHelper.getInstance(TruckMemoSearchCap.this).getTruckMemoByCapCode(id);
        if (truckMemoList.size() == 0) {
            no_records.setVisibility(View.VISIBLE);
            return;
        }
        adapter = new TruckMemoCapAdapter(TruckMemoSearchCap.this, truckMemoList);
        listViewCap.setAdapter(adapter);
    }
}
