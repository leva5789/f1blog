package com.example.f1blog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    TextView welcomeText;
    Button logoutButton;
    RecyclerView driversRecyclerView;
    LinearLayout contentLayout;
    DriversAdapter driversAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        welcomeText = findViewById(R.id.textViewWelcomeAnimation);
        logoutButton = findViewById(R.id.buttonLogout);
        driversRecyclerView = findViewById(R.id.recyclerViewDrivers);
        contentLayout = findViewById(R.id.contentLayout);

        // Felhasználónév lekérése Firestore-ból
        String uid = currentUser.getUid();
        db.collection("users").document(uid).get()
                .addOnSuccessListener(documentSnapshot -> {
                    String username = "Névtelen";
                    if (documentSnapshot.exists()) {
                        String storedUsername = documentSnapshot.getString("username");
                        if (storedUsername != null && !storedUsername.isEmpty()) {
                            username = storedUsername;
                        }
                    }
                    welcomeText.setText("Üdv, " + username + "!");

                    // Animáció indítása
                    startWelcomeAnimation();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Hiba a felhasználónév lekérése közben: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    welcomeText.setText("Üdv, Névtelen!");

                    // Animáció indítása hiba esetén is
                    startWelcomeAnimation();
                });

        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        // RecyclerView beállítása
        driversRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        driversAdapter = new DriversAdapter(getDriversList());
        driversRecyclerView.setAdapter(driversAdapter);
    }

    private void startWelcomeAnimation() {
        // Fade-in animáció
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        welcomeText.setVisibility(View.VISIBLE);
        welcomeText.startAnimation(fadeIn);

        // Fade-out animáció a fade-in vége után
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                welcomeText.startAnimation(fadeOut);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        // Lista megjelenítése a fade-out vége után
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                welcomeText.setVisibility(View.GONE);
                contentLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
    }

    private List<Driver> getDriversList() {
        List<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver("Lando Norris", "McLaren", 4, 4, 135));
        drivers.add(new Driver("Oscar Piastri", "McLaren", 2, 1, 64));
        drivers.add(new Driver("Lewis Hamilton", "Ferrari", 104, 104, 350));
        drivers.add(new Driver("Charles Leclerc", "Ferrari", 7, 12, 149));
        drivers.add(new Driver("Max Verstappen", "Red Bull", 63, 40, 211));
        drivers.add(new Driver("Yuki Tsunoda", "Red Bull", 0, 0, 87));
        drivers.add(new Driver("George Russell", "Mercedes", 3, 3, 127));
        drivers.add(new Driver("Andrea Kimi Antonelli", "Mercedes", 0, 0, 0));
        drivers.add(new Driver("Fernando Alonso", "Aston Martin", 32, 22, 400));
        drivers.add(new Driver("Lance Stroll", "Aston Martin", 0, 0, 166));
        drivers.add(new Driver("Pierre Gasly", "Alpine", 1, 0, 151));
        drivers.add(new Driver("Jack Doohan", "Alpine", 0, 0, 0));
        drivers.add(new Driver("Alex Albon", "Williams", 0, 0, 104));
        drivers.add(new Driver("Carlos Sainz", "Williams", 3, 3, 211));
        drivers.add(new Driver("Esteban Ocon", "Haas", 1, 0, 149));
        drivers.add(new Driver("Oliver Bearman", "Haas", 0, 0, 2));
        drivers.add(new Driver("Liam Lawson", "Racing Bulls", 0, 0, 11));
        drivers.add(new Driver("Isack Hadjar", "Racing Bulls", 0, 0, 0));
        drivers.add(new Driver("Nico Hülkenberg", "Sauber", 0, 1, 224));
        drivers.add(new Driver("Gabriel Bortoleto", "Sauber", 0, 0, 0));
        return drivers;
    }
}