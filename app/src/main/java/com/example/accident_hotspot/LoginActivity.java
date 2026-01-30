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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
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

        // Auto login
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
                startActivity(new Intent(this, ForgotPasswordActivity.class)));
    }

    // ✅ LOGIN LOGIC (WORKS WITH UPDATED PASSWORD)
    private void loginUser() {

        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Enter email");
            return;
        }

        if (TextUtils.isEmpty(pass)) {
            etPassword.setError("Enter password");
            return;
        }

        String savedEmail = prefs.getString("email", "");
        String savedPassword = prefs.getString("password", "");

        if (email.equals(savedEmail) && pass.equals(savedPassword)) {

            editor.putBoolean("islogin", true);
            editor.apply();

            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(this, HomeActivity.class));
            finish();

        } else {
            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        }
    }

    // GOOGLE LOGIN
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
