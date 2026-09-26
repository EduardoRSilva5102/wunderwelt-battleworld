package com.hexinteractive.wunderwelt.model.game;

import java.util.ArrayList;
import java.util.List;

public enum RaceVariant {
    NORMAL(Race.HUMAN, "Normal"),
    SUPER_SOLDIER(Race.HUMAN, "Super Soldado"),
    GAMMA_RADIATION(Race.HUMAN, "Radiação Gama"),
    AESIR(Race.ASGARDIAN, "Aesir"),
    JOTUN(Race.ASGARDIAN, "Jotun"),
    HOSTLESS(Race.SYMBIOTE, "Sem hospedeiro"),
    TELEPATHY(Race.MUTANT, "Telepatia"),
    TELEKINESIS(Race.MUTANT, "Telecinese"),
    CRYOKINESIS(Race.MUTANT, "Controle criogênico"),
    ELECTROMAGNETISM(Race.MUTANT, "Manipulação eletromagnética"),
    HEALING_FACTOR(Race.MUTANT, "Fator de cura acelerado"),
    OPTIC_BLAST(Race.MUTANT, "Projeção óptica de energia"),
    KINETIC_ABSORPTION(Race.MUTANT, "Absorção cinética");

    private final Race race;
    private final String displayName;

    RaceVariant(Race race, String displayName) {
        this.race = race;
        this.displayName = displayName;
    }

    public Race getRace() {
        return race;
    }

    public BattleType getPowerType() {
        switch (this) {
            case TELEPATHY:
            case TELEKINESIS:
            case CRYOKINESIS: return BattleType.MYSTICAL;
            case ELECTROMAGNETISM: return BattleType.TECHNOLOGICAL;
            case OPTIC_BLAST: return BattleType.BALLISTIC;
            default: return null;
        }
    }

    public String getPowerDescription() {
        if (this == HEALING_FACTOR) return "Passivo: +5 de HP máximo.";
        if (this == KINETIC_ABSORPTION) return "Passivo: absorve 1 ponto de dano por ataque recebido.";
        return getPowerType() == null ? "" : "Tipo da habilidade: " + getPowerType() + ". Ataques comuns usam a arma.";
    }

    public static RaceVariant rollMutantPower(java.util.Random random) {
        List<RaceVariant> powers = forRace(Race.MUTANT);
        return powers.get(random.nextInt(powers.size()));
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
