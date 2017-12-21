package com.omneagate.dpc.Activity.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.omneagate.dpc.Activity.DashBoardActivity;
import com.omneagate.dpc.R;

/**
 * Created by root on 16/9/16.
 */
public class SuccessTruckMemoDialog extends Dialog {

    private final Activity context;  //  Context from the user
    private String ReferenceName;
    TextView referenceNUmber;


    /*Constructor class for this dialog*/
    public SuccessTruckMemoDialog(Activity _context, String ReferenceNumber) {
        super(_context);
        context = _context;
        ReferenceName = ReferenceNumber;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_successful_truckmemo);
        setCancelable(false);
        Button ok = (Button) findViewById(R.id.btn_ok);
        referenceNUmber = (TextView) findViewById(R.id.referenceNmber);
        referenceNUmber.setText(ReferenceName);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, DashBoardActivity.class);
                context.startActivity(i);
                dismiss();
                context.finish();
            }
        });
    }

}
