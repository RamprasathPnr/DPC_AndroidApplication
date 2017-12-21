package com.omneagate.dpc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.omneagate.dpc.Activity.ViewFarmerDetailsActivity;
import com.omneagate.dpc.Model.FarmerListLocalDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Utility.DBHelper;

import java.util.List;

/**
 * Created by user on 8/8/16.
 */
public class FarmerListAdapter extends RecyclerView.Adapter<FarmerListAdapter.LandsAdapterHolder> {


    private Context context;
    List<FarmerListLocalDto> list_farmer;
    private LayoutInflater mLayoutInflater;
    private DBHelper db;

    public FarmerListAdapter(Context context, List<FarmerListLocalDto> list_farmer) {
        this.context = context;
        this.list_farmer = list_farmer;
        this.mLayoutInflater = LayoutInflater.from(context);
        db = DBHelper.getInstance(context);
    }


    @Override
    public LandsAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_farmer_list, parent, false);
        return new LandsAdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LandsAdapterHolder holder, int position) {

        try {
            int snumber = position + 1;
            holder.number.setText("" + snumber);
            holder.ref_number.setText(list_farmer.get(position).getReference_number());
            reference_click(holder.ref_number);
            holder.fr_name.setText(list_farmer.get(position).getFarmer_name());
            holder.mob_number.setText(list_farmer.get(position).getMobileNumber());
            long fm_bank_id = list_farmer.get(position).getBank_id();
            holder.no_lands.setText(list_farmer.get(position).getLandCount());
//            db = new DBHelper(context);
//            db.getWritableDatabase();
            String bnk = db.getAssociatedBankName(fm_bank_id);
            holder.bank_name.setText(bnk);
//            db = new DBHelper(context);
//            db.getWritableDatabase();
            String aadhar = list_farmer.get(position).getAadhar();
//            Log.e("AADHAR IN ADAPTER", aadhar);
            if (aadhar == null || aadhar.equalsIgnoreCase("null") || aadhar.equalsIgnoreCase("")) {
                holder.adhr.setImageResource(R.drawable.ic_non_aadhar);
            } else {
                holder.adhr.setImageResource(R.drawable.ic_aadhar);
            }
            String ration = list_farmer.get(position).getRationCardNumber();
            if (ration == null || ration.equalsIgnoreCase("null") || ration.equalsIgnoreCase("")) {
                holder.ration_number.setImageResource(R.drawable.ic_non_aadhar);
            } else {
                holder.ration_number.setImageResource(R.drawable.ic_aadhar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void reference_click(final TextView ref_number) {
        ref_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent view = new Intent(context, ViewFarmerDetailsActivity.class);
                view.putExtra("reference_no", ref_number.getText().toString());
                context.startActivity(view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_farmer.size();
    }

    public class LandsAdapterHolder extends RecyclerView.ViewHolder {
        TextView number, ref_number, fr_name, mob_number, bank_name, no_lands;
        ImageView adhr, ration_number;

        public LandsAdapterHolder(View v) {
            super(v);
            number = (TextView) v.findViewById(R.id.number);
            ref_number = (TextView) v.findViewById(R.id.ref_number);
            fr_name = (TextView) v.findViewById(R.id.fr_name);
            mob_number = (TextView) v.findViewById(R.id.mob_number);
            bank_name = (TextView) v.findViewById(R.id.bank_name);
            no_lands = (TextView) v.findViewById(R.id.num_land);
            adhr = (ImageView) v.findViewById(R.id.adhr);
            ration_number = (ImageView) v.findViewById(R.id.ration);
            no_lands = (TextView) v.findViewById(R.id.num_land);
        }
    }
}
