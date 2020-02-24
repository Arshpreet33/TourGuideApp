package com.example.tourguideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin, btnSignUp;
    TextView txtUserName, txtPassword;
    Intent intent;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        fAuth=FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        String email = txtUserName.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            txtUserName.setError("Email is Required.");
            return;
        }

        if(TextUtils.isEmpty(password)){
            txtPassword.setError("Password is Required.");
            return;
        }

        if(password.length() < 6){
            txtPassword.setError("Password Must be >= 6 Characters");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);



        switch (v.getId()) {
            case R.id.btnLogin:
                intent = new Intent(MainActivity.this, HomeActivity.class);
                break;
            case R.id.btnSignUp:
                intent = new Intent(MainActivity.this, SignUpActivity.class);
                break;
            default:
                Toast.makeText(this, "Invalid click operation!", Toast.LENGTH_SHORT).show();
                break;
        }

        startActivity(intent);
    }
}
