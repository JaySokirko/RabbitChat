package com.jay.rabbitchat.model.firebasestorage;

import android.net.Uri;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.jay.rabbitchat.view.mainscreen.SettingsContract;

import java.util.HashMap;

public class UserImageUploader implements SettingsContract.Model.UploadProfileImage {

    private FirebaseUser currentUser;

    private DatabaseReference databaseReference;

    private StorageReference storageReference;


    private FirebaseUser getCurrentUser() {
        if (currentUser == null){
            currentUser = FirebaseAuth.getInstance().getCurrentUser();
        }
        return currentUser;
    }


    private DatabaseReference getDatabaseReference() {
        if (databaseReference == null){
            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
        return databaseReference;
    }


    private StorageReference getStorageReference() {
        if (storageReference == null){
            storageReference = FirebaseStorage.getInstance().getReference();
        }
        return storageReference;
    }


    @Override
    public void uploadProfileImage(UploadProfileImageFeedback feedback, Uri profileImageUri,
                                   String fileExtension) {

        if (profileImageUri != null){

            final StorageReference fileReference = getStorageReference().child("ProfileImages")
                    .child(getCurrentUser().getUid())
                    .child(profileImageUri + "." + fileExtension);

            StorageTask uploadTask = fileReference.putFile(profileImageUri);

            uploadTask.continueWithTask((Continuation<UploadTask.TaskSnapshot, Task<Uri>>) task -> {

                if (!task.isSuccessful()){
                    feedback.onFailureUploadProfileImage(task.getException());
                }

                return fileReference.getDownloadUrl();

            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {

                if (task.isSuccessful()){

                    Uri downloadUri = task.getResult();

                    if (downloadUri != null) {

                        String mUri = downloadUri.toString();

                        HashMap<String, Object> map = new HashMap<>();
                        map.put("imageURL", mUri);

                        getDatabaseReference().child("Users").child(getCurrentUser().getUid()).updateChildren(map);

                    } else {

                        feedback.onFailureUploadProfileImage(task.getException());
                    }
                }
            }).addOnFailureListener(feedback::onFailureUploadProfileImage);
        }
    }
}
