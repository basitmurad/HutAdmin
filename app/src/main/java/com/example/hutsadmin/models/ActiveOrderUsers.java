package com.example.hutsadmin.models;

public class ActiveOrderUsers {
    String name , email , number ,userId;
    Boolean hasOrder = false;
    String time;


    public ActiveOrderUsers() {
    }

    public ActiveOrderUsers(String name, String email, String number, String userId, Boolean hasOrder, String time) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.userId = userId;
        this.hasOrder = hasOrder;
        this.time = time;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getHasOrder() {
        return hasOrder;
    }

    public void setHasOrder(Boolean hasOrder) {
        this.hasOrder = hasOrder;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
