package com.example.accident_hotspot.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.accident_hotspot.HotspotAlertSettingsActivity;
import com.example.accident_hotspot.LiveLocationActivity;
import com.example.accident_hotspot.R;
import com.example.accident_hotspot.SOSSettingsActivity;
import com.example.accident_hotspot.SpeedLimitSettingsActivity;

public class SettingsFragment extends Fragment {

    Toolbar toolbar;

    LinearLayout layoutEmergencyContacts;
    LinearLayout layoutSOS;
    LinearLayout layoutHotspotAlerts;
    LinearLayout layoutLiveLocation;
    LinearLayout layoutPermission;
    LinearLayout layoutClearData;

    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Toolbar
        toolbar = view.findViewById(R.id.toolbarSettings);

        // Layout IDs
        layoutEmergencyContacts = view.findViewById(R.id.layoutEmergencyContacts);
        layoutSOS = view.findViewById(R.id.layoutSOS);
        layoutHotspotAlerts = view.findViewById(R.id.layoutHotspotAlerts);
        layoutLiveLocation = view.findViewById(R.id.layoutLiveLocation);
        layoutPermission = view.findViewById(R.id.layoutPermission);
        layoutClearData = view.findViewById(R.id.layoutClearData);

        sharedPreferences = requireActivity()
                .getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);

        setupToolbar();
        clickListeners();

        return view;
    }

    private void setupToolbar() {
        toolbar.setNavigationOnClickListener(v ->
                requireActivity().onBackPressed()
        );
    }

    private void clickListeners() {

        // 🚦 Speed Limit Alerts
        layoutEmergencyContacts.setOnClickListener(v -> {
            Toast.makeText(getContext(),
                    "Speed Limit Alerts Settings",
                    Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getContext(),
                    SpeedLimitSettingsActivity.class));
        });

        // 🆘 SOS Settings
        layoutSOS.setOnClickListener(v -> {
            Toast.makeText(getContext(),
                    "SOS Alert Settings",
                    Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getContext(),
                    SOSSettingsActivity.class));
        });

        // ⚠ Accident Hotspot Alerts
        layoutHotspotAlerts.setOnClickListener(v -> {
            Toast.makeText(getContext(),
                    "Accident Hotspot Alerts",
                    Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getContext(),
                    HotspotAlertSettingsActivity.class));
        });

        // 📍 Live Location Sharing
        layoutLiveLocation.setOnClickListener(v -> {
            Toast.makeText(getContext(),
                    "Live Location Sharing",
                    Toast.LENGTH_SHORT).show();

            startActivity(new Intent(getContext(),
                    LiveLocationActivity.class));
        });

        // 🔐 Location Permissions
        layoutPermission.setOnClickListener(v -> {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package",
                    requireActivity().getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        });

        // 🗑 Clear App Data
        layoutClearData.setOnClickListener(v -> {
            clearAppData();
        });
    }

    private void clearAppData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Toast.makeText(getContext(),
                "App data cleared successfully",
                Toast.LENGTH_SHORT).show();
    }
}
