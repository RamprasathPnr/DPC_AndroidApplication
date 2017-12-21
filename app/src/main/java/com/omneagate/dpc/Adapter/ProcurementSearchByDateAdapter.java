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

import com.omneagate.dpc.Activity.ProcurementDuplicatePrintActivity;
import com.omneagate.dpc.Model.DPCProcurementDto;
import com.omneagate.dpc.Model.FarmerRegistrationDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Utility.DBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 4/10/16.
 */
public class ProcurementSearchByDateAdapter extends BaseAdapter {

    private Context context;

    public List<DPCProcurementDto> procurementList = new ArrayList<>();


    public ProcurementSearchByDateAdapter(Context context, List<DPCProcurementDto> procurementList) {
        this.context = context;
        this.procurementList.addAll(procurementList);
    }

    @Override
    public int getCount() {
        return procurementList.size();
    }

    @Override
    public DPCProcurementDto getItem(int position) {
        return procurementList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ProcurementHolder holder = null;
        holder = new ProcurementHolder();

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.item_search_procurement_date, viewGroup, false);
            holder.procurement_num = (TextView) convertView.findViewById(R.id.procurement_num);
            holder.proc_date_time = (TextView) convertView.findViewById(R.id.proc_date_time);
            holder.farmer_code = (TextView) convertView.findViewById(R.id.farmer_code);
            holder.proc_moisture = (TextView) convertView.findViewById(R.id.proc_moisture);
            holder.number_txt = (TextView) convertView.findViewById(R.id.number);
            holder.net_qty = (TextView) convertView.findViewById(R.id.proc_qty);
            holder.linear_parent = (LinearLayout) convertView.findViewById(R.id.linear_parent);

            convertView.setTag(holder);
            /*convertView.setTag(R.id.procurement_num, holder.procurement_num);
            convertView.setTag(R.id.proc_date_time, holder.proc_date_time);
            convertView.setTag(R.id.farmer_code, holder.farmer_code);
            convertView.setTag(R.id.proc_moisture, holder.proc_moisture);
            convertView.setTag(R.id.number, holder.number_txt);
            convertView.setTag(R.id.proc_qty, holder.net_qty);
            convertView.setTag(R.id.linear_parent, holder.linear_parent);*/


        } else {
            holder = (ProcurementHolder) convertView.getTag();
        }

        int snumber = position + 1;

        if (getItem(position).getSyncStatus().equalsIgnoreCase("A")) {
            holder.linear_parent.setBackgroundColor(context.getResources().getColor(R.color.offline_truck_memo));
        }else{
            holder.linear_parent.setBackgroundColor(context.getResources().getColor(R.color.unsynced));
        }

        holder.number_txt.setText("" + snumber);
//        holder.procurement_num.setText(getItem(position).getProcurementReceiptNo());

        SpannableString content = new SpannableString(getItem(position).getProcurementReceiptNo());
        content.setSpan(new UnderlineSpan(), 0, getItem(position).getProcurementReceiptNo().length(), 0);
        holder.procurement_num.setText(content);

        holder.procurement_num.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DPCProcurementDto procurement_dto = getItem(position);
                Intent duplicate = new Intent(context, ProcurementDuplicatePrintActivity.class);
                duplicate.putExtra("procurement_dto", procurement_dto);
                context.startActivity(duplicate);
            }
        });

        long paddy_id = getItem(position).getPaddyCategoryDto().getId();
         String Paddy =DBHelper.getInstance(context).getpaddy(paddy_id);

        holder.proc_date_time.setText(Paddy);

        long farmer_id = getItem(position).getFarmerRegistrationDto().getId();
        FarmerRegistrationDto farmer_dto = DBHelper.getInstance(context).getAssociatedFarmerCode(farmer_id);

        Log.e("farmer_dto",""+farmer_dto.getFarmerCode());

        holder.farmer_code.setText("" + farmer_dto.getFarmerCode());

        holder.proc_moisture.setText(""+getItem(position).getMoistureContent());
        holder.net_qty.setText(""+getItem(position).getNetWeight());

        return convertView;
    }

    class ProcurementHolder {
        TextView number_txt, procurement_num, proc_date_time, farmer_code, proc_moisture,net_qty;
        LinearLayout linear_parent;

    }
}
