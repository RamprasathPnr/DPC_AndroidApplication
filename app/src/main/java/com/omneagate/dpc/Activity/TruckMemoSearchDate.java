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

import com.omneagate.dpc.Adapter.TruckMemoNumberAdapter;
import com.omneagate.dpc.Model.DpcTruckMemoDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TruckMemoSearchDate extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private String truckMemoDate;
    private TextView date;
    private ListView listViewDate;
    private List<DpcTruckMemoDto> truckMemoList;
    private TextView total_unSynced;
    private TruckMemoNumberAdapter adapter;
    private ImageView btn_unsynced;
    private TextView no_records;
    private Button btn_close;
    private int un_synced_count;
    private String formatedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_search_by_number);
        checkInternetConnection();
        setUpView();
    }


    private void setUpView() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_truck_memo_history));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        setUpPopUpPage();
        date = (TextView) findViewById(R.id.number);
        truckMemoDate = getIntent().getStringExtra("truck_memo_date");
        btn_unsynced = (ImageView) findViewById(R.id.btn_unsynced);
        btn_unsynced.setOnClickListener(this);
        no_records = (TextView) findViewById(R.id.no_records);
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);


        String oldFormat = "dd-MM-yyyy";
        String newFormat = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(truckMemoDate);
            SimpleDateFormat timeFormat = new SimpleDateFormat(newFormat);
            formatedDate = timeFormat.format(myDate);
            Log.e("truckMemoDate", "output date " + formatedDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        date.setText(truckMemoDate);
        listViewDate = (ListView) findViewById(R.id.listViewNumber);
        Log.e("truckMemoListDate", "Date List is" + truckMemoList);

        un_synced_count = DBHelper.getInstance(TruckMemoSearchDate.this).getUnSyncedTruckmemoCountByDate(formatedDate);
        Log.e("un_synced_count", "" + un_synced_count);
        total_unSynced = (TextView) findViewById(R.id.total_unSynced);
        total_unSynced.setText("" + un_synced_count);
        total_unSynced.setOnClickListener(this);
        listViewDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DpcTruckMemoDto truck_dto = truckMemoList.get(position);
                Intent duplicate = new Intent(TruckMemoSearchDate.this, TruckMemoDuplicatePrintActivity.class);
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
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
        truckMemoList = new ArrayList<>();
        truckMemoList = DBHelper.getInstance(TruckMemoSearchDate.this).getTruckMemoByDate(formatedDate);
        if (truckMemoList.size() == 0) {
            no_records.setVisibility(View.VISIBLE);
            return;
        }
        adapter = new TruckMemoNumberAdapter(TruckMemoSearchDate.this, truckMemoList);
        listViewDate.setAdapter(adapter);
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
                    Intent j = new Intent(TruckMemoSearchDate.this, TruckMemoUnSyncedActivity.class);
                    startActivity(j);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent imageback = new Intent(TruckMemoSearchDate.this, TruckMemoHistoryActivity.class);
        startActivity(imageback);
        finish();
    }
}
