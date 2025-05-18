package com.example.f1blog;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DriverDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);

        TextView driverNameTextView = findViewById(R.id.textViewDriverName);
        TextView teamTextView = findViewById(R.id.textViewTeam);
        TextView winsTextView = findViewById(R.id.textViewWins);
        TextView polesTextView = findViewById(R.id.textViewPoles);
        TextView pointsTextView = findViewById(R.id.textViewPoints);
        Button backButton = findViewById(R.id.buttonBack);

        String driverName = getIntent().getStringExtra("driverName");
        String team = getIntent().getStringExtra("team");
        int wins = getIntent().getIntExtra("wins", 0);
        int poles = getIntent().getIntExtra("poles", 0);
        int points = getIntent().getIntExtra("points", 0);

        driverNameTextView.setText(driverName);
        teamTextView.setText("Csapat: " + team);
        winsTextView.setText("Győzelmek: " + wins);
        polesTextView.setText("Pole pozíciók: " + poles);
        pointsTextView.setText("Pontok: " + points);

        backButton.setOnClickListener(v -> finish());
    }
}