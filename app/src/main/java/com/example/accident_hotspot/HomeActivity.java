package com.example.accident_hotspot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

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

import com.example.accident_hotspot.fragment.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView mapImage;
    LinearLayout alertCard;
    BottomNavigationView bottomNav;

    SharedPreferences preferences, userPrefs;
    SharedPreferences.Editor editor;

    TextView txtTraffic, txtWeather, txtEmergency;
    TextView txtSafetyScoreValue, txtPastAlertsValue;

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        userPrefs = getSharedPreferences("USER_DATA", MODE_PRIVATE);

        initializeViews();

        setupToolbar();

        setupDrawerHeader();

        setupDrawerMenuClick();

        setupBottomNav();

        setupMapClick();

        updateDynamicData();

        showWelcomePopup();
    }


    private void initializeViews() {

        toolbar = findViewById(R.id.toolbar);

        mapImage = findViewById(R.id.mapView);

        alertCard = findViewById(R.id.alertCard);

        bottomNav = findViewById(R.id.bottomNav);

        drawerLayout = findViewById(R.id.drawerLayout);

        navigationView = findViewById(R.id.navigationView);


        txtTraffic = findViewById(R.id.txtTraffic);

        txtWeather = findViewById(R.id.txtWeather);

        txtEmergency = findViewById(R.id.txtEmergency);

        txtSafetyScoreValue = findViewById(R.id.txtSafetyScoreValue);

        txtPastAlertsValue = findViewById(R.id.txtPastAlertsValue);
    }



    private void setupToolbar() {

        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START));
    }



    private void setupDrawerHeader() {

        View header = navigationView.getHeaderView(0);

        if (header == null)
            return;


        TextView txtName = header.findViewById(R.id.txtName);

        TextView txtEmail = header.findViewById(R.id.txtEmail);

        ImageView profileImage = header.findViewById(R.id.profileImage);


        if (txtName != null)
            txtName.setText(userPrefs.getString("name", "Your Name"));


        if (txtEmail != null)
            txtEmail.setText(userPrefs.getString("email", "your@email.com"));


        if (profileImage != null) {

            String img = userPrefs.getString("profileImage", "");

            if (!img.isEmpty())
                profileImage.setImageURI(android.net.Uri.parse(img));


            profileImage.setOnClickListener(v ->

                    startActivity(new Intent(this, ProfileActivity.class)));
        }
    }



    private void setupDrawerMenuClick() {

        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.nav_dashboard)
                startActivity(new Intent(this, DashboardActivity.class));

            else if (id == R.id.nav_trips)
                startActivity(new Intent(this, MyTripActivity.class));

            else if (id == R.id.nav_vehicle)
                startActivity(new Intent(this, VehicleInfoActivity.class));

            else if (id == R.id.nav_help)
                startActivity(new Intent(this, HelpSupportActivity.class));


            drawerLayout.closeDrawer(GravityCompat.START);

            return true;
        });
    }



    private void setupBottomNav() {

        bottomNav.setOnItemSelectedListener(this::onNavigationItemSelected);
    }



    private void setupMapClick() {

        if (mapImage != null)

            mapImage.setOnClickListener(v ->

                    Toast.makeText(this, "Map Clicked", Toast.LENGTH_SHORT).show());
    }



    private void updateDynamicData() {

        if (txtTraffic != null)
            txtTraffic.setText("Moderate");

        if (txtWeather != null)
            txtWeather.setText("Clear");

        if (txtEmergency != null)
            txtEmergency.setText("Police Nearby");

        if (txtSafetyScoreValue != null)
            txtSafetyScoreValue.setText("82%");

        if (txtPastAlertsValue != null)
            txtPastAlertsValue.setText("5");


        if (alertCard != null)
            alertCard.setOnClickListener(v ->

                    Toast.makeText(this,
                            "Danger Hotspot Ahead",
                            Toast.LENGTH_SHORT).show());
    }



    private void showWelcomePopup() {

        boolean first = preferences.getBoolean("first", true);

        if (!first)
            return;


        new AlertDialog.Builder(this)

                .setTitle("Accident Hotspot Finder")

                .setMessage("Welcome to Accident Hotspot Finder")

                .setPositiveButton("OK", null)

                .show();


        editor.putBoolean("first", false).apply();
    }



    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home)
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();

        else if (id == R.id.nav_reports)
            Toast.makeText(this, "Report", Toast.LENGTH_SHORT).show();

        else if (id == R.id.nav_settings)
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer,
                            new SettingsFragment())
                    .commit();

        return true;
    }

}
