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

public class ForgotPasswordActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText etNewPassword, etConfirmPassword;
    TextView tvTimer, tvLogin;
    View btnGetOtp;

    private CountDownTimer countDownTimer;
    private final long totalTime = 60000; // 60 seconds
    private final long interval = 1000;   // 1 second

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();
        setupToolbar();
        clickListeners();
        startTimer(); // Start countdown when screen opens
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
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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

            // ✅ SUCCESS
            Toast.makeText(ForgotPasswordActivity.this,
                    "OTP Sent Successfully", Toast.LENGTH_SHORT).show();

            // ✅ OPEN OTP VERIFICATION SCREEN
            Intent intent = new Intent(
                    ForgotPasswordActivity.this,
                    OtpVerificationActivity.class
            );
            intent.putExtra("new_password", newPass); // optional
            startActivity(intent);

            // Restart timer if OTP is requested again
            startTimer();
        });

        tvLogin.setOnClickListener(v -> finish());
    }

    // ✅ Countdown Timer Logic
    private void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(totalTime, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                long seconds = millisUntilFinished / 1000;
                tvTimer.setText("Resend OTP in " + seconds + "s");
            }

            @Override
            public void onFinish() {
                tvTimer.setText("Resend OTP");
                tvTimer.setOnClickListener(v -> {
                    Toast.makeText(ForgotPasswordActivity.this,
                            "OTP Resent", Toast.LENGTH_SHORT).show();
                    startTimer(); // restart timer on resend
                });
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
