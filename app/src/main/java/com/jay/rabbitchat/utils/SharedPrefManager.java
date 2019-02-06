package com.jay.rabbitchat.utils;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class SharedPrefManager{

    private SharedPreferences preferences;

    @Inject
    public SharedPrefManager(SharedPreferences preferences) {

       this.preferences = preferences;
    }


    public void putBoolean(String key, boolean b){
        preferences.edit().putBoolean(key, b).apply();
    }


    public boolean getBoolean(String key){
        return preferences.getBoolean(key, false);
    }
}
