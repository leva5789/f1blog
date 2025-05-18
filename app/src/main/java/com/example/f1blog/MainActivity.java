package com.example.f1blog;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView textViewWelcomeAnimation;
    private View contentLayout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        // Bejelentkezési ellenőrzés
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        textViewWelcomeAnimation = findViewById(R.id.textViewWelcomeAnimation);
        contentLayout = findViewById(R.id.contentLayout);

        // Üdvözlő animáció
        String username = getIntent().getStringExtra("username");
        if (username != null && !username.isEmpty()) {
            textViewWelcomeAnimation.setText("Üdv, " + username + "!");
        } else {
            // Ha nincs username, próbáljuk meg az emailből kinyerni
            String email = mAuth.getCurrentUser().getEmail();
            if (email != null && !email.isEmpty()) {
                username = email.split("@")[0];
                textViewWelcomeAnimation.setText("Üdv, " + username + "!");
            } else {
                textViewWelcomeAnimation.setText("Üdv!");
            }
        }

        AlphaAnimation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setDuration(2000);
        fadeIn.setFillAfter(true);
        textViewWelcomeAnimation.setVisibility(View.VISIBLE);
        textViewWelcomeAnimation.startAnimation(fadeIn);

        // Fő tartalom megjelenítése az animáció után
        fadeIn.setAnimationListener(new AlphaAnimation.AnimationListener() {
            @Override
            public void onAnimationStart(android.view.animation.Animation animation) {}

            @Override
            public void onAnimationEnd(android.view.animation.Animation animation) {
                contentLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(android.view.animation.Animation animation) {}
        });

        // TabLayout és ViewPager2 beállítása
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager = findViewById(R.id.viewPager);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new DriversFragment());
        fragments.add(new TeamsFragment());
        fragments.add(new NewsFragment());
        fragments.add(new CircuitsFragment()); // Új fül hozzáadása

        List<String> tabTitles = new ArrayList<>();
        tabTitles.add("Pilóták");
        tabTitles.add("Csapatok");
        tabTitles.add("Hírek");
        tabTitles.add("Pályák"); // Új fül címe

        ViewPagerAdapter adapter = new ViewPagerAdapter(this, fragments);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> tab.setText(tabTitles.get(position))).attach();

        // Kijelentkezés gomb
        Button buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}