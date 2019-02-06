package com.jay.rabbitchat.model.firebasedatabase.chat;

public class Interlocutors {

    private String currentUserId;

    private String interlocutorId;

    public Interlocutors(String currentUserId, String interlocutorId) {
        this.currentUserId = currentUserId;
        this.interlocutorId = interlocutorId;
    }

    public Interlocutors() {
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    public String getInterlocutorId() {
        return interlocutorId;
    }

    public void setInterlocutorId(String interlocutorId) {
        this.interlocutorId = interlocutorId;
    }
}
