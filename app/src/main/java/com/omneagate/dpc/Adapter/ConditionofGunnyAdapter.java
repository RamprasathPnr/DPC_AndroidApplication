package com.omneagate.dpc.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.R;

import java.util.List;

/**
 * Created by root on 22/9/16.
 */
public class ConditionofGunnyAdapter extends BaseAdapter {

    private Context context;
    private List<String> Condition_of_gunnyList;

    public ConditionofGunnyAdapter(Context context,List<String> Condition_of_gunnyList){
    this.context=context;
    this.Condition_of_gunnyList=Condition_of_gunnyList;
    }

    @Override
    public int getCount() {
        return Condition_of_gunnyList.size();
    }

    @Override
    public Object getItem(int position) {
        return Condition_of_gunnyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewgroup) {
        ConditionGunnyHolder holder=null;
        holder =new ConditionGunnyHolder();

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_paddy_category, viewgroup, false);
            holder.ConditionofGunny = (TextView) convertView.findViewById(R.id.text_view_paddy_category);


            convertView.setTag(holder);
            convertView.setTag(R.id.text_view_guardian_type, holder.ConditionofGunny);

        } else {
            holder = (ConditionGunnyHolder) convertView.getTag();
        }

        if (!GlobalAppState.language.equalsIgnoreCase("ta")) {
            holder.ConditionofGunny.setText(Condition_of_gunnyList.get(position));
        } else {
            holder.ConditionofGunny.setText(Condition_of_gunnyList.get(position));
        }

        return convertView;
    }
    class ConditionGunnyHolder {
        TextView ConditionofGunny;
    }
}
