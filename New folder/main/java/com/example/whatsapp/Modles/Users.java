package com.example.whatsapp.Modles;

public class Users {
    String profilepic,userName, mail,password,userId,lastMessage,status;

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserId() {
        this.userId = userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Users(String profilepic, String userNmae, String mail, String password, String userId, String lastMessage) {
        this.profilepic = profilepic;
        this.userName = userNmae;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.lastMessage = lastMessage;
    }
    public Users() {

    }
    //signup constractor
    public Users( String userNmae, String mail, String password) {
            this.userName = userNmae;
            this.mail = mail;
            this.password = password;
        }


    public void getUserId(String key) {
        this.userId=userId;
    }
}
