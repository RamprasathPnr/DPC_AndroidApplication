package com.omneagate.dpc.Activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omneagate.dpc.Adapter.TruckSearchPaddyAdapter;
import com.omneagate.dpc.Model.DpcTruckMemoDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Service.ResReqController;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.NetworkConnection;

import java.util.List;

public class TruckMemoUnSyncedActivity extends BaseActivity implements View.OnClickListener, Handler.Callback, ConnectivityReceiver.ConnectivityReceiverListener {
    ResReqController sync_controller = new ResReqController(this);
    private List<DpcTruckMemoDto> unsync_list;
    private TruckSearchPaddyAdapter adapter;
    private ListView pady_list_view;
    private int syncedlist = 0;
    private Button btn_close;
    private TextView tru_syn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truckmemo_unsynced);
        checkInternetConnection();
        setupview();
        sync_controller.addOutboxHandler(new Handler(this));
    }

    private void setupview() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_truck_memo_unSync));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        setUpPopUpPage();
        btn_close = (Button) findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
        tru_syn=(TextView)findViewById(R.id.tru_syn);
        ((Button) findViewById(R.id.sync_btn)).setOnClickListener(this);
        pady_list_view = (ListView) findViewById(R.id.listViewPaddy);
        unsync_list = DBHelper.getInstance(this).GetUnsynedTruckMemo();
        adapter = new TruckSearchPaddyAdapter(this, unsync_list);
        pady_list_view.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_back:
                onBackPressed();
                break;
            case R.id.sync_btn:
                sync_truckmemo();
                break;
            case R.id.btn_close:
                onBackPressed();
                break;
        }
    }

    private void sync_truckmemo() {
        if (new NetworkConnection(this).isNetworkAvailable()) {
            syncedlist = 0;
            if (unsync_list.size() > 0) {
                for (DpcTruckMemoDto truckMemoDto : unsync_list) {
                    syncTruckMemoToServer(truckMemoDto);
                }
            } else {
                Toast.makeText(this, getResources().getString(R.string.sync_done), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.toast_network_error), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        try {
            switch (msg.what) {
                case ResReqController.TRUCKMEMO_SUCCESS:
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();
                    DpcTruckMemoDto truckMemo_response = gson.fromJson(msg.obj.toString(), DpcTruckMemoDto.class);
                    String truckMemoNumber = truckMemo_response.getTruckMemoNumber();
                    if (truckMemo_response.getStatusCode().equalsIgnoreCase("0")) {
                        DBHelper.getInstance(this).UpdateTruckMemoNumber(truckMemoNumber);
                    }
                    syncedlist += 1;
                    if (syncedlist == unsync_list.size())
                        tru_syn.setVisibility(View.VISIBLE);
                    break;
                case ResReqController.TRUCKMEMO_FAILED:
                    showToastMessage(getString(R.string.toast_server_unreachable), 3000);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void syncTruckMemoToServer(DpcTruckMemoDto dpcTruckMemoDto) {
        new OfflineDataSyncTask().execute(dpcTruckMemoDto);
    }

    private class OfflineDataSyncTask extends AsyncTask<DpcTruckMemoDto, String, String> {
        @Override
        protected String doInBackground(DpcTruckMemoDto... dpcTruckMemoDtos) {
            try {
                String dpctruckmeo = new Gson().toJson(dpcTruckMemoDtos[0]);
                sync_controller.handleMessage_(ResReqController.TRUCKMEMO, dpctruckmeo, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
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
