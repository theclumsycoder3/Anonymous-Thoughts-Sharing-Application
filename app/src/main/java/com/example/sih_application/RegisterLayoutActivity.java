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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterLayoutActivity extends AppCompatActivity {
    private Button registerButton, goToLogin;
    private EditText registerEmail, registerPassword, userNameEntered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerButton = findViewById(R.id.registerButton);
        goToLogin = findViewById(R.id.goToLogin);
        userNameEntered = findViewById(R.id.userNameEntered);
        String userName = userNameEntered.getText().toString();
        String email = registerEmail.getText().toString();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();

                if (!userName.isEmpty() && !email.isEmpty() || !password.isEmpty()) {
                    final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((task -> {
                        if (task.isSuccessful()) {

                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterLayoutActivity.this, "Verify your email", Toast.LENGTH_SHORT).show();
                                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                                        DatabaseReference reference = db.getReference("UserDetails");
                                        UserDetails userDetails = new UserDetails(userNameEntered.getText().toString(), registerEmail.getText().toString(), registerPassword.getText().toString());
                                        reference.child(userNameEntered.getText().toString()).setValue(userDetails);
                                        registerEmail.setText("");
                                        registerPassword.setText("");
                                        userNameEntered.setText("");
                                    } else {
                                        Toast.makeText(RegisterLayoutActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(RegisterLayoutActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }));
                }
                
                else {
                    Toast.makeText(RegisterLayoutActivity.this, "Enter all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterLayoutActivity.this, LoginLayoutActivity.class);
                intent.putExtra("userName@SIH", userName);
                intent.putExtra("email@SIH", email);
                startActivity(intent);
                finish();
            }
        });
    }
}