package com.example.f1blog;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class DriversFragment extends Fragment {

    private static final String TAG = "DriversFragment";
    private RecyclerView recyclerView;
    private DriversAdapter driversAdapter;
    private List<Driver> driversList;
    private FirebaseFirestore db;
    private ImageButton filterButton;
    private Spinner filterSpinner;
    private boolean isFilterVisible = false;

    public DriversFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drivers, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewDrivers);
        filterButton = view.findViewById(R.id.filterButton);
        filterSpinner = view.findViewById(R.id.filterSpinner);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        driversList = new ArrayList<>();
        driversAdapter = new DriversAdapter(driversList);
        recyclerView.setAdapter(driversAdapter);

        // Szűrési opciók beállítása a Spinner-hez
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.filter_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filterSpinner.setAdapter(adapter);

        // Alapértelmezett szűrési opció: Pontok
        filterSpinner.setSelection(0);

        // Szűrő ikon kattintás kezelése
        filterButton.setOnClickListener(v -> {
            isFilterVisible = !isFilterVisible;
            filterSpinner.setVisibility(isFilterVisible ? View.VISIBLE : View.GONE);
        });

        // Spinner kiválasztás kezelése
        filterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortDrivers(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Alapértelmezett rendezés: pontok
                sortDrivers(0);
            }
        });

        // Versenyzők betöltése Firestore-ból
        loadDriversFromFirestore();

        return view;
    }

    private void loadDriversFromFirestore() {
        Log.d(TAG, "Loading drivers from Firestore...");
        db.collection("drivers").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    Log.d(TAG, "Successfully fetched drivers from Firestore");
                    driversList.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Driver driver = document.toObject(Driver.class);
                        driver.setId(document.getId());
                        if (driver.getName() != null && driver.getTeam() != null) {
                            driversList.add(driver);
                            Log.d(TAG, "Driver loaded: " + driver.getName());
                        } else {
                            Log.w(TAG, "Invalid driver data, skipping: " + document.getId());
                            db.collection("drivers").document(document.getId()).delete();
                        }
                    }
                    if (driversList.isEmpty()) {
                        Log.w(TAG, "No valid drivers found in Firestore");
                        Toast.makeText(getContext(), "Nincsenek versenyzők a Firestore-ban!", Toast.LENGTH_SHORT).show();
                    } else {
                        // Alapértelmezett rendezés: pontok
                        sortDrivers(filterSpinner.getSelectedItemPosition());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error loading drivers: " + e.getMessage(), e);
                    Toast.makeText(getContext(), "Hiba a versenyzők betöltésekor: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void sortDrivers(int filterPosition) {
        if (driversList.isEmpty()) return;

        switch (filterPosition) {
            case 0: // Pontok
                driversList.sort((d1, d2) -> Integer.compare(d2.getPoints(), d1.getPoints()));
                break;
            case 1: // Futamgyőzelmek
                driversList.sort((d1, d2) -> Integer.compare(d2.getWins(), d1.getWins()));
                break;
            case 2: // Pole pozíciók
                driversList.sort((d1, d2) -> Integer.compare(d2.getPoles(), d1.getPoles()));
                break;
        }
        driversAdapter.notifyDataSetChanged();
        Log.d(TAG, "Drivers sorted by option " + filterPosition + ", size: " + driversList.size());
    }
}