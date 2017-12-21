package com.omneagate.dpc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Model.FarmerClassDto;
import com.omneagate.dpc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 28/7/16.
 */
public class FarmerClassAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<FarmerClassDto> farmer_class;

    public FarmerClassAdapter(Context context, List<FarmerClassDto> farmer_class) {
        this.context = context;
        this.farmer_class = (ArrayList<FarmerClassDto>) farmer_class;
    }


    @Override
    public int getCount() {
        return farmer_class.size();
    }

    @Override
    public Object getItem(int position) {
        return farmer_class.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        FarmerClassHolder holder = null;
        holder = new FarmerClassHolder();

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_farmer_class, viewGroup, false);
            holder.farmer_classTv = (TextView) convertView.findViewById(R.id.text_view_farmer_class);
            convertView.setTag(holder);
            convertView.setTag(R.id.text_view_farmer_class, holder.farmer_classTv);


        } else {
            holder = (FarmerClassHolder) convertView.getTag();
        }


        if(!GlobalAppState.language.equalsIgnoreCase("ta")) {
            holder.farmer_classTv.setText(farmer_class.get(position).getName());
        }else{
            holder.farmer_classTv.setText(farmer_class.get(position).getLname());
        }

        return convertView;
    }

    class FarmerClassHolder {
        TextView farmer_classTv;

    }

}
