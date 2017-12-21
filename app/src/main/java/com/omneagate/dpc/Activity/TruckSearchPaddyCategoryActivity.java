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

import com.omneagate.dpc.Adapter.TruckSearchPaddyAdapter;
import com.omneagate.dpc.Model.DpcTruckMemoDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class TruckSearchPaddyCategoryActivity extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private ListView pady_list_view;
    private Button btn_close;
    private TruckSearchPaddyAdapter adapter;
    private List<DpcTruckMemoDto> truckMemoList;
    private long paddyCategoryId;
    private TextView category, total_unSynced;
    private String paddyCategoryName;
    private ImageView btn_unsynced;
    private TextView no_records;
    private int un_synced_count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_search_paddy_category);
        checkInternetConnection();
        setUpView();
    }


    private void setUpView() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_truck_memo_history));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        setUpPopUpPage();
        category = (TextView) findViewById(R.id.category);
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
        btn_unsynced = (ImageView) findViewById(R.id.btn_unsynced);
        btn_unsynced.setOnClickListener(this);

        no_records = (TextView) findViewById(R.id.no_records);
        paddyCategoryId = getIntent().getLongExtra("paddyCategoryId", 0);
        paddyCategoryName = getIntent().getStringExtra("paddyCategoryName");
        category.setText(paddyCategoryName);

        un_synced_count = DBHelper.getInstance(TruckSearchPaddyCategoryActivity.this).getUnSyncedTruckmemoCountByPaddy(paddyCategoryId);
        total_unSynced = (TextView) findViewById(R.id.total_unSynced);
        total_unSynced.setText("" + un_synced_count);
        total_unSynced.setOnClickListener(this);


        pady_list_view = (ListView) findViewById(R.id.listViewPaddy);

        pady_list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DpcTruckMemoDto truck_dto = truckMemoList.get(position);
                Intent duplicate = new Intent(TruckSearchPaddyCategoryActivity.this, TruckMemoDuplicatePrintActivity.class);
                duplicate.putExtra("DpcTruckMemoDto", truck_dto);
                startActivity(duplicate);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        truckMemoList = new ArrayList<>();
        truckMemoList = DBHelper.getInstance(TruckSearchPaddyCategoryActivity.this).getTruckMemoPaddy(paddyCategoryId);
        if (truckMemoList.size() == 0) {
            no_records.setVisibility(View.VISIBLE);
            return;
        }
        adapter = new TruckSearchPaddyAdapter(TruckSearchPaddyCategoryActivity.this, truckMemoList);
        pady_list_view.setAdapter(adapter);
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

            case R.id.btn_close:
                onBackPressed();
                break;

            case R.id.btn_unsynced:
                if (un_synced_count > 0) {
                    Intent i = new Intent(TruckSearchPaddyCategoryActivity.this, TruckMemoUnSyncedActivity.class);
                    startActivity(i);
                }
                break;

            default:
                Log.e("default", "default");
                break;
        }

    }

    @Override
    public void onBackPressed() {
        Intent imageback = new Intent(TruckSearchPaddyCategoryActivity.this, TruckMemoHistoryActivity.class);
        startActivity(imageback);
        finish();
    }


}
