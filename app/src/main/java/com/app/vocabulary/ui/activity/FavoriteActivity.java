package com.app.vocabulary.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.app.vocabulary.R;
import com.app.vocabulary.adapter.VocabularyListAdapter;
import com.app.vocabulary.databinding.ActivityDashboardBinding;
import com.app.vocabulary.databinding.ActivityFavoriteBinding;
import com.app.vocabulary.room.WordEntity;
import com.app.vocabulary.utils.Constants;
import com.app.vocabulary.utils.CustomProgressDialog;
import com.app.vocabulary.utils.FirebaseRepo;
import com.app.vocabulary.utils.SharedPreferenceUtil;
import com.app.vocabulary.utils.Tools;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {
    private ActivityFavoriteBinding binding;

    private SharedPreferenceUtil sharedPreferenceUtil;
    private static final String TAG = "FavoriteActivity";
    private FirebaseFirestore firebaseFirestore;
    CustomProgressDialog customProgressDialog;

    List<WordEntity> modelList = new ArrayList<>();

    private VocabularyListAdapter vocabularyListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoriteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(this);
        firebaseFirestore = FirebaseRepo.createInstance();
        customProgressDialog = new CustomProgressDialog(FavoriteActivity.this, "Please wait....");
        getFavoriteList();

    }

    private void getFavoriteList() {
        modelList.clear();
        customProgressDialog.show();
        CollectionReference collectionRef = firebaseFirestore.collection(Constants.FAVORITE_COLLECTION_NAME);

        Query query = collectionRef.whereEqualTo("user_id", sharedPreferenceUtil.getUserId());


        query.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        customProgressDialog.dismiss();
                        if (documentSnapshot.exists()) {
                            WordEntity model = documentSnapshot.toObject(WordEntity.class);
                            modelList.add(model);
                            setAdapter();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    customProgressDialog.dismiss();
                    // Handle any errors that occur while fetching documents
                    Tools.logs(TAG, "Error getting documents: " + e);
                });

    }


    private void setAdapter() {
        if (modelList != null && modelList.size() > 0) {
            vocabularyListAdapter = new VocabularyListAdapter(FavoriteActivity.this, modelList);
            binding.rvVocabulary.setAdapter(vocabularyListAdapter);
            binding.rvVocabulary.setVisibility(View.VISIBLE);
        } else {
            binding.rvVocabulary.setVisibility(View.GONE);
        }
    }
}