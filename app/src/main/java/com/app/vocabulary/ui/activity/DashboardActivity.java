package com.app.vocabulary.ui.activity;

import static kotlinx.coroutines.CoroutineScopeKt.CoroutineScope;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.vocabulary.R;
import com.app.vocabulary.databinding.ActivityDashboardBinding;
import com.app.vocabulary.databinding.ActivityLoginBinding;
import com.app.vocabulary.room.AppApplication;
import com.app.vocabulary.room.WordDao;
import com.app.vocabulary.room.WordDatabase;
import com.app.vocabulary.room.WordEntity;
import com.app.vocabulary.utils.Constants;
import com.app.vocabulary.utils.CustomProgressDialog;
import com.app.vocabulary.utils.FirebaseRepo;
import com.app.vocabulary.utils.SharedPreferenceUtil;
import com.app.vocabulary.utils.Tools;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;


public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private SharedPreferenceUtil sharedPreferenceUtil;
    private static final String TAG = "DashboardActivity";
    private FirebaseFirestore firebaseFirestore;
    CustomProgressDialog customProgressDialog;
    ListenerRegistration userListener;
    String synonyms = "", word = "", antonyms = "", date = "", description = "";

    Long id, isBookmarks, isToday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();

    }

    private void init() {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(this);
        firebaseFirestore = FirebaseRepo.createInstance();
        customProgressDialog = new CustomProgressDialog(DashboardActivity.this, "Please wait....");
        checkUser(getCurrentData());
        clickHandle();
        setViews();
    }

    private void setViews() {
        binding.tvFullName.setText("Hi, " + sharedPreferenceUtil.getUserDetails(Constants.FULL_NAME));
        binding.tvEmail.setText(sharedPreferenceUtil.getUserDetails(Constants.EMAIL));
        binding.btnMobile.setText("Mobile No. " + sharedPreferenceUtil.getUserDetails(Constants.MOBILE));
    }

    private void clickHandle() {
        binding.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(
                        Intent.EXTRA_TEXT, "Word : " + word + "\nDescription : " + description + "\n"
                                + "Synonyms: " + synonyms + "\n" + "Antonyms: " + antonyms
                );
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Send To"));

            }
        });
    }

    private String getCurrentData() {
        Date currentDate = new Date();

        // Define the date format pattern
        String dateFormatPattern = "dd-MM-yyyy";

        // Create a SimpleDateFormat object with the specified pattern
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormatPattern, Locale.getDefault());

        // Format the current date using the SimpleDateFormat
        String formattedDate = simpleDateFormat.format(currentDate);

        // Now, formattedDate contains the current date in the "dd-MM-yyyy" format
        Log.d("CurrentDate", "Formatted Date: " + formattedDate);
        return formattedDate;
    }


    public void checkUser(String date) {
        customProgressDialog.show();
        CollectionReference usersRef = firebaseFirestore.collection(Constants.NOTIFICATION_COLLECTION_NAME);
        // CollectionReference usersRef = firebaseFirestore.collection(Constants.ACCOUNT_COLLECTION_NAME);

        Query query = usersRef.whereEqualTo("date", date);
        query.get().addOnCompleteListener(task -> {
            customProgressDialog.dismiss();
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                Log.d(TAG, "Number of Documents: " + (querySnapshot != null ? querySnapshot.size() : 0));

                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        String documentDate = document.getString("date");
                        Log.d(TAG, "Document Date: " + documentDate);

                        if (date.equals(documentDate)) {
                            getUserInfo(document);
                            return;
                        }
                    }
                    setVisibility(false);
                    // Tools.showToast(DashboardActivity.this, "No matching documents for the specified date!");
                } else {
                    // Tools.showToast(DashboardActivity.this, "Query result is null or empty!");
                    setVisibility(false);
                }
            } else {
                Tools.logs(TAG, "Error: " + task.getException());
                Tools.showToast(DashboardActivity.this, "Error fetching documents!");
                setVisibility(false);
            }
        });
    }


    private void getUserInfo(QueryDocumentSnapshot document) {
        Tools.logs(TAG, "User Details  " + document.getData());
        Map<String, Object> documentData = document.getData();
        if (documentData != null) {
            id = (Long) documentData.get("id");
            isBookmarks = (Long) documentData.get("isBookmarks");
            isToday = (Long) documentData.get("isToday");
            synonyms = (String) documentData.get("synonyms");
            word = (String) documentData.get("word");
            antonyms = (String) documentData.get("antonyms");
            date = (String) documentData.get("date");
            description = (String) documentData.get("description");

            setVisibility(true);
            binding.tvWord.setText(word);
            binding.tvSynonyms.setText(synonyms);
            binding.tvAntonyms.setText(antonyms);
            binding.tvDescription.setText(description);
            saveInRoom();
        }
    }

    private void setVisibility(boolean flag) {
        if (flag) {
            binding.tvNoWords.setVisibility(View.VISIBLE);
            binding.cvCard.setVisibility(View.VISIBLE);
        } else {
            binding.tvNoWords.setVisibility(View.GONE);
            binding.cvCard.setVisibility(View.GONE);
        }
    }

    private void saveInRoom() {
        try {
            WordEntity entity = new WordEntity();
            entity.setWord_id(Math.toIntExact(id));
            entity.setWord(word);
            entity.setAntonyms(antonyms);
            entity.setDate(date);
            entity.setDescription(description);
            entity.setSynonyms(synonyms);
            entity.setBookmarks(Math.toIntExact(isBookmarks));


            WordDatabase database = AppApplication.database;
            if (database != null) {
                new InsertAsyncTask(database.yourDao()).execute(entity);

               // database.yourDao().insert(entity); // Replace with your actual operation
               // AppApplication.database.yourDao().insert(entity);
                Tools.logs(TAG, "saveInRoom: Save record" );
                Tools.showToast(DashboardActivity.this, "Save record!");

            } else {
                // Handle the case where the database is null
                // For example, show a message or log an error
                System.out.println("Database is null");
                Tools.logs(TAG, "Database is null" );

            }

        } catch (Exception e) {
            Tools.logs(TAG, "Exception: " + e.getMessage());

        }

    }

    public class InsertAsyncTask extends AsyncTask<WordEntity, Void, Void> {
        private WordDao dao;

        public InsertAsyncTask(WordDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(WordEntity... entities) {
            dao.insert(entities[0]);
            return null;
        }
    }

}