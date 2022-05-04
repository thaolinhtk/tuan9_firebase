package com.example.firebase_auth;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private TextView textRegEmail;
    private TextView textRegPassword;
    private TextView textSignInView;
    private Button buttonRegisterView;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        textSignInView = (TextView) findViewById(R.id.textViewCallSignInView);
        textSignInView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSignInView();
            }
        });

        textRegEmail = findViewById(R.id.textEmailRegister);
        textRegPassword = findViewById(R.id.textPasswordRegister);

        mAuth = FirebaseAuth.getInstance();

        buttonRegisterView = (Button) findViewById(R.id.buttonViewRegisterView);
        buttonRegisterView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
                //openRatingView();
            }
        });
    }

    private void createUser() {
        String email = textRegEmail.getText().toString();
        String password = textRegPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            textRegEmail.setError("Email cannot be empty");
            textRegEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            textRegPassword.setError("Password cannot be empty");
            textRegPassword.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, com.example.sharepreference.StorageActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void openRatingView() {
        Intent intentRatingView = new Intent(this, com.example.sharepreference.RatingActivity.class);
        startActivity(intentRatingView);
    }

    private void openSignInView() {
        Intent intentSignInView = new Intent(this, SignInActivity.class);
        startActivity(intentSignInView);
    }
}