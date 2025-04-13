package com.example.f1blog;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TeamDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        TextView nameTextView = findViewById(R.id.textViewTeamName);
        TextView foundedTextView = findViewById(R.id.textViewFounded);
        TextView championshipsTextView = findViewById(R.id.textViewChampionships);
        TextView driversTextView = findViewById(R.id.textViewDrivers);
        Button backButton = findViewById(R.id.buttonBack);

        // Csapat adatainak lekérése az Intent-ből
        Team team = (Team) getIntent().getSerializableExtra("team");

        if (team != null) {
            nameTextView.setText(team.getName());
            foundedTextView.setText("Alapítva: " + team.getFoundedYear());
            championshipsTextView.setText("Világbajnoki címek: " + team.getChampionships());
            driversTextView.setText("Pilóták: " + team.getDrivers());
        }

        // Vissza gomb kezelése
        backButton.setOnClickListener(v -> finish());
    }
}