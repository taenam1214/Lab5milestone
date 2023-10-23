package com.cs407.lab5_milestone;

public class Notes {
    private final String date;
    private final String username;
    private  final String title;
    private final String content;

    public Notes(String date, String username, String title, String content) {
        this.date = date;
        this.username = username;
        this.title = title;
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public String getUsername() {
        return username;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

}
