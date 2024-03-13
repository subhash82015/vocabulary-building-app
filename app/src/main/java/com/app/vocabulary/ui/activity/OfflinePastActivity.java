package com.app.vocabulary.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.app.vocabulary.R;
import com.app.vocabulary.adapter.VocabularyListAdapter;
import com.app.vocabulary.databinding.ActivityDashboardBinding;
import com.app.vocabulary.databinding.ActivityOfflinePastBinding;
import com.app.vocabulary.room.AppApplication;
import com.app.vocabulary.room.WordDao;
import com.app.vocabulary.room.WordEntity;
import com.app.vocabulary.utils.Constants;
import com.app.vocabulary.utils.CustomProgressDialog;
import com.app.vocabulary.utils.FirebaseRepo;
import com.app.vocabulary.utils.SharedPreferenceUtil;
import com.app.vocabulary.utils.Tools;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class OfflinePastActivity extends AppCompatActivity implements AsyncResponse<List<WordEntity>>, AdapterView.OnItemSelectedListener {
    private ActivityOfflinePastBinding binding;

    private SharedPreferenceUtil sharedPreferenceUtil;
    private static final String TAG = "OfflinePastActivity";
    private FirebaseFirestore firebaseFirestore;
    CustomProgressDialog customProgressDialog;
    ListenerRegistration userListener;

    List<WordEntity> resultNew;
    List<WordEntity> modelList = new ArrayList<>();


    private VocabularyListAdapter vocabularyListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOfflinePastBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(this);
        firebaseFirestore = FirebaseRepo.createInstance();
        customProgressDialog = new CustomProgressDialog(OfflinePastActivity.this, "Please wait....");
        binding.spMode.setOnItemSelectedListener(this);
        getRoomList();
    }

    private void getRoomList() {
        // List<WordEntity> entities = AppApplication.database.wordDao().getAllEntities();
        // new InsertAsyncTask(AppApplication.database.wordDao()).execute();

        new GetEntitiesAsyncTask(AppApplication.database.wordDao(), this).execute();

    }

    @Override
    public void processFinish(List<WordEntity> result) {
        Tools.logs(TAG, "processFinish " + result.size());
        resultNew = result;
        // binding.tvOfflineCount.setText(result.size() + " Words");
    }

    private void setAdapter() {
        if (resultNew != null && resultNew.size() > 0) {
            vocabularyListAdapter = new VocabularyListAdapter(OfflinePastActivity.this, resultNew);
            binding.rvVocabulary.setAdapter(vocabularyListAdapter);
            binding.rvVocabulary.setVisibility(View.VISIBLE);
        } else {
            binding.rvVocabulary.setVisibility(View.GONE);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedItem = adapterView.getItemAtPosition(i).toString();

        if (selectedItem.equals("Offline Vocabulary")) {
            setAdapter();
        } else if (selectedItem.equals("Past Vocabulary")) {
            getPastList();
        } else {
            binding.rvVocabulary.setVisibility(View.GONE);
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getPastList() {
        modelList.clear();
        customProgressDialog.show();
        CollectionReference collectionRef = firebaseFirestore.collection(Constants.NOTIFICATION_COLLECTION_NAME);

        collectionRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        customProgressDialog.dismiss();
                        if (documentSnapshot.exists()) {
                            WordEntity model = documentSnapshot.toObject(WordEntity.class);
                            modelList.add(model);
                            setAdapter1();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    customProgressDialog.dismiss();
                    // Handle any errors that occur while fetching documents
                    Tools.logs(TAG, "Error getting documents: " + e);
                });

    }

    private void setAdapter1() {
        if (modelList != null && modelList.size() > 0) {
            vocabularyListAdapter = new VocabularyListAdapter(OfflinePastActivity.this, modelList);
            binding.rvVocabulary.setAdapter(vocabularyListAdapter);
            binding.rvVocabulary.setVisibility(View.VISIBLE);
        } else {
            binding.rvVocabulary.setVisibility(View.GONE);
        }
    }


    public class GetEntitiesAsyncTask extends AsyncTask<Void, Void, List<WordEntity>> {
        private WordDao wordDao;
        private AsyncResponse<List<WordEntity>> delegate;

        public GetEntitiesAsyncTask(WordDao wordDao, AsyncResponse<List<WordEntity>> delegate) {
            this.wordDao = wordDao;
            this.delegate = delegate;
        }

        @Override
        protected List<WordEntity> doInBackground(Void... voids) {
            return wordDao.getAllEntities();
        }

        @Override
        protected void onPostExecute(List<WordEntity> entities) {
            delegate.processFinish(entities);
        }
    }
}