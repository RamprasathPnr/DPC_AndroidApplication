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
import android.widget.TextView;


import com.omneagate.dpc.Activity.LoginActivity;
import com.omneagate.dpc.Model.DPCSyncExceptionDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Service.FirstSyncExceptionService;

import java.util.List;


/**
 * This dialog will appear on the time of user logout
 */
public class TableExceptionErrorDialog extends Dialog implements
        View.OnClickListener {


    private final Activity context;  //    Context from the user
    List<DPCSyncExceptionDto> dpcSyncExceptionDtoList;

    /*Constructor class for this dialog*/
    public TableExceptionErrorDialog(Activity _context) {
        super(_context);
        context = _context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_retry_failed);
        setCancelable(false);
        TextView message = (TextView) findViewById(R.id.textViewNwText);
        String userText = "Error in Sync....";
        ((TextView) findViewById(R.id.textViewNwTitle)).setText("Loading Failed");
        message.setText(userText);
        userText = "Please contact HelpDesk";
        ((TextView) findViewById(R.id.textViewNwTextSecond)).setText(userText);
        Button okButton = (Button) findViewById(R.id.buttonNwOk);
        okButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonNwOk:
                Log.e("buttonNwOk", "buttonNwOk");
                dismiss();
                context.startService(new Intent(context, FirstSyncExceptionService.class));
                Intent i=new Intent(context, LoginActivity.class);
                context.startActivity(i);
                break;
        }
    }


}