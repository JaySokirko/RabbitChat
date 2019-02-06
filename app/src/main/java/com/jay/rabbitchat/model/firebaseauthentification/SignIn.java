package com.jay.rabbitchat.model.firebaseauthentification;

import com.google.firebase.auth.FirebaseAuth;
import com.jay.rabbitchat.view.authorizationscreen.LoginContract;

public class SignIn implements LoginContract.Model {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    public void signIn(SignInFeedBack feedBack, String email, String password) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()){

                        feedBack.onSuccessfulSignIn(true);

                    } else {
                        feedBack.onFailureSignIn(task.getException());
                    }
                });
    }
}
