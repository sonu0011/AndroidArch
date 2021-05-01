package com.sonu.androidarchitecture.model.beans;

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

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ToDo) {
            ToDo temp = (ToDo) obj;
            if (temp.id == this.id) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}


