package com.example.tourguideapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfile extends AppCompatActivity implements View.OnClickListener {
    EditText txtName, txtAddress, txtPhone;
    Button btnCancelProfile, btnSaveProfile;
    Intent intent;
    SharedPreferences sp;
    int userID;

    DataServices service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
//
//        txtName = findViewById(R.id.txtNameProfile);
//        txtAddress = findViewById(R.id.txtAddressProfile);
//        txtPhone = findViewById(R.id.txtPhoneProfile);
//        btnSaveProfile = findViewById(R.id.btnSaveProfile);
//        btnCancelProfile = findViewById(R.id.btnCancelProfile);
//
//        btnSaveProfile.setOnClickListener(this);
//        btnCancelProfile.setOnClickListener(this);

        if (!validateLogin()) {
            Toast.makeText(this, "Login to continue", Toast.LENGTH_SHORT).show();
            finish();
            intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        getUserDetails();
    }

    private void getUserDetails() {

        service = RetrofitClientInstance.getRetrofitInstance().create(DataServices.class);

        Call<UserDetails> call = service.executeGetUserProfileByID(userID);

        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "r" + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }

                UserDetails userDetails = response.body();

                txtName.setText(userDetails.getName());
                txtAddress.setText(userDetails.getAddress());
                txtPhone.setText(userDetails.getPhone());

            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "t" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private boolean validateLogin() {
        sp = getSharedPreferences(MyVariables.cacheFile, Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean(MyVariables.keyLoginAuth, MyVariables.defaultLoginAuth);
        userID = sp.getInt(MyVariables.keyUserID, MyVariables.defaultUserID);

        if (isLogin && userID > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.btnCancelProfile) {
//            finish();
//            intent = new Intent(getApplicationContext(), HomeActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//        } else if (v.getId() == R.id.btnSaveProfile) {
//            btnSaveProfileClick(v);
//        }
    }

    private void btnSaveProfileClick(View v) {

        final String firstName = txtName.getText().toString();
        final String phone = txtPhone.getText().toString();
        final String address = txtAddress.getText().toString();

        UserDetails userDetails = new UserDetails();
        userDetails.setUserID(userID);
        userDetails.setName(firstName);
        userDetails.setPhone(phone);
        userDetails.setAddress(address);

        service = RetrofitClientInstance.getRetrofitInstance().create(DataServices.class);

        Call<String> call = service.executeEditUserProfile(userDetails);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(UserProfile.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!response.body().equals("success")) {
                    Toast.makeText(UserProfile.this, "Could not save the changes. Please try again!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(UserProfile.this, "Changes saved", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(UserProfile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
