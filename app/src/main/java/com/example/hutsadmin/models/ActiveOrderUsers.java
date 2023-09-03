package com.example.hutsadmin.models;

public class ActiveOrderUsers {

    String name , email , number ,userId;
    Boolean hasOrder = false;

    public ActiveOrderUsers() {
    }

    public ActiveOrderUsers(String name, String email, String number, String userId, Boolean hasOrder) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.userId = userId;
        this.hasOrder = hasOrder;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Boolean getHasOrder() {
        return hasOrder;
    }

    public void setHasOrder(Boolean hasOrder) {
        this.hasOrder = hasOrder;
    }
}
