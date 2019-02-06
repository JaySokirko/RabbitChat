package com.jay.rabbitchat.di;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferencesModule {

    private Context context;

    public SharedPreferencesModule(Context context) {
        this.context = context;
    }

    @Provides
    SharedPreferences provideSharedPreferences(){
        return context.getSharedPreferences("Settings", Context.MODE_PRIVATE);
    }
}
