package com.omneagate.dpc.Activity.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Activity.LoginActivity;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Utility.TamilUtil;
import com.omneagate.dpc.Utility.Util;

import java.util.Locale;

/**
 * Created by user on 27/6/16.
 */
public class LanguageSelectionDialog extends Dialog implements View.OnClickListener {

    private RelativeLayout layout_english;
    private RelativeLayout layout_tamil;
    private Locale myLocale;
    private GlobalAppState appState;
    private final Activity context;  //    Context from the user
    private boolean tamil = false;

    /*Constructor class for this dialog*/
    public LanguageSelectionDialog(Activity _context) {
        super(_context);
        context = _context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setUpView();


    }

    public void setUpView() {
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_language);
        setCancelable(true);
        layout_english = (RelativeLayout) findViewById(R.id.layout_english);
        layout_tamil = (RelativeLayout) findViewById(R.id.layout_tamil);
        layout_english.setOnClickListener(this);
        layout_tamil.setOnClickListener(this);


        if (GlobalAppState.language.equals("ta")) {
            tamil = true;
            findViewById(R.id.image_ta_tick).setVisibility(View.VISIBLE);
            findViewById(R.id.image_en_tick).setVisibility(View.GONE);

        } else {
            findViewById(R.id.image_en_tick).setVisibility(View.VISIBLE);
            findViewById(R.id.image_ta_tick).setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.layout_tamil:
                tamil = true;
                changeLanguage();
                dismiss();
                context.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;


            case R.id.layout_english:
                tamil = false;
                changeLanguage();
                dismiss();
                context.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                break;


        }

    }

    /* Re-starts the application where language change take effects */
    private void restartApplication() {
//        Intent restart = context.getBaseContext().getPackageManager()
//                .getLaunchIntentForPackage(context.getBaseContext().getPackageName());
//        restart.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        context.startActivity(restart);
        Intent in = new Intent(getContext(), LoginActivity.class);
        in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getContext().startActivity(in);
    }

    private void changeLanguage() {

        if (tamil) {
            // Util.LoggingQueue(context, "Selected", "Tamil");
            Util.changeLanguage(context, "ta");
        } else {
            //Util.LoggingQueue(context, "Selected", "English");
            Util.changeLanguage(context, "en");
        }
        restartApplication();
    }


    /**
     * Tamil text textView typeface
     * input  textView name and id for string.xml
     */
    public void setTamilText(TextView textName, int id) {
        Typeface tfBamini = Typeface.createFromAsset(context.getAssets(), "fonts/Bamini.ttf");
        textName.setTypeface(tfBamini);
        textName.setText(TamilUtil.convertToTamil(TamilUtil.BAMINI, context.getString(id)));
    }


}
