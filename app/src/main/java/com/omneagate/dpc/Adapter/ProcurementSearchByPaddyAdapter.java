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
import com.omneagate.dpc.Model.DpcCapDto;
import com.omneagate.dpc.Model.DpcTruckMemoDto;
import com.omneagate.dpc.Model.FarmerRegistrationDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Utility.DBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 4/10/16.
 */
public class ProcurementSearchByPaddyAdapter extends BaseAdapter {

    private Context context;

    public List<DPCProcurementDto> procurementList = new ArrayList<>();

    public ProcurementSearchByPaddyAdapter(Context context, List<DPCProcurementDto> procurementList) {
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


        if (convertView == null) {
            holder = new ProcurementHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_search_procurement_paddy, viewGroup, false);
            holder.procurement_num = (TextView) convertView.findViewById(R.id.procurement_num);
//            holder.proc_addy_variety = (TextView) convertView.findViewById(R.id.proc_paddy_variety);
            holder.farmer_code = (TextView) convertView.findViewById(R.id.farmer_code);
            holder.proc_moisture = (TextView) convertView.findViewById(R.id.proc_moisture);
            holder.number_txt = (TextView) convertView.findViewById(R.id.number);
            holder.net_qty = (TextView) convertView.findViewById(R.id.proc_qty);
            holder.linear_parent = (LinearLayout) convertView.findViewById(R.id.linear_parent);
            holder.proc_date_time = (TextView) convertView.findViewById(R.id.proc_date_time);

            convertView.setTag(holder);


        } else {
            holder = (ProcurementHolder) convertView.getTag();
        }

        int snumber = position + 1;

        if (getItem(position).getSyncStatus().equalsIgnoreCase("A")) {
            holder.linear_parent.setBackgroundColor(context.getResources().getColor(R.color.offline_truck_memo));
        } else {
            holder.linear_parent.setBackgroundColor(context.getResources().getColor(R.color.unsynced));
        }

        holder.number_txt.setText("" + snumber);

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

        String procurementDate = getItem(position).getTxnDateTime();
        String formatedDate = "";
        String oldFormat = "yyyy-MM-dd HH:mm:ss";
        String newFormat = "dd-MM-yyyy hh:mm:ss";

        SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
        Date myDate = null;
        try {
            myDate = dateFormat.parse(procurementDate);
            SimpleDateFormat timeFormat = new SimpleDateFormat(newFormat);
            formatedDate = timeFormat.format(myDate);
            Log.e("truckMemoDate", "output date " + formatedDate);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        holder.proc_date_time.setText(formatedDate);

        long paddy_id = getItem(position).getPaddyCategoryDto().getId();
        String paddy = DBHelper.getInstance(context).getpaddy(paddy_id);
//        holder.proc_addy_variety.setText(paddy);
        long farmer_id = getItem(position).getFarmerRegistrationDto().getId();
        FarmerRegistrationDto farmer_dto = DBHelper.getInstance(context).getAssociatedFarmerCode(farmer_id);
        holder.farmer_code.setText("" + farmer_dto.getFarmerCode());
        holder.proc_moisture.setText("" + getItem(position).getMoistureContent());
        holder.net_qty.setText("" + getItem(position).getNetWeight());

        return convertView;
    }


    class ProcurementHolder {
        TextView number_txt, procurement_num, proc_addy_variety, farmer_code, proc_moisture, net_qty, proc_date_time;
        LinearLayout linear_parent;

    }
}
