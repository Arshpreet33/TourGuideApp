package com.example.tourguideapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtFirstName, txtLastName, txtPassword, txtEmail, txtPhone;
    TextView lblLogin;
    Button btnSignUp;

    private FirebaseAuth mAuth;

    //    FirebaseAuth fAuth;
    ProgressBar progressBar;
    //    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        txtFirstName = findViewById(R.id.txtFirstNameSignUp);
        txtLastName = findViewById(R.id.txtLastNameSignUp);
        txtEmail = findViewById(R.id.txtEmailSignUp);
        txtPhone = findViewById(R.id.txtPhoneSignUp);
        txtPassword = findViewById(R.id.txtPasswordSignUp);
        lblLogin = findViewById(R.id.lblLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(this);
        lblLogin.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

//        fStore = FirebaseFirestore.getInstance();
//
//        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);


//        if (fAuth.getCurrentUser() != null) {
//            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//            finish();
//        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSignUp) {
            btnSignUpClick(v);
        } else if (v.getId() == R.id.lblLogin) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }

    private void btnSignUpClick(View v) {

        final String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        final String firstName = txtFirstName.getText().toString();
        final String lastName = txtFirstName.getText().toString();
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

    }
}

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
