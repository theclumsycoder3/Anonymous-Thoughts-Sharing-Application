package com.example.sih_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginLayoutActivity extends AppCompatActivity {

    private Button loginButton, goToRegister;
    private EditText loginEmail, loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        loginButton = findViewById(R.id.loginButton);
        goToRegister = findViewById(R.id.goToRegister);
        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName@SIH");
        String email = intent.getStringExtra("email@SIH");

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();
                
                if (email.length() == 0 || password.length() == 0) {
                    Toast.makeText(LoginLayoutActivity.this, "Enter email or password", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginLayoutActivity.this, MainActivity.class);
                            intent.putExtra("userName@SIH", userName);
                            intent.putExtra("email@SIH", email);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(LoginLayoutActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginLayoutActivity.this, RegisterLayoutActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}