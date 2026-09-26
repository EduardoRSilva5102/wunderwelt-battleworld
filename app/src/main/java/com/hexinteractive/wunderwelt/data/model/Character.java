package com.hexinteractive.wunderwelt.data.model;

import java.util.Collections;
import java.util.List;

public class Character {
    private long id;
    private String name;
    private String deck;
    private String description;
    private List<Power> powers;
    private Issue firstAppearedInIssue;
    private String siteDetailUrl;

    public Character(long id, String name, String deck, String description,
                     List<Power> powers, Issue firstAppearedInIssue, String siteDetailUrl) {
        this.id = id;
        this.name = name;
        this.deck = deck;
        this.description = description;
        this.powers = powers;
        this.firstAppearedInIssue = firstAppearedInIssue;
        this.siteDetailUrl = siteDetailUrl;
    }

    public long getId() { return id; }
    public String getName() { return name == null ? "Personagem sem nome" : name; }
    public String getDeck() { return deck == null ? "" : deck; }
    public String getDescription() { return description == null ? "" : description; }
    public List<Power> getPowers() { return powers == null ? Collections.emptyList() : powers; }
    public Issue getFirstAppearedInIssue() { return firstAppearedInIssue; }
    public String getSiteDetailUrl() { return siteDetailUrl == null ? "" : siteDetailUrl; }
}
