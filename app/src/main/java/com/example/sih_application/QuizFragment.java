package com.example.sih_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizFragment extends AppCompatActivity {

    TextView questionView, finalScore;
    Button option01, option02, option03, option04, option05;
    Button submit, checkScore;
    Button clBtn;
    String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    String userName = "";

    int questionIndex = 0;
    int currentOption = 0;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_fragment);

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("UserDetails");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    if (snapshot1.child("email").getValue().toString().equals(email)) {
                        userName = snapshot1.child("userName").getValue().toString();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        questionView = findViewById(R.id.questionView);
        finalScore = findViewById(R.id.finalScore);
        option01 = findViewById(R.id.option01);
        option02 = findViewById(R.id.option02);
        option03 = findViewById(R.id.option03);
        option04 = findViewById(R.id.option04);
        option05 = findViewById(R.id.option05);
        submit = findViewById(R.id.submit);
        checkScore = findViewById(R.id.checkScore);

        loadQuestion();

        option01.setBackgroundColor(Color.parseColor("#dba72c"));
        option02.setBackgroundColor(Color.parseColor("#dba72c"));
        option03.setBackgroundColor(Color.parseColor("#dba72c"));
        option04.setBackgroundColor(Color.parseColor("#dba72c"));
        option05.setBackgroundColor(Color.parseColor("#dba72c"));

        option01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option01.setBackgroundColor(Color.MAGENTA);
                clBtn = findViewById(R.id.option01);
                currentOption = 5;
                option02.setBackgroundColor(Color.parseColor("#dba72c"));
                option03.setBackgroundColor(Color.parseColor("#dba72c"));
                option04.setBackgroundColor(Color.parseColor("#dba72c"));
                option05.setBackgroundColor(Color.parseColor("#dba72c"));
            }
        });
        option02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option02.setBackgroundColor(Color.MAGENTA);
                clBtn = findViewById(R.id.option02);
                currentOption = 4;
                option01.setBackgroundColor(Color.parseColor("#dba72c"));
                option03.setBackgroundColor(Color.parseColor("#dba72c"));
                option04.setBackgroundColor(Color.parseColor("#dba72c"));
                option05.setBackgroundColor(Color.parseColor("#dba72c"));
            }
        });
        option03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option03.setBackgroundColor(Color.MAGENTA);
                clBtn = findViewById(R.id.option03);
                currentOption = 3;
                option01.setBackgroundColor(Color.parseColor("#dba72c"));
                option02.setBackgroundColor(Color.parseColor("#dba72c"));
                option04.setBackgroundColor(Color.parseColor("#dba72c"));
                option05.setBackgroundColor(Color.parseColor("#dba72c"));
            }
        });
        option04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option04.setBackgroundColor(Color.MAGENTA);
                clBtn = findViewById(R.id.option04);
                currentOption = 2;
                option01.setBackgroundColor(Color.parseColor("#dba72c"));
                option02.setBackgroundColor(Color.parseColor("#dba72c"));
                option03.setBackgroundColor(Color.parseColor("#dba72c"));
                option05.setBackgroundColor(Color.parseColor("#dba72c"));
            }
        });
        option05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                option05.setBackgroundColor(Color.MAGENTA);
                clBtn = findViewById(R.id.option05);
                currentOption = 1;
                option01.setBackgroundColor(Color.parseColor("#dba72c"));
                option02.setBackgroundColor(Color.parseColor("#dba72c"));
                option03.setBackgroundColor(Color.parseColor("#dba72c"));
                option04.setBackgroundColor(Color.parseColor("#dba72c"));
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (clBtn == null) {
                    Intent intent = new Intent(QuizFragment.this, MainActivity.class);
                    startActivity(intent);
                }
                clBtn.setBackgroundColor(Color.parseColor("#dba72c"));
                String text = clBtn.getText().toString();
                questionIndex++;
                if (questionIndex < QuestionsAnswers.questions.length) {
                    score += currentOption;
                    loadQuestion();
                }
                else if (questionIndex <= QuestionsAnswers.questions.length) {
                    score += currentOption;
                }
                else {
                    finalScore.setTextColor(Color.parseColor("#01ffff"));
                    finalScore.setText(Integer.toString(score) + " / " + "100");
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("AssessmentResults");
                    MarksObtained marksObtained = new MarksObtained(Integer.toString(score), email, userName);
                    reference.child(userName).setValue(marksObtained);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(QuizFragment.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
                clBtn = null;
            }
        });

        checkScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("AssessmentResults");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int flag = 0;
                        for (DataSnapshot snapshot1: snapshot.getChildren()) {
                            if (snapshot1.child("email").getValue().toString().equals(email)) {
                                flag = 1;
                                Toast.makeText(QuizFragment.this, "Your previous score: " + snapshot1.child("marks").getValue().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        if (flag == 0) {
                            Toast.makeText(QuizFragment.this, "Your previous score: 0", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    public void loadQuestion() {
        questionView.setText(QuestionsAnswers.questions[questionIndex]);
    }
}