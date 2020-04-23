package com.example.tourguideapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin, btnRegister;
    EditText txtEmail, txtPassword;
    TextView lblLogin, txtForgotPasswordLink;
    Intent intent;

//    private FirebaseAuth mAuth;

    DataServices service;

    //    FirebaseAuth fAuth;
//    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRegister = findViewById(R.id.btnRegister);
        btnLogin = findViewById(R.id.btnLogin);
        txtEmail = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        txtForgotPasswordLink = findViewById(R.id.txtForgotPasswordLink);
        lblLogin = findViewById(R.id.lblLogin);
//        progressBar = findViewById(R.id.progressBar);
//
//        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        txtForgotPasswordLink.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

//        if (mAuth.getCurrentUser() != null) {
//
//        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnLogin:
                btnLoginClick(v);
                break;
            case R.id.btnRegister:
                intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
                break;
            case R.id.txtForgotPasswordLink:
                intent = new Intent(MainActivity.this, ForgotPassword.class);
                startActivity(intent);
                break;
            default:
                Toast.makeText(this, "Invalid click operation!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void btnLoginClick(View v) {

        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            txtEmail.setError("Email is Required.");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            txtPassword.setError("Password is Required.");
            return;
        }

        if (password.length() < 6) {
            txtPassword.setError("Password Must be >= 6 Characters");
            return;
        }

        LoginDetails loginDetails = new LoginDetails(email, password);

        service = RetrofitClientInstance.getRetrofitInstance().create(DataServices.class);

        Call<String> call = service.executeLogin(loginDetails);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!response.body().equals("Valid")) {
                    Toast.makeText(MainActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                    return;
                }
                finish();
                intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        /*  Firebase Authentication Code - Commented!!!

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.INVISIBLE);

                if (task.isSuccessful()) {
                    intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
         */
    }
}
