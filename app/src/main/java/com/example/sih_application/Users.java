package com.example.sih_application;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Users {
    String user, age;
    ArrayList<String> problem = new ArrayList<>();

    public Users() {
    }

    public Users(String user, ArrayList<String> problem, String age) {
        this.user = user;
        this.age = age;
        this.problem.addAll(problem);
    }

    public String getName() {
        return user;
    }

    public void setName(String name) {
        this.user = name;
    }

    public ArrayList<String> getProblem() {
        return problem;
    }

    public void setProblem(ArrayList<String> problem) {
        this.problem = problem;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age)   {
        this.age = age;
    }
}
