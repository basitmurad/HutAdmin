package com.example.hutsadmin.models;

public class MessegeDetails {

    private String messege;
    private String senderId;
    private String pushId;
    long timestamp;



    public MessegeDetails() {
    }

    public MessegeDetails(String messege, String senderId, String pushId, long timestamp) {
        this.messege = messege;
        this.senderId = senderId;
        this.pushId = pushId;
        this.timestamp = timestamp;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
