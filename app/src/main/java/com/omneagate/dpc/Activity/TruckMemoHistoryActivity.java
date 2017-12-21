package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omneagate.dpc.Activity.Dialog.DialogTruckMemoDateSelection;
import com.omneagate.dpc.Adapter.CapTruckAdapter;
import com.omneagate.dpc.Adapter.TruckPaddyCategoryAdapter;
import com.omneagate.dpc.Model.DpcCapDto;
import com.omneagate.dpc.Model.PaddyCategoryDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.NoDefaultSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TruckMemoHistoryActivity extends BaseActivity implements View.OnClickListener, Handler.Callback, ConnectivityReceiver.ConnectivityReceiverListener {
    private Button btn_next, btn_clear;
    private NoDefaultSpinner spinner_paddycategory, spinner_CapCodeName;
    private List<PaddyCategoryDto> paddy_category_list;
    private TruckPaddyCategoryAdapter paddy_category_adapter;
    private CapTruckAdapter cap_adapter;
    private String paddyCategoryName;
    private long paddyCategoryId;
    private List<DpcCapDto> Cap_List;
    private String s_CapCode;
    private String s_CapName;
    private Long l_CapId;
    private EditText edTv_truck_memo;
    private LinearLayout search_by_date;
    private TextView tv_date;
    public static boolean IsClickedDate;
    private String formatedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_memo_history);
        checkInternetConnection();
        setUpView();
    }


    private void setUpView() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_truck_memo_history));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        setUpPopUpPage();
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        edTv_truck_memo = (EditText) findViewById(R.id.edTv_truck_memo);
        search_by_date = (LinearLayout) findViewById(R.id.search_by_date);
        search_by_date.setOnClickListener(this);
        tv_date = (TextView) findViewById(R.id.tv_date);
        paddy_category_list = new ArrayList<PaddyCategoryDto>();
        Cap_List = new ArrayList<DpcCapDto>();
        spinner_paddycategory = (NoDefaultSpinner) findViewById(R.id.spinner_paddy_category_truck_history);
        spinner_CapCodeName = (NoDefaultSpinner) findViewById(R.id.spinner_cap_code_truck_history);
        paddy_category_list = DBHelper.getInstance(this).getPaddyCategory();
        Cap_List = DBHelper.getInstance(this).getCap();
        spinner_paddycategory();
        Spinner_CapNameCode();
        edTv_truck_memo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    clearVales();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    private void spinner_paddycategory() {
        paddy_category_adapter = new TruckPaddyCategoryAdapter(this, paddy_category_list);
        spinner_paddycategory.setAdapter(paddy_category_adapter);
        spinner_paddycategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (GlobalAppState.language.equals("ta")) {
                    paddyCategoryName = paddy_category_list.get(position).getLname();
                } else {
                    paddyCategoryName = paddy_category_list.get(position).getName();
                }
                paddyCategoryId = paddy_category_list.get(position).getId();
                edTv_truck_memo.setText("");
                tv_date.setText("");
                cap_adapter = new CapTruckAdapter(TruckMemoHistoryActivity.this, Cap_List);
                spinner_CapCodeName.setAdapter(cap_adapter);
                s_CapName = null;
                s_CapCode = null;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void Spinner_CapNameCode() {
        try {
            cap_adapter = new CapTruckAdapter(this, Cap_List);
            spinner_CapCodeName.setAdapter(cap_adapter);
            spinner_CapCodeName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    s_CapCode = Cap_List.get(position).getGeneratedCode();
                    s_CapName = Cap_List.get(position).getName();
                    l_CapId = Cap_List.get(position).getId();
                    edTv_truck_memo.setText("");
                    tv_date.setText("");
                    paddy_category_adapter = new TruckPaddyCategoryAdapter(TruckMemoHistoryActivity.this, paddy_category_list);
                    spinner_paddycategory.setAdapter(paddy_category_adapter);
                    paddyCategoryName = null;
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
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

            case R.id.btn_clear:
                clearDatas();
                break;

            case R.id.search_by_date:
                edTv_truck_memo.setText("");
                paddy_category_adapter = new TruckPaddyCategoryAdapter(TruckMemoHistoryActivity.this, paddy_category_list);
                spinner_paddycategory.setAdapter(paddy_category_adapter);
                paddyCategoryName = null;

                cap_adapter = new CapTruckAdapter(TruckMemoHistoryActivity.this, Cap_List);
                spinner_CapCodeName.setAdapter(cap_adapter);
                s_CapName = null;
                s_CapCode = null;

                if (IsClickedDate) {
                    return;
                } else {
                    IsClickedDate = true;
                    new DialogTruckMemoDateSelection(this).show();
                }
                break;

            case R.id.btn_next:
                startActivityBySearch();
                break;
        }
    }


    @Override
    public void onBackPressed() {
        Intent imageback = new Intent(TruckMemoHistoryActivity.this, DashBoardActivity.class);
        startActivity(imageback);
        finish();
    }

    private void startActivityBySearch() {
        try {
            if (paddyCategoryName != null && paddyCategoryId > 0) {
                if (DBHelper.getInstance(TruckMemoHistoryActivity.this).checkTruckmemoByPaddy(paddyCategoryId)) {
                    Intent i = new Intent(TruckMemoHistoryActivity.this, TruckSearchPaddyCategoryActivity.class);
                    i.putExtra("paddyCategoryId", paddyCategoryId);
                    i.putExtra("paddyCategoryName", paddyCategoryName);
                    startActivity(i);
                    finish();
                } else {
                    showToastMessage(getString(R.string.no_records_truck_paddy), 3000);
                }

            } else if (!edTv_truck_memo.getText().toString().trim().isEmpty()) {
                if (DBHelper.getInstance(TruckMemoHistoryActivity.this).checkTruckmemoByNumber(edTv_truck_memo.getText().toString().trim())) {
                    Intent i = new Intent(TruckMemoHistoryActivity.this, TruckSearchNumber.class);
                    i.putExtra("truck_memo_number", edTv_truck_memo.getText().toString());
                    startActivity(i);
                    finish();
                } else {
                    showToastMessage(getString(R.string.no_records_truck_number), 3000);
                }
            } else if (!tv_date.getText().toString().trim().isEmpty()) {
                formatedDate = "";
                String oldFormat = "dd-mm-yyyy";
                String newFormat = "yyyy-mm-dd";
                SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
                Date myDate = null;
                try {
                    myDate = dateFormat.parse(tv_date.getText().toString().trim());
                    SimpleDateFormat timeFormat = new SimpleDateFormat(newFormat);
                    formatedDate = timeFormat.format(myDate);
                    Log.e("truckMemoDate", "output date " + formatedDate);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
                if (DBHelper.getInstance(TruckMemoHistoryActivity.this).checkTruckmemoByDate(formatedDate)) {
                    Intent i = new Intent(TruckMemoHistoryActivity.this, TruckMemoSearchDate.class);
                    i.putExtra("truck_memo_date", tv_date.getText().toString());
                    startActivity(i);
                    finish();
                } else {
                    showToastMessage(getString(R.string.no_records_truck_date), 3000);
                }
            } else if (s_CapName != null && s_CapCode != null) {
                if (DBHelper.getInstance(TruckMemoHistoryActivity.this).checkTruckmemoByCapcode(l_CapId)) {
                    Intent i = new Intent(TruckMemoHistoryActivity.this, TruckMemoSearchCap.class);
                    i.putExtra("capId", l_CapId);
                    i.putExtra("s_CapCode", s_CapCode);
                    startActivity(i);
                    finish();
                } else {
                    showToastMessage(getString(R.string.no_records_truck_cap), 3000);
                }
            } else {
                showToastMessage(getString(R.string.no_records_search), 3000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void clearDatas() {
        edTv_truck_memo.setText("");
        tv_date.setText("");
        paddy_category_adapter = new TruckPaddyCategoryAdapter(TruckMemoHistoryActivity.this, paddy_category_list);
        spinner_paddycategory.setAdapter(paddy_category_adapter);
        paddyCategoryName = null;
        cap_adapter = new CapTruckAdapter(TruckMemoHistoryActivity.this, Cap_List);
        spinner_CapCodeName.setAdapter(cap_adapter);
        s_CapName = null;
        s_CapCode = null;
    }


    private void clearVales() {
        tv_date.setText("");
        paddy_category_adapter = new TruckPaddyCategoryAdapter(TruckMemoHistoryActivity.this, paddy_category_list);
        spinner_paddycategory.setAdapter(paddy_category_adapter);
        paddyCategoryName = null;
        cap_adapter = new CapTruckAdapter(TruckMemoHistoryActivity.this, Cap_List);
        spinner_CapCodeName.setAdapter(cap_adapter);
        s_CapName = null;
        s_CapCode = null;
    }

    public void setTextDate(String textDate) {
        tv_date.setText(textDate);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }
}
