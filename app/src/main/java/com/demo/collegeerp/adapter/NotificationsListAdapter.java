package com.demo.collegeerp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.demo.collegeerp.databinding.NotificationsListItemsBinding;
import com.demo.collegeerp.models.NotificationResponse;

import java.util.ArrayList;
import java.util.List;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.ViewHolder> {
    private NotificationsListItemsBinding binding;
    private Context context;
    private List<NotificationResponse> list;

    public NotificationsListAdapter(Context context, List<NotificationResponse> list) {
        this.context = context;
        this.list = list != null ? list : new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = NotificationsListItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        NotificationsListItemsBinding binding = NotificationsListItemsBinding.bind(holder.itemView);

        binding.tvNotificationTitle.setText(list.get(position).getTitle() != null ? list.get(position).getTitle() : "");
        binding.tvDate.setText(list.get(position).getDt() != null ? list.get(position).getDt() : "");
        binding.tvBody.setText(list.get(position).getBody() != null ? list.get(position).getBody() : "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
