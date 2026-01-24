package com.example.accident_hotspot;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class AddnewVehicleActivity extends AppCompatActivity {

    Spinner spinnerMake, spinnerYear, spinnerType, spinnerFuel;
    EditText etModel, etPlate, etVin;
    Button btnAddVehicle;
    ImageView btnScanVin;

    static final int CAMERA_REQUEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew_vehicle);

        init();
        setupSpinners();
        listeners();
    }

    private void init() {
        spinnerMake = findViewById(R.id.spinnerMake);
        spinnerYear = findViewById(R.id.spinnerYear);
        spinnerType = findViewById(R.id.spinnerType);
        spinnerFuel = findViewById(R.id.spinnerFuel);
        etModel = findViewById(R.id.etModel);
        etPlate = findViewById(R.id.etPlate);
        etVin = findViewById(R.id.etVin);
        btnAddVehicle = findViewById(R.id.btnAddVehicle);
        btnScanVin = findViewById(R.id.btnScanVin);
    }

    private void setupSpinners() {

        spinnerMake.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Select Make","Honda","Hyundai","Toyota","Tata","Mahindra"}));

        ArrayList<String> years = new ArrayList<>();
        years.add("Select Year");
        for (int i = Calendar.getInstance().get(Calendar.YEAR); i >= 1990; i--) {
            years.add(String.valueOf(i));
        }
        spinnerYear.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, years));

        spinnerType.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Vehicle Type","Car","Bike","Truck","Bus"}));

        spinnerFuel.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Fuel Type","Petrol","Diesel","Electric","CNG"}));
    }

    private void listeners() {

        btnScanVin.setOnClickListener(v -> {
            Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(camera, CAMERA_REQUEST);
        });

        btnAddVehicle.setOnClickListener(v -> {
            if (validate()) {
                saveVehicle();
                Toast.makeText(this, "Vehicle Added Successfully", Toast.LENGTH_LONG).show();
                finish(); // go back to VehicleInfoActivity
            }
        });
    }

    private boolean validate() {
        if (spinnerMake.getSelectedItemPosition() == 0) return toast("Select Make");
        if (etModel.getText().toString().trim().isEmpty()) return error(etModel);
        if (spinnerYear.getSelectedItemPosition() == 0) return toast("Select Year");
        if (etPlate.getText().toString().trim().isEmpty()) return error(etPlate);
        if (spinnerType.getSelectedItemPosition() == 0) return toast("Select Vehicle Type");
        if (spinnerFuel.getSelectedItemPosition() == 0) return toast("Select Fuel Type");
        return true;
    }

    // ✅ CORRECT SAVE METHOD (VehiclePrefManager)
    private void saveVehicle() {

        VehiclePrefManager manager = new VehiclePrefManager(this);

        String model = etModel.getText().toString();
        String license = etPlate.getText().toString();
        String type = spinnerType.getSelectedItem().toString();
        String fuel = spinnerFuel.getSelectedItem().toString();

        // Static for now (can be dynamic later)
        String rating = "5-Star";
        String service = "Not Scheduled";

        manager.saveVehicle(
                model,
                license,
                type,
                fuel,
                rating,
                service
        );
    }

    private boolean toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        return false;
    }

    private boolean error(EditText e) {
        e.setError("Required");
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            etVin.setText("VIN" + System.currentTimeMillis());
        }
    }
}
