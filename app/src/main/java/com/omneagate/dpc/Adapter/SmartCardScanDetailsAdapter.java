package com.omneagate.dpc.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omneagate.dpc.R;

/**
 * Created by user on 15/7/16.
 */
public class SmartCardScanDetailsAdapter extends RecyclerView.Adapter<SmartCardScanDetailsAdapter.SmartCardScanHolder> {

    private LayoutInflater mLayoutInflater;

    @Override
    public SmartCardScanHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mLayoutInflater.inflate(R.layout.item_smat_card_list, parent, false);
        return new SmartCardScanHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SmartCardScanHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class SmartCardScanHolder extends RecyclerView.ViewHolder {

        public SmartCardScanHolder(View v) {
            super(v);
            TextView name, date_of_birth;
        }
    }
}
