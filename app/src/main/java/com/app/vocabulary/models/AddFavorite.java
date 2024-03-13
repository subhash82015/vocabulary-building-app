package com.app.vocabulary.models;

public class AddFavorite {
    private String antonyms;
    private String date;
    private String description;
    private String synonyms;
    private Long favorite_id;
    // Add other properties as needed
    private Long user_id;
    private Long notification_id;
    private String word;

    public AddFavorite() {
        // Default constructor required for Firestore
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

    public Long getFavorite_id() {
        return favorite_id;
    }

    public void setFavorite_id(Long favorite_id) {
        this.favorite_id = favorite_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public String getWord() {
        return word;
    }

    public Long getNotification_id() {
        return notification_id;
    }

    public void setNotification_id(Long notification_id) {
        this.notification_id = notification_id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public AddFavorite(String antonyms, String date, String description, String synonyms, Long favorite_id, Long user_id, Long notification_id, String word) {
        this.antonyms = antonyms;
        this.date = date;
        this.description = description;
        this.synonyms = synonyms;
        this.favorite_id = favorite_id;
        this.user_id = user_id;
        this.word = word;
        this.notification_id = notification_id;
    }


}
