package com.example.accident_hotspot.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.accident_hotspot.R;
import com.example.accident_hotspot.MapViewActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private TextView txtTraffic, txtWeather, txtEmergency;
    private TextView txtSafetyScoreValue, txtPastAlertsValue;
    private LinearLayout alertCard;

    // Google Map reference
    private GoogleMap mMap;

    public HomeFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize views
        alertCard = view.findViewById(R.id.alertCard);

        txtTraffic = view.findViewById(R.id.txtTraffic);
        txtWeather = view.findViewById(R.id.txtWeather);
        txtEmergency = view.findViewById(R.id.txtEmergency);

        txtSafetyScoreValue = view.findViewById(R.id.txtSafetyScoreValue);
        txtPastAlertsValue = view.findViewById(R.id.txtPastAlertsValue);

        // Load dashboard values
        loadDashboardData();
        loadConditions();

        // Click listener for alert card
        alertCard.setOnClickListener(v ->
                Toast.makeText(getContext(), "Alert details opened", Toast.LENGTH_SHORT).show()
        );

        // Initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.homeMap);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        return view;
    }

    // Map is ready callback
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Example accident location
        LatLng accidentSpot = new LatLng(28.6139, 77.2090); // Delhi example

        // Add marker
        mMap.addMarker(new MarkerOptions()
                .position(accidentSpot)
                .title("Accident Spot"));

        // Move camera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(accidentSpot, 14));

        // Map click listener → open full-screen map
        mMap.setOnMapClickListener(latLng -> {
            // Open MapviewActivity
            if (getContext() != null) {
                startActivity(new Intent(getContext(), MapViewActivity.class));
            }
        });
    }

    private void loadConditions() {
        txtTraffic.setText("Traffic");
        txtWeather.setText("Weather");
        txtEmergency.setText("Emergency");
    }

    private void loadDashboardData() {
        txtSafetyScoreValue.setText("82%");
        txtPastAlertsValue.setText("5");
    }
}
