package com.jay.rabbitchat.model.firebaseauthentification;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jay.rabbitchat.view.authorizationscreen.NewAccountContract;

import java.util.HashMap;

public class NewAccount implements NewAccountContract.Model {

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private DatabaseReference databaseReference;


    @Override
    public void createAccount(CreateAccountFeedback feedback, String userName, String email,
                              String password) {

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                        String userId = null;

                        if (firebaseUser != null) {
                            userId = firebaseUser.getUid();
                        }

                        if (userId != null) {
                            databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                                    .child(userId);
                        }
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id", userId);
                        hashMap.put("userName", userName);
                        hashMap.put("imageURL", "default");

                        databaseReference.setValue(hashMap).addOnCompleteListener(task1 -> {

                            if (task1.isSuccessful()) {

                                feedback.onSuccessCreateAccount(true);

                            } else {
                                feedback.onFailureCreateAccount(task.getException());
                            }
                        });
                    }
                });
    }
}
