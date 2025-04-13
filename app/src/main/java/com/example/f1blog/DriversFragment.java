package com.example.f1blog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DriversFragment extends Fragment {

    public DriversFragment() {
        // Üres konstruktor szükséges
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drivers, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewDrivers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new DriversAdapter(getDriversList()));

        return view;
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