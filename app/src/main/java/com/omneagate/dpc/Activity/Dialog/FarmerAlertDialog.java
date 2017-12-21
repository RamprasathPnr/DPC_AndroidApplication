package com.omneagate.dpc.Activity.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.omneagate.dpc.Activity.FarmerListActivity;
import com.omneagate.dpc.Activity.LoginActivity;
import com.omneagate.dpc.Model.AadharDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Utility.Util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by user on 16/7/16.
 */
public class FarmerAlertDialog extends Dialog implements View.OnClickListener {

    private final Activity context;
    /*Constructor class for this dialog*/
    public FarmerAlertDialog(Activity _context) {
        super(_context);
        context = _context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            setContentView(R.layout.dialog_farmer_alert);
            setCancelable(true);

            Button okButton = (Button) findViewById(R.id.buttonNwOk);
            okButton.setOnClickListener(this);
            Button cancelButton = (Button) findViewById(R.id.buttonNwCancel);
            cancelButton.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonNwOk:
                try {
                    if (Util.farmerLandDetailsDtoList != null) {
                        Util.farmerLandDetailsDtoList.clear();
                    }
                    context.startActivity(new Intent(context, FarmerListActivity.class));
                    context.finish();
                    dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.buttonNwCancel:
                try {
                    dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

}
