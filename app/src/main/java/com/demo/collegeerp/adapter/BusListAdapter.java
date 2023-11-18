package com.demo.collegeerp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.demo.collegeerp.databinding.BusListItemsBinding;
import com.demo.collegeerp.models.BusesResponse;
import com.demo.collegeerp.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.ViewHolder> {
    private BusListItemsBinding binding;
    private Context context;
    private List<BusesResponse> list;

    OnItemClickListener onItemClickListener;

    public BusListAdapter(Context context, List<BusesResponse> list, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list != null ? list : new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = BusListItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BusListItemsBinding binding = BusListItemsBinding.bind(holder.itemView);

        binding.tvName.setText(list.get(position).getDriver_name() != null ? list.get(position).getDriver_name() : "");
        binding.tvBusNumber.setText(list.get(position).getBus_number() != null ? list.get(position).getBus_number() : "");


        binding.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.getSelectedValue(list.get(position).getDriver_name(), (int) list.get(position).getId().intValue());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(BusListItemsBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
