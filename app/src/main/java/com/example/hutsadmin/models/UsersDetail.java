package com.example.hutsadmin.models;

import java.util.ArrayList;

public class UsersDetail {

    private String name, email , userId ;

    public UsersDetail(String name, String email, String userId) {
        this.name = name;
        this.email = email;
        this.userId = userId;
    }

    public UsersDetail() {
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


