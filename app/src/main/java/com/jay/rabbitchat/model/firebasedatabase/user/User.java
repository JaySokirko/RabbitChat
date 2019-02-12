package com.jay.rabbitchat.model.firebasedatabase.user;

public class User {

    private String id;

    private String userName;

    private String imageURL;

    private boolean online;

    private String wasOnline;

    public User() {
    }

    public User(String id, String userName, String imageURL, boolean online, String wasOnline) {
        this.id = id;
        this.userName = userName;
        this.imageURL = imageURL;
        this.online = online;
        this.wasOnline = wasOnline;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getWasOnline() {
        return wasOnline;
    }

    public void setWasOnline(String wasOnline) {
        this.wasOnline = wasOnline;
    }
}
