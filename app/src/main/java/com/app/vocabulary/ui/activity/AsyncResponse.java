package com.app.vocabulary.ui.activity;

public interface AsyncResponse<T> {
    void processFinish(T result);
}
