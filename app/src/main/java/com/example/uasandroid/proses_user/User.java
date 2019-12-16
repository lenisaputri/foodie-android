package com.example.uasandroid.proses_user;

public class User {
    private String userName;
    private String userUserName;
    private String userPassword;

    public User() {
    }

    public User(String userName, String userUserName, String userPassword) {
        this.userName = userName;
        this.userUserName = userUserName;
        this.userPassword = userPassword;
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
}
