package com.example.accident_hotspot;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.accident_hotspot.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapViewActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        // Initialize the map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Example accident hotspot locations (add more if needed)
        LatLng accidentSpot1 = new LatLng(28.6139, 77.2090); // Delhi
        LatLng accidentSpot2 = new LatLng(28.7041, 77.1025); // Another example

        // Add markers on the map
        mMap.addMarker(new MarkerOptions().position(accidentSpot1).title("Accident Spot 1"));
        mMap.addMarker(new MarkerOptions().position(accidentSpot2).title("Accident Spot 2"));

        // Move camera to first spot
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(accidentSpot1, 12));

        // Optional: enable zoom controls
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(true);
    }
}
