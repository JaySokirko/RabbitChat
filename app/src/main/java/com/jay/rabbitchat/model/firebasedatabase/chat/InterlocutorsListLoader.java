package com.jay.rabbitchat.model.firebasedatabase.chat;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jay.rabbitchat.model.firebasedatabase.user.User;
import com.jay.rabbitchat.view.mainscreen.InterlocutorsContract;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;


public class InterlocutorsListLoader implements InterlocutorsContract.Model {

    private static final String TAG = "LOG";
    private FirebaseUser currentUser;

    private DatabaseReference databaseReference;

    private List<String> interlocutorsIdList = new ArrayList<>();
    private List<User> userList = new ArrayList<>();


    private FirebaseUser getCurrentUser() {

        if (currentUser == null) {
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
        }
        return currentUser;
    }


    private DatabaseReference getDatabaseReference() {

        if (databaseReference == null) {
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }


    @Override
    public void loadInterlocutors(LoadInterlocutorsFeedback feedback) {

        getDatabaseReference().child("Interlocutors")
                .child(getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                interlocutorsIdList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Interlocutors interlocutor = snapshot.getValue(Interlocutors.class);

                    if (interlocutor != null) {

                        interlocutorsIdList.add(interlocutor.getInterlocutorId());
                    }
                }
                getAllInterlocutorsForCurrentUser(feedback);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void getAllInterlocutorsForCurrentUser(LoadInterlocutorsFeedback feedback) {

        getDatabaseReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    User user = snapshot.getValue(User.class);

                    if (user != null) {

                        for (String id : interlocutorsIdList) {

                            if (user.getId().equals(id)) {

                                userList.add(user);
                            }
                        }
                    }
                }
                feedback.onLoadInterlocutorsSuccess(userList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
