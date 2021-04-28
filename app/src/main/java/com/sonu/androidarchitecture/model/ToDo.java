package com.sonu.androidarchitecture.model;

public class ToDo {
    private long id;
    private String name;
    private String place;

    public ToDo(long id, String name, String place) {
        this.id = id;
        this.name = name;
        this.place = place;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}


