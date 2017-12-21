package com.omneagate.dpc.Activity;

import android.content.Intent;
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
import android.widget.Toast;

import com.omneagate.dpc.Activity.Dialog.DialogProcurementDateSelection;
import com.omneagate.dpc.Adapter.TruckPaddyCategoryAdapter;
import com.omneagate.dpc.Model.FarmerRegistrationDto;
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

public class ProcurementHistory extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {

    private Button btn_search;
    private Button btn_clear;
    private TruckPaddyCategoryAdapter paddy_category_adapter;
    private List<PaddyCategoryDto> paddy_category_list;
    private NoDefaultSpinner spinner_paddycategory;
    private String paddyCategoryName;
    private long paddyCategoryId;
    private LinearLayout search_by_date;
    private TextView tv_date;
    private EditText edTv_receipt_number, edTv_farmer_code;

    public static boolean IsClickedDate;
    private String formatedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurement_history);
        checkInternetConnection();
        setUpView();
    }


    private void setUpView() {
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_procurement_history));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        setUpPopUpPage();
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_date.setOnClickListener(this);
        search_by_date = (LinearLayout) findViewById(R.id.search_by_date);
        search_by_date.setOnClickListener(this);
        edTv_receipt_number = (EditText) findViewById(R.id.edTv_receipt_number);
        edTv_farmer_code = (EditText) findViewById(R.id.edTv_farmer_code);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
        paddy_category_list = new ArrayList<PaddyCategoryDto>();
        paddy_category_list = DBHelper.getInstance(this).getPaddyCategory();
        spinner_paddycategory = (NoDefaultSpinner) findViewById(R.id.spinner_paddy_category_procurement_history);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        btn_clear.setOnClickListener(this);
        spinner_paddycategory();

        edTv_receipt_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0) {
                    tv_date.setText("");
                    edTv_farmer_code.setText("");
                    paddy_category_adapter = new TruckPaddyCategoryAdapter(ProcurementHistory.this, paddy_category_list);
                    spinner_paddycategory.setAdapter(paddy_category_adapter);
                    paddyCategoryName = null;
                    paddyCategoryId = 0;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        edTv_farmer_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (count > 0) {
                    tv_date.setText("");
                    edTv_receipt_number.setText("");
                    paddy_category_adapter = new TruckPaddyCategoryAdapter(ProcurementHistory.this, paddy_category_list);
                    spinner_paddycategory.setAdapter(paddy_category_adapter);
                    paddyCategoryName = null;
                    paddyCategoryId = 0;
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
                tv_date.setText("");
                edTv_farmer_code.setText("");
                edTv_receipt_number.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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
            case R.id.btn_clear:
                clearDatas();
                break;
            case R.id.search_by_date:
                if (!edTv_receipt_number.getText().toString().trim().isEmpty()) {
                    edTv_receipt_number.setText("");
                }
                if (!edTv_farmer_code.getText().toString().trim().isEmpty()) {
                    edTv_farmer_code.setText("");
                }
                paddy_category_adapter = new TruckPaddyCategoryAdapter(ProcurementHistory.this, paddy_category_list);
                spinner_paddycategory.setAdapter(paddy_category_adapter);
                paddyCategoryName = null;
                paddyCategoryId = 0;

                if (IsClickedDate) {
                    return;
                } else {
                    IsClickedDate = true;
                    new DialogProcurementDateSelection(this).show();
                }
                break;
            case R.id.btn_search:
                startActivityBySearch();
                break;
        }

    }

    private void startActivityBySearch() {

        if (!edTv_receipt_number.getText().toString().trim().isEmpty()) {
            if (DBHelper.getInstance(ProcurementHistory.this).checkProcurementNumber(edTv_receipt_number.getText().toString().trim())) {
                Intent i = new Intent(ProcurementHistory.this, ProcurementSearchByNumberActivity.class);
                i.putExtra("ProcurementNumber", edTv_receipt_number.getText().toString().trim());
                startActivity(i);
                finish();
            } else {
                showToastMessage(getString(R.string.no_records_receipt), 3000);
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

            if (DBHelper.getInstance(ProcurementHistory.this).checkProcurementDate(formatedDate)) {
                Intent i = new Intent(ProcurementHistory.this, ProcurementSearchByDate.class);
                i.putExtra("procurement_date", tv_date.getText().toString());
                startActivity(i);
                finish();
            } else {
                showToastMessage(getString(R.string.no_records_date), 3000);
            }
        } else if (!edTv_farmer_code.getText().toString().trim().isEmpty()) {

            FarmerRegistrationDto farmer_dto = DBHelper.getInstance(ProcurementHistory.this).getAssociatedFarmerId(edTv_farmer_code.getText().toString().trim());
            if (farmer_dto.getId() != null) {
                long farmer_id = farmer_dto.getId();
                if (DBHelper.getInstance(ProcurementHistory.this).checkProcurementFarmerCode(farmer_id)) {
                    Intent i = new Intent(ProcurementHistory.this, ProcurementSearchByFarmerCode.class);
                    i.putExtra("farmer_code", edTv_farmer_code.getText().toString().trim());
                    startActivity(i);
                    finish();
                } else {
                    showToastMessage(getString(R.string.no_records_code), 3000);
                }
            } else {
                showToastMessage(getString(R.string.no_records_code), 3000);
            }
        } else if (paddyCategoryName != null && paddyCategoryId > 0) {
            if (DBHelper.getInstance(ProcurementHistory.this).checkProcurementPaddy(paddyCategoryId)) {
                Intent i = new Intent(ProcurementHistory.this, ProcurementSearchByPaddy.class);
                i.putExtra("paddyCategoryId", paddyCategoryId);
                i.putExtra("paddyCategoryName", paddyCategoryName);
                startActivity(i);
                finish();
            } else {
                showToastMessage(getString(R.string.no_records_paddy), 3000);
            }
        }else {
            showToastMessage(getString(R.string.no_records_search), 3000);
        }

    }

    private void clearDatas() {
        edTv_receipt_number.setText("");
        tv_date.setText("");
        edTv_farmer_code.setText("");
        paddy_category_adapter = new TruckPaddyCategoryAdapter(ProcurementHistory.this, paddy_category_list);
        spinner_paddycategory.setAdapter(paddy_category_adapter);
        paddyCategoryName = null;
        paddyCategoryId = 0;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.getInstance().setConnectivityListener(this);
    }


    public void setTextDate(String textDate) {
        tv_date.setText(textDate);
    }


    @Override
    public void onBackPressed() {
        Intent imageback = new Intent(ProcurementHistory.this, DashBoardActivity.class);
        startActivity(imageback);
        finish();
    }
}
