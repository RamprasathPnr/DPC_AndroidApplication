package com.omneagate.dpc.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.omneagate.dpc.Activity.AssociatedFarmerViewDetails;
import com.omneagate.dpc.Model.AssosiatedFarmersListLocalDto;
import com.omneagate.dpc.R;
import com.omneagate.dpc.Utility.DBHelper;

import java.util.List;

/**
 * Created by user on 8/8/16.
 */
public class AssociatedFarmerListAdapter extends RecyclerView.Adapter<AssociatedFarmerListAdapter.LandsAdapterHolder> {


    private Context context;
    List<AssosiatedFarmersListLocalDto> list_farmer;
    private LayoutInflater mLayoutInflater;
    private DBHelper db;

    public AssociatedFarmerListAdapter(Context context, List<AssosiatedFarmersListLocalDto> list_farmer) {
        this.context = context;
        this.list_farmer = list_farmer;
        this.mLayoutInflater = LayoutInflater.from(context);
    }


    @Override
    public LandsAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_associated_farmer_list, parent, false);
        return new LandsAdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LandsAdapterHolder holder, final int position) {

        try {
            int snumber = position + 1;
            final int pos=position;
            holder.number.setText("" + snumber);
            holder.farmer_code.setText(list_farmer.get(position).getFarmerCode());
            holder.fm_name.setText(list_farmer.get(position).getFarmerName());
            holder.mob_number.setText(list_farmer.get(position).getMobileNumber());
            holder.farmer_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent view=new Intent(context, AssociatedFarmerViewDetails.class);
                    view.putExtra("farmer_code",list_farmer.get(pos).getFarmerCode());

                    context.startActivity(view);
                }
            });
            long fm_bank_id = list_farmer.get(position).getBank_id();

//            holder.fm_branch.setText(list_farmer.get(position).getBranchName());
            holder.fm_land_count.setText(list_farmer.get(position).getLandCount());


            db = new DBHelper(context);
            db.getWritableDatabase();
            String bnk = db.getAssociatedBankName(fm_bank_id);
//            if (GlobalAppState.language.equalsIgnoreCase("ta")) {
            holder.fm_bank.setText(bnk);

            String aadhar = list_farmer.get(position).getAadhaarNumber();

            String ration = list_farmer.get(position).getRationCardNumber();


            if (aadhar.equalsIgnoreCase("") || aadhar.equalsIgnoreCase("null")) {
                holder.fm_aadhaar.setImageResource(R.drawable.ic_non_aadhar);
            } else {
                holder.fm_aadhaar.setImageResource(R.drawable.ic_aadhar);
            }

            if (ration.equalsIgnoreCase("") || ration.equalsIgnoreCase("null")) {
                holder.fm_ration.setImageResource(R.drawable.ic_non_aadhar);
            } else {
                holder.fm_ration.setImageResource(R.drawable.ic_aadhar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list_farmer.size();
    }

    public class LandsAdapterHolder extends RecyclerView.ViewHolder {


        TextView number, farmer_code, fm_name, mob_number, fm_bank, fm_branch, fm_land_count;
        ImageView fm_aadhaar, fm_ration;

        public LandsAdapterHolder(View v) {
            super(v);
            number = (TextView) v.findViewById(R.id.number);
            farmer_code = (TextView) v.findViewById(R.id.farmer_code);
            fm_name = (TextView) v.findViewById(R.id.fm_name);
            mob_number = (TextView) v.findViewById(R.id.mob_number);
            fm_bank = (TextView) v.findViewById(R.id.fm_bank);
//            fm_branch = (TextView) v.findViewById(R.id.fm_branch);
            fm_aadhaar = (ImageView) v.findViewById(R.id.fm_aadhaar);
            fm_ration = (ImageView) v.findViewById(R.id.fm_ration);
            fm_land_count = (TextView) v.findViewById(R.id.fm_land_count);
        }
    }
}
