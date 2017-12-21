package com.omneagate.dpc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Model.PaddyGradeDto;
import com.omneagate.dpc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 28/7/16.
 */
public class GradeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PaddyGradeDto> grades;

    public GradeAdapter(Context context, List<PaddyGradeDto> grades) {
        this.context = context;
        this.grades = (ArrayList<PaddyGradeDto>) grades;
    }

    @Override
    public int getCount() {
        return grades.size();
    }

    @Override
    public Object getItem(int position) {
        return grades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        BankHolder holder = null;
        holder = new BankHolder();

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_grade_truck_memo, viewGroup, false);
            holder.grade_name = (TextView) convertView.findViewById(R.id.text_view_grade_name);


            convertView.setTag(holder);
            convertView.setTag(R.id.text_view_grade_name, holder.grade_name);


        } else {
            holder = (BankHolder) convertView.getTag();
        }


        if (!GlobalAppState.language.equalsIgnoreCase("ta")) {
            holder.grade_name.setText(grades.get(position).getName());
        } else {
            holder.grade_name.setText(grades.get(position).getLname());
        }

        return convertView;
    }

    class BankHolder {
        TextView grade_name;

    }
}
