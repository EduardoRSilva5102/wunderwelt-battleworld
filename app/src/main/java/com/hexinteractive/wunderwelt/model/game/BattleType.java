package com.hexinteractive.wunderwelt.model.game;

public enum BattleType {
    MARTIAL("Marcial"),
    BALLISTIC("Balístico"),
    TECHNOLOGICAL("Tecnológico"),
    MYSTICAL("Místico");

    private final String displayName;

    BattleType(String displayName) {
        this.displayName = displayName;
    }

    public boolean hasAdvantageOver(BattleType defender) {
        return (this == MARTIAL && defender == BALLISTIC)
                || (this == BALLISTIC && defender == TECHNOLOGICAL)
                || (this == TECHNOLOGICAL && defender == MARTIAL)
                || (this == MYSTICAL && defender == TECHNOLOGICAL);
    }

    @Override
    public String toString() {
        return displayName;
    }
}
