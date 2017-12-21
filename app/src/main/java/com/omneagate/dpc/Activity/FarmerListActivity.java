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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omneagate.dpc.Adapter.FarmerListAdapter;
import com.omneagate.dpc.Model.FarmerListLocalDto;
import com.omneagate.dpc.Model.FarmerRegistrationRequestDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 1/8/16.
 */
public class FarmerListActivity extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener, View.OnFocusChangeListener {

    private LinearLayout btn_add_farmer;
    private Button btn_calcel;
    private List<FarmerListLocalDto> farmer_list, nameSearchList, SearchList;
    private FarmerListAdapter farmer_adapter;
    private RecyclerView recyclerView;
    private DBHelper db;
    private TextView tot_farmer;
    private EditText reg_no, nm_sch, mob_scarch;
    private ImageView btn_search;
    private String regnumber;
    private String name;
    private String renumb;
    private String mobnum;
    private String flage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_list);
        checkInternetConnection();
        setUpView();
    }

    private void setUpView() {
        setUpPopUpPage();
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_Farmer_list));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);

        if (Util.farmerLandDetailsDtoList != null) {
            Util.farmerLandDetailsDtoList.clear();
        }


        reg_no = (EditText) findViewById(R.id.reg_no);
        nm_sch = (EditText) findViewById(R.id.nm_sch);
        db = new DBHelper(this);
        db.getWritableDatabase();
        if(!GlobalAppState.language.equals("ta"))
            ((LinearLayout)findViewById(R.id.hint_layout)).setVisibility(View.GONE);
        mob_scarch = (EditText) findViewById(R.id.mob_scarch);
        btn_search = (ImageView) findViewById(R.id.btn_search_);
        btn_search.setOnClickListener(this);

        farmer_list = new ArrayList<FarmerListLocalDto>();
        nameSearchList = new ArrayList<FarmerListLocalDto>();
        SearchList = new ArrayList<FarmerListLocalDto>();
        farmer_list = db.getFarmerList();
        farmer_adapter = new FarmerListAdapter(this, farmer_list);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_lands);
        recyclerView.setLayoutManager(new LinearLayoutManager(FarmerListActivity.this));
        recyclerView.setAdapter(farmer_adapter);
        btn_add_farmer = (LinearLayout) findViewById(R.id.btn_add_farmer);
        btn_add_farmer.setOnClickListener(this);
        btn_calcel = (Button) findViewById(R.id.btn_cancel);
        btn_calcel.setOnClickListener(this);
        tot_farmer = (TextView) findViewById(R.id.tot_farmer);
        int tot_fm = db.getTotalFarmer();
        tot_farmer.setText("" + tot_fm);

        nm_sch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                flage = "name";
                Log.e("flage", flage);
                Integer textlength2 = nm_sch.getText().length();
                Log.e("Integer length", "" + textlength2);
                if (textlength2 == 0) {
                    farmer_list.clear();
                    farmer_list = db.getFarmerList();
                    farmer_adapter = new FarmerListAdapter(FarmerListActivity.this, farmer_list);
                    recyclerView.setAdapter(farmer_adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        reg_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                flage = "regnumber";

                Integer textlength2 = nm_sch.getText().length();
                if (textlength2 == 0) {
                    farmer_list.clear();
                    farmer_list = db.getFarmerList();
                    farmer_adapter = new FarmerListAdapter(FarmerListActivity.this, farmer_list);
                    recyclerView.setAdapter(farmer_adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mob_scarch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                flage = "mob";
                Integer textlength2 = nm_sch.getText().length();

                if (textlength2 == 0) {
                    farmer_list.clear();
                    farmer_list = db.getFarmerList();
                    farmer_adapter = new FarmerListAdapter(FarmerListActivity.this, farmer_list);
                    recyclerView.setAdapter(farmer_adapter);
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
                Intent ii_ = new Intent(FarmerListActivity.this, DashBoardActivity.class);
                startActivity(ii_);
                finish();
                break;
            case R.id.btn_add_farmer:
                Intent i = new Intent(FarmerListActivity.this, FarmerRegistrationActivity.class);
                FarmerRegistrationRequestDto farmerRegistrationRequestDto = new FarmerRegistrationRequestDto();
                i.putExtra("FarmerRegistrationRequest", farmerRegistrationRequestDto);
                startActivity(i);
                finish();
                break;
            case R.id.btn_cancel:
                Intent i_ = new Intent(FarmerListActivity.this, DashBoardActivity.class);
                startActivity(i_);
                finish();
                break;
            case R.id.btn_search_:
                name = nm_sch.getText().toString();
                renumb = reg_no.getText().toString();
                mobnum = mob_scarch.getText().toString();
                Log.e("datas---", "NAME------" + name + "------" + renumb + "----" + mobnum);
                if (name.equalsIgnoreCase("") && renumb.equalsIgnoreCase("") && mobnum.equalsIgnoreCase("")) {
                    showToastMessage(getString(R.string.toast_one_option), 3000);
                    return;
                } else if (!mobnum.equalsIgnoreCase("") && mobnum.length() < 10) {
                    showToastMessage(getString(R.string.toast_mob), 3000);
                    return;
                } else {
                    SearchList = DBHelper.getInstance(FarmerListActivity.this).getFarmerListbyFarmerName(name, mobnum, renumb);
                    if (SearchList != null) {
                        if (SearchList.size() == 0) {
                            showToastMessage(getString(R.string.toast_No_records), 3000);
                            farmer_adapter = new FarmerListAdapter(FarmerListActivity.this, SearchList);
                            recyclerView.setAdapter(farmer_adapter);
                            return;
                        }
                        farmer_adapter = new FarmerListAdapter(FarmerListActivity.this, SearchList);
                        recyclerView.setAdapter(farmer_adapter);
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent ii_ = new Intent(FarmerListActivity.this, DashBoardActivity.class);
        startActivity(ii_);
        finish();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }
}
