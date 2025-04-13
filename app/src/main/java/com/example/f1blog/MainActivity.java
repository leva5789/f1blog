package com.example.f1blog;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseFirestore db;
    TextView welcomeText;
    Button logoutButton;
    LinearLayout contentLayout;
    TabLayout tabLayout;
    ViewPager2 viewPager;

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
        contentLayout = findViewById(R.id.contentLayout);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        // Kezdetben a contentLayout láthatatlan
        contentLayout.setVisibility(View.GONE);

        // ViewPager inicializálása előre, de még nem látható
        viewPager.setAdapter(new ViewPagerAdapter(this));
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Pilóták");
                    break;
                case 1:
                    tab.setText("Csapatok");
                    break;
            }
        }).attach();

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
                    startWelcomeAnimation();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Hiba a felhasználónév lekérése közben: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    welcomeText.setText("Üdv, Névtelen!");
                    startWelcomeAnimation();
                });

        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void startWelcomeAnimation() {
        // Fade-in animáció
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        welcomeText.setVisibility(View.VISIBLE);
        welcomeText.startAnimation(fadeIn);

        // Késleltetett fade-out és tartalom megjelenítés
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
            welcomeText.startAnimation(fadeOut);
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
        }, 1500); // 1.5 másodperc várakozás
    }
}