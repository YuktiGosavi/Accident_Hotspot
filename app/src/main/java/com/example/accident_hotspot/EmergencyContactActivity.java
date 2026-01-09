package com.example.accident_hotspot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EmergencyContactActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<EmergencyContact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.contactRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactList = new ArrayList<>();
        loadContacts();

        recyclerView.setAdapter(new EmergencyContactAdapter(this, contactList));

        findViewById(R.id.btnAddContact)
                .setOnClickListener(v ->
                        startActivity(new Intent(this, AddEmergencyContactActivity.class)));
    }

    private void loadContacts() {
        SharedPreferences prefs = getSharedPreferences("EMERGENCY_CONTACTS", MODE_PRIVATE);
        String data = prefs.getString("contacts", null);

        if (data == null) return;

        try {
            JSONArray array = new JSONArray(data);
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                contactList.add(new EmergencyContact(
                        obj.getString("name"),
                        obj.getString("relation"),
                        obj.getString("phone")
                ));
            }
        } catch (Exception ignored) {}
    }
}
