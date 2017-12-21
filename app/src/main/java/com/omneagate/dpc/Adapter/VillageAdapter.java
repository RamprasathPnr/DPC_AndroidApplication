package com.omneagate.dpc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Model.FirstSyncTalukDto;
import com.omneagate.dpc.Model.VillageDto;
import com.omneagate.dpc.Model.VillageDtoId;
import com.omneagate.dpc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 29/7/16.
 */
public class VillageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<VillageDtoId> village_name;

    public VillageAdapter(Context context, List<VillageDtoId> village_name) {
        this.context = context;
        this.village_name = (ArrayList<VillageDtoId>) village_name;
    }

    @Override
    public int getCount() {
        return village_name.size();
    }

    @Override
    public Object getItem(int position) {
        return village_name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        VillageHolder holder = null;
        holder = new VillageHolder();

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_village, viewGroup, false);
            holder.village_name = (TextView) convertView.findViewById(R.id.text_view_village_name);
            convertView.setTag(holder);
            convertView.setTag(R.id.text_view_village_name, holder.village_name);
        } else {
            holder = (VillageHolder) convertView.getTag();
        }

        if (!GlobalAppState.language.equalsIgnoreCase("ta")) {
            holder.village_name.setText(village_name.get(position).getName());
        } else {
            holder.village_name.setText(village_name.get(position).getVillagelname());
        }

        return convertView;
    }

    class VillageHolder {
        TextView village_name;
    }
}
