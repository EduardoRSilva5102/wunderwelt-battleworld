package com.hexinteractive.wunderwelt.model.game;

public enum AttributeType {
    STRENGTH("Força"),
    VITALITY("Vitalidade"),
    AGILITY("Agilidade"),
    INTELLIGENCE("Inteligência"),
    ENERGY("Energia");

    private final String displayName;

    AttributeType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
