package com.app.vocabulary.room;


import android.app.Application;

import androidx.room.Room;

public class AppApplication extends Application {
    public static WordDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();

        database = Room.databaseBuilder(getApplicationContext(),
                        WordDatabase.class, "vocabulary_db")
                .build();
    }
}

