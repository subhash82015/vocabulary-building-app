package com.app.vocabulary.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


import com.app.vocabulary.databinding.VocabularyItemsBinding;
import com.app.vocabulary.room.WordEntity;

import java.util.ArrayList;
import java.util.List;

public class VocabularyListAdapter extends RecyclerView.Adapter<VocabularyListAdapter.ViewHolder> {
    private VocabularyItemsBinding binding;
    private Context context;
    private List<WordEntity> list;


    public VocabularyListAdapter(Context context, List<WordEntity> list) {
        this.context = context;
        this.list = list != null ? list : new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = VocabularyItemsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        VocabularyItemsBinding binding = VocabularyItemsBinding.bind(holder.itemView);


        binding.tvWord.setText(list.get(position).getWord());
        binding.tvSynonyms.setText(list.get(position).getSynonyms());
        binding.tvAntonyms.setText(list.get(position).getAntonyms());
        binding.tvDescription.setText(list.get(position).getDescription());
        binding.tvDate.setText(list.get(position).getDate());


        binding.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(
                        Intent.EXTRA_TEXT, "Word : " + list.get(position).getWord() + "\nDescription : " + list.get(position).getDescription() + "\n"
                                + "Synonyms: " + list.get(position).getDate() + "\n" + "Antonyms: " + list.get(position).getAntonyms()
                );
                intent.setType("text/plain");
                context.startActivity(Intent.createChooser(intent, "Send To"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(VocabularyItemsBinding itemView) {
            super(itemView.getRoot());
        }
    }
}
