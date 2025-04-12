package com.example.f1blog;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText emailInput, passwordInput, usernameInput;
    Button registerButton;
    Toolbar toolbar;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailInput = findViewById(R.id.editTextEmail);
        passwordInput = findViewById(R.id.editTextPassword);
        usernameInput = findViewById(R.id.editTextUsername);
        registerButton = findViewById(R.id.buttonRegister);
        toolbar = findViewById(R.id.toolbar);

        // Toolbar beállítása
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Regisztráció");
        }

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        registerButton.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String username = usernameInput.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                Toast.makeText(this, "Minden mező kötelező", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        String uid = authResult.getUser().getUid();

                        HashMap<String, Object> userData = new HashMap<>();
                        userData.put("email", email);
                        userData.put("username", username);

                        db.collection("users").document(uid)
                                .set(userData)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this, "Sikeres regisztráció!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(this, MainActivity.class));
                                    finish();
                                });
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Hiba: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    });
        });

        // Toolbar vissza gomb kezelése
        toolbar.setNavigationOnClickListener(v -> {
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }
}