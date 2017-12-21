package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.omneagate.dpc.Adapter.AssociatedFarmerListAdapter;
import com.omneagate.dpc.Model.AssosiatedFarmersListLocalDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class AssociatedFarmersListActivity extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private RecyclerView recyclerView;
    private DBHelper db;
    private AssociatedFarmerListAdapter associated_farmer_adapter;
    List<AssosiatedFarmersListLocalDto> associated_farmer_list;
    private Button btn_next;
    private String name;
    private String farmer_id;
    private String mobile_number;
    private EditText edTvfarmer_id;
    private EditText edTvfarmer_name;
    private EditText edTvfarmer_mobile_number;
    List<AssosiatedFarmersListLocalDto> SearchList;
    private ImageView btn_search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_associated_farmers_list);
        checkInternetConnection();
        setUpView();
    }

    private void setUpView() {
        setUpPopUpPage();
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_associate_farmer_detailview));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        btn_next = (Button) findViewById(R.id.btn_cancel);
        btn_next.setOnClickListener(this);
        btn_search = (ImageView) findViewById(R.id.btn_search_);
        btn_search.setOnClickListener(this);
        edTvfarmer_name = (EditText) findViewById(R.id.fm_nm_sch);
        edTvfarmer_id = (EditText) findViewById(R.id.farmer_id);
        edTvfarmer_mobile_number = (EditText) findViewById(R.id.fm_mob_scarch);
        SearchList = new ArrayList<AssosiatedFarmersListLocalDto>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_lands);
        recyclerView.setLayoutManager(new LinearLayoutManager(AssociatedFarmersListActivity.this));
        db = new DBHelper(this);
        db.getWritableDatabase();
        TextView tot_farmer = (TextView) findViewById(R.id.tot_farmer);
        int tot_fm = db.getTotalAssociatedFarmer();
        tot_farmer.setText("" + tot_fm);
        associated_farmer_list = db.getAllAssociatedFarmerList();
        associated_farmer_adapter = new AssociatedFarmerListAdapter(this, associated_farmer_list);
        recyclerView.setAdapter(associated_farmer_adapter);

        edTvfarmer_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength2 = edTvfarmer_name.getText().length();
                Log.e("Integer length", "" + textlength2);
                if (textlength2 == 0) {
                    associated_farmer_list.clear();
                    associated_farmer_list = db.getAllAssociatedFarmerList();
                    associated_farmer_adapter = new AssociatedFarmerListAdapter(AssociatedFarmersListActivity.this, associated_farmer_list);
                    recyclerView.setAdapter(associated_farmer_adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edTvfarmer_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength2 = edTvfarmer_name.getText().length();
                Log.e("Integer length", "" + textlength2);
                if (textlength2 == 0) {
                    associated_farmer_list.clear();
                    associated_farmer_list = db.getAllAssociatedFarmerList();
                    associated_farmer_adapter = new AssociatedFarmerListAdapter(AssociatedFarmersListActivity.this, associated_farmer_list);
                    recyclerView.setAdapter(associated_farmer_adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edTvfarmer_mobile_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength2 = edTvfarmer_name.getText().length();
                Log.e("Integer length", "" + textlength2);
                if (textlength2 == 0) {
                    associated_farmer_list.clear();
                    associated_farmer_list = db.getAllAssociatedFarmerList();
                    associated_farmer_adapter = new AssociatedFarmerListAdapter(AssociatedFarmersListActivity.this, associated_farmer_list);
                    recyclerView.setAdapter(associated_farmer_adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
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
                Intent i = new Intent(AssociatedFarmersListActivity.this, OthersActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.btn_cancel:
                Intent cancel_intent = new Intent(AssociatedFarmersListActivity.this, OthersActivity.class);
                startActivity(cancel_intent);
                finish();
                break;

            case R.id.btn_search_:
                search();
                break;
            default:
                break;
        }

    }

    private void search() {
        name = edTvfarmer_name.getText().toString();
        farmer_id = edTvfarmer_id.getText().toString();
        mobile_number = edTvfarmer_mobile_number.getText().toString();

        Log.e("data---", "NAME------" + name + "------" + farmer_id + "----" + mobile_number);

        if (name.equalsIgnoreCase("") && farmer_id.equalsIgnoreCase("") && mobile_number.equalsIgnoreCase("")) {
            showToastMessage(getString(R.string.toast_one_option), 3000);
            return;
        } else if (!mobile_number.equalsIgnoreCase("") && mobile_number.length() < 10) {
            showToastMessage(getString(R.string.toast_mob), 3000);
            return;
        } else {
            SearchList = DBHelper.getInstance(AssociatedFarmersListActivity.this).getSearchedAssociatedFarmerList(name, mobile_number, farmer_id);
            if (SearchList != null) {
                if (SearchList.size() == 0) {
                    showToastMessage(getString(R.string.toast_No_records), 3000);
                    associated_farmer_adapter = new AssociatedFarmerListAdapter(AssociatedFarmersListActivity.this, SearchList);
                    recyclerView.setAdapter(associated_farmer_adapter);
                    return;
                }
                associated_farmer_adapter = new AssociatedFarmerListAdapter(AssociatedFarmersListActivity.this, SearchList);
                recyclerView.setAdapter(associated_farmer_adapter);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent cancel_intent = new Intent(AssociatedFarmersListActivity.this, OthersActivity.class);
        startActivity(cancel_intent);
        finish();
    }
}
