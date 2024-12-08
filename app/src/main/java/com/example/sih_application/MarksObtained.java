package com.example.sih_application;

public class MarksObtained {
    String userName, email, marks;

    public MarksObtained(String marks, String email, String userName) {
        this.userName = userName;
        this.email = email;
        this.marks = marks;
    }

    public MarksObtained() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }
}
