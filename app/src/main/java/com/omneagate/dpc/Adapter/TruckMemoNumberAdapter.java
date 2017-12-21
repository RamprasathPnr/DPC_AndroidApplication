package com.omneagate.dpc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omneagate.dpc.Activity.TruckMemoDuplicatePrintActivity;
import com.omneagate.dpc.Model.DpcCapDto;
import com.omneagate.dpc.Model.DpcTruckMemoDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Utility.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 4/10/16.
 */
public class TruckMemoNumberAdapter extends BaseAdapter {

    private Context context;

    private ArrayList<DpcTruckMemoDto> truckMemoList;


    public TruckMemoNumberAdapter(Context context, List<DpcTruckMemoDto> truckMemoList) {
        this.context = context;
        this.truckMemoList = (ArrayList<DpcTruckMemoDto>) truckMemoList;
    }

    @Override
    public int getCount() {
        return truckMemoList.size();
    }

    @Override
    public Object getItem(int position) {
        return truckMemoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        TruckHolder holder = null;
        holder = new TruckHolder();

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.item_search_truck_number, viewGroup, false);
            holder.truck_memo_number = (TextView) convertView.findViewById(R.id.truck_memo_number);
            holder.date_time = (TextView) convertView.findViewById(R.id.date_time);
            holder.paddy_name = (TextView) convertView.findViewById(R.id.paddy_name);
            holder.net_weight = (TextView) convertView.findViewById(R.id.net_weight);
            holder.number_txt = (TextView) convertView.findViewById(R.id.number);
            holder.linear_parent = (LinearLayout) convertView.findViewById(R.id.linear_parent);

            convertView.setTag(holder);
        } else {
            holder = (TruckHolder) convertView.getTag();
        }

        int snumber = position + 1;
        if (truckMemoList.get(position).getSyncStatus().equalsIgnoreCase("A")) {
            holder.linear_parent.setBackgroundColor(context.getResources().getColor(R.color.offline_truck_memo));
        }else{
            holder.linear_parent.setBackgroundColor(context.getResources().getColor(R.color.unsynced));
        }
        holder.number_txt.setText("" + snumber);

//        holder.truck_memo_number.setText(truckMemoList.get(position).getTruckMemoNumber());

        SpannableString content = new SpannableString(truckMemoList.get(position).getTruckMemoNumber());
        content.setSpan(new UnderlineSpan(), 0, truckMemoList.get(position).getTruckMemoNumber().length(), 0);
        holder.truck_memo_number.setText(content);


        holder.truck_memo_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DpcTruckMemoDto truck_dto = truckMemoList.get(position);

                Intent duplicate=new Intent(context,TruckMemoDuplicatePrintActivity.class);
                duplicate.putExtra("DpcTruckMemoDto",truck_dto);
                context.startActivity(duplicate);
            }
        });

        long cap_id = truckMemoList.get(position).getDpcCapDto().getId();
        Log.e("cap_id", "CAP ID IN ADAPTER" + cap_id);
        DpcCapDto cap = DBHelper.getInstance(context).getCapName(cap_id);
        holder.date_time.setText(cap.getGeneratedCode());

//        holder.date_time.setText(""+truckMemoList.get(position).getDpcCapDto().getId());
        long id = truckMemoList.get(position).getPaddyCategoryDto().getId();

        String paddy = DBHelper.getInstance(context).getpaddy(id);
        holder.paddy_name.setText(paddy);
        holder.net_weight.setText("" + truckMemoList.get(position).getNetQuantity());


        return convertView;
    }

    class TruckHolder {
        TextView number_txt, truck_memo_number, date_time, paddy_name, net_weight;
        LinearLayout linear_parent;

    }
}
