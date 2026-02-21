 package com.example.accident_hotspot;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    ImageView logo;
    TextView title, tagline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.imgLogo); // give id to ImageView
        title = findViewById(R.id.txtTitle); // give id to title TextView
        tagline = findViewById(R.id.txtTagline);

        // Load animations
        Animation zoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Start animations
        logo.startAnimation(zoomIn);
        title.startAnimation(fadeIn);
        tagline.startAnimation(fadeIn);

        // Splash delay
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }, 2000); // 2 seconds
    }
}