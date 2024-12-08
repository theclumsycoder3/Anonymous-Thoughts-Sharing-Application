package com.example.sih_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ThirdFragment extends AppCompatActivity {

    private ListView listView;
    private Adapter_02 ad;
    private ArrayList<String> arrComments = new ArrayList<>();
    private ArrayList<String> arrUsers = new ArrayList<>();
    FirebaseDatabase db;
    DatabaseReference reference, reference1;
    Button button3, submitComment;
    EditText currentComment;
    TextView problemView;
    String email, userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_third);

        button3 = findViewById(R.id.button3);
        submitComment = findViewById(R.id.submitComment);
        currentComment = findViewById(R.id.currentComment);
        problemView = findViewById(R.id.problemView);

        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("UserDetails");
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    if (snapshot1.child("email").getValue().toString().equals(email)) {
                        userName = snapshot1.child("userName").getValue().toString();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Intent intent = getIntent();
        String problem = intent.getStringExtra("problem@clicked");
        String postingUserName = intent.getStringExtra("userWho@PostedIt");
        problemView.setText("Problem --> " + problem);
        listView = findViewById(R.id.commentsList);
        ad = new Adapter_02(this, R.layout.comment_layout, arrComments, arrUsers);
        listView.setAdapter(ad);

        reference = FirebaseDatabase.getInstance().getReference().child("Comments");
        reference1 = FirebaseDatabase.getInstance().getReference().child("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                    if (snapshot1.getKey().equals(problem)) {
                        DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference().child("Comments/" + problem + "/");
                        reference2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot snapshot1: snapshot.getChildren()) {
                                    if (!arrComments.contains(snapshot1.child("comments").getValue().toString())) {
                                        arrUsers.add(snapshot1.child("userName").getValue().toString());
                                        arrComments.add(snapshot1.child("comments").getValue().toString());
                                    }
                                    ad.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdFragment.this, MainActivity.class);
                startActivity(intent);
            }
        });

        submitComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = currentComment.getText().toString();
                if (!comment.isEmpty()) {
                    Comments comments = new Comments(comment, userName);
                    db = FirebaseDatabase.getInstance();
                    reference = db.getReference("Comments");
                    reference.child(problem).child(userName).setValue(comments);
                    Toast.makeText(ThirdFragment.this, "Your Comment will be posted soon", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}