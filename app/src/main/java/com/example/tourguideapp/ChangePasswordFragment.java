package com.example.tourguideapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordFragment extends Fragment implements View.OnClickListener {
    private EditText txtCurrentPassword, txtNewPassword, txtNewConfirmPassword;
    private Button btnSavePassword, btnResetPassword;
    private DataServices service;
    int userID;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtCurrentPassword = getActivity().findViewById(R.id.txtCurrentPassword);
        txtNewPassword = getActivity().findViewById(R.id.txtNewPassword);
        txtNewConfirmPassword = getActivity().findViewById(R.id.txtNewConfirmPassword);
        btnResetPassword = getActivity().findViewById(R.id.btnResetPassword);
        btnSavePassword = getActivity().findViewById(R.id.btnSavePassword);

        btnSavePassword.setOnClickListener(this);
        btnResetPassword.setOnClickListener(this);
        userID = HomeActivity.userID;
    }

    private void resetFields() {
        txtCurrentPassword.setText("");
        txtNewPassword.setText("");
        txtNewConfirmPassword.setText("");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnResetPassword) {
            resetFields();
        } else if (v.getId() == R.id.btnSavePassword) {
            btnSavePasswordClick(v);
        }
    }

    private void btnSavePasswordClick(View v) {

        final String passwordRequired = "New Password is Required";
        final String passwordShort = "New Password Must be >= 6 Characters";
        final String passwordMatch = "Passwords do not match";

        String currentPassword = txtCurrentPassword.getText().toString().trim();
        String newPassword = txtNewPassword.getText().toString().trim();
        String newConfirmPassword = txtNewConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(newPassword)) {
            txtNewPassword.requestFocus();
            txtNewPassword.setError(passwordRequired);
            return;
        }

        if (newPassword.length() < 6) {
            txtNewPassword.requestFocus();
            txtNewPassword.setError(passwordShort);
            return;
        }

        if (!newConfirmPassword.equals(newPassword)) {
            txtNewConfirmPassword.requestFocus();
            txtNewConfirmPassword.setError(passwordMatch);
            return;
        }

        final ChangePassword changePassword = new ChangePassword();
        changePassword.setCurrentPassword(currentPassword);
        changePassword.setNewPassword(newPassword);
        changePassword.setUserID(userID);

        service = RetrofitClientInstance.getRetrofitInstance().create(DataServices.class);

        Call<ChangePassword> call = service.executeEditUserPassword(changePassword);

        call.enqueue(new Callback<ChangePassword>() {
            @Override
            public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity().getApplicationContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                ChangePassword password = response.body();

                if (!password.getMessage().equals("i")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                }

                Toast.makeText(getActivity().getApplicationContext(), password.getData(), Toast.LENGTH_LONG).show();
                resetFields();
            }

            @Override
            public void onFailure(Call<ChangePassword> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
}
