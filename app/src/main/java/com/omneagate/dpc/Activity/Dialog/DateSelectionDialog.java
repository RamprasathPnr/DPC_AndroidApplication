package com.omneagate.dpc.Activity.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


import com.omneagate.dpc.Activity.AddLandDetailsActivity;
import com.omneagate.dpc.R;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.joda.time.DateTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This dialog will appear on the time of user logout
 */
public class DateSelectionDialog extends Dialog implements View.OnClickListener {


    private final Activity context;  //    Context from the user

    MaterialCalendarView widget;//

    /*Constructor class for this dialog*/
    public DateSelectionDialog(Activity _context) {
        super(_context);
        context = _context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_date_selection);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
        widget = (MaterialCalendarView) findViewById(R.id.calendarView);

        Calendar calendar = Calendar.getInstance();
        widget.setSelectedDate(calendar.getTime());
        widget.setMinimumDate(calendar);

        DateTime today = new DateTime();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, today.getDayOfMonth() + 1);


        Button okButton = (Button) findViewById(R.id.buttonNwOk);
        okButton.setOnClickListener(this);
        Button cancelButton = (Button) findViewById(R.id.buttonNwCancel);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonNwOk:
                AddLandDetailsActivity.IsClickedDate=false;
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                ((AddLandDetailsActivity) context).setTextDate(sdf.format(widget.getSelectedDate().getDate()));
                dismiss();
                break;
            case R.id.buttonNwCancel:
                AddLandDetailsActivity.IsClickedDate=false;
                dismiss();
                break;
        }
    }

}