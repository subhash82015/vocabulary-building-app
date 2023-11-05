package com.demo.collegeerp.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.collegeerp.R;
import com.demo.collegeerp.models.MenuItems;
import com.demo.collegeerp.utils.OnItemClickListener;

import java.util.ArrayList;

public class DashboardOptionsAdapter extends RecyclerView.Adapter<DashboardOptionsAdapter.MyViewHolder> {
    private Context context;
    private OnItemClickListener onItemClickListener;
    private ArrayList<MenuItems> list;

    public DashboardOptionsAdapter(Context context, OnItemClickListener onItemClickListener, ArrayList<MenuItems> list) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.list = list;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button btn_option;
        ImageView ivIcon;

        public MyViewHolder(View view) {
            super(view);
            btn_option = view.findViewById(R.id.btn_option);
            ivIcon = view.findViewById(R.id.ivIcon);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dashboard_card_options_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MenuItems model = list.get(position);
        Log.d("Dashboad ", "loadServicesOptions: " + model.getName());
        holder.btn_option.setText(model.getName());
        holder.ivIcon.setBackgroundResource(model.getIcon());
        holder.itemView.setOnClickListener(view -> onItemClickListener.getSelectedValue(model.getName(), model.getId()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}