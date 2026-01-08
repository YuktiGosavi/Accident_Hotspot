package com.example.accident_hotspot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView profileImage;
    TextView txtName, txtEmail, txtPhone, txtMemberSince, tveditprofile;
    LinearLayout layoutAddAccount, layoutEmergencyContacts, layoutReports, layoutSettings;
    Button btnLogout;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        editor = prefs.edit();

        toolbar = findViewById(R.id.toolbar);
        profileImage = findViewById(R.id.profileImage);
        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        txtMemberSince = findViewById(R.id.txtMemberSince);
        tveditprofile = findViewById(R.id.tveditprofile);

        layoutAddAccount = findViewById(R.id.layoutAddAccount);
        layoutEmergencyContacts = findViewById(R.id.layoutEmergencyContacts);
        layoutReports = findViewById(R.id.layoutReports);
        layoutSettings = findViewById(R.id.layoutSettings);
        btnLogout = findViewById(R.id.btnLogout);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        tveditprofile.setOnClickListener(v ->
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class)));

        btnLogout.setOnClickListener(v -> {
            editor.putBoolean("islogin", false);
            editor.apply();

            Toast.makeText(ProfileActivity.this, "Logged Out!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        loadUserProfile();
    }

    private void loadUserProfile() {
        txtName.setText(prefs.getString("name", "Your Name"));
        txtEmail.setText("Email: " + prefs.getString("email", "your@email.com"));
        txtPhone.setText("Phone: " + prefs.getString("phone", "0000000000"));
        txtMemberSince.setText("Member Since: Oct 2024");

        String imageUri = prefs.getString("profileImage", "");
        if (!imageUri.isEmpty()) {
            try {
                profileImage.setImageURI(Uri.parse(imageUri));
            } catch (Exception ignored) {}
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserProfile();
    }
}
