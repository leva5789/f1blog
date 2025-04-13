package com.example.f1blog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DriverDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);

        // TextView-ek inicializálása
        TextView driverNameTextView = findViewById(R.id.textViewDriverName);
        TextView teamTextView = findViewById(R.id.textViewTeam);
        TextView winsTextView = findViewById(R.id.textViewWins);
        TextView polesTextView = findViewById(R.id.textViewPoles);
        TextView pointsTextView = findViewById(R.id.textViewPoints);
        Button backButton = findViewById(R.id.buttonBack);

        // Adatok lekérése (pl. Intent-ből)
        String driverName = getIntent().getStringExtra("driverName");
        String team = getIntent().getStringExtra("team");
        int wins = getIntent().getIntExtra("wins", 0);
        int poles = getIntent().getIntExtra("poles", 0);
        int points = getIntent().getIntExtra("points", 0);

        // Adatok beállítása
        driverNameTextView.setText(driverName);
        teamTextView.setText("Csapat: " + team);
        winsTextView.setText("Győzelmek: " + wins);
        polesTextView.setText("Pole pozíciók: " + poles);
        pointsTextView.setText("Pontok: " + points);

        // Vissza gomb
        backButton.setOnClickListener(v -> finish());
    }
}