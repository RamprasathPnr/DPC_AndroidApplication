package com.omneagate.dpc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Model.FirstSyncTalukDto;
import com.omneagate.dpc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 29/7/16.
 */
public class TalukAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<FirstSyncTalukDto> taluk_name;

    public TalukAdapter(Context context, List<FirstSyncTalukDto> taluk_name) {
        this.context = context;
        this.taluk_name = (ArrayList<FirstSyncTalukDto>) taluk_name;
    }

    @Override
    public int getCount() {
        return taluk_name.size();
    }

    @Override
    public Object getItem(int position) {
        return taluk_name.get(position);
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
            holder.taluk_name.setText(taluk_name.get(position).getName());
        } else {
            holder.taluk_name.setText(taluk_name.get(position).getLtalukName());

        }

        return convertView;
    }

    class TalukHolder {
        TextView taluk_name;

    }
}
