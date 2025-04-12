package com.example.f1blog;

import java.io.Serializable;

public class Driver implements Serializable {
    private String name;
    private String team;
    private int wins;
    private int poles;
    private int races;

    public Driver(String name, String team, int wins, int poles, int races) {
        this.name = name;
        this.team = team;
        this.wins = wins;
        this.poles = poles;
        this.races = races;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public int getWins() {
        return wins;
    }

    public int getPoles() {
        return poles;
    }

    public int getRaces() {
        return races;
    }
}