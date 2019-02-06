package com.jay.rabbitchat.model.firebasedatabase.user;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jay.rabbitchat.view.mainscreen.SettingsContract;

import androidx.annotation.NonNull;

public class CurrentUserDataLoader implements SettingsContract.Model.LoadCurrentUserData {

    private FirebaseUser currentUser;

    private DatabaseReference databaseReference;


    private FirebaseUser getCurrentUser(){

        if (currentUser == null){
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
        }
        return currentUser;
    }


    private DatabaseReference getDatabaseReference(){

        if (databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                    .child(getCurrentUser().getUid());
        }
        return databaseReference;
    }


    @Override
    public void loadCurrentUserData(LoadCurrentUserDataFeedBack feedBack) {

        getDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                if (user != null){

                    feedBack.onLoadCurrentUserDataSuccessful(user.getImageURL(), user.getUserName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                feedBack.onLoadCurrentUserDataFailure(databaseError.getMessage());
            }
        });

    }
}
