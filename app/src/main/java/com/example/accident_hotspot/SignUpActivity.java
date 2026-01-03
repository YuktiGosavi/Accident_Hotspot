package com.example.accident_hotspot;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {

    TextInputEditText edtName, edtEmail, edtPassword, edtConfirmPassword;
    Button btnSignUp;
    TextView tvLogin;

    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        sharedPreferences = getSharedPreferences("USER_DATA", MODE_PRIVATE);

        edtName = findViewById(R.id.textinput_fullname);  // we will assign ids below
        edtEmail = findViewById(R.id.textinput_email);
        edtPassword = findViewById(R.id.textinput_passwords);
        edtConfirmPassword = findViewById(R.id.textinput_confirm_password);
        btnSignUp = findViewById(R.id.btnSignup);
        tvLogin = findViewById(R.id.tvLogin);

        btnSignUp.setOnClickListener(v -> registerUser());

        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void registerUser() {
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();
        String confirmPass = edtConfirmPassword.getText().toString().trim();

        if (name.isEmpty()) {
            edtName.setError("Enter full name");
            return;
        }

        if (email.isEmpty()) {
            edtEmail.setError("Enter email");
            return;
        }

        if (pass.isEmpty()) {
            edtPassword.setError("Enter password");
            return;
        }

        if (!pass.equals(confirmPass)) {
            edtConfirmPassword.setError("Passwords do not match");
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("FULLNAME", name);
        editor.putString("EMAIL", email);
        editor.putString("PASSWORD", pass);
        editor.apply();

        Toast.makeText(this, "Registration Successful!", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
        finish();
    }
}
