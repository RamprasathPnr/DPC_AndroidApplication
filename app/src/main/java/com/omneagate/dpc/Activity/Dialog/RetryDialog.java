package com.omneagate.dpc.Activity.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.omneagate.dpc.Activity.SyncPageActivity;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Utility.NetworkConnection;

/**
 * This dialog will appear on the time of user logout
 */
public class RetryDialog extends Dialog implements
        View.OnClickListener {


    private final Activity context;  //    Context from the user
    private int attempt;

    /*Constructor class for this dialog*/
    public RetryDialog(Activity _context, int attempt) {
        super(_context);
        this.attempt = attempt;
        context = _context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_retry);
        setCancelable(false);
        TextView message = (TextView) findViewById(R.id.textViewNwText);
        String userText = "Check your connection ";
        int remain = 3 - attempt;
        Log.e("remain",""+remain);

        ((TextView) findViewById(R.id.textViewNwTitle)).setText("Loading Failed");
        message.setText(userText);
        userText = " You have " + remain + " attempts";
        ((TextView) findViewById(R.id.textViewNwTextSecond)).setText(userText);
        Button okButton = (Button) findViewById(R.id.buttonNwOk);
        okButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonNwOk:
//                NetworkConnection networkConnection = new NetworkConnection(context);
                if (new NetworkConnection(context).isNetworkAvailable()) {
                    ((SyncPageActivity) context).firstTimeSyncDetails();

                }else{
                    ((SyncPageActivity) context).call_failed_dialog();
//                    Toast.makeText(context, context.getString(R.string.internetnotavailable),
//                            Toast.LENGTH_SHORT).show();
                }
                dismiss();
                break;
        }
    }


}