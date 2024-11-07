package com.example.locationfinder;


//this is a constructor class with setters and getters for the actual location objects to be
//stored in the DB

public class Location {
    private int id;
    private String address;
    private double latitude;
    private double longitude;

    public Location(int id, String address, double latitude, double longitude) {
        this.id = id;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}