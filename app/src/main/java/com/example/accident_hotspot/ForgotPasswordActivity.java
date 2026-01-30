package com.example.accident_hotspot;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

public class ForgotPasswordActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText etNewPassword, etConfirmPassword;
    AppCompatButton btnSavePassword;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        toolbar = findViewById(R.id.toolbar);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSavePassword = findViewById(R.id.btnSavePassword);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        // SharedPreferences
        sharedPreferences = getSharedPreferences("UserData", MODE_PRIVATE);

        btnSavePassword.setOnClickListener(v -> updatePassword());
    }

    private void updatePassword() {

        String newPassword = etNewPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Validation
        if (TextUtils.isEmpty(newPassword)) {
            etNewPassword.setError("Enter new password");
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            etConfirmPassword.setError("Confirm your password");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            return;
        }

        // Save updated password
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USER_PASSWORD", newPassword);
        editor.apply();

        Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show();

        finish(); // Go back to Login screen
    }
}
