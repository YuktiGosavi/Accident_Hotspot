package com.example.accident_hotspot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etEmail, etPassword;
    Button btnLogin, btnGoogle;
    TextView tvSignUp, tvForgot;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Shared Preference Check
        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        editor = preferences.edit();

        if (preferences.getBoolean("islogin", false)) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
            return;
        }

        // Initialize UI
        etEmail = findViewById(R.id.textinput_edittext);
        etPassword = findViewById(R.id.textinput_password);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoogle = findViewById(R.id.acbLoginSignInWithGoogle);
        tvSignUp = findViewById(R.id.textSignUp);
        tvForgot = findViewById(R.id.textForgot);

        // Login Button
        btnLogin.setOnClickListener(view -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Email required!");
                return;
            }

            if (TextUtils.isEmpty(pass)) {
                etPassword.setError("Password required!");
                return;
            }

            Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();

            editor.putBoolean("islogin", true);
            editor.apply();

            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        });

        // Google Sign In Setup
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        // Google Button Click
        btnGoogle.setOnClickListener(v -> signIn());

        // Forgot Password
        tvForgot.setOnClickListener(v ->
                Toast.makeText(this, "Forgot Password Clicked!", Toast.LENGTH_SHORT).show()
        );

        // Sign Up
        tvSignUp.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class))
        );
    }

    // ---------------- GOOGLE SIGN IN ------------------
    private void signIn() {
        Intent intent = googleSignInClient.getSignInIntent();
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100) {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

            if (account != null) {
                Toast.makeText(this, "Google Login Successful!", Toast.LENGTH_SHORT).show();

                editor.putBoolean("islogin", true);
                editor.apply();

                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Google Sign-In Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
