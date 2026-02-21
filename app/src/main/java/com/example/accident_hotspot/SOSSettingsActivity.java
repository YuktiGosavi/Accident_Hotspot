package com.example.accident_hotspot;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SOSSettingsActivity extends AppCompatActivity {

    private EditText etSosNumber, etSosMessage;
    private Switch switchAutoSend;
    private AppCompatButton btnSaveSos, btnTestSos;

    SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "SOS_PREF";
    private static final int SMS_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sossettings);

        // Toolbar Setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Initialize Views
        etSosNumber = findViewById(R.id.etSosNumber);
        etSosMessage = findViewById(R.id.etSosMessage);
        switchAutoSend = findViewById(R.id.switchAutoSend);
        btnSaveSos = findViewById(R.id.btnSaveSos);
        btnTestSos = findViewById(R.id.btnTestSos);

        // SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        // Load saved previous values
        loadSavedSettings();

        // Save Button Click
        btnSaveSos.setOnClickListener(v -> saveSettings());

        // Test SOS Button Click
        btnTestSos.setOnClickListener(v -> sendTestSOS());
    }

    // Load saved values
    private void loadSavedSettings() {
        etSosNumber.setText(sharedPreferences.getString("sos_number", ""));
        etSosMessage.setText(sharedPreferences.getString("sos_message", ""));
        switchAutoSend.setChecked(sharedPreferences.getBoolean("auto_send", false));
    }

    // Save values
    private void saveSettings() {
        String number = etSosNumber.getText().toString().trim();
        String message = etSosMessage.getText().toString().trim();

        if (number.isEmpty()) {
            etSosNumber.setError("Enter SOS Number");
            return;
        }
        if (message.isEmpty()) {
            etSosMessage.setError("Enter SOS Message");
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("sos_number", number);
        editor.putString("sos_message", message);
        editor.putBoolean("auto_send", switchAutoSend.isChecked());
        editor.apply();

        Toast.makeText(this, "Settings Saved", Toast.LENGTH_SHORT).show();
    }

    // Send Test SOS
    private void sendTestSOS() {
        if (!checkSmsPermission()) {
            requestSmsPermission();
            return;
        }

        String number = etSosNumber.getText().toString().trim();
        String message = etSosMessage.getText().toString().trim();

        if (number.isEmpty()) {
            Toast.makeText(this, "Enter SOS Number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (message.isEmpty()) {
            Toast.makeText(this, "Enter SOS Message", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            SmsManager sms = SmsManager.getDefault();
            sms.sendTextMessage(number, null, "TEST: " + message, null, null);

            Toast.makeText(this, "Test SOS Sent!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Failed to send SOS", Toast.LENGTH_LONG).show();
        }
    }

    // Permission Check
    private boolean checkSmsPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED;
    }

    // Ask for Permission
    private void requestSmsPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
    }

    // Permission Result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendTestSOS();
            } else {
                Toast.makeText(this, "SMS Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}