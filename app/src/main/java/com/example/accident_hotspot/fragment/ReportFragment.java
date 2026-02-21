package com.example.accident_hotspot.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.accident_hotspot.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class ReportFragment extends Fragment {

    TextView tvLocation;
    Spinner spinnerAccidentType;
    EditText etDescription;
    ImageView ivAttach;

    Uri attachedFileUri = null;

    FusedLocationProviderClient fusedLocationProviderClient;

    private static final int REQUEST_LOCATION = 100;
    private static final int REQUEST_GALLERY = 200;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report, container, false);

        tvLocation = view.findViewById(R.id.tvLocation);
        spinnerAccidentType = view.findViewById(R.id.spinnerAccidentType);
        etDescription = view.findViewById(R.id.etDescription);
        ivAttach = view.findViewById(R.id.ivAttach);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        loadAccidentTypes();
        fetchLocation();

        ivAttach.setOnClickListener(v -> openGallery());

        view.findViewById(R.id.btnSubmitReport).setOnClickListener(v -> submitReport());

        return view;
    }

    // ------------------------- LOAD ACCIDENT TYPES -------------------------
    private void loadAccidentTypes() {
        String[] types = {
                "Select Accident Type",
                "Car Accident",
                "Bike Accident",
                "Truck Accident",
                "Pedestrian Hit",
                "Fire Accident",
                "Other"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                types
        );

        spinnerAccidentType.setAdapter(adapter);
    }

    // ------------------------- FETCH USER LOCATION -------------------------
    private void fetchLocation() {

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION);

        } else {

            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {

                if (location != null) {
                    tvLocation.setText("Lat: " + location.getLatitude() +
                            "\nLng: " + location.getLongitude());
                } else {
                    tvLocation.setText("Unable to fetch location");
                }

            });
        }
    }

    // ------------------------- GALLERY PICKER -------------------------
    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GALLERY && resultCode == Activity.RESULT_OK && data != null) {
            attachedFileUri = data.getData();
            ivAttach.setImageURI(attachedFileUri);
        }
    }

    // ------------------------- SUBMIT REPORT -------------------------
    private void submitReport() {

        String location = tvLocation.getText().toString();
        String type = spinnerAccidentType.getSelectedItem().toString();
        String description = etDescription.getText().toString();

        if (type.equals("Select Accident Type")) {
            Toast.makeText(requireContext(), "Select accident type", Toast.LENGTH_SHORT).show();
            return;
        }

        if (description.isEmpty()) {
            Toast.makeText(requireContext(), "Enter description", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(requireContext(), "Report Submitted Successfully!", Toast.LENGTH_LONG).show();
    }

    // ------------------------- PERMISSION RESULT -------------------------
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_LOCATION && grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchLocation();
        }
    }
}