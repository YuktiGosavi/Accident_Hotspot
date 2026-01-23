package com.example.accident_hotspot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.*;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText etEmail, etPassword;
    Button btnLogin, btnGoogle;
    TextView tvSignUp, tvForgot;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs = getSharedPreferences("USER_DATA", MODE_PRIVATE);
        editor = prefs.edit();

        if (prefs.getBoolean("islogin", false)) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }

        etEmail = findViewById(R.id.textinput_edittext);
        etPassword = findViewById(R.id.textinput_password);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoogle = findViewById(R.id.acbLoginSignInWithGoogle);
        tvSignUp = findViewById(R.id.textSignUp);
        tvForgot = findViewById(R.id.textForgot);

        btnLogin.setOnClickListener(v -> loginUser());

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        btnGoogle.setOnClickListener(v ->
                googleLauncher.launch(googleSignInClient.getSignInIntent()));

        tvSignUp.setOnClickListener(v ->
                startActivity(new Intent(this, SignUpActivity.class)));

        tvForgot.setOnClickListener(v ->
                Toast.makeText(this, "Forgot Password clicked", Toast.LENGTH_SHORT).show());
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "All fields required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (email.equals(prefs.getString("email", "")) &&
                pass.equals(prefs.getString("password", ""))) {

            editor.putBoolean("islogin", true).apply();
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        }
    }

    ActivityResultLauncher<Intent> googleLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == RESULT_OK) {
                            GoogleSignInAccount account =
                                    GoogleSignIn.getLastSignedInAccount(this);

                            if (account != null) {
                                editor.putString("name", account.getDisplayName());
                                editor.putString("email", account.getEmail());
                                editor.putBoolean("islogin", true);
                                editor.apply();

                                startActivity(new Intent(this, HomeActivity.class));
                                finish();
                            }
                        }
                    });
}
