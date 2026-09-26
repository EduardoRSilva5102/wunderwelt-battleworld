package com.hexinteractive.wunderwelt.data.model;

public class Location {
    private long id;
    private String name;
    private String deck;

    public long getId() { return id; }
    public String getName() { return name == null ? "Local não informado" : name; }
    public String getDeck() { return deck == null ? "" : deck; }
}
