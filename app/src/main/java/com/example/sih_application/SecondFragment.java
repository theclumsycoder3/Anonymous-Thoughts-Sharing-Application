package com.example.sih_application;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class SecondFragment extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference ref;
    EditText problem, userAge;
    Button submitForm;
    String email, user, prob, age;
    static ArrayList<String> problems = new ArrayList<>();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_second);
        problem = findViewById(R.id.problem);
        userAge = findViewById(R.id.userAge);
        submitForm = findViewById(R.id.submitForm);

        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserDetails");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    if (snapshot1.child("email").getValue().toString().equals(email)) {
                        user = snapshot1.child("userName").getValue().toString();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void openActivity(View view) {
        age = userAge.getText().toString();
        prob = problem.getText().toString();
        if (!age.isEmpty() && !prob.isEmpty()) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<String> arl1 = new ArrayList<>();
                    for (DataSnapshot snapshot1: snapshot.getChildren()) {
                        if (snapshot1.getKey().equals(user)) {
                            arl1.addAll(snapshot1.getValue(Users.class).problem);
                        }
                    }
                    problems = arl1;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            problems.add(prob);
            Users users = new Users(user, problems, age);
            db = FirebaseDatabase.getInstance();
            ref = db.getReference("Users");
            ref.child(user).setValue(users);
            Toast.makeText(this, "Posting soon", Toast.LENGTH_SHORT).show();
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}