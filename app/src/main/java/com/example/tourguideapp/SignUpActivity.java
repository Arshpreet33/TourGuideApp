package com.example.tourguideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {
TextView FirstNameSignup,LastNameSignup,EmailSingup,PhoneSignup,radioview;
Button Signupbtn;
FireBaseAuth Fauth;
ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Signupbtn=findViewById(R.id.btnSignUp);
        FirstNameSignup=findViewById(R.id.firstname);
        LastNameSignup=findViewById(R.id.lastname);
        EmailSingup=findViewById(R.id.textViewemail);
        PhoneSignup=findViewById(R.id.textViewphone);


        Signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
