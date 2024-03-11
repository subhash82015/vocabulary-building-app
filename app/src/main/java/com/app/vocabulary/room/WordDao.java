package com.app.vocabulary.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(WordEntity entity);

    @Query("SELECT * FROM vocabulary_tb")
    List<WordEntity> getAllEntities();
}
