package com.example.accident_hotspot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

public class AddEmergencyContactActivity extends AppCompatActivity {

    EditText etName, etRelationship, etPhone, etEmail;
    Switch notifySwitch;
    AppCompatButton btnSave;

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emergency_contact);

        prefs = getSharedPreferences("EMERGENCY_CONTACTS", MODE_PRIVATE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        etName = findViewById(R.id.etname1);
        etRelationship = findViewById(R.id.etrelationship);
        etPhone = findViewById(R.id.etnumber);
        etEmail = findViewById(R.id.etemailid);
        notifySwitch = findViewById(R.id.Switch);
        btnSave = findViewById(R.id.savecontact);

        btnSave.setOnClickListener(v -> saveContact());
    }

    private void saveContact() {

        String name = etName.getText().toString().trim();
        String relation = etRelationship.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(relation) || TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            JSONArray array;

            String oldData = prefs.getString("contacts", null);
            if (oldData != null) {
                array = new JSONArray(oldData);
            } else {
                array = new JSONArray();
            }

            JSONObject obj = new JSONObject();
            obj.put("name", name);
            obj.put("relation", relation);
            obj.put("phone", phone);

            array.put(obj);

            prefs.edit().putString("contacts", array.toString()).apply();

            Toast.makeText(this, "Contact Saved", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, EmergencyContactActivity.class));
            finish();

        } catch (Exception e) {
            Toast.makeText(this, "Error saving contact", Toast.LENGTH_SHORT).show();
        }
    }
}
