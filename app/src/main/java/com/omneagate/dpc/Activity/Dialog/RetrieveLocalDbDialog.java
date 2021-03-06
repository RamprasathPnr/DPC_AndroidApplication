package com.omneagate.dpc.Activity.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.omneagate.dpc.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by root on 10/8/16.
 */
public class RetrieveLocalDbDialog extends Dialog implements
        View.OnClickListener {

    private final Activity context;  //    Context from the user
    File sourcePath, destinationPath;

    /*Constructor class for this dialog*/
    public RetrieveLocalDbDialog(Activity _context, File source, File destination) {
        super(_context);
        context = _context;
        sourcePath = source;
        destinationPath = destination;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_retrieve_db);
        setCancelable(false);
        Button okButton = (Button) findViewById(R.id.buttonNwOk);
        okButton.setOnClickListener(this);
        Button cancelButton = (Button) findViewById(R.id.buttonNwCancel);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonNwOk:
                try {
                    FileChannel source = new FileInputStream(sourcePath).getChannel();
                    FileChannel destination = new FileOutputStream(destinationPath).getChannel();
                    destination.transferFrom(source, 0, source.size());
                    source.close();
                    destination.close();
                } catch (IOException e) {
                    Log.e("source recovery", e.toString());
                }
                dismiss();
                break;
            case R.id.buttonNwCancel:
                dismiss();
                break;
        }
    }
}
