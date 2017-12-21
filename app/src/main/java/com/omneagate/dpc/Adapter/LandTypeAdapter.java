package com.omneagate.dpc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Model.BankDto;
import com.omneagate.dpc.Model.LandTypeDto;
import com.omneagate.dpc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 28/7/16.
 */
public class LandTypeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<LandTypeDto> land_type;

    public LandTypeAdapter(Context context, List<LandTypeDto> land_type) {
        this.context = context;
        this.land_type = (ArrayList<LandTypeDto>) land_type;
    }

    @Override
    public int getCount() {
        return land_type.size();
    }

    @Override
    public Object getItem(int position) {
        return land_type.get(position);
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

            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_land_type, viewGroup, false);
            holder.land_type = (TextView) convertView.findViewById(R.id.text_view_land_type);


            convertView.setTag(holder);
            convertView.setTag(R.id.text_view_land_type, holder.land_type);


        } else {
            holder = (BankHolder) convertView.getTag();
        }


        if (!GlobalAppState.language.equalsIgnoreCase("ta")) {
            holder.land_type.setText(land_type.get(position).getName());
        } else {
            holder.land_type.setText(land_type.get(position).getName());
        }

        return convertView;
    }

    class BankHolder {
        TextView land_type;

    }
}
