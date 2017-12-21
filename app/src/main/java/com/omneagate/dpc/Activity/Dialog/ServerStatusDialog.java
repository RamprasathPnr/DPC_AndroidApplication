package com.omneagate.dpc.Activity.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.omneagate.dpc.Model.AadharDto;
import com.omneagate.dpc.R;

/**
 * Created by user on 16/7/16.
 */
public class ServerStatusDialog extends Dialog  {

    private final Activity context;
    TextView addressTv;
    Button btn_ok;

    /*Constructor class for this dialog*/
    public ServerStatusDialog(Activity _context) {
        super(_context);
        context = _context;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_server_status);
        setCancelable(true);
        btn_ok=(Button)findViewById(R.id.btn_server_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

//        addressTv = (TextView) findViewById(R.id.text_view_user_address);


    }

}
