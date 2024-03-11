package com.app.vocabulary.room;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WordEntity.class}, version = 1, exportSchema = false)
public abstract class WordDatabase extends RoomDatabase {
    public abstract WordDao wordDao();
}

