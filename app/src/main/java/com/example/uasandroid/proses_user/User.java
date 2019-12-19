package com.example.uasandroid.proses_user;

import java.io.Serializable;

public class User implements Serializable{
    private String userName;
    private String userUserName;
    private String userPassword;
//    private String key;

    public User() {
    }

    public User(String userName, String userUserName, String userPassword) {
        this.userName = userName;
        this.userUserName = userUserName;
        this.userPassword = userPassword;
//        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserUserName() {
        return userUserName;
    }

    public void setUserUserName(String userUserName) {
        this.userUserName = userUserName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }
}
