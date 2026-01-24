package com.example.accident_hotspot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    TextView viewDetails;
    ImageView heavyTrafficItem, nearbyItem;   // FIXED TYPE

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        viewDetails = findViewById(R.id.btnViewDetails);

        // FIXED — ImageView instead of LinearLayout
        heavyTrafficItem = findViewById(R.id.ivarrow1);
        nearbyItem = findViewById(R.id.ivarrow2);

        // View Details click
        viewDetails.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, ViewDetailsActivity.class));
        });

        // Heavy Traffic click
        heavyTrafficItem.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, HeavyTrafficActivity.class));
        });

        // Nearby Pothole click
        nearbyItem.setOnClickListener(v -> {
            startActivity(new Intent(DashboardActivity.this, NearbyActivity.class));
        });
    }
}
