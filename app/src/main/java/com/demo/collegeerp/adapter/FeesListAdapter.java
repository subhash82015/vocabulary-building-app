package com.demo.collegeerp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.demo.collegeerp.databinding.FeesUserListItemsBinding;
import com.demo.collegeerp.models.post.AddFees;
import com.demo.collegeerp.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class FeesListAdapter extends RecyclerView.Adapter<FeesListAdapter.ViewHolder> {
    private FeesUserListItemsBinding binding;
    private Context context;
    private List<AddFees> list;

    OnItemClickListener onItemClickListener;

    public FeesListAdapter(Context context, List<AddFees> list) {
        this.context = context;
        this.list = list != null ? list : new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = FeesUserListItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FeesUserListItemsBinding binding = FeesUserListItemsBinding.bind(holder.itemView);

        binding.tvName.setText(list.get(position).getName() != null ? list.get(position).getName() : "");
        binding.tvAmount.setText(list.get(position).getAmount() != null ? list.get(position).getAmount() : "");
        binding.tvType.setText(list.get(position).getPayType() != null ? list.get(position).getPayType() : "");
        binding.tvRemarks.setText(list.get(position).getRemark() != null ? list.get(position).getRemark() : "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(FeesUserListItemsBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
