package com.hexinteractive.wunderwelt.data.model;

public final class CharacterPair {
    public final Character normal;
    public final String searchName;
    public final String battleworldName;
    public final String region;
    public final String lore;
    public final String publicId;
    public CharacterPair(Character normal, String searchName, String battleworldName, String region, String lore, String subject) {
        this.normal = normal;
        this.searchName = searchName;
        this.battleworldName = battleworldName;
        this.region = region;
        this.lore = lore;
        this.publicId = "characters/character_" + subject + "_battleworld_portrait";
    }
}
