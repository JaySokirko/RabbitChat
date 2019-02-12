package com.jay.rabbitchat.model.firebasedatabase.user;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jay.rabbitchat.view.mainscreen.MainContract;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Edit when the user was online.
 */
public class UserWasOnline implements MainContract.Model.UserWasOnlineEditable {

    private FirebaseUser currentUser;

    private DatabaseReference databaseReference;

    private HashMap<String, Object> updateWasOnlineMap = new HashMap<>();


    private FirebaseUser getCurrentUser() {

        if (currentUser == null){
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
        }
        return currentUser;
    }


    private DatabaseReference getDatabaseReference() {

        if (databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference("Users")
                    .child(getCurrentUser().getUid());
        }
        return databaseReference;
    }


    @Override
    public void setWasOnline() {

        //Set the current time when the user closes the application.
        String time =  new SimpleDateFormat("dd MMM HH:mm", Locale.getDefault())
                .format(new Date());

        updateWasOnlineMap.put("wasOnline", time);

        getDatabaseReference().updateChildren(updateWasOnlineMap);
    }
}
