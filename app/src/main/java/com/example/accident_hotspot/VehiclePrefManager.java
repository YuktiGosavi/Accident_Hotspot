package com.example.accident_hotspot;

import android.content.Context;
import android.content.SharedPreferences;

public class VehiclePrefManager {

    // Use ONE preference name everywhere
    private static final String PREF_NAME = "VEHICLE_DATA";

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public VehiclePrefManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    // SAVE VEHICLE DATA
    public void saveVehicle(String model, String license,
                            String type, String fuel,
                            String rating, String service) {

        editor.putString("model", model);
        editor.putString("license", license);
        editor.putString("type", type);
        editor.putString("fuel", fuel);
        editor.putString("rating", rating);
        editor.putString("service", service);
        editor.apply();
    }

    // GETTERS (safe defaults – no crash)
    public String getModel() {
        return pref.getString("model", "No Vehicle Added");
    }

    public String getLicense() {
        return pref.getString("license", "N/A");
    }

    public String getType() {
        return pref.getString("type", "N/A");
    }

    public String getFuel() {
        return pref.getString("fuel", "N/A");
    }

    public String getRating() {
        return pref.getString("rating", "N/A");
    }

    public String getService() {
        return pref.getString("service", "N/A");
    }

    // OPTIONAL: clear data (future use)
    public void clearVehicle() {
        editor.clear();
        editor.apply();
    }
}
