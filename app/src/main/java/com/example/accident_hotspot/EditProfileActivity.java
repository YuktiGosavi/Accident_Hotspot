package com.example.accident_hotspot;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity {

    EditText edtName, edtEmail, edtPhone, edtDob, edtPassword, edtAddress;
    ImageView profileImage, editCamera;
    TextView txtName;
    View btnSave;

    SharedPreferences prefs;
    ActivityResultLauncher<Intent> imagePickerLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE);

        // UI Initialization
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtDob = findViewById(R.id.edtDob);
        edtPassword = findViewById(R.id.edtPassword);
        edtAddress = findViewById(R.id.edtAddress);
        txtName = findViewById(R.id.txtName);
        profileImage = findViewById(R.id.profileImage);
        editCamera = findViewById(R.id.editCamera);
        btnSave = findViewById(R.id.btnSave);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        setupImagePicker();
        loadUserData();

        editCamera.setOnClickListener(v -> pickImage());
        btnSave.setOnClickListener(v -> saveProfile());
    }

    private void loadUserData() {
        edtName.setText(prefs.getString("name", ""));
        edtEmail.setText(prefs.getString("email", ""));
        edtPhone.setText(prefs.getString("phone", ""));
        edtDob.setText(prefs.getString("dob", ""));
        edtPassword.setText(prefs.getString("password", ""));
        edtAddress.setText(prefs.getString("address", ""));
        txtName.setText(prefs.getString("name", ""));

        String imageUriString = prefs.getString("profileImage", "");
        if (!imageUriString.isEmpty()) {
            Uri imageUri = Uri.parse(imageUriString);
            try {
                // Use a try-catch specifically for the URI to prevent crash if permission is lost
                profileImage.setImageURI(imageUri);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveProfile() {
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();

        if (name.isEmpty()) {
            edtName.setError("Enter name");
            return;
        }
        if (email.isEmpty()) {
            edtEmail.setError("Enter email");
            return;
        }

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", name);
        editor.putString("email", email);
        editor.putString("phone", edtPhone.getText().toString());
        editor.putString("dob", edtDob.getText().toString());
        editor.putString("password", edtPassword.getText().toString());
        editor.putString("address", edtAddress.getText().toString());
        editor.apply();

        txtName.setText(name);
        Toast.makeText(this, "Profile Updated", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void setupImagePicker() {
        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri selectedImage = result.getData().getData();
                        if (selectedImage != null) {
                            try {
                                // 1. Grant Persistable Permission (CRITICAL to prevent crash on reload)
                                final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                                getContentResolver().takePersistableUriPermission(selectedImage, takeFlags);

                                // 2. Load the Bitmap safely
                                Bitmap bitmap;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), selectedImage);
                                    bitmap = ImageDecoder.decodeBitmap(source);
                                } else {
                                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                                }

                                profileImage.setImageBitmap(bitmap);

                                // 3. Save the URI string to SharedPreferences
                                prefs.edit().putString("profileImage", selectedImage.toString()).apply();

                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );
    }

    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
        imagePickerLauncher.launch(intent);
    }
}