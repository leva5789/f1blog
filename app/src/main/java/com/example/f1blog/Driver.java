package com.example.f1blog;

public class Driver {
    private String id;
    private String name;
    private String team;
    private int wins;
    private int poles;
    private int points;

    public Driver() {
    }

    public Driver(String name, String team, int wins, int poles, int points) {
        this.name = name;
        this.team = team;
        this.wins = wins;
        this.poles = poles;
        this.points = points;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getPoles() {
        return poles;
    }

    public void setPoles(int poles) {
        this.poles = poles;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}