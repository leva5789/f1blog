package com.example.f1blog;

public class Driver {
    private String name;
    private String team;
    private int wins;
    private int poles;
    private int points;

    public Driver(String name, String team, int wins, int poles, int points) {
        this.name = name;
        this.team = team;
        this.wins = wins;
        this.poles = poles;
        this.points = points;
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

    public int getPoints() {
        return points;
    }
}