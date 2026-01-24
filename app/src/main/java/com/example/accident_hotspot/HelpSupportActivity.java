package com.example.accident_hotspot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HelpSupportActivity extends AppCompatActivity {

    ExpandableListView faqListView;
    FAQAdapter faqAdapter;
    List<String> faqQuestions;
    HashMap<String, List<String>> faqAnswers;

    CardView cardContactSupport, cardSubmitRequest;   // FIXED TYPE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_support);

        // Toolbar Back Button
        findViewById(R.id.toolbar).setOnClickListener(v -> finish());

        faqListView = findViewById(R.id.faqListView);

        // FIXED: Now correctly cast as CardView
        cardContactSupport = findViewById(R.id.cardContactSupport);
        cardSubmitRequest = findViewById(R.id.cardSubmitRequest);

        loadFAQData();

        faqAdapter = new FAQAdapter(this, faqQuestions, faqAnswers);
        faqListView.setAdapter(faqAdapter);

        // Contact Support Click
        cardContactSupport.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:support@accidentapp.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Need Support - Accident Hotspot Finder");
            startActivity(Intent.createChooser(emailIntent, "Send Email"));
        });

        // Submit Request Click
        cardSubmitRequest.setOnClickListener(v -> {
            Toast.makeText(this, "Redirecting to Request Form...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://yourwebsite.com/support-request")));
        });
    }

    private void loadFAQData() {
        faqQuestions = new ArrayList<>();
        faqAnswers = new HashMap<>();

        faqQuestions.add("How does the accident hotspot detection work?");
        faqQuestions.add("How do I enable real-time alerts?");
        faqQuestions.add("Why is my location not updating?");
        faqQuestions.add("How to report a new accident spot?");

        List<String> ans1 = new ArrayList<>();
        ans1.add("The app uses past accident data and real-time reports to calculate risk zones.");

        List<String> ans2 = new ArrayList<>();
        ans2.add("Enable GPS and allow location permissions for accurate alerts.");

        List<String> ans3 = new ArrayList<>();
        ans3.add("Ensure your GPS is ON and you have granted Location Permissions in app settings.");

        List<String> ans4 = new ArrayList<>();
        ans4.add("Go to Reports > Add New Report and fill location and description.");

        faqAnswers.put(faqQuestions.get(0), ans1);
        faqAnswers.put(faqQuestions.get(1), ans2);
        faqAnswers.put(faqQuestions.get(2), ans3);
        faqAnswers.put(faqQuestions.get(3), ans4);
    }
}
