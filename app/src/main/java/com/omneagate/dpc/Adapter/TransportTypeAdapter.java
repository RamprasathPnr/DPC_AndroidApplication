package com.omneagate.dpc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 22/9/16.
 */
public class TransportTypeAdapter extends BaseAdapter {
    private Context context;
    private List<String> transport_type_list;

    public  TransportTypeAdapter(Context context,List<String> transport_type){
        this.context=context;
        this.transport_type_list=transport_type;

    }
    @Override
    public int getCount() {
        return transport_type_list.size();
    }

    @Override
    public Object getItem(int position) {
        return transport_type_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewgroup) {
        TransportHolder holder = null;
        holder = new TransportHolder();
        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_paddy_category, viewgroup, false);
            holder.TransportType = (TextView) convertView.findViewById(R.id.text_view_paddy_category);


            convertView.setTag(holder);
            convertView.setTag(R.id.text_view_guardian_type, holder.TransportType);

        } else {
            holder = (TransportHolder) convertView.getTag();
        }

        if (!GlobalAppState.language.equalsIgnoreCase("ta")) {
            holder.TransportType.setText(transport_type_list.get(position));
        } else {
            holder.TransportType.setText(transport_type_list.get(position));
        }

        return convertView;
    }

    class TransportHolder {
        TextView TransportType;
    }
}
