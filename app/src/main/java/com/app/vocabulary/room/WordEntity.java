package com.app.vocabulary.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vocabulary_tb")
public class WordEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int word_id;

    private String word;
    private String antonyms;
    private String date;
    private String description;
    private String synonyms;
    private int bookmarks;


    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWord_id() {
        return word_id;
    }

    public void setWord_id(int word_id) {
        this.word_id = word_id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getAntonyms() {
        return antonyms;
    }

    public void setAntonyms(String antonyms) {
        this.antonyms = antonyms;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public int getBookmarks() {
        return bookmarks;
    }

    public void setBookmarks(int bookmarks) {
        this.bookmarks = bookmarks;
    }
}

