package com.example.hutsadmin.models;

public class MessegeDetails {

    private String messege;
    private String senderId;
    private String pushId;

    public MessegeDetails() {
    }

    public MessegeDetails(String messege, String senderId, String pushId) {
        this.messege = messege;
        this.senderId = senderId;
        this.pushId = pushId;
    }

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
