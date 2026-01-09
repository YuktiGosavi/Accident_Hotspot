package com.example.accident_hotspot;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.accident_hotspot.VehiclePrefManager;

public class VehicleInfoActivity extends AppCompatActivity {

    TextView tvModel, tvLicense, tvType, tvFuel, tvRating, tvService;
    Button btnEdit, btnAdd;

    VehiclePrefManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_info);

        manager = new VehiclePrefManager(this);

        tvModel = findViewById(R.id.tvModel);
        tvLicense = findViewById(R.id.tvLicense);
        tvType = findViewById(R.id.tvType);
        tvFuel = findViewById(R.id.tvFuel);
        tvRating = findViewById(R.id.tvRating);
        tvService = findViewById(R.id.tvService);

        btnEdit = findViewById(R.id.btnEdit);
        btnAdd = findViewById(R.id.btnAdd);

        loadData();

        btnAdd.setOnClickListener(v -> {
            manager.saveVehicle(
                    "2024 Model",
                    "MH-12-XY-9090",
                    "SUV",
                    "Electric",
                    "5-Star",
                    "01/2026"
            );
            loadData();
            Toast.makeText(this, "Vehicle Added", Toast.LENGTH_SHORT).show();
        });

        btnEdit.setOnClickListener(v ->
                Toast.makeText(this, "Edit feature can be added", Toast.LENGTH_SHORT).show()
        );
    }

    private void loadData() {
        tvModel.setText(manager.getModel());
        tvLicense.setText("License Plate: " + manager.getLicense());
        tvType.setText("Vehicle Type: " + manager.getType());
        tvFuel.setText("Fuel Type: " + manager.getFuel());
        tvRating.setText("Safety Rating: " + manager.getRating());
        tvService.setText("Last Service: " + manager.getService());
    }
}
