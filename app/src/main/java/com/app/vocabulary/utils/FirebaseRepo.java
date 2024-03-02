package com.app.vocabulary.utils;

import android.annotation.SuppressLint;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseRepo {


    @SuppressLint("StaticFieldLeak")
    public static FirebaseFirestore db;
    public static DocumentSnapshot document = null;


    public static FirebaseFirestore createInstance() {
        if (db == null) {
            db = FirebaseFirestore.getInstance();
        }
        return db;
    }

    public static DocumentSnapshot getDocumentSnapshot(String tag,final FirebaseFirestore db, String collectionPath, String documentPah) {
        // Reference to the document in a collection
        DocumentReference docRef = db.collection(collectionPath).document(documentPah);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    document = task.getResult();
                    if (document.exists()) {
                        Tools.logs(tag, "DocumentSnapshot data: " + document.getData());
                        // Access the data using document.getData()
                    } else {
                        Tools.logs(tag, "No such document");
                    }
                } else {
                    Tools.logs(tag, "get failed with " + task.getException().toString());
                }
            }
        });

        return document;
    }
}
