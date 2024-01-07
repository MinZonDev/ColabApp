package com.example.collaborators.model;

import com.google.gson.annotations.SerializedName;

public class Event {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("description")

    private String description;
    @SerializedName("location")
    private String location;
    @SerializedName("time")
    private String time;
    @SerializedName("image")
    private String image;

    public Event(String title, String description, String location, String time, String image) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.time = time;
        this.image = image;
    }

    public Event(int id, String title, String description, String location, String time, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.time = time;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
