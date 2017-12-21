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
 * Created by user on 28/7/16.
 */
public class GuardianAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> guardian_type;

    public GuardianAdapter(Context context, List<String> guardian_type) {
        this.context = context;
        this.guardian_type = (ArrayList<String>) guardian_type;
    }

    @Override
    public int getCount() {
        return guardian_type.size();
    }

    @Override
    public Object getItem(int position) {
        return guardian_type.get(position);
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

            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_guardian_type, viewGroup, false);
            holder.guardian = (TextView) convertView.findViewById(R.id.text_view_guardian_type);


            convertView.setTag(holder);
            convertView.setTag(R.id.text_view_guardian_type, holder.guardian);


        } else {
            holder = (BankHolder) convertView.getTag();
        }


        if (!GlobalAppState.language.equalsIgnoreCase("ta")) {
            holder.guardian.setText(guardian_type.get(position));
        } else {
            holder.guardian.setText(guardian_type.get(position));
        }

        return convertView;
    }

    class BankHolder {
        TextView guardian;

    }
}
