package com.example.sih_application;

public class Comments {

    String comments, userName;

    Comments(String comments, String userName) {
        this.comments = comments;
        this.userName = userName;
    }

    public Comments() {

    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
