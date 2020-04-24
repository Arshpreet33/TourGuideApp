package com.example.tourguideapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisplayFragment extends Fragment {

    private TextView placeName, placeLocation, placeDescription;
    private ImageView placeImage;

    private Place place;
//    private ArrayList<Place> placeList;

    public DisplayFragment() {
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
        return inflater.inflate(R.layout.fragment_display, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        place = getArguments().getParcelable("place");

        placeName = getActivity().findViewById(R.id.place_name);
        placeLocation = getActivity().findViewById(R.id.place_location);
        placeDescription = getActivity().findViewById(R.id.place_description);
        placeImage = getActivity().findViewById(R.id.place_image);

        getPlaceDetails(place.getPlaceID());
    }

    private void fillData() {

        placeName.setText(place.getName());
        placeLocation.setText(place.getLocation());
        placeDescription.setText(place.getDescription());

        Picasso.get().load(place.getImageURL()).into(placeImage);
    }

    private void getPlaceDetails(int ID) {

        DataServices service = RetrofitClientInstance.getRetrofitInstance().create(DataServices.class);

        Call<Place> call = service.executeGetPlacesByID(ID);

        call.enqueue(new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getActivity().getApplicationContext(), response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
//                    Places places = response.body();
//                    placeList = new ArrayList<>(places.getPlaceList());

                    place = response.body();

                    fillData();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Place> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
