package com.example.tourguideapp;

import android.os.Bundle;
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

public class EditProfileFragment extends Fragment implements View.OnClickListener {
    private EditText txtName, txtAddress, txtPhone;
    private Button btnResetProfile, btnSaveProfile;
    private UserDetails userDetails;
    private DataServices service;
    int userID;

    public EditProfileFragment() {
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
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtName = getActivity().findViewById(R.id.txtNameProfile);
        txtAddress = getActivity().findViewById(R.id.txtAddressProfile);
        txtPhone = getActivity().findViewById(R.id.txtPhoneProfile);
        btnSaveProfile = getActivity().findViewById(R.id.btnSaveProfile);
        btnResetProfile = getActivity().findViewById(R.id.btnResetProfile);

        btnSaveProfile.setOnClickListener(this);
        btnResetProfile.setOnClickListener(this);
        userID = HomeActivity.userID;
        getUserDetails();
    }

    private void getUserDetails() {

        service = RetrofitClientInstance.getRetrofitInstance().create(DataServices.class);

        Call<UserDetails> call = service.executeGetUserProfileByID(userID);

        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity().getApplicationContext(), "r" + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                userDetails = response.body();
                setUserDetails();
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "t" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void setUserDetails() {
        txtName.setText(userDetails.getName());
        txtAddress.setText(userDetails.getAddress());
        txtPhone.setText(userDetails.getPhone());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnResetProfile) {
            btnResetProfileClick(v);
        } else if (v.getId() == R.id.btnSaveProfile) {
            btnSaveProfileClick(v);
        }
    }

    private void btnResetProfileClick(View v) {
        setUserDetails();
    }

    private void btnSaveProfileClick(View v) {

        final String firstName = txtName.getText().toString();
        final String phone = txtPhone.getText().toString();
        final String address = txtAddress.getText().toString();

        UserDetails userDetail = new UserDetails();
        userDetail.setUserID(userID);
        userDetail.setName(firstName);
        userDetail.setPhone(phone);
        userDetail.setAddress(address);

        service = RetrofitClientInstance.getRetrofitInstance().create(DataServices.class);

        Call<String> call = service.executeEditUserProfile(userDetail);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity().getApplicationContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!response.body().equals("success")) {
                    Toast.makeText(getActivity().getApplicationContext(), "Could not save the changes. Please try again!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getActivity().getApplicationContext(), "Changes saved", Toast.LENGTH_SHORT).show();

//                userDetails = userDetail;
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }
}
