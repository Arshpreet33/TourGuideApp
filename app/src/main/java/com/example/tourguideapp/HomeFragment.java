package com.example.tourguideapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private TextView homeUser, homeContent;
    private DataServices service;
    int userID;

    public HomeFragment() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeUser = getActivity().findViewById(R.id.lbl_home_user);
        homeContent = getActivity().findViewById(R.id.lbl_home_content);

        homeContent.setText("Tour Guide app provides you the information about the tourist places \n" +
                " in Canada. We currently have information about Montreal and Toronto. \n" +
                "Click on MENU in top-left corner to explore this app. \n");

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
                UserDetails userDetails = response.body();
                homeUser.setText(userDetails.getName());
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), "t" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}
