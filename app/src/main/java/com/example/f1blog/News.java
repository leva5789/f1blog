package com.example.f1blog;

public class News {
    private String title;
    private String description;
    private String date;
    private int imageResId;

    public News(String title, String description, String date, int imageResId) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public int getImageResId() {
        return imageResId;
    }
}