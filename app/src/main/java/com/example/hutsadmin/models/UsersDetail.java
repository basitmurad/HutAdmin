package com.example.hutsadmin.models;

import java.util.ArrayList;

public class UsersDetail {

    private String name, email , userId , userFcmToken , number;


    public UsersDetail(String name, String email, String userId, String userFcmToken, String number) {
        this.name = name;
        this.email = email;
        this.userId = userId;
        this.userFcmToken = userFcmToken;
        this.number = number;
    }

    public UsersDetail() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUserFcmToken() {
        return userFcmToken;
    }

    public void setUserFcmToken(String userFcmToken) {
        this.userFcmToken = userFcmToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
}


