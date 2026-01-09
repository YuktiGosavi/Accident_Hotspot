package com.example.accident_hotspot;

import android.content.Context;
import android.content.SharedPreferences;

public class VehiclePrefManager {

    private static final String PREF_NAME = "vehicle_pref";
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    public VehiclePrefManager(Context context) {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

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

    public String getModel() { return pref.getString("model", "2022 Model"); }
    public String getLicense() { return pref.getString("license", "ABC-1234"); }
    public String getType() { return pref.getString("type", "Sedan"); }
    public String getFuel() { return pref.getString("fuel", "Electric"); }
    public String getRating() { return pref.getString("rating", "5-Star"); }
    public String getService() { return pref.getString("service", "12/2025"); }
}
