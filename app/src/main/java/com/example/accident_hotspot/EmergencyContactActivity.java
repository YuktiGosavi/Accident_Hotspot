package com.example.accident_hotspot;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EmergencyContactActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button btnAdd;
    List<EmergencyContact> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        recyclerView = findViewById(R.id.contactRecycler);
        btnAdd = findViewById(R.id.btnAddContact);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactList = new ArrayList<>();
        loadDummyContacts();

        recyclerView.setAdapter(
                new EmergencyContactAdapter(this, contactList)
        );

        btnAdd.setOnClickListener(v ->
                Toast.makeText(this,
                        "Add Contact Clicked",
                        Toast.LENGTH_SHORT).show()
        );
    }

    private void loadDummyContacts() {
        contactList.add(new EmergencyContact("Rahul Jain","Brother","1142569886"));
        contactList.add(new EmergencyContact("Anuska Sharma","Friend","00229269878"));
        contactList.add(new EmergencyContact("Mom","Family","00189562838"));
    }
}
