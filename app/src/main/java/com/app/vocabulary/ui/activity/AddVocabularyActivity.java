package com.app.vocabulary.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.app.vocabulary.R;
import com.app.vocabulary.databinding.ActivityAddVocabularyBinding;
import com.app.vocabulary.databinding.ActivityLoginBinding;
import com.app.vocabulary.models.AddUsers;
import com.app.vocabulary.models.AddVocabularyWord;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class AddVocabularyActivity extends AppCompatActivity {
    private ActivityAddVocabularyBinding binding;
    CustomProgressDialog customProgressDialog;
    private SharedPreferenceUtil sharedPreferenceUtil;
    private FirebaseFirestore firebaseFirestore;
    private String TAG = "AddVocabularyActivity";
    Long id = 0L;
    Date c;
    String todayDate;
    SimpleDateFormat simpleDateFormat;

    String vocabulary = "", antonyms = "", synonyms = "", date = "", description = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_vocabulary);
        binding = ActivityAddVocabularyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(this);
        firebaseFirestore = FirebaseRepo.createInstance();
        customProgressDialog = new CustomProgressDialog(AddVocabularyActivity.this, "Please wait....");
        c= Calendar.getInstance().getTime();
        simpleDateFormat=new SimpleDateFormat("dd-MM-yyyy");
        todayDate=simpleDateFormat.format(c);
        binding.etData.setText(todayDate);
        handleClickListener();
    }

    private void handleClickListener() {
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vocabulary = binding.etVocabulary.getText().toString().trim();
                antonyms = binding.etAntonyms.getText().toString().trim();
                synonyms = binding.etSynonyms.getText().toString().trim();
                date = binding.etData.getText().toString().trim();
                description = binding.etDescription.getText().toString().trim();
                validate();
            }
        });

    }

    private void validate() {
        if (vocabulary.equals("")) {
            Tools.showToast(AddVocabularyActivity.this, "Please enter vocabulary Word");
        } else if (antonyms.equals("")) {
            Tools.showToast(AddVocabularyActivity.this, "Please enter antonyms");
        } else if (synonyms.equals("")) {
            Tools.showToast(AddVocabularyActivity.this, "Please enter synonyms");
        } else if (description.equals("")) {
            Tools.showToast(AddVocabularyActivity.this, "Please enter description");
        } else {
            getLastUserInfo();
        }
    }



    private void getLastUserInfo() {
        CollectionReference collectionRef = firebaseFirestore.collection(Constants.NOTIFICATION_COLLECTION_NAME);


// Create a query to order the collection in descending order and limit to 1 document
        Query query = collectionRef.orderBy("id", Query.Direction.DESCENDING).limit(1);

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
                            id = (Long) documentData.get("id");
                            id = id + 1L;
                            addNotification();
                        }
                    }
                } else {
                    // Handle any errors
                    Tools.showToast(AddVocabularyActivity.this, "Error");
                }
            }
        });

    }

    private void addNotification() {
        DocumentReference docRef = firebaseFirestore.collection(Constants.NOTIFICATION_COLLECTION_NAME).document(todayDate); // Firestore database reference
        // Create a new user object
        AddVocabularyWord addVocabularyWord = new AddVocabularyWord(antonyms, todayDate, description, synonyms, id, vocabulary);

        // Adding user information to Firestore
        docRef.set(addVocabularyWord).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                customProgressDialog.dismiss();
                // User information added successfully
                Tools.logs(TAG, "User information added successfully");
                Tools.showToast(AddVocabularyActivity.this, "User information added successfully");
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                customProgressDialog.dismiss();

                // Handle any errors that may occur
                Tools.logs(TAG, "Error adding user information" + e);
                Tools.showToast(AddVocabularyActivity.this, "Error adding user information" + e);

            }
        });
    }

}