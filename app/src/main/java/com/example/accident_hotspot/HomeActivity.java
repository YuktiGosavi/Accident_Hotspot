package com.example.accident_hotspot;

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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.accident_hotspot.fragment.ReportFragment;
import com.example.accident_hotspot.fragment.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    BottomNavigationView bottomNav;

    ImageView mapImage;
    LinearLayout alertCard;

    TextView txtTraffic, txtWeather, txtEmergency;
    TextView txtSafetyScoreValue, txtPastAlertsValue;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        initializeViews();
        setupToolbar();
        setupDrawerHeader();
        setupBottomNav();
        setupMapClick();
        updateDynamicData();

        navigationView.setNavigationItemSelectedListener(this);

        if (preferences.getBoolean("isFirstTime", true)) {
            welcomePopup();
        }
    }

    private void initializeViews() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        bottomNav = findViewById(R.id.bottomNav);

        mapImage = findViewById(R.id.mapView);
        alertCard = findViewById(R.id.alertCard);

        txtTraffic = findViewById(R.id.txtTraffic);
        txtWeather = findViewById(R.id.txtWeather);
        txtEmergency = findViewById(R.id.txtEmergency);

        txtSafetyScoreValue = findViewById(R.id.txtSafetyScoreValue);
        txtPastAlertsValue = findViewById(R.id.txtPastAlertsValue);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START));
    }

    private void welcomePopup() {
        new AlertDialog.Builder(this)
                .setTitle("Accident Hotspot Finder")
                .setMessage("Welcome to Accident Hotspot Finder")
                .setPositiveButton("Thank You", (d, w) -> d.dismiss())
                .show();

        editor.putBoolean("isFirstTime", false).apply();
    }

    // ================= DRAWER HEADER =================
    private void setupDrawerHeader() {

        View headerView = navigationView.getHeaderCount() == 0
                ? navigationView.inflateHeaderView(R.layout.drawer_header)
                : navigationView.getHeaderView(0);

        TextView txtName = headerView.findViewById(R.id.txtName);
        TextView txtEmail = headerView.findViewById(R.id.txtEmail);
        ImageView profileImage = headerView.findViewById(R.id.profileImage);

        SharedPreferences prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE);

        txtName.setText(prefs.getString("name", "Your Name"));
        txtEmail.setText(prefs.getString("email", "your@gmail.com"));

        profileImage.setOnClickListener(v ->
                startActivity(new Intent(this, ProfileActivity.class)));
    }

    // ================= MAP =================
    private void setupMapClick() {
        mapImage.setOnClickListener(v ->
                Toast.makeText(this, "Map clicked", Toast.LENGTH_SHORT).show());
    }

    // ================= BOTTOM NAV =================
    private void setupBottomNav() {
        bottomNav.setOnItemSelectedListener(item -> {

            Fragment fragment = null;

            if (item.getItemId() == R.id.nav_home) {
                return true;
            }
            else if (item.getItemId() == R.id.nav_reports) {
                fragment = new ReportFragment();
            }
            else if (item.getItemId() == R.id.nav_emergency) {
                startActivity(new Intent(this, EmergenceActivity.class));
                return true;
            }
            else if (item.getItemId() == R.id.nav_settings) {
                fragment = new SettingsFragment();
            }

            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                return true;
            }
            return false;
        });
    }

    // ================= DRAWER MENU =================
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            startActivity(new Intent(this, DashboardActivity.class));

        } else if (id == R.id.nav_emergency) {
            startActivity(new Intent(this, EmergencyContactActivity.class));

        } else if (id == R.id.nav_trips) {
            startActivity(new Intent(this, MyTripActivity.class));

        } else if (id == R.id.nav_vehicle) {
            startActivity(new Intent(this, VehicleInfoActivity.class));

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // ================= DASHBOARD DATA =================
    private void updateDynamicData() {
        txtTraffic.setText("Moderate");
        txtWeather.setText("Clear");
        txtEmergency.setText("Police Nearby");

        txtSafetyScoreValue.setText("82%");
        txtPastAlertsValue.setText("5");

        alertCard.setOnClickListener(v ->
                Toast.makeText(this,
                        "Hotspot Warning: Slow Down!",
                        Toast.LENGTH_SHORT).show());
    }
}
