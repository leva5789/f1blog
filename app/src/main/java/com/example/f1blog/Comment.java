package com.example.f1blog;

public class Comment {
    private String id;
    private String text;
    private String username;

    public Comment() {
    }

    public Comment(String id, String text, String username) {
        this.id = id;
        this.text = text;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getUsername() {
        return username;
    }
}