package com.app.vocabulary.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;

import com.app.vocabulary.databinding.ActivityDashboardBinding;
import com.app.vocabulary.models.AddFavorite;
import com.app.vocabulary.room.AppApplication;
import com.app.vocabulary.room.WordDao;
import com.app.vocabulary.room.WordDatabase;
import com.app.vocabulary.room.WordEntity;
import com.app.vocabulary.utils.Constants;
import com.app.vocabulary.utils.CustomProgressDialog;
import com.app.vocabulary.utils.FirebaseRepo;
import com.app.vocabulary.utils.SharedPreferenceUtil;
import com.app.vocabulary.utils.Tools;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class DashboardActivity extends AppCompatActivity implements AsyncResponse<List<WordEntity>> {

    private ActivityDashboardBinding binding;
    private SharedPreferenceUtil sharedPreferenceUtil;
    private static final String TAG = "DashboardActivity";
    private FirebaseFirestore firebaseFirestore;
    CustomProgressDialog customProgressDialog;

    List<WordEntity> modelList = new ArrayList<>();

    ListenerRegistration userListener;
    String synonyms = "", word = "", antonyms = "", date = "", description = "";

    Long id, isBookmarks, isToday, favorite_id = 0L;

    TextToSpeech t1;

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
        checkUser(getCurrentDate());
        clickHandle();
        setViews();
        getRoomList();
        getFavoriteList();
    }

    private void setViews() {
        binding.tvFullName.setText("Hi, " + sharedPreferenceUtil.getUserDetails(Constants.FULL_NAME));
        binding.tvEmail.setText(sharedPreferenceUtil.getUserDetails(Constants.EMAIL));
        binding.btnMobile.setText("Mobile No. " + sharedPreferenceUtil.getUserDetails(Constants.MOBILE));

        if (sharedPreferenceUtil.getUserTYPE()==1) {
            binding.llAddNotification.setVisibility(View.VISIBLE);
        } else {
            binding.llAddNotification.setVisibility(View.GONE);
        }

        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        binding.ivSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = word;
                //  Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }

    private void clickHandle() {
        binding.imgShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "Word : " + word + "\nDescription : " + description + "\n" + "Synonyms: " + synonyms + "\n" + "Antonyms: " + antonyms);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Send To"));

            }
        });
        binding.cvOfflinePast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, OfflinePastActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
        binding.cvFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, FavoriteActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        binding.cvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, AddVocabularyActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
        binding.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkFavorite();
            }
        });
    }

    private String getCurrentDate() {
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

    private String getCurrentDateTime() {
        Date currentDate = new Date();

        // Define the date format pattern
        String dateFormatPattern = "dd-MM-yyyy_HH:mm:ss";

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
            if (!sharedPreferenceUtil.getUserDetails(Constants.CURRENT_DATE).equals(getCurrentDate())) {
                sharedPreferenceUtil.setUserDetails(Constants.CURRENT_DATE, getCurrentDate());
                saveInRoom();
            }
        }
    }

    public void checkFavorite() {
        customProgressDialog.show();
        CollectionReference usersRef = firebaseFirestore.collection(Constants.FAVORITE_COLLECTION_NAME);
        Query query = usersRef.whereEqualTo("notification_id", id).whereEqualTo("user_id", sharedPreferenceUtil.getUserId());
        query.get().addOnCompleteListener(task -> {
            customProgressDialog.dismiss();
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        Tools.showToast(DashboardActivity.this, "Already added in Favorite");
                        customProgressDialog.dismiss();
                    }
                } else {
                    getLastFavoriteInfo();
                }
            } else {
                Tools.logs(TAG, "Error " + task.getException());
            }
        });
    }

    private void getLastFavoriteInfo() {
        CollectionReference collectionRef = firebaseFirestore.collection(Constants.FAVORITE_COLLECTION_NAME);


// Create a query to order the collection in descending order and limit to 1 document
        Query query = collectionRef.orderBy("favorite_id", Query.Direction.DESCENDING).limit(1);

// Perform the query
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Access the last document
                        // String lastDocumentId = document.getId();
                        // Now you can work with the data in 'document'


                        Tools.logs(TAG, "User Details  " + document.getData());
                        Map<String, Object> documentData = document.getData();
                        if (documentData != null) {
                            // Access 'userid' and 'usertype' fields
                            favorite_id = (Long) documentData.get("favorite_id");
                            favorite_id = favorite_id + 1L;
                            addFavorite();
                        }
                    }
                } else {
                    // Handle any errors
                    Tools.showToast(DashboardActivity.this, "Error");
                }
            }
        });

    }

    private void addFavorite() {
        DocumentReference docRef = firebaseFirestore.collection(Constants.FAVORITE_COLLECTION_NAME).document(getCurrentDateTime()); //
        // Create a new user object
        AddFavorite newUser = new AddFavorite(antonyms, getCurrentDate(), description, synonyms, 1L, sharedPreferenceUtil.getUserId(), id, word);

        // Adding user information to Firestore
        docRef.set(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                customProgressDialog.dismiss();
                // User information added successfully
                Tools.logs(TAG, "Favorite added successfully");
                Tools.showToast(DashboardActivity.this, "Favorite added successfully");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                customProgressDialog.dismiss();

                // Handle any errors that may occur
                Tools.logs(TAG, "Error adding Favorite information" + e);
                Tools.showToast(DashboardActivity.this, "Error adding Favorite information" + e);

            }
        });
    }


    private void setVisibility(boolean flag) {
        if (flag) {
            binding.tvNoWords.setVisibility(View.VISIBLE);
            binding.cvCard.setVisibility(View.VISIBLE);
            binding.llNoRecord.setVisibility(View.GONE);
        } else {
            binding.tvNoWords.setVisibility(View.GONE);
            binding.cvCard.setVisibility(View.GONE);
            binding.llNoRecord.setVisibility(View.VISIBLE);
        }
    }

    private void getRoomList() {
        // List<WordEntity> entities = AppApplication.database.wordDao().getAllEntities();
        // new InsertAsyncTask(AppApplication.database.wordDao()).execute();

        new GetEntitiesAsyncTask(AppApplication.database.wordDao(), this).execute();

    }

    @Override
    public void processFinish(List<WordEntity> result) {
        Tools.logs(TAG, "processFinish " + result.size());
        binding.tvOfflineCount.setText(result.size() + " Words");
    }

    private void getFavoriteList() {
        modelList.clear();
        customProgressDialog.show();
        CollectionReference collectionRef = firebaseFirestore.collection(Constants.FAVORITE_COLLECTION_NAME);

        collectionRef.get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        customProgressDialog.dismiss();
                        if (documentSnapshot.exists()) {
                            WordEntity model = documentSnapshot.toObject(WordEntity.class);
                            modelList.add(model);
                            binding.tvFavoriteCount.setText(modelList.size() + " Words");
                            // setAdapter1();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    customProgressDialog.dismiss();
                    // Handle any errors that occur while fetching documents
                    Tools.logs(TAG, "Error getting documents: " + e);
                });

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
                new InsertAsyncTask(database.wordDao()).execute(entity);

                // database.yourDao().insert(entity); // Replace with your actual operation
                // AppApplication.database.yourDao().insert(entity);
                Tools.logs(TAG, "saveInRoom: Save record");
                Tools.showToast(DashboardActivity.this, "Save record!");

            } else {
                // Handle the case where the database is null
                // For example, show a message or log an error
                System.out.println("Database is null");
                Tools.logs(TAG, "Database is null");

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

    public class getAsyncTask extends AsyncTask<List<WordEntity>, Void, Void> {
        private WordDao dao;

        public getAsyncTask(WordDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(List<WordEntity>... entities) {
            dao.getAllEntities();
            return null;
        }
    }


}

