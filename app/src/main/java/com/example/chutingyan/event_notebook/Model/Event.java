package com.example.chutingyan.event_notebook.Model;

public class Event {

    private String time;
    private String description;
    private int id;

    public Event() {
    }

    public Event(String time, String description, int id) {
        this.time = time;
        this.description = description;
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
