package com.omneagate.dpc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Model.DpcCapDto;
import com.omneagate.dpc.R;

import java.util.List;

/**
 * Created by root on 23/9/16.
 */
public class CapAdapter extends BaseAdapter {
    private Context context;
    private List<DpcCapDto> cap_list;


    public CapAdapter(Context context, List<DpcCapDto> cap_list) {
        this.context = context;
        this.cap_list = cap_list;
    }

    @Override
    public int getCount() {
        return cap_list.size();
    }

    @Override
    public Object getItem(int position) {
        return cap_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        CapHolder holder = null;
        holder = new CapHolder();

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_paddy_category, viewGroup, false);
            holder.capCodeName = (TextView) convertView.findViewById(R.id.text_view_paddy_category);


            convertView.setTag(holder);
            convertView.setTag(R.id.text_view_paddy_category, holder.capCodeName);


        } else {
            holder = (CapHolder) convertView.getTag();
        }


        if (!GlobalAppState.language.equalsIgnoreCase("ta")) {
            holder.capCodeName.setText(cap_list.get(position).getGeneratedCode() +" / "+cap_list.get(position).getName());
        } else {
            holder.capCodeName.setText(cap_list.get(position).getGeneratedCode() +" / "+ cap_list.get(position).getName());
        }

        return convertView;
    }

    class CapHolder {
        TextView capCodeName;

    }
}
