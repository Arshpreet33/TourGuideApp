package com.example.tourguideapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ForgotPassword extends AppCompatActivity {
Button ResetLinkbtn;
TextView resetpasswordemail;
EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ResetLinkbtn=findViewById(R.id.resetbtn);
        resetpasswordemail=findViewById(R.id.emailreset);
        email=findViewById(R.id.emaillinktxt);

    ResetLinkbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    });


    }

}
