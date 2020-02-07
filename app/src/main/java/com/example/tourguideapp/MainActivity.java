package com.example.tourguideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogin, btnSignUp;
    TextView txtUserName, txtPassword;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnLogin = findViewById(R.id.btnLogin);
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);

        btnLogin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:

                intent = new Intent(MainActivity.this, HomeActivity.class);
                break;

            case R.id.btnSignUp:

                /**TODO - Intent for SignUpPage

                intent = new Intent(MainActivity.this, SignUpActivity.class);
                 */
                break;

            default:
                Toast.makeText(this, "Invalid click operation!", Toast.LENGTH_SHORT).show();
                break;
        }

        startActivity(intent);
    }
}
