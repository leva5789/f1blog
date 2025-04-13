package com.example.f1blog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TeamDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_details);

        TextView teamNameTextView = findViewById(R.id.textViewTeamName);
        TextView foundedTextView = findViewById(R.id.textViewFounded);
        TextView championshipsTextView = findViewById(R.id.textViewChampionships);
        TextView driversTextView = findViewById(R.id.textViewDrivers);
        Button backButton = findViewById(R.id.buttonBack);

        Intent intent = getIntent();
        Team team = (Team) intent.getSerializableExtra("team");

        if (team != null) {
            teamNameTextView.setText(team.getName());
            foundedTextView.setText("Alapítva: " + team.getFoundedYear());
            championshipsTextView.setText("Bajnokságok: " + team.getChampionships());
            driversTextView.setText("Pilóták: " + team.getDrivers());
        } else {
            teamNameTextView.setText("Ismeretlen csapat");
            foundedTextView.setText("Alapítva: N/A");
            championshipsTextView.setText("Bajnokságok: N/A");
            driversTextView.setText("Pilóták: N/A");
        }

        backButton.setOnClickListener(v -> finish());
    }
}