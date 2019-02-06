package com.jay.rabbitchat.model.firebasedatabase.user;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jay.rabbitchat.view.mainscreen.SearchUsersContract;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;


public class UsersListLoader implements SearchUsersContract.Model {

    private FirebaseUser currentUser;

    private DatabaseReference databaseReference;

    private List<User> userList = new ArrayList<>();

    private String TAG = "LOG_TAG";


    private FirebaseUser getCurrentUser(){

        if (currentUser == null){
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
        }
        return currentUser;
    }


    private DatabaseReference getDatabaseReference(){

        if (databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        }
        return databaseReference;
    }


    @Override
    public void loadUsersList(LoadUsersFeedback feedback) {

        getDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    User user = snapshot.getValue(User.class);

                    if (user != null && !user.getId().equals(getCurrentUser().getUid())){

                        userList.add(user);
                    }
                    feedback.onSuccessfulLoad(userList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                feedback.onFailureLoad(databaseError.getMessage());
            }
        });
    }
}
