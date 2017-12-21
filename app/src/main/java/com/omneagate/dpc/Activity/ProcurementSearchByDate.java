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
import com.omneagate.dpc.Adapter.TruckMemoNumberAdapter;
import com.omneagate.dpc.Model.DPCProcurementDto;
import com.omneagate.dpc.Model.DpcTruckMemoDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProcurementSearchByDate extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private TextView date;
    private String procurementDate;
    private ListView listViewDate;
    private List<DPCProcurementDto> procurementList;
    private TextView total_unSynced;
    private ProcurementSearchByDateAdapter adapter;
    private Button btn_close;
    private TextView no_records;
    private ImageView btn_unsynced;
    private int un_synced_count;
    private String formatedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurement_search_by_date);
        checkInternetConnection();
        setUpView();
    }


    private void setUpView() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_procurements));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        setUpPopUpPage();
        btn_unsynced = (ImageView) findViewById(R.id.btn_unsynced);
        btn_unsynced.setOnClickListener(this);
        date = (TextView) findViewById(R.id.pro_date_time);
        no_records = (TextView) findViewById(R.id.no_records);
        procurementDate = getIntent().getStringExtra("procurement_date");
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
        formatedDate = "";
        String oldFormat = "dd-mm-yyyy";
        String newFormat = "yyyy-mm-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(procurementDate);
            SimpleDateFormat timeFormat = new SimpleDateFormat(newFormat);
            formatedDate = timeFormat.format(myDate);
            Log.e("truckMemoDate", "output date " + formatedDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        date.setText(procurementDate);
        listViewDate = (ListView) findViewById(R.id.listViewDate);

        un_synced_count = DBHelper.getInstance(ProcurementSearchByDate.this).getUnSyncedProcurementCountByDate(formatedDate);
        total_unSynced = (TextView) findViewById(R.id.total_unSynced);
        total_unSynced.setText("" + un_synced_count);

        listViewDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DPCProcurementDto procurement_dto =  adapter.procurementList.get(position);

                Intent duplicate = new Intent(ProcurementSearchByDate.this, ProcurementDuplicatePrintActivity.class);
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
                    Intent i = new Intent(ProcurementSearchByDate.this, ProcurementUnSyncedActivity.class);
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
        procurementList = DBHelper.getInstance(ProcurementSearchByDate.this).getProcurementByDate(formatedDate);
        if(procurementList.size()==0){
            no_records.setVisibility(View.VISIBLE);
            return;
        }
        adapter = new ProcurementSearchByDateAdapter(ProcurementSearchByDate.this, procurementList);
        listViewDate.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Intent ii_ = new Intent(ProcurementSearchByDate.this, ProcurementHistory.class);
        startActivity(ii_);
        finish();
    }
}
