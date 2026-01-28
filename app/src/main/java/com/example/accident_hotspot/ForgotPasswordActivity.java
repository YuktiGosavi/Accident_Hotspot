package com.example.accident_hotspot;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.tasks.Task;

public class ForgotPasswordActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText etNewPassword, etConfirmPassword;
    TextView tvTimer, tvLogin;
    View btnGetOtp;

    private CountDownTimer countDownTimer;
    private final long totalTime = 60000;
    private final long interval = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();
        setupToolbar();
        clickListeners();
        startTimer();
        startSmsListener(); // 🔥 Start listening OTP automatically
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        tvTimer = findViewById(R.id.tvTimer);
        tvLogin = findViewById(R.id.tvLogin);
        btnGetOtp = findViewById(R.id.btnGetOtp);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void clickListeners() {

        btnGetOtp.setOnClickListener(v -> {

            String newPass = etNewPassword.getText().toString().trim();
            String confirmPass = etConfirmPassword.getText().toString().trim();

            if (TextUtils.isEmpty(newPass)) {
                etNewPassword.setError("Enter new password");
                return;
            }

            if (TextUtils.isEmpty(confirmPass)) {
                etConfirmPassword.setError("Confirm password");
                return;
            }

            if (!newPass.equals(confirmPass)) {
                etConfirmPassword.setError("Passwords do not match");
                return;
            }

            // 🔥 Backend / Firebase OTP send happens here
            Toast.makeText(this, "OTP sent to your phone", Toast.LENGTH_SHORT).show();

            startTimer();
        });

        tvLogin.setOnClickListener(v -> finish());
    }

    // 🔥 SMS Retriever (NO SMS permission needed)
    private void startSmsListener() {
        Task<Void> task = SmsRetriever.getClient(this).startSmsRetriever();
        task.addOnSuccessListener(aVoid -> {
            // Listener started
        });
        task.addOnFailureListener(e ->
                Toast.makeText(this, "SMS listener failed", Toast.LENGTH_SHORT).show());
    }

    private void startTimer() {
        if (countDownTimer != null) countDownTimer.cancel();

        countDownTimer = new CountDownTimer(totalTime, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("Resend OTP in " + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                tvTimer.setText("Resend OTP");
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}
