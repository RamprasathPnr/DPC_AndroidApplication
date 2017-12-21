package com.omneagate.dpc.Activity.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.omneagate.dpc.Activity.FarmerListActivity;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Utility.DBHelper;
import com.omneagate.dpc.Utility.Util;

/**
 * Created by user on 4/8/16.
 */
public class SuccessRegistrationDialog extends Dialog {

    private final Activity context;  //    Context from the user
    private String ReferenceName;
    TextView referenceNUmber;
    boolean isInsert;

    /*Constructor class for this dialog*/
    public SuccessRegistrationDialog(Activity _context, String ReferenceNumber,boolean isInsert) {
        super(_context);
        context = _context;
        ReferenceName = ReferenceNumber;
        this.isInsert=isInsert;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_successful_registration);
        setCancelable(false);
        Button ok = (Button) findViewById(R.id.btn_ok);
        referenceNUmber = (TextView) findViewById(R.id.referenceNmber);
        referenceNUmber.setText(ReferenceName);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInsert) {
                    DBHelper.getInstance(context).UpdateRegistrationNumber(referenceNUmber.getText().toString());
                }
                Intent i = new Intent(context, FarmerListActivity.class);
                context.startActivity(i);
                dismiss();
                context.finish();
                if( Util.farmerLandDetailsDtoList!=null) {
                    Util.farmerLandDetailsDtoList.clear();
                }
            }
        });
    }

}
