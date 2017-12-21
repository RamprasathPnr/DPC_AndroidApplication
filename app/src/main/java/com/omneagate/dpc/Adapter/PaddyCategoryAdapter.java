package com.omneagate.dpc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Model.PaddyCategoryDto;
import com.omneagate.dpc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 29/7/16.
 */
public class PaddyCategoryAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PaddyCategoryDto> paddy_name;

    public PaddyCategoryAdapter(Context context, List<PaddyCategoryDto> paddy_name) {
        this.context = context;
        this.paddy_name = (ArrayList<PaddyCategoryDto>) paddy_name;
    }

    @Override
    public int getCount() {
        return paddy_name.size();
    }

    @Override
    public Object getItem(int position) {
        return paddy_name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        PaddyCategoryHolder holder = null;
        holder = new PaddyCategoryHolder();

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_paddy_category, viewGroup, false);
            holder.paddy_category = (TextView) convertView.findViewById(R.id.text_view_paddy_category);


            convertView.setTag(holder);
            convertView.setTag(R.id.text_view_paddy_category, holder.paddy_category);


        } else {
            holder = (PaddyCategoryHolder) convertView.getTag();
        }


        if (!GlobalAppState.language.equalsIgnoreCase("ta")) {
            holder.paddy_category.setText(paddy_name.get(position).getName());
        } else {
            holder.paddy_category.setText(paddy_name.get(position).getLname());
        }

        return convertView;
    }

    class PaddyCategoryHolder {
        TextView paddy_category;
    }
}
