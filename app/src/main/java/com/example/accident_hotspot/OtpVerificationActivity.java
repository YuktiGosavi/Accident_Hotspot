package com.example.accident_hotspot;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

public class OtpVerificationActivity extends AppCompatActivity {

    Toolbar toolbar;

    EditText otp1, otp2, otp3, otp4, otp5, otp6;
    TextView tvResend, tvLogin;
    AppCompatButton btnVerify;

    String generatedOtp = "123456"; // demo OTP
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        // ================= INIT =================
        toolbar = findViewById(R.id.toolbar);

        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);

        tvResend = findViewById(R.id.tvResend);
        tvLogin = findViewById(R.id.tvLogin);
        btnVerify = findViewById(R.id.btnVerify);

        // ================= TOOLBAR =================
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // ================= OTP AUTO MOVE =================
        setupOtpInputs();

        // ================= AUTO FILL OTP FROM INTENT =================
        autoFillOtpFromIntent();

        // ================= TIMER =================
        startResendTimer();

        // ================= VERIFY OTP =================
        btnVerify.setOnClickListener(v -> verifyOtp());

        // ================= RESEND OTP =================
        tvResend.setOnClickListener(v -> {
            resendOtp();
            startResendTimer();
        });

        // ================= BACK TO LOGIN =================
        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    // =================================================
    // OTP AUTO MOVE
    // =================================================
    private void setupOtpInputs() {
        otp1.addTextChangedListener(new OtpTextWatcher(otp1, otp2));
        otp2.addTextChangedListener(new OtpTextWatcher(otp2, otp3));
        otp3.addTextChangedListener(new OtpTextWatcher(otp3, otp4));
        otp4.addTextChangedListener(new OtpTextWatcher(otp4, otp5));
        otp5.addTextChangedListener(new OtpTextWatcher(otp5, otp6));
        otp6.addTextChangedListener(new OtpTextWatcher(otp6, null));
    }

    private class OtpTextWatcher implements TextWatcher {

        EditText current, next;

        OtpTextWatcher(EditText current, EditText next) {
            this.current = current;
            this.next = next;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 1 && next != null) {
                next.requestFocus();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }

    // =================================================
    // AUTO FILL OTP FROM INTENT
    // =================================================
    private void autoFillOtpFromIntent() {

        String otp = getIntent().getStringExtra("otp");

        if (otp != null && otp.length() == 6) {
            otp1.setText(String.valueOf(otp.charAt(0)));
            otp2.setText(String.valueOf(otp.charAt(1)));
            otp3.setText(String.valueOf(otp.charAt(2)));
            otp4.setText(String.valueOf(otp.charAt(3)));
            otp5.setText(String.valueOf(otp.charAt(4)));
            otp6.setText(String.valueOf(otp.charAt(5)));
        }
    }

    // =================================================
    // VERIFY OTP → HOME ACTIVITY
    // =================================================
    private void verifyOtp() {

        String enteredOtp =
                otp1.getText().toString() +
                        otp2.getText().toString() +
                        otp3.getText().toString() +
                        otp4.getText().toString() +
                        otp5.getText().toString() +
                        otp6.getText().toString();

        if (enteredOtp.length() < 6) {
            Toast.makeText(this, "Please enter complete OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        if (enteredOtp.equals(generatedOtp)) {

            Toast.makeText(this, "OTP Verified Successfully", Toast.LENGTH_SHORT).show();

            // ✅ DIRECTLY GO TO HOME ACTIVITY
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_SHORT).show();
        }
    }

    // =================================================
    // RESEND OTP
    // =================================================
    private void resendOtp() {
        generatedOtp = "654321"; // demo new OTP
        Toast.makeText(this, "OTP Resent Successfully", Toast.LENGTH_SHORT).show();
    }

    // =================================================
    // TIMER
    // =================================================
    private void startResendTimer() {

        tvResend.setEnabled(false);

        if (countDownTimer != null)
            countDownTimer.cancel();

        countDownTimer = new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvResend.setText("Resend OTP in 00:" + (millisUntilFinished / 1000));
            }

            public void onFinish() {
                tvResend.setText("Resend OTP");
                tvResend.setEnabled(true);
            }
        }.start();
    }
}
