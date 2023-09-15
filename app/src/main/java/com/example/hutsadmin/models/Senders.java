package com.example.hutsadmin.models;

import kotlinx.coroutines.channels.Send;

public class Senders {
    private String name, email , userId  , fcmToken , number ;

    boolean isRead = false;
//    public Senders(String name, String email, String userId, String fcmToken, String number) {
//        this.name = name;
//        this.email = email;
//        this.userId = userId;
//        this.fcmToken = fcmToken;
//        this.number = number;
//    }


    public Senders(String name, String userId, boolean isRead)
    {
        this.name = name;

        this.userId = userId;

        this.isRead = isRead;
    }

    public Senders(String name, String email, String userId, String fcmToken, String number, boolean isRead) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.fcmToken = fcmToken;
        this.number = number;
        this.isRead = isRead;
    }

    public boolean isRead() {

        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Senders() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
