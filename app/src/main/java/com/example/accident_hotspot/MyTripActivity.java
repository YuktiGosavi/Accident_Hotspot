package com.example.accident_hotspot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MyTripActivity extends AppCompatActivity {

    ImageView ivback, ivback1, ivback2, ivback3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_trip);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Toolbar Back Arrow Click
        toolbar.setNavigationOnClickListener(v -> finish());

        initViews();
        setupListeners();
    }

    private void initViews() {
        ivback = findViewById(R.id.ivback);     // Destination
        ivback1 = findViewById(R.id.ivback1);   // Distance
        ivback2 = findViewById(R.id.ivback2);   // Avg Speed
        ivback3 = findViewById(R.id.ivback3);   // Arrival Time
    }

    private void setupListeners() {

        // OPEN DESTINATION
        ivback.setOnClickListener(v -> {
            Intent intent = new Intent(MyTripActivity.this, EditTripActivity.class);
            intent.putExtra("editType", "destination");
            startActivity(intent);
        });

        // OPEN DISTANCE
        ivback1.setOnClickListener(v -> {
            Intent intent = new Intent(MyTripActivity.this, EditTripActivity.class);
            intent.putExtra("editType", "distance");
            startActivity(intent);
        });

        // OPEN AVG SPEED
        ivback2.setOnClickListener(v -> {
            Intent intent = new Intent(MyTripActivity.this, EditTripActivity.class);
            intent.putExtra("editType", "avg_speed");
            startActivity(intent);
        });

        // OPEN ARRIVAL TIME
        ivback3.setOnClickListener(v -> {
            Intent intent = new Intent(MyTripActivity.this, EditTripActivity.class);
            intent.putExtra("editType", "arrival_time");
            startActivity(intent);
        });
    }
}
