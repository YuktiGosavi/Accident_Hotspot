package com.example.accident_hotspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView mapImage;
    LinearLayout alertCard;
    BottomNavigationView bottomNav;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    // info boxes
    TextView txtTraffic, txtWeather, txtEmergency;

    // dashboard values
    TextView txtSafetyScore, txtPastAlerts, txtSafetyScoreValue, txtPastAlertsValue;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        preferences = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        editor = preferences.edit();

        boolean isFirstTime = preferences.getBoolean("isFirstTime", true);
        if (isFirstTime) welcomePopup();

        setupToolbar();
        initializeViews();
        setupDrawerHeader();
        setupDrawerMenuClick(); // <-- ADDED
        setupBottomNav();
        setupMapClick();

        updateDynamicData();
    }

    private void welcomePopup() {
        AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity.this);
        ad.setTitle("Accident Hotspot Finder");
        ad.setMessage("Welcome to Accident Hotspot Finder");
        ad.setPositiveButton("Thank You", (dialog, which) -> dialog.dismiss());
        ad.show();

        editor.putBoolean("isFirstTime", false).apply();
    }

    private void setupToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START));
    }

    private void initializeViews() {
        mapImage = findViewById(R.id.mapView);

        txtTraffic = findViewById(R.id.txtTraffic);
        txtWeather = findViewById(R.id.txtWeather);
        txtEmergency = findViewById(R.id.txtEmergency);

        txtSafetyScore = findViewById(R.id.txtSafetyScore);
        txtPastAlerts = findViewById(R.id.txtPastAlerts);

        txtSafetyScoreValue = findViewById(R.id.txtSafetyScoreValue);
        txtPastAlertsValue = findViewById(R.id.txtPastAlertsValue);

        alertCard = findViewById(R.id.alertCard);

        bottomNav = findViewById(R.id.bottomNav);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
    }

    // Drawer Header Update
    private void setupDrawerHeader() {
        View headerView = navigationView.getHeaderView(0);

        TextView txtName = headerView.findViewById(R.id.txtName);
        TextView txtEmail = headerView.findViewById(R.id.txtEmail);
        ImageView profileImage = headerView.findViewById(R.id.profileImage);

        SharedPreferences prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE);

        txtName.setText(prefs.getString("name", "Your Name"));
        txtEmail.setText(prefs.getString("email", "your@gmail.com"));

        String imgUri = prefs.getString("profileImage", "");
        if (!imgUri.isEmpty()) profileImage.setImageURI(android.net.Uri.parse(imgUri));

        profileImage.setOnClickListener(v ->
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class)));
    }

    // -------------------------------------------------------------------------
    // Drawer Menu Click Listener (ADDED)
    // -------------------------------------------------------------------------
    private void setupDrawerMenuClick() {

        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_dashboard) {
                startActivity(new Intent(HomeActivity.this, DashboardActivity.class));

            } else if (id == R.id.nav_contacts) {
                startActivity(new Intent(HomeActivity.this, EmergencyContactActivity.class));

            } else if (id == R.id.nav_trips) {
                startActivity(new Intent(HomeActivity.this, MyTripActivity.class));

            } else if (id == R.id.nav_vehicle) {
                startActivity(new Intent(HomeActivity.this, VehicleInfoActivity.class));

            } else if (id == R.id.nav_help) {
                startActivity(new Intent(HomeActivity.this, HelpSupportActivity.class));
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }


    private void setupMapClick() {
        mapImage.setOnClickListener(v ->
                Toast.makeText(this, "Map clicked", Toast.LENGTH_SHORT).show());
    }

    private void setupBottomNav() {
        bottomNav.setOnItemSelectedListener(this::onNavigationItemSelected);
    }

    private void updateDynamicData() {

        txtTraffic.setText("Moderate");
        txtWeather.setText("Clear");
        txtEmergency.setText("Police Nearby");

        txtSafetyScoreValue.setText("82%");
        txtPastAlertsValue.setText("5");

        alertCard.setOnClickListener(v ->
                Toast.makeText(this, "Hotspot Warning: Slow Down!", Toast.LENGTH_SHORT).show());
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
            return true;

        } else if (id == R.id.nav_reports) {
            Toast.makeText(this, "Report Accident", Toast.LENGTH_SHORT).show();
            return true;

        } else if (id == R.id.nav_emergency) {
            startActivity(new Intent(HomeActivity.this, EmergenceActivity.class));
            Toast.makeText(this, "Emergency", Toast.LENGTH_SHORT).show();
            return true;

        } else if (id == R.id.nav_settings) {
            Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
}
