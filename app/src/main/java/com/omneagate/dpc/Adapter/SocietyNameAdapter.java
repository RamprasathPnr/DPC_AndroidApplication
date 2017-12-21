package com.omneagate.dpc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Model.SocietyDto;
import com.omneagate.dpc.R;

import java.util.ArrayList;
import java.util.List;


public class SocietyNameAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<SocietyDto> society_name;

    public SocietyNameAdapter(Context context, List<SocietyDto> society_name) {
        this.context = context;
        this.society_name = (ArrayList<SocietyDto>) society_name;
    }

    @Override
    public int getCount() {
        return society_name.size();
    }

    @Override
    public Object getItem(int position) {
        return society_name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        SocietyHolder holder = null;
        holder = new SocietyHolder();

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_society_name, viewGroup, false);
            holder.society_name = (TextView) convertView.findViewById(R.id.text_view_society_name);


            convertView.setTag(holder);
            convertView.setTag(R.id.text_view_society_name, holder.society_name);


        } else {
            holder = (SocietyHolder) convertView.getTag();
        }


        if(!GlobalAppState.language.equalsIgnoreCase("ta")) {
            holder.society_name.setText(society_name.get(position).getName());
        }else{
            holder.society_name.setText(society_name.get(position).getLname());
        }

        return convertView;
    }

    class SocietyHolder {
        TextView society_name;

    }

}




