package com.omneagate.dpc.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Activity.ViewLandDetailsActivity;
import com.omneagate.dpc.Model.FarmerLandDetailsDto;
import com.omneagate.dpc.Model.FarmerRegistrationRequestDto;
import com.omneagate.dpc.R;

import java.util.List;

/**
 * Created by user on 4/8/16.
 */
public class LandsAdapter extends RecyclerView.Adapter<LandsAdapter.LandsAdapterHolder> {
    private Context context;
    List<FarmerLandDetailsDto> list_lands;
    private LayoutInflater mLayoutInflater;
    FarmerRegistrationRequestDto farmerRegistrationRequest;

    public LandsAdapter(Context context, List<FarmerLandDetailsDto> list_lands, FarmerRegistrationRequestDto farmerRegistrationRequest) {
        this.context = context;
        this.list_lands = list_lands;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.farmerRegistrationRequest = farmerRegistrationRequest;
    }

    @Override
    public LandsAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_former_lands, parent, false);
        return new LandsAdapterHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LandsAdapterHolder holder, final int position) {
        try {
            if (GlobalAppState.language.equalsIgnoreCase("ta")) {
                holder.village.setText(list_lands.get(position).getDpcVillageDto().getLvillageName());
            } else {
                holder.village.setText(list_lands.get(position).getDpcVillageDto().getName());
            }

            if (GlobalAppState.language.equalsIgnoreCase("ta")) {
                holder.crop.setText(list_lands.get(position).getPaddyCategoryDto().getLname());
            } else {
                holder.crop.setText(list_lands.get(position).getPaddyCategoryDto().getName());
            }
            holder.lord.setText(list_lands.get(position).getMainLandLordName());
//            holder.loan.setText(list_lands.get(position).getLoanBookNumber());
            holder.survey.setText(list_lands.get(position).getSurveyNumber());
            holder.view_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent view = new Intent(context, ViewLandDetailsActivity.class);
                    view.putExtra("farmer_lands", list_lands.get(position));
                    view.putExtra("classname", ((Activity) context).getLocalClassName());
                    view.putExtra("FarmerRegistrationRequest", farmerRegistrationRequest);
                    context.startActivity(view);
//                    Log.e("getLocalClassName", ((Activity) context).getLocalClassName());
//                    Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list_lands.size();
    }

    public class LandsAdapterHolder extends RecyclerView.ViewHolder {
        TextView village, crop, lord, loan, survey;
        LinearLayout view_btn;

        public LandsAdapterHolder(View v) {
            super(v);
            village = (TextView) v.findViewById(R.id.t_village);
            crop = (TextView) v.findViewById(R.id.t_crop);
            lord = (TextView) v.findViewById(R.id.t_lord);
//            loan=(TextView)v.findViewById(R.id.t_loan);
            survey = (TextView) v.findViewById(R.id.t_survey_num);
            view_btn = (LinearLayout) v.findViewById(R.id.button_view);
        }
    }
}
