package com.example.accident_hotspot;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class EditTripActivity extends AppCompatActivity {

    LinearLayout layoutDestination, layoutDistance, layoutAvgSpeed, layoutArrivalTime;

    Spinner spinnerCountry, spinnerState, spinnerCity;
    Button btnSaveDestination;

    EditText etDistanceKm;
    Button btnSaveDistance;

    EditText etAvgSpeed;
    Button btnSaveAvgSpeed;

    EditText etArrivalTime;
    Button btnSaveArrivalTime;

    String[] indiaStates;
    HashMap<String, String[]> indiaCities = new HashMap<>();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        // ---------- FIND VIEWS ----------
        layoutDestination = findViewById(R.id.layoutDestination);
        layoutDistance = findViewById(R.id.layoutDistance);
        layoutAvgSpeed = findViewById(R.id.layoutAvgSpeed);
        layoutArrivalTime = findViewById(R.id.layoutArrivalTime);

        spinnerCountry = findViewById(R.id.spinnerCountry);
        spinnerState = findViewById(R.id.spinnerState);
        spinnerCity = findViewById(R.id.spinnerCity);
        btnSaveDestination = findViewById(R.id.btnSaveDestination);

        etDistanceKm = findViewById(R.id.etDistance);
        btnSaveDistance = findViewById(R.id.btnSaveDistance);

        etAvgSpeed = findViewById(R.id.etAvgSpeed);
        btnSaveAvgSpeed = findViewById(R.id.btnSaveAvgSpeed);

        etArrivalTime = findViewById(R.id.etArrivalTime);
        btnSaveArrivalTime = findViewById(R.id.btnSaveArrivalTime);

        // ---------- LOAD DATA ----------
        loadIndiaStates();
        loadIndiaCities();
        setupCountrySpinner();

        // ---------- FIX NULL CRASH ----------
        String editType = getIntent().getStringExtra("editType");

        if (editType == null || editType.trim().isEmpty()) {
            editType = "destination";  // Default to avoid crash
        }

        showSelectedLayout(editType);

        // ---------- SAVE BUTTONS ----------
        btnSaveDestination.setOnClickListener(v -> finish());
        btnSaveDistance.setOnClickListener(v -> finish());
        btnSaveAvgSpeed.setOnClickListener(v -> finish());
        btnSaveArrivalTime.setOnClickListener(v -> finish());
    }

    // ---------- SHOW SELECTED SECTION ----------
    private void showSelectedLayout(String type) {

        // Hide all first
        layoutDestination.setVisibility(View.GONE);
        layoutDistance.setVisibility(View.GONE);
        layoutAvgSpeed.setVisibility(View.GONE);
        layoutArrivalTime.setVisibility(View.GONE);

        if (type == null) return;  // Prevent crash

        switch (type) {

            case "destination":
                layoutDestination.setVisibility(View.VISIBLE);
                break;

            case "distance":
                layoutDistance.setVisibility(View.VISIBLE);
                break;

            case "avg_speed":
            case "speed":
                layoutAvgSpeed.setVisibility(View.VISIBLE);
                break;

            case "arrival_time":
            case "arrival":
                layoutArrivalTime.setVisibility(View.VISIBLE);
                break;

            default:
                layoutDestination.setVisibility(View.VISIBLE);
                break;
        }
    }

    // ---------- COUNTRY SPINNER ----------
    private void setupCountrySpinner() {

        String[] countries = {"India"};

        ArrayAdapter<String> countryAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countries);

        spinnerCountry.setAdapter(countryAdapter);

        spinnerCountry.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int pos, long id) {
                loadStates("India");
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });
    }

    // ---------- LOAD STATES ----------
    private void loadStates(String country) {

        ArrayAdapter<String> stateAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, indiaStates);

        spinnerState.setAdapter(stateAdapter);

        spinnerState.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selectedState = spinnerState.getSelectedItem().toString();
                loadCities(selectedState);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });
    }

    // ---------- LOAD CITIES ----------
    private void loadCities(String state) {

        if (indiaCities.containsKey(state)) {

            ArrayAdapter<String> cityAdapter =
                    new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                            indiaCities.get(state));

            spinnerCity.setAdapter(cityAdapter);
        }
    }

    // ---------- STATES OF INDIA ----------
    private void loadIndiaStates() {

        indiaStates = new String[]{
                "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh",
                "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jharkhand",
                "Karnataka", "Kerala", "Madhya Pradesh", "Maharashtra", "Manipur",
                "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Punjab",
                "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura",
                "Uttar Pradesh", "Uttarakhand", "West Bengal",
                "Andaman & Nicobar Islands", "Chandigarh",
                "Dadra & Nagar Haveli and Daman & Diu", "Delhi", "Jammu & Kashmir",
                "Ladakh", "Lakshadweep", "Puducherry"
        };
    }

    // ---------- CITIES OF INDIA ----------
    private void loadIndiaCities() {

        indiaCities.put("Andhra Pradesh", new String[]{"Visakhapatnam", "Vijayawada", "Tirupati"});
        indiaCities.put("Arunachal Pradesh", new String[]{"Itanagar", "Tawang", "Ziro"});
        indiaCities.put("Assam", new String[]{"Guwahati", "Dibrugarh", "Silchar"});
        indiaCities.put("Bihar", new String[]{"Patna", "Gaya", "Bhagalpur"});
        indiaCities.put("Chhattisgarh", new String[]{"Raipur", "Bhilai", "Bilaspur"});
        indiaCities.put("Goa", new String[]{"Panaji", "Margao", "Vasco da Gama"});
        indiaCities.put("Gujarat", new String[]{"Ahmedabad", "Surat", "Vadodara"});
        indiaCities.put("Haryana", new String[]{"Gurugram", "Faridabad", "Panipat"});
        indiaCities.put("Himachal Pradesh", new String[]{"Shimla", "Manali", "Dharamshala"});
        indiaCities.put("Jharkhand", new String[]{"Ranchi", "Jamshedpur", "Dhanbad"});
        indiaCities.put("Karnataka", new String[]{"Bengaluru", "Mysuru", "Mangalore"});
        indiaCities.put("Kerala", new String[]{"Kochi", "Thiruvananthapuram", "Kozhikode"});
        indiaCities.put("Madhya Pradesh", new String[]{"Indore", "Bhopal", "Gwalior"});
        indiaCities.put("Maharashtra", new String[]{"Mumbai", "Pune", "Nagpur"});
        indiaCities.put("Manipur", new String[]{"Imphal", "Churachandpur", "Thoubal"});
        indiaCities.put("Meghalaya", new String[]{"Shillong", "Tura", "Jowai"});
        indiaCities.put("Mizoram", new String[]{"Aizawl", "Lunglei", "Champhai"});
        indiaCities.put("Nagaland", new String[]{"Kohima", "Dimapur", "Mokokchung"});
        indiaCities.put("Odisha", new String[]{"Bhubaneswar", "Cuttack", "Puri"});
        indiaCities.put("Punjab", new String[]{"Amritsar", "Ludhiana", "Jalandhar"});
        indiaCities.put("Rajasthan", new String[]{"Jaipur", "Udaipur", "Jodhpur"});
        indiaCities.put("Sikkim", new String[]{"Gangtok", "Pelling", "Namchi"});
        indiaCities.put("Tamil Nadu", new String[]{"Chennai", "Coimbatore", "Madurai"});
        indiaCities.put("Telangana", new String[]{"Hyderabad", "Warangal", "Nizamabad"});
        indiaCities.put("Tripura", new String[]{"Agartala", "Udaipur", "Dharmanagar"});
        indiaCities.put("Uttar Pradesh", new String[]{"Lucknow", "Kanpur", "Varanasi"});
        indiaCities.put("Uttarakhand", new String[]{"Dehradun", "Haridwar", "Nainital"});
        indiaCities.put("West Bengal", new String[]{"Kolkata", "Siliguri", "Durgapur"});

        indiaCities.put("Andaman & Nicobar Islands", new String[]{"Port Blair", "Havelock", "Diglipur"});
        indiaCities.put("Chandigarh", new String[]{"Chandigarh"});
        indiaCities.put("Dadra & Nagar Haveli and Daman & Diu", new String[]{"Daman", "Diu"});
        indiaCities.put("Delhi", new String[]{"New Delhi", "Dwarka", "Rohini"});
        indiaCities.put("Jammu & Kashmir", new String[]{"Srinagar", "Jammu", "Gulmarg"});
        indiaCities.put("Ladakh", new String[]{"Leh", "Kargil"});
        indiaCities.put("Lakshadweep", new String[]{"Kavaratti", "Agatti"});
        indiaCities.put("Puducherry", new String[]{"Puducherry", "Karaikal"});
    }
}
