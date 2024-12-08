package com.example.sih_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sih_application.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Adapter_01 ad;
    static ArrayList<String> arr = new ArrayList<>();
    DatabaseReference reference;
    Button takeAssesment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        takeAssesment = findViewById(R.id.takeAssesment);

        listView = findViewById(R.id.listView);
        ad = new Adapter_01(this, R.layout.list_layout, arr);
        listView.setAdapter(ad);


        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    Users users = snapshot1.getValue(Users.class);
                    if (users != null) {
                        ArrayList<String> problem = users.getProblem();
                        for (String s: problem) {
                            if (!arr.contains(s)) {
                                arr.add(s);
                            }
                        }
                    }
                    ad.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemNo = (String) parent.getItemAtPosition(position);
                String[] postingUserName = new String[1];
                String problemClickd = arr.get(position);
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1: snapshot.getChildren()) {
                            if (snapshot1.child("problem").getValue().toString().equals(problemClickd)) {
                                postingUserName[0] = snapshot1.child("name").getValue().toString();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Intent intent = new Intent(MainActivity.this, ThirdFragment.class);
                intent.putExtra("problem@clicked", problemClickd);
                intent.putExtra("userWho@PostedIt", postingUserName[0]);
                startActivity(intent);
            }
        });

        takeAssesment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuizFragment.class);
                startActivity(intent);
            }
        });
    }
    public void openFormPage(View view) {
        Intent intent = new Intent(this, SecondFragment.class);
        startActivity(intent);
    }
}