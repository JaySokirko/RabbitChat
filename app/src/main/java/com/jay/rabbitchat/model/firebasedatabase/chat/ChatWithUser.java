package com.jay.rabbitchat.model.firebasedatabase.chat;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jay.rabbitchat.view.chatscreen.ChatContract;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;

public class ChatWithUser implements ChatContract.Model {


    private FirebaseUser currentUser;

    private DatabaseReference databaseReference;

    private HashMap<String, Object> messagesMap = new HashMap<>();
    private HashMap<String, Object> currentUserMap = new HashMap<>();

    private List<Chat> chatList = new ArrayList<>();
    private String TAG = "LOG_TAG";


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
    public void updateChat(LoadChatFeedback listener, String senderId) {

        getDatabaseReference().child("Chats")
                .child(getCurrentUser().getUid())
                .child(senderId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        chatList.clear();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            Chat chat = snapshot.getValue(Chat.class);

                            if (chat != null) {

                                chatList.add(chat);
                            }
                        }
                        listener.onLoadChatComplete(chatList);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    @Override
    public void sendMessage(String message, String receiver) {

        //get current time in hours and minutes
        String time =  new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        messagesMap.put("sender", getCurrentUser().getUid());
        messagesMap.put("receiver", receiver);
        messagesMap.put("message", message);
        messagesMap.put("time", time);

        getDatabaseReference().child("Chats").child(getCurrentUser().getUid())
                .child(receiver).push().setValue(messagesMap);

        getDatabaseReference().child("Chats").child(receiver)
                .child(getCurrentUser().getUid()).push().setValue(messagesMap);

        //add user to chat list (InterlocutorsFragment)
        currentUserMap.put("interlocutorId", receiver);

        //add user to chat list (InterlocutorsFragment)
        getDatabaseReference().child("Interlocutors")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        getDatabaseReference().child("Interlocutors")
                                .child(getCurrentUser().getUid())
                                .child(receiver)
                                .child("interlocutorId")
                                .setValue(receiver);

                        getDatabaseReference().child("Interlocutors")
                                .child(receiver)
                                .child(getCurrentUser().getUid())
                                .child("interlocutorId")
                                .setValue(getCurrentUser().getUid());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
