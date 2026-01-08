package com.example.accident_hotspot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    TextView viewDetails;
    LinearLayout heavyTrafficItem, nearbyItem;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        viewDetails = findViewById(R.id.btnViewDetails);
        heavyTrafficItem = findViewById(R.id.ivarrow1);
        nearbyItem = findViewById(R.id.ivarrow2);

        // View Details click
        viewDetails.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, ViewDetailsActivity.class);
            startActivity(intent);
        });

        // Heavy Traffic click
        heavyTrafficItem.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, HeavyTrafficActivity.class);
            startActivity(intent);
        });

        // Nearby Pothole click
        nearbyItem.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, NearbyActivity.class);
            startActivity(intent);
        });
    }
}
