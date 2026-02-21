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
import android.widget.TextView;
import android.widget.ImageView;

import com.example.accident_hotspot.fragment.CallFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;

import com.example.accident_hotspot.fragment.HomeFragment;
import com.example.accident_hotspot.fragment.ReportFragment;
import com.example.accident_hotspot.fragment.SettingsFragment;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNav;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        preferences = PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        editor = preferences.edit();

        // Welcome popup first time only
        if (preferences.getBoolean("isFirstTime", true)) welcomePopup();

        setupToolbar();
        initializeViews();
        setupDrawerHeader();
        setupDrawerMenuClick();
        setupBottomNav();

        // ⭐ Load HomeFragment initially
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, new HomeFragment())
                .commit();
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
        bottomNav = findViewById(R.id.bottomNav);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
    }

    // Drawer Header Data
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

    // Drawer Menu Click Events
    private void setupDrawerMenuClick() {

        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_dashboard) {
                startActivity(new Intent(HomeActivity.this, DashboardActivity.class));

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

    // Bottom Navigation → Fragment Switching
    private void setupBottomNav() {
        bottomNav.setOnItemSelectedListener(this::onNavigationItemSelected);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new HomeFragment())
                    .commit();

            return true;

        } else if (id == R.id.nav_reports) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new ReportFragment())
                    .commit();

            return true;

        } else if (id == R.id.nav_emergency) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new CallFragment())
                    .addToBackStack(null)
                    .commit();
            return true;

        } else if (id == R.id.nav_settings) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, new SettingsFragment())
                    .addToBackStack(null)
                    .commit();

            return true;
        }

        return false;
    }
}