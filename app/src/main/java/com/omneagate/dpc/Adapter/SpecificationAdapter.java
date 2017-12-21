package com.omneagate.dpc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Model.DpcSpecificationDto;
import com.omneagate.dpc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 28/7/16.
 */
public class SpecificationAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DpcSpecificationDto> specification;

    public SpecificationAdapter(Context context, List<DpcSpecificationDto> specification) {
        this.context = context;
        this.specification = (ArrayList<DpcSpecificationDto>) specification;
    }

    @Override
    public int getCount() {
        return specification.size();
    }

    @Override
    public Object getItem(int position) {
        return specification.get(position);
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

            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_specification, viewGroup, false);
            holder.specification_nm = (TextView) convertView.findViewById(R.id.text_view_specification);

            convertView.setTag(holder);

        } else {
            holder = (BankHolder) convertView.getTag();
        }


        if (!GlobalAppState.language.equalsIgnoreCase("ta")) {
            holder.specification_nm.setText(specification.get(position).getSpecificationType());
        } else {
            holder.specification_nm.setText(specification.get(position).getSpecificationType());
        }

        return convertView;
    }

    class BankHolder {
        TextView specification_nm;

    }
}
