package com.example.accident_hotspot;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SpeedLimitSettingsActivity extends AppCompatActivity {

    private TextView tvSpeedValue;
    private SeekBar seekBarSpeed;
    private Spinner spinnerUnit;
    private Switch switchAlert;
    private AppCompatButton btnSaveSpeed;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_limit_settings);

        // Initialize views
        tvSpeedValue = findViewById(R.id.tvSpeedValue);
        seekBarSpeed = findViewById(R.id.seekBarSpeed);
        spinnerUnit = findViewById(R.id.spinnerUnit);
        switchAlert = findViewById(R.id.switchAlert);
        btnSaveSpeed = findViewById(R.id.btnSaveSpeed);

        // SharedPreferences
        preferences = getSharedPreferences("SpeedPrefs", MODE_PRIVATE);

        // -------------------------
        // LOAD SAVED SETTINGS
        // -------------------------
        loadSavedSettings();

        // -------------------------
        // SPEED SLIDER LISTENER
        // -------------------------
        seekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int value, boolean fromUser) {
                String unit = spinnerUnit.getSelectedItem().toString();
                tvSpeedValue.setText(value + " " + unit);
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // -------------------------
        // SPEED UNIT DROPDOWN
        // -------------------------
        String[] units = {"km/h", "mph"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                units
        );

        spinnerUnit.setAdapter(adapter);

        // -------------------------
        // SAVE BUTTON CLICK
        // -------------------------
        btnSaveSpeed.setOnClickListener(v -> saveSettings());
    }

    // ------------------------------------------
    // SAVE DATA IN SHARED PREFERENCES
    // ------------------------------------------
    private void saveSettings() {
        int speed = seekBarSpeed.getProgress();
        String unit = spinnerUnit.getSelectedItem().toString();
        boolean isAlertEnabled = switchAlert.isChecked();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("speed_limit", speed);
        editor.putString("speed_unit", unit);
        editor.putBoolean("alert_enabled", isAlertEnabled);
        editor.apply();

        tvSpeedValue.setText(speed + " " + unit);
    }

    // ------------------------------------------
    // LOAD PREVIOUS SAVED SETTINGS
    // ------------------------------------------
    private void loadSavedSettings() {
        int savedSpeed = preferences.getInt("speed_limit", 60);
        String savedUnit = preferences.getString("speed_unit", "km/h");
        boolean savedAlert = preferences.getBoolean("alert_enabled", true);

        // Set widgets
        seekBarSpeed.setProgress(savedSpeed);
        tvSpeedValue.setText(savedSpeed + " " + savedUnit);

        // Set spinner selection
        if (savedUnit.equals("km/h"))
            spinnerUnit.setSelection(0);
        else
            spinnerUnit.setSelection(1);

        switchAlert.setChecked(savedAlert);
    }
}