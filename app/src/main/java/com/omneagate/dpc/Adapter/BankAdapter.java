package com.omneagate.dpc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Model.BankDto;
import com.omneagate.dpc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 28/7/16.
 */
public class BankAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<BankDto> society_name;

    public BankAdapter(Context context, List<BankDto> society_name) {
        this.context = context;
        this.society_name = (ArrayList<BankDto>) society_name;
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
        BankHolder holder = null;
        holder = new BankHolder();

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_bank_name, viewGroup, false);
            holder.bank_name = (TextView) convertView.findViewById(R.id.text_view_bank_name);


            convertView.setTag(holder);
            convertView.setTag(R.id.text_view_bank_name, holder.bank_name);


        } else {
            holder = (BankHolder) convertView.getTag();
        }


        if (!GlobalAppState.language.equalsIgnoreCase("ta")) {
            holder.bank_name.setText(society_name.get(position).getBankName());
        } else {
            holder.bank_name.setText(society_name.get(position).getLname());
        }

        return convertView;
    }

    class BankHolder {
        TextView bank_name;

    }
}
