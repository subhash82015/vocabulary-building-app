package com.app.vocabulary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    private final String TAG = SharedPreferenceUtil.class.getSimpleName();

    private final SharedPreferences sharedPreferences;
    private final SharedPreferences persistableSharedPreferences;
    private final SharedPreferences.Editor editor;
    private final SharedPreferences.Editor persistableEditor;

    private SharedPreferenceUtil(Context context) {
        this.sharedPreferences = context.getSharedPreferences(Constants.PREFRENCE_NAME, Context.MODE_PRIVATE);
        this.persistableSharedPreferences = context.getSharedPreferences(Constants.PERSISTABLE_PREFRENCE_NAME, Context.MODE_PRIVATE);
        this.editor = this.sharedPreferences.edit();
        this.persistableEditor = this.persistableSharedPreferences.edit();
    }

    @SuppressLint("StaticFieldLeak")
    private static SharedPreferenceUtil instance = null;

    public static SharedPreferenceUtil getInstance(Context ctx) {
        if (instance == null) {
            instance = new SharedPreferenceUtil(ctx);
        }
        return instance;
    }

    public Boolean isLoginAlready() {
        return sharedPreferences.getBoolean(Constants.IS_LOGIN_ALREADY, false);
    }

    public void setLoginAlready(Boolean value) {
        editor.putBoolean(Constants.IS_LOGIN_ALREADY, value).apply();
    }


    public Long getUserId() {
        return sharedPreferences.getLong(Constants.USERID, Constants.DEFAULT);
    }

    public void setUserId(Long value) {
        editor.putLong(Constants.USERID, value).apply();
    }

    public void setUserType(Long value) {
        editor.putLong(Constants.USER_TYPE, value).apply();
    }

    public String getUserDetails(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void setUserDetails(String key, String value) {
        editor.putString(key, value).apply();
    }


    // Define getter and setter methods for other SharedPreferences variables similarly

    public void deletePreferences() {
        editor.clear().apply();
    }

    public void deletePreference() {
        persistableEditor.clear().apply();
    }

}