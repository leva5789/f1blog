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
        drivers.add(new Driver("Lando Norris", "McLaren", 4, 4, 77));
        drivers.add(new Driver("Oscar Piastri", "McLaren", 4, 1, 74));
        drivers.add(new Driver("Lewis Hamilton", "Ferrari", 104, 104, 25));
        drivers.add(new Driver("Charles Leclerc", "Ferrari", 7, 12, 32));
        drivers.add(new Driver("Max Verstappen", "Red Bull", 63, 40, 69));
        drivers.add(new Driver("Yuki Tsunoda", "Red Bull", 0, 0, 5));
        drivers.add(new Driver("George Russell", "Mercedes", 3, 3, 63));
        drivers.add(new Driver("Andrea Kimi Antonelli", "Mercedes", 0, 0, 30));
        drivers.add(new Driver("Fernando Alonso", "Aston Martin", 32, 22, 0));
        drivers.add(new Driver("Lance Stroll", "Aston Martin", 0, 0, 10));
        drivers.add(new Driver("Pierre Gasly", "Alpine", 1, 0, 6));
        drivers.add(new Driver("Jack Doohan", "Alpine", 0, 0, 0));
        drivers.add(new Driver("Alex Albon", "Williams", 0, 0, 18));
        drivers.add(new Driver("Carlos Sainz", "Williams", 3, 3, 1));
        drivers.add(new Driver("Esteban Ocon", "Haas", 1, 0, 14));
        drivers.add(new Driver("Oliver Bearman", "Haas", 0, 0, 6));
        drivers.add(new Driver("Liam Lawson", "Racing Bulls", 0, 0, 0));
        drivers.add(new Driver("Isack Hadjar", "Racing Bulls", 0, 0, 4));
        drivers.add(new Driver("Nico Hülkenberg", "Sauber", 0, 1, 6));
        drivers.add(new Driver("Gabriel Bortoleto", "Sauber", 0, 0, 0));


        drivers.sort((d1, d2) -> Integer.compare(d2.getPoints(), d1.getPoints()));

        return drivers;
    }
}