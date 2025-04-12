package com.example.f1blog;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class DriverDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView nameTextView = findViewById(R.id.textViewDriverName);
        TextView teamTextView = findViewById(R.id.textViewTeam);
        TextView winsTextView = findViewById(R.id.textViewWins);
        TextView polesTextView = findViewById(R.id.textViewPoles);
        TextView racesTextView = findViewById(R.id.textViewRaces);

        // Toolbar beállítása
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Versenyző adatai");
        }

        // Versenyző adatainak lekérése az Intent-ből
        Driver driver = (Driver) getIntent().getSerializableExtra("driver");

        if (driver != null) {
            nameTextView.setText(driver.getName());
            teamTextView.setText("Csapat: " + driver.getTeam());
            winsTextView.setText("Futamgyőzelmek: " + driver.getWins());
            polesTextView.setText("Pole pozíciók: " + driver.getPoles());
            racesTextView.setText("Versenyek száma: " + driver.getRaces());
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(driver.getName());
            }
        }

        // Toolbar vissza nyíl kezelése
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}