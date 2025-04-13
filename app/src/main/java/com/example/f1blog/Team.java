package com.example.f1blog;

import java.io.Serializable;

public class Team implements Serializable {
    private String name;
    private int foundedYear;
    private int championships;
    private String drivers;

    public Team(String name, int foundedYear, int championships, String drivers) {
        this.name = name;
        this.foundedYear = foundedYear;
        this.championships = championships;
        this.drivers = drivers;
    }

    public String getName() {
        return name;
    }

    public int getFoundedYear() {
        return foundedYear;
    }

    public int getChampionships() {
        return championships;
    }

    public String getDrivers() {
        return drivers;
    }
}