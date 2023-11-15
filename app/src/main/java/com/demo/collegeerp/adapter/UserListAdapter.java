package com.demo.collegeerp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.demo.collegeerp.databinding.UserListItemsBinding;
import com.demo.collegeerp.models.UsersResponse;
import com.demo.collegeerp.utils.Constants;
import com.demo.collegeerp.utils.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private UserListItemsBinding binding;
    private Context context;
    private List<UsersResponse> list;

    OnItemClickListener onItemClickListener;

    public UserListAdapter(Context context, List<UsersResponse> list, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list != null ? list : new ArrayList<>();
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = UserListItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserListItemsBinding binding = UserListItemsBinding.bind(holder.itemView);

        binding.tvName.setText(list.get(position).getFull_name() != null ? list.get(position).getFull_name() : "");
        if (list.get(position).getUsertype().equals(Constants.ADMIN)) {
            binding.tvUserType.setText("Admin");
        } else if (list.get(position).getUsertype().equals(Constants.STUDENT)) {
            binding.tvUserType.setText("Student");
        } else if (list.get(position).getUsertype().equals(Constants.PARENT)) {
            binding.tvUserType.setText("Parent");
        } else if (list.get(position).getUsertype().equals(Constants.DRIVER)) {
            binding.tvUserType.setText("Driver");
        }

        binding.tvCourseDetails.setText(list.get(position).getCourse() + "-" + list.get(position).getBranch() + "(" + list.get(position).getSection() + ")");
        binding.tvMobile.setText(list.get(position).getMobile() != null ? list.get(position).getMobile() : "");

        binding.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.getSelectedValue(list.get(position).getFull_name(), (int) list.get(position).getUserid().intValue());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(UserListItemsBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
