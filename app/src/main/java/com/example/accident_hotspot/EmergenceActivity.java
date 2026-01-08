package com.example.accident_hotspot;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

public class EmergenceActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtCallTimer;
    private ImageView icMicOff, icCallEnd, icAddContact;

    private Handler handler = new Handler();
    private int seconds = 0;
    private boolean isMuted = false;

    private final Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            seconds++;
            int min = seconds / 60;
            int sec = seconds % 60;
            txtCallTimer.setText(String.format("%02d:%02d", min, sec));
            handler.postDelayed(this, 1000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_call); // reuse same UI

        // Bind views (Activity way)
        toolbar = findViewById(R.id.toolbar);
        txtCallTimer = findViewById(R.id.txtCallTimer);
        icMicOff = findViewById(R.id.ic_mic_off_btn);
        icCallEnd = findViewById(R.id.ic_call_end_btn);
        icAddContact = findViewById(R.id.ic_add_contacts_btn);

        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        startTimer();
        makeEmergencyCall();
        setupClicks();
    }

    private void startTimer() {
        handler.post(timerRunnable);
    }

    private void makeEmergencyCall() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:112"));

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    101
            );
            return;
        }
        startActivity(intent);
    }

    private void setupClicks() {

        icMicOff.setOnClickListener(v -> {
            isMuted = !isMuted;
            Toast.makeText(
                    this,
                    isMuted ? "Call Muted" : "Call Unmuted",
                    Toast.LENGTH_SHORT
            ).show();
        });

        icCallEnd.setOnClickListener(v -> {
            handler.removeCallbacks(timerRunnable);
            Toast.makeText(this, "Call Ended", Toast.LENGTH_SHORT).show();
            finish(); // close activity
        });

        icAddContact.setOnClickListener(v ->
                Toast.makeText(this,
                        "Add Contact Clicked",
                        Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(timerRunnable);
    }
}
