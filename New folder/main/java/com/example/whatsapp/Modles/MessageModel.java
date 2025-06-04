package com.example.whatsapp.Modles;

public class MessageModel {
    String uId,message,messageid;
    Long timestamp;

    public MessageModel(String uId, String message) {
        this.uId = uId;
        this.message = message;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getMessageid() {
        return messageid;
    }

    public MessageModel(String uId, String message, Long timestamp) {
        this.uId = uId;
        this.message = message;
        this.timestamp = timestamp;
    }
    public MessageModel(){

    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
