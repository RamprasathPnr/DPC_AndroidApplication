package com.omneagate.dpc.Activity.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.omneagate.dpc.Activity.FarmerListActivity;
import com.omneagate.dpc.Activity.ProcurementActivity;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Utility.DBHelper;

/**
 * Created by user on 4/8/16.
 */
public class SuccessProcurementDialog extends Dialog {

    private final Activity context;  //    Context from the user
    private String receiptNumber;
    TextView receiptNumber_txt;
    boolean isInsert;


    /*Constructor class for this dialog*/
    public SuccessProcurementDialog(Activity _context, String receiptNumber, boolean isInsert) {
        super(_context);
        context = _context;
        this.isInsert = isInsert;
        this.receiptNumber = receiptNumber;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_procurement_success);
        setCancelable(false);
        Button ok = (Button) findViewById(R.id.btn_ok);
        receiptNumber_txt = (TextView) findViewById(R.id.receipt_number);
        receiptNumber_txt.setText(receiptNumber);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (isInsert) {
                    DBHelper.getInstance(context).UpdateProcurementReceiptNumber(receiptNumber_txt.getText().toString());
                }*/
                Intent i = new Intent(context, ProcurementActivity.class);
                context.startActivity(i);
                dismiss();
                context.finish();
            }
        });
    }

}
