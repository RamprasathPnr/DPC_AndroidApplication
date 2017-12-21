package com.omneagate.dpc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Model.FirstSyncDistrictDto;
import com.omneagate.dpc.Model.FirstSyncTalukDto;
import com.omneagate.dpc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 29/7/16.
 */
public class DistrictAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<FirstSyncDistrictDto> district_name;

    public DistrictAdapter(Context context, List<FirstSyncDistrictDto> district_name) {
        this.context = context;
        this.district_name = (ArrayList<FirstSyncDistrictDto>) district_name;
    }

    @Override
    public int getCount() {
        return district_name.size();
    }

    @Override
    public Object getItem(int position) {
        return district_name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        TalukHolder holder = null;
        holder = new TalukHolder();

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_taluk, viewGroup, false);
            holder.taluk_name = (TextView) convertView.findViewById(R.id.text_view_taluk_name);


            convertView.setTag(holder);
            convertView.setTag(R.id.text_view_taluk_name, holder.taluk_name);


        } else {
            holder = (TalukHolder) convertView.getTag();
        }


        if (!GlobalAppState.language.equalsIgnoreCase("ta")) {
            holder.taluk_name.setText(district_name.get(position).getName());
        } else {
            holder.taluk_name.setText(district_name.get(position).getLdistrictName());
        }

        return convertView;
    }

    class TalukHolder {
        TextView taluk_name;

    }
}
