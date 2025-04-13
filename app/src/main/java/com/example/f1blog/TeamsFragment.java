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

public class TeamsFragment extends Fragment {

    public TeamsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_teams, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewTeams);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new TeamsAdapter(getTeamsList()));

        return view;
    }

    private List<Team> getTeamsList() {
        List<Team> teams = new ArrayList<>();
        teams.add(new Team("McLaren", 1963, 8, "Lando Norris, Oscar Piastri"));
        teams.add(new Team("Ferrari", 1929, 16, "Lewis Hamilton, Charles Leclerc"));
        teams.add(new Team("Red Bull", 2005, 4, "Max Verstappen, Yuki Tsunoda"));
        teams.add(new Team("Mercedes", 2009, 8, "George Russell, Andrea Kimi Antonelli"));
        teams.add(new Team("Aston Martin", 2018, 0, "Fernando Alonso, Lance Stroll"));
        teams.add(new Team("Alpine", 1986, 2, "Pierre Gasly, Jack Doohan"));
        teams.add(new Team("Williams", 1978, 9, "Alex Albon, Carlos Sainz"));
        teams.add(new Team("Haas", 2016, 0, "Esteban Ocon, Oliver Bearman"));
        teams.add(new Team("Racing Bulls", 2006, 0, "Liam Lawson, Isack Hadjar"));
        teams.add(new Team("Sauber", 1993, 0, "Nico Hülkenberg, Gabriel Bortoleto"));
        return teams;
    }
}