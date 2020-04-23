package com.example.tourguideapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

    Button btnLogin;
    EditText txtEmail, txtPassword;
    TextView lblRegister;
    Intent intent;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    DataServices service;

    boolean isLogin;
    int userID;

//    private FirebaseAuth mAuth;

    //    FirebaseAuth fAuth;
//    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lblRegister = findViewById(R.id.lblRegister);
        btnLogin = findViewById(R.id.btnLogin);
        txtEmail = findViewById(R.id.txtUserNameLogin);
        txtPassword = findViewById(R.id.txtPasswordLogin);

//        progressBar = findViewById(R.id.progressBar);
//        mAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(this);
        lblRegister.setOnClickListener(this);

        sp = getSharedPreferences(MyVariables.cacheFile, Context.MODE_PRIVATE);

        isLogin = sp.getBoolean(MyVariables.keyLoginAuth, MyVariables.defaultLoginAuth);
        userID = sp.getInt(MyVariables.keyUserID, MyVariables.defaultUserID);

        if (isLogin && userID > 0) {
            finish();
            intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

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
            case R.id.lblRegister:
                intent = new Intent(MainActivity.this, SignUpActivity.class);
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

        Call<UserDetails> call = service.executeLogin(loginDetails);

        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                UserDetails userDetails = response.body();

                if (!userDetails.getStr().equals("Valid")) {
                    Toast.makeText(MainActivity.this, userDetails.getStr(), Toast.LENGTH_SHORT).show();
                    return;
                }

                editor = sp.edit();
                editor.putBoolean(MyVariables.keyLoginAuth, true);
                editor.putInt(MyVariables.keyUserID, userDetails.getUserID());
                editor.apply();

                finish();
                intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
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
