package com.jay.rabbitchat.view;

import android.content.Intent;
import android.os.Bundle;

import com.jay.rabbitchat.di.DaggerSharedPrefComponent;
import com.jay.rabbitchat.di.SharedPreferencesModule;
import com.jay.rabbitchat.utils.SharedPrefManager;
import com.jay.rabbitchat.view.authorizationscreen.AuthorizationActivity;
import com.jay.rabbitchat.view.mainscreen.MainActivity;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;

public class LauncherActivity extends AppCompatActivity {

    @Inject
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerSharedPrefComponent.builder()
                .sharedPreferencesModule(new SharedPreferencesModule(this))
                .build()
                .inject(this);

        boolean isUserSignedIn = sharedPrefManager.getBoolean("isUserSignedIn");

        if (isUserSignedIn) {

            startActivity(new Intent(this, MainActivity.class));
        } else {
            startActivity(new Intent(this, AuthorizationActivity.class));
        }
        finish();
    }
}
