package com.jay.rabbitchat.model.firebasedatabase.user;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jay.rabbitchat.view.mainscreen.MainContract;

import java.util.HashMap;

/**
 * Changes user status to online or offline.
 */
public class UserStatus implements MainContract.Model.UserStatusEditable {

    private FirebaseUser currentUser;

    private DatabaseReference databaseReference;

    private HashMap<String, Object> updateStatusMap = new HashMap<>();


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
    public void changeStatus(boolean status) {

        updateStatusMap.put("online", status);

        getDatabaseReference().updateChildren(updateStatusMap);
    }
}
