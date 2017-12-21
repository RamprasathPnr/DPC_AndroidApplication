package com.omneagate.dpc.Activity.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;


import com.omneagate.dpc.Activity.AddLandDetailsActivity;
import com.omneagate.dpc.Activity.TruckMemoHistoryActivity;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Utility.DBHelper;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * This dialog will appear on the time of user logout
 */
public class DialogTruckMemoDateSelection extends Dialog implements View.OnClickListener {


    private final Activity context;  //    Context from the user

    MaterialCalendarView widget;//

    /*Constructor class for this dialog*/
    public DialogTruckMemoDateSelection(Activity _context) {
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
        widget.setMaximumDate(new Date());

        DateTime today = new DateTime();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, today.getDayOfMonth() + 1);
        String days = DBHelper.getInstance(context).getMasterData("purgeBill");
        int purgeDays = Integer.parseInt(days);
        if(purgeDays == 90) {
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 3);
        }
        else if(purgeDays == 60) {
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 2);
        }
        else if(purgeDays == 30) {
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
        }
        cal.set(Calendar.YEAR, today.getYear());
        widget.setMinimumDate(cal);

        Button okButton = (Button) findViewById(R.id.buttonNwOk);
        okButton.setOnClickListener(this);
        Button cancelButton = (Button) findViewById(R.id.buttonNwCancel);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonNwOk:
                TruckMemoHistoryActivity.IsClickedDate=false;
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                ((TruckMemoHistoryActivity) context).setTextDate(sdf.format(widget.getSelectedDate().getDate()));
                dismiss();
                break;
            case R.id.buttonNwCancel:
                TruckMemoHistoryActivity.IsClickedDate=false;
                dismiss();
                break;
        }
    }

}