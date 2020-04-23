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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtName, txtAddress, txtPassword, txtEmail, txtPhone;
    TextView lblLogin;
    Button btnSignUp;
    Intent intent;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    DataServices service;

    boolean isLogin;
    int userID;

//    private FirebaseAuth mAuth;
//    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        txtName = findViewById(R.id.txtNameSignUp);
        txtAddress = findViewById(R.id.txtAddressSignUp);
        txtEmail = findViewById(R.id.txtEmailSignUp);
        txtPhone = findViewById(R.id.txtPhoneSignUp);
        txtPassword = findViewById(R.id.txtPasswordSignUp);
        lblLogin = findViewById(R.id.lblLogin);
        btnSignUp = findViewById(R.id.btnRegister);

        btnSignUp.setOnClickListener(this);
        lblLogin.setOnClickListener(this);

        sp = getSharedPreferences(MyVariables.cacheFile, Context.MODE_PRIVATE);

        isLogin = sp.getBoolean(MyVariables.keyLoginAuth, MyVariables.defaultLoginAuth);
        userID = sp.getInt(MyVariables.keyUserID, MyVariables.defaultUserID);

        if (isLogin && userID > 0) {
            finish();
            intent = new Intent(getApplicationContext(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

//        mAuth = FirebaseAuth.getInstance();
//        progressBar = findViewById(R.id.progressBar);

//        if (fAuth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//            finish();
//        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnRegister) {
            btnSignUpClick(v);
        } else if (v.getId() == R.id.lblLogin) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    private void btnSignUpClick(View v) {

        final String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        final String name = txtName.getText().toString();
        final String address = txtAddress.getText().toString();
        final String phone = txtPhone.getText().toString();

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
        loginDetails.setUser(new UserDetails(name, email, phone, address));

        service = RetrofitClientInstance.getRetrofitInstance().create(DataServices.class);

        Call<UserDetails> call = service.executeSignUp(loginDetails);

        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                UserDetails userDetails = response.body();

                if (userDetails.getStr().equals("Already registered")) {
                    Toast.makeText(SignUpActivity.this, "This email is already registered.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(SignUpActivity.this, userDetails.getStr(), Toast.LENGTH_SHORT).show();

                editor = sp.edit();
                editor.putBoolean(MyVariables.keyLoginAuth, true);
                editor.putInt(MyVariables.keyUserID, userDetails.getUserID());
                editor.apply();

                finish();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        /*  Firebase Authentication Code - Commented!!!

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String toastMessage = "";

                        progressBar.setVisibility(View.INVISIBLE);

                        if (task.isSuccessful()) {

                            User user = new User();
                            user.setEmail(email);
                            user.setFirstName(firstName);
                            user.setLastName(lastName);
                            user.setPhone(phone);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignUpActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        Toast.makeText(SignUpActivity.this, "Registration Successful. But could not save data.", Toast.LENGTH_SHORT).show();
                                    }
                                    finish();
                                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                }
                            });


                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                toastMessage = "This email is already registered. \n " +
                                        "Please login with this email or Register using another email.";
                            } else {
                                toastMessage = task.getException().getMessage();
                            }
                        }

                        Toast.makeText(SignUpActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                    }

                });
        */
    }
}

//  Commented superfluous code

//        btnSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {

//                final String email = FirstNameSignup.getText().toString().trim();
//                String password = LastNameSignup.getText().toString().trim();
//                final String fullName = EmailSingup.getText().toString();
//                final String phone = PhoneSignup.getText().toString();
//
//                if (TextUtils.isEmpty(email)) {
//                    FirstNameSignup.setError("Email is Required.");
//                    return;
//                }
//
//                if (TextUtils.isEmpty(password)) {
//                    Password.setError("Password is Required.");
//                    return;
//                }
//
//                if (password.length() < 6) {
//                    Password.setError("Password Must be >= 6 Characters");
//                    return;
//                }

//progressBar.setVisibility(View.VISIBLE);


// register the user in firebase

//                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(SignUpActivity.this, "User Created.", Toast.LENGTH_SHORT).show();
//  userID = fAuth.getCurrentUser().getUid();
/* DocumentReference documentReference = fStore.collection("users").document(userID);
                                Map<String,Object> user = new HashMap<>();
                                user.put("fName",fullName);
                                user.put("email",email);
                                user.put("phone",phone);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.d(TAG, "onFailure: " + e.toString());
                                    }
                                });*/
//                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//
//                        } else {
//                            Toast.makeText(SignUpActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    }
//                });
//            }
//        });
