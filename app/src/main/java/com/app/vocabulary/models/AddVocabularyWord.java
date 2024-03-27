package com.app.vocabulary.models;

public class AddVocabularyWord {
    private String antonyms;
    private String date;
    private String description;
    private String synonyms;

    private Long id;
    private String word;

    public AddVocabularyWord() {
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



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }



    public void setWord(String word) {
        this.word = word;
    }

    public AddVocabularyWord(String antonyms, String date, String description, String synonyms, Long id, String word) {
        this.antonyms = antonyms;
        this.date = date;
        this.description = description;
        this.synonyms = synonyms;
        this.id = id;
        this.word = word;

    }


}
