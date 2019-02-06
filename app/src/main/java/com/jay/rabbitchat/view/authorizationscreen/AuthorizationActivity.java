package com.jay.rabbitchat.view.authorizationscreen;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.jay.rabbitchat.R;

public class AuthorizationActivity extends AppCompatActivity {

    private AnimationDrawable backgroundAnimation;
    private Fragment loginFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        FrameLayout parentLayout = findViewById(R.id.authorization_container);

        backgroundAnimation = (AnimationDrawable) parentLayout.getBackground();
        backgroundAnimation.setExitFadeDuration(4000);

        attachLoginFragment();
    }


    private void attachLoginFragment(){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (loginFragment == null){
            loginFragment = new LoginFragment();
            transaction.replace(R.id.authorization_container, loginFragment);
            transaction.commit();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (backgroundAnimation != null && !backgroundAnimation.isRunning())
            backgroundAnimation.start();
    }


    @Override
    protected void onPause() {
        super.onPause();

        if (backgroundAnimation != null && backgroundAnimation.isRunning())
            backgroundAnimation.stop();
    }
}
