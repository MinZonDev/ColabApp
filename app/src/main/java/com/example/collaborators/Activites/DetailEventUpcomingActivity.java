package com.example.collaborators.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.collaborators.R;

public class DetailEventUpcomingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event_upcoming);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        TextView titleTextView = findViewById(R.id.detailTitleTextView);
        ImageView imageView = findViewById(R.id.detailImageView);
        TextView descriptionTextView = findViewById(R.id.detailDescriptionTextView);
        TextView locationTextView = findViewById(R.id.detailLocationTextView);
        TextView timeTextView = findViewById(R.id.detailTimeTextView);

        // Retrieve event details from intent
        Intent intent = getIntent();
        if (intent != null) {
            titleTextView.setText(intent.getStringExtra("eventTitle"));
            descriptionTextView.setText(intent.getStringExtra("eventDescription"));
            locationTextView.setText("Location: " + intent.getStringExtra("eventLocation"));
            timeTextView.setText("Time: " + intent.getStringExtra("eventTime"));

            // Load image using Glide or your preferred image loading library
            String imageUrl = intent.getStringExtra("eventImage");
            Glide.with(this).load(imageUrl).into(imageView);
        }
    }
}