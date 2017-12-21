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

import com.omneagate.dpc.Activity.ApprovedLandDetailsActivity;
import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Model.FarmerApprovedLandDetailsDto;
import com.omneagate.dpc.Model.FarmerRegistrationDto;
import com.omneagate.dpc.R;

import java.util.List;

/**
 * Created by user1 on 14/9/16.
 */
public class Approvedlandadapter extends RecyclerView.Adapter<Approvedlandadapter.ApprovedlandHolder> {
    private Context context;
    List<FarmerApprovedLandDetailsDto> list_lands;
    private LayoutInflater mLayoutInflater;
    FarmerRegistrationDto farmerRegistration;

    public Approvedlandadapter(Context context, List<FarmerApprovedLandDetailsDto> list_lands, FarmerRegistrationDto farmerRegistration) {
        this.context = context;
        this.list_lands = list_lands;
        this.mLayoutInflater = LayoutInflater.from(context);
        this.farmerRegistration = farmerRegistration;
    }

    @Override
    public ApprovedlandHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_former_lands, parent, false);
        return new ApprovedlandHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ApprovedlandHolder holder, final int position) {
        try {

            if (GlobalAppState.language.equalsIgnoreCase("ta")) {
                holder.village.setText(list_lands.get(position).getDpcVillageDto().getLvillageName());
            } else {
                holder.village.setText(list_lands.get(position).getDpcVillageDto().getName());
            }


            if (GlobalAppState.language.equalsIgnoreCase("ta")) {
                holder.crop.setText(list_lands.get(position).getPaddyCategoryDto().getName());
            }else{
                holder.crop.setText(list_lands.get(position).getPaddyCategoryDto().getName());
            }



            holder.lord.setText(list_lands.get(position).getMainLandLordName());
            holder.survey.setText(list_lands.get(position).getSurveyNumber());
            holder.view_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent view = new Intent(context, ApprovedLandDetailsActivity.class);
                    view.putExtra("farmer_lands", list_lands.get(position));
                    view.putExtra("classname", ((Activity) context).getLocalClassName());
                    view.putExtra("FarmerRegistrationRequest", farmerRegistration);
                    context.startActivity(view);
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

    public class ApprovedlandHolder extends RecyclerView.ViewHolder {
        TextView village, crop, lord, loan, survey;
        LinearLayout view_btn;

        public ApprovedlandHolder(View v) {
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
