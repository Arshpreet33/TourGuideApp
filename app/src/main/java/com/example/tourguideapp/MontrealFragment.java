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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MontrealFragment extends Fragment implements View.OnClickListener {

    EditText txtSearchDrink;
    Button btnSearch;
    int cityID;

    private ArrayList<Place> placeList;

    DataServices service;

    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private RecyclerView.LayoutManager recyclerLayoutManager;

    private NavController navController;

    public MontrealFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_montreal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        txtSearchDrink = getActivity().findViewById(R.id.text_search_place);
        btnSearch = getActivity().findViewById(R.id.button_search);

        btnSearch.setOnClickListener(this);

        cityID = 1;
        getAllPlaces();
    }

    private void getAllPlaces() {
        service = RetrofitClientInstance.getRetrofitInstance().create(DataServices.class);

        Call<Places> call = service.executeGetPlacesByCityID(cityID);

        call.enqueue(new Callback<Places>() {
            @Override
            public void onResponse(Call<Places> call, Response<Places> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity().getApplicationContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Places places = response.body();
                    placeList = new ArrayList<>(places.getPlaceList());

                    fillRecyclerView();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Places> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_search) {
            btnSearchClick(v);
        }
    }

    private void btnSearchClick(View v) {

        String searchedText = txtSearchDrink.getText().toString();

        service = RetrofitClientInstance.getRetrofitInstance().create(DataServices.class);

        Place place = new Place();
        place.setCityID(cityID);
        place.setStr(searchedText);
        Call<Places> call = service.executeSearchPlaces(place);

        call.enqueue(new Callback<Places>() {
            @Override
            public void onResponse(Call<Places> call, Response<Places> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity().getApplicationContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Places places = response.body();
                    placeList = new ArrayList<>(places.getPlaceList());

                    fillRecyclerView();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Places> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fillRecyclerView() {
        recyclerView = getActivity().findViewById(R.id.recycler_view_places);
        recyclerLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerAdapter = new RecyclerAdapter(placeList);
        recyclerView.setLayoutManager(recyclerLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Bundle bundle = new Bundle();
                bundle.putParcelable("place", placeList.get(position));
                navController.navigate(R.id.displayFragment, bundle);
            }
        });
    }
}
