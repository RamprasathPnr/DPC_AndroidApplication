package com.omneagate.dpc.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.omneagate.dpc.Activity.GlobalAppState;
import com.omneagate.dpc.Model.MenuDataDto;
import com.omneagate.dpc.R;

import java.util.List;

/**
 * Created by user on 27/6/16.
 */
public class MenuAdapter extends BaseAdapter {

    Context ct;
    List<MenuDataDto> menuList;

    public MenuAdapter(Context ct, List<MenuDataDto> menuList) {
        this.ct = ct;
        this.menuList = menuList;
    }


    @Override
    public int getCount() {
         return menuList.size();
    }

    @Override
    public Object getItem(int position) {
        return menuList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {



        final ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(ct).inflate(R.layout.menu_background, viewGroup, false);
            holder = new ViewHolder();
            holder.number = (TextView) convertView.findViewById(R.id.textViewMenu);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageViewMenu);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.number.setText(menuList.get(position).getName());
        if (GlobalAppState.language != null && GlobalAppState.language.equalsIgnoreCase("ta")) {
            holder.number.setText(menuList.get(position).getLName());
        }
        holder.imageView.setImageResource(menuList.get(position).getId());

        return convertView;
    }


    class ViewHolder {
        TextView number;
        ImageView imageView;
    }
}
