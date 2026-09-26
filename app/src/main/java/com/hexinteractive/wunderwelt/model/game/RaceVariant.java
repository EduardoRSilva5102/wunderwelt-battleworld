package com.hexinteractive.wunderwelt.model.game;

import java.util.ArrayList;
import java.util.List;

public enum RaceVariant {
    NORMAL(Race.HUMAN, "Normal"),
    SUPER_SOLDIER(Race.HUMAN, "Super Soldado"),
    GAMMA_RADIATION(Race.HUMAN, "Radiação Gama"),
    AESIR(Race.ASGARDIAN, "Aesir"),
    JOTUN(Race.ASGARDIAN, "Jotun"),
    HOSTLESS(Race.SYMBIOTE, "Sem hospedeiro");

    private final Race race;
    private final String displayName;

    RaceVariant(Race race, String displayName) {
        this.race = race;
        this.displayName = displayName;
    }

    public Race getRace() {
        return race;
    }

    public static List<RaceVariant> forRace(Race race) {
        List<RaceVariant> variants = new ArrayList<>();
        for (RaceVariant variant : values()) {
            if (variant.race == race) {
                variants.add(variant);
            }
        }
        return variants;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
