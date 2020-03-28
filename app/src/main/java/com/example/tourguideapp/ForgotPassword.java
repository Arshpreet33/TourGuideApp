package com.example.tourguideapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPassword extends AppCompatActivity {
    Button btnResetEmail;
    EditText textEmailReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        textEmailReset = findViewById(R.id.textEmailReset);
        btnResetEmail = findViewById(R.id.btnResetEmail);

        btnResetEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
