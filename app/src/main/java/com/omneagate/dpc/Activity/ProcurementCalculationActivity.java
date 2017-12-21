package com.omneagate.dpc.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.omneagate.dpc.Model.DPCProcurementDto;
import com.omneagate.dpc.Model.DpcPaddyRateDto;
import com.omneagate.dpc.Model.FarmerRegistrationDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Receiver.ConnectivityReceiver;
import com.omneagate.dpc.Service.Application;
import com.omneagate.dpc.Utility.DBHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ProcurementCalculationActivity extends BaseActivity implements View.OnClickListener, ConnectivityReceiver.ConnectivityReceiverListener {
    private Button btn_next, btn_cancel;
    private TextView step1;
    FarmerRegistrationDto farmer_registration_dto;
    DPCProcurementDto dpcProcurementDto;
    private EditText netWeight, gradeCut, moistureCut, AdditionalCut;
    private TextView tv_purchaseRate, tv_bonusRate, tv_totalRate, tv_totalAmount,
            tv_totalCut, tv_totalCutAmount, txt_gnd_total_amount, txt_gnd_cut_amount, txt_gnd_net_amount;
    private double purchase_rate;
    private double bonus_rate;
    private double total_rate;
    private int grade_;
    private int moisture_;
    private int additional_;
    private int total;
    private long rate_id;
    private NumberFormat formater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_procurement_calculation_activity_new);
        checkInternetConnection();
        setUpView();
//        settemptxt();
    }

    private void settemptxt() {
//        netWeight.setText("5");
        gradeCut.setText("5");
        moistureCut.setText("5");
        AdditionalCut.setText("5");
    }

    private void setUpView() {
        setUpPopUpPage();
        TextView title = (TextView) findViewById(R.id.title);
        title.setText(getString(R.string.title_Farmer_Procurement_details));
        ImageView btn_back = (ImageView) findViewById(R.id.image_back);
        btn_back.setOnClickListener(this);
        netWeight = (EditText) findViewById(R.id.ed_net_weight);
        gradeCut = (EditText) findViewById(R.id.edit_text_grade_cut);
        moistureCut = (EditText) findViewById(R.id.edit_text_moisture_cut);
        AdditionalCut = (EditText) findViewById(R.id.edit_text_additional_cut);
        tv_purchaseRate = (TextView) findViewById(R.id.tv_purchaseRate);
        tv_bonusRate = (TextView) findViewById(R.id.tv_bonusRate);
        tv_totalRate = (TextView) findViewById(R.id.tv_totalRate);
        tv_totalAmount = (TextView) findViewById(R.id.tv_totalAmount);
        tv_totalCut = (TextView) findViewById(R.id.tv_totalCut);
        tv_totalCutAmount = (TextView) findViewById(R.id.tv_totalCutAmount);
        txt_gnd_total_amount = (TextView) findViewById(R.id.txt_gnd_total_amount);
        txt_gnd_cut_amount = (TextView) findViewById(R.id.txt_gnd_cut_amount);
        txt_gnd_net_amount = (TextView) findViewById(R.id.txt_gnd_net_amount);
        farmer_registration_dto = (FarmerRegistrationDto) getIntent().getSerializableExtra("farmerRegistrationDto");
        dpcProcurementDto = (DPCProcurementDto) getIntent().getSerializableExtra("dpcProcurementDto");

        DpcPaddyRateDto rate_dto = DBHelper.getInstance(ProcurementCalculationActivity.this).getRate(dpcProcurementDto.getPaddyGradeDto().getId());


        formater = new DecimalFormat("#.00");

        if (rate_dto != null) {
            rate_id = rate_dto.getId();
            Log.e("*******rate_id*****",""+rate_id);
            Log.e("TGGGGGGG", "purchase_rate : " + formater.format(rate_dto.getPurchaseRate()));
            purchase_rate = rate_dto.getPurchaseRate();
            bonus_rate = rate_dto.getBonusRate();
            total_rate = rate_dto.getTotalRate();

            Log.e("TGGGGGGG", "purchase_rate : " + purchase_rate + "   bonus_rate :" + bonus_rate + "    total_rate :" + total_rate);
            if (purchase_rate > 0) {
                tv_purchaseRate.setText("" + formater.format(rate_dto.getPurchaseRate()));
            } else {
                tv_purchaseRate.setText("0.00");
            }

            if (bonus_rate > 0) {
                tv_bonusRate.setText("" + formater.format(rate_dto.getBonusRate()));
            } else {
                tv_bonusRate.setText("0.00");
            }
            if (total_rate > 0) {
                tv_totalRate.setText("" + formater.format(rate_dto.getTotalRate()));
            } else {
                tv_totalRate.setText("0.00");
            }
        } else {
            tv_purchaseRate.setText("0.00");
            tv_bonusRate.setText("0.00");
            tv_totalRate.setText("0.00");
        }


        netWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addValues();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        setPreviousValues();

        gradeCut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addValues();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        moistureCut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addValues();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        AdditionalCut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addValues();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        tv_totalCut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        step1 = (TextView) findViewById(R.id.num_txt_1);
        btn_next.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        step1.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            case R.id.btn_next:
                checkValues();
                break;
            case R.id.btn_cancel:
                onBackPressed();
                break;
            case R.id.num_txt_1:
                onBackPressed();
                break;
            default:
                return;
        }
    }

    private void checkValues() {
        if (netWeight.getText().toString().trim().equalsIgnoreCase("") || netWeight.getText().toString().trim().equalsIgnoreCase("0")) {
            showToastMessage(getString(R.string.toast_net_weight), 3000);
            return;
        } else if ((Double.parseDouble(netWeight.getText().toString().trim()) == 0.0d) || (Double.parseDouble(netWeight.getText().toString().trim()) == 0d)) {
            showToastMessage(getString(R.string.toast_grater_than_one), 3000);
            return;
        } else if (!tv_totalCutAmount.getText().toString().equalsIgnoreCase("")
                && Double.parseDouble(tv_totalCutAmount.getText().toString())
                > (Double.parseDouble(tv_totalAmount.getText().toString()))) {
            showToastMessage(getString(R.string.toast_grater_than_totalamount), 3000);
        } else if (bonus_rate <= 0 || purchase_rate <= 0) {
            showToastMessage(getString(R.string.toast_invalid_rate), 3000);
        } else if (txt_gnd_net_amount.getText().toString().equalsIgnoreCase("0")
                || txt_gnd_net_amount.getText().toString().equalsIgnoreCase("0.00")) {
            showToastMessage(getString(R.string.toast_net_amount), 3000);
        } else {
            setValues();
            Intent i = new Intent(ProcurementCalculationActivity.this, ProcurementEditActivity.class);
            i.putExtra("farmerRegistrationDto", farmer_registration_dto);
            i.putExtra("dpcProcurementDto", dpcProcurementDto);
            startActivity(i);
            finish();
        }
    }

    private void setValues() {
        dpcProcurementDto.setNetWeight(Double.parseDouble(netWeight.getText().toString().trim()));
        DpcPaddyRateDto rate_dto = new DpcPaddyRateDto();
        rate_dto.setId(rate_id);
        rate_dto.setPurchaseRate(Double.parseDouble(tv_purchaseRate.getText().toString()));
        rate_dto.setBonusRate(Double.parseDouble(tv_bonusRate.getText().toString()));
        rate_dto.setTotalRate(Double.parseDouble(tv_totalRate.getText().toString()));
        dpcProcurementDto.setDpcPaddyRateDto(rate_dto);
        dpcProcurementDto.setTotalAmount(Double.parseDouble(tv_totalAmount.getText().toString().trim()));
        if (gradeCut.getText().toString().equalsIgnoreCase("")) {
            dpcProcurementDto.setGradeCut(0.0d);
        } else {
            dpcProcurementDto.setGradeCut(Double.parseDouble(gradeCut.getText().toString().trim()));
        }
        if (moistureCut.getText().toString().equalsIgnoreCase("")) {
            dpcProcurementDto.setMoistureCut(0.0d);
        } else {
            dpcProcurementDto.setMoistureCut(Double.parseDouble(moistureCut.getText().toString().trim()));
        }
        if (AdditionalCut.getText().toString().equalsIgnoreCase("")) {
            dpcProcurementDto.setAdditionalCut(0.0d);
        } else {
            dpcProcurementDto.setAdditionalCut(Double.parseDouble(AdditionalCut.getText().toString().trim()));
        }

        if (tv_totalCutAmount.getText().toString().equalsIgnoreCase("")) {
            dpcProcurementDto.setTotalCutAmount(0.0d);
        } else {
            dpcProcurementDto.setTotalCutAmount(Double.parseDouble(tv_totalCutAmount.getText().toString()));
        }
        dpcProcurementDto.setTotalCut(Double.parseDouble(tv_totalCut.getText().toString()));
        dpcProcurementDto.setNetAmount(Double.parseDouble(txt_gnd_net_amount.getText().toString()));
    }

    @Override
    public void onBackPressed() {
        Intent ii_ = new Intent(ProcurementCalculationActivity.this, ProcurementDetailsActivity.class);
        ii_.putExtra("farmerRegistrationDto", farmer_registration_dto);
        ii_.putExtra("dpcProcurementDto", dpcProcurementDto);
        startActivity(ii_);
        finish();
    }

    private void addValues() {
        try {
            NumberFormat num_format = new DecimalFormat("#.000");

            double net_weight_;
            double total_amt = 0;
            if (!netWeight.getText().toString().trim().isEmpty()) {
                net_weight_ = Double.parseDouble(num_format.format(Double.parseDouble(netWeight.getText().toString())));
                Log.e("TAGGGGG FORMAT", "" + net_weight_);
                total_amt = (net_weight_ * total_rate);
                Log.e("TAGGGGG", "" + total_amt);
                tv_totalAmount.setText("" + String.format("%.2f", total_amt));
                txt_gnd_total_amount.setText("(" + String.format("%.2f", total_amt) + ")");
            } else {
                tv_totalAmount.setText("");
                txt_gnd_total_amount.setText("( 0 )");
            }
            if (!gradeCut.getText().toString().equalsIgnoreCase("")) {
                grade_ = Integer.parseInt(gradeCut.getText().toString());
            } else {
                grade_ = 0;
            }
            if (!moistureCut.getText().toString().trim().isEmpty()) {
                moisture_ = Integer.parseInt(moistureCut.getText().toString());
            } else {
                moisture_ = 0;
            }
            if (!AdditionalCut.getText().toString().trim().isEmpty()) {
                additional_ = Integer.parseInt(AdditionalCut.getText().toString());
            } else {
                additional_ = 0;
            }

            int total = grade_ + moisture_ + additional_;
            tv_totalCut.setText("" + String.format("%.2f", (double) total));
            double tot_ct_amt = 0;
            if (!netWeight.getText().toString().trim().equalsIgnoreCase("")) {
                double net_weight = Double.parseDouble(netWeight.getText().toString());
                if (total != 0) {
                    tot_ct_amt = total * net_weight;
                    tv_totalCutAmount.setText("" + formater.format(tot_ct_amt));
                    txt_gnd_cut_amount.setText("(" + formater.format(tot_ct_amt) + ")");
                } else {
                    tv_totalCutAmount.setText("0.00");
                    txt_gnd_cut_amount.setText("( 0 )");
                }
            } else {
                tv_totalCutAmount.setText("");
                txt_gnd_cut_amount.setText("( 0 )");
            }
            double netamount = total_amt - tot_ct_amt;
            txt_gnd_net_amount.setText("" + formater.format(netamount));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void setPreviousValues() {
        if (dpcProcurementDto.getNetWeight() != 0) {
            DecimalFormat net_formater = new DecimalFormat("#.000");
            netWeight.setText("" + String.format("%.3f", dpcProcurementDto.getNetWeight()));
        }
        if (dpcProcurementDto.getDpcPaddyRateDto() != null) {
            if (dpcProcurementDto.getDpcPaddyRateDto().getBonusRate() != 0) {
                tv_bonusRate.setText("" + formater.format(dpcProcurementDto.getDpcPaddyRateDto().getBonusRate()));
            }
            if (dpcProcurementDto.getDpcPaddyRateDto().getTotalRate() != 0) {
                tv_bonusRate.setText("" + formater.format(dpcProcurementDto.getDpcPaddyRateDto().getBonusRate()));
            }
            if (dpcProcurementDto.getDpcPaddyRateDto().getTotalRate() != 0) {
                double net_weight_;
                double total_amt = 0;
                if (!netWeight.getText().toString().trim().isEmpty()) {
                    net_weight_ = Double.parseDouble(netWeight.getText().toString());

                    total_amt = (net_weight_ * total_rate);

                    Log.e("TAGGGGG", "" + total_amt);

                    tv_totalAmount.setText("" + String.format("%.2f", total_amt));
                    txt_gnd_total_amount.setText("(" + String.format("%.2f", total_amt) + ")");
                } else {
                    tv_totalAmount.setText("");
                    txt_gnd_total_amount.setText("( 0 )");
                }


            }
        }
        /*if (dpcProcurementDto.getTotalAmount() != 0) {
            tv_totalAmount.setText("" + dpcProcurementDto.getTotalAmount());
        }*/
        if (dpcProcurementDto.getGradeCut() != 0) {
            gradeCut.setText("" + formater.format(dpcProcurementDto.getGradeCut()));
        }
        if (dpcProcurementDto.getMoistureCut() != 0) {
            moistureCut.setText("" + formater.format(dpcProcurementDto.getMoistureCut()));
        }
        if (dpcProcurementDto.getAdditionalCut() != 0) {
            AdditionalCut.setText("" + formater.format(dpcProcurementDto.getAdditionalCut()));
        }
        if (dpcProcurementDto.getTotalCut() != 0) {
            tv_totalCut.setText("" + formater.format(dpcProcurementDto.getTotalCut()));
        }
        if (dpcProcurementDto.getTotalCutAmount() != 0) {
            tv_totalCutAmount.setText("" + formater.format(dpcProcurementDto.getTotalCutAmount()));
        }
        if (dpcProcurementDto.getTotalAmount() != 0) {
            txt_gnd_total_amount.setText("" + formater.format(dpcProcurementDto.getTotalAmount()));
        }
        if (dpcProcurementDto.getTotalCutAmount() != 0) {
            txt_gnd_cut_amount.setText("" + formater.format(dpcProcurementDto.getTotalCutAmount()));
        }
        if (dpcProcurementDto.getNetAmount() != 0) {
            txt_gnd_net_amount.setText("" + formater.format(dpcProcurementDto.getNetAmount()));
        }
    }
}
