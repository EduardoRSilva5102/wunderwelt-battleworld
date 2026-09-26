package com.hexinteractive.wunderwelt.data.model;

public class Power {
    private long id;
    private String name;

    public Power(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() { return id; }
    public String getName() { return name == null ? "Poder não informado" : name; }
}
