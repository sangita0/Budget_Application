package com.example.budgetapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import static androidx.core.content.ContextCompat.startActivity;

public class RegistrationActivity extends AppCompatActivity {
    private EditText email, password;
    private Button registrationBtn;
    private TextView registrationQn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registrationBtn = findViewById(R.id.registrationBtn);
        registrationQn = findViewById(R.id.registrationQn);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        ProgressDialog progressDialog = new ProgressDialog(this);
        registrationQn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        registrationBtn.setOnClickListener(view -> {
            String emailString = email.getText().toString();
            String passwordString = password.getText().toString();
            if(TextUtils.isEmpty(emailString)){
                email.setError("Email is required");
            }
            if(TextUtils.isEmpty(passwordString)){
                password.setError("Password is required");
            }
            else{
                progressDialog.setMessage("registration in progress");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(emailString,passwordString).addOnCompleteListener( task -> {
                    if(task.isSuccessful()){
                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        progressDialog.dismiss();
                    }
                    else{
                        Toast.makeText(RegistrationActivity.this, Objects.requireNonNull(task.getException()).toString(),Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        });

    }
}
