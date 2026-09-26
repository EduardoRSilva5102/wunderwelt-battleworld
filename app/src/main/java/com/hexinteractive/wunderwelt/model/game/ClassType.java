package com.hexinteractive.wunderwelt.model.game;

public enum ClassType {
    WARRIOR("Guerreiro", "Força e vitalidade em ataques com armas brancas."),
    BRAWLER("Brawler", "Força e agilidade no combate corpo a corpo."),
    MARKSMAN("Marksman", "Agilidade e inteligência em ataques à distância."),
    MAGE("Mago", "Energia e inteligência canalizadas por artes arcanas."),
    TECHNOLOGIST("Tecnólogo", "Inteligência e energia aplicadas a equipamentos avançados.");

    private final String displayName;
    private final String description;

    ClassType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getAbilityName() {
        switch (this) {
            case WARRIOR: return "Golpe Ancestral";
            case BRAWLER: return "Fúria Contida";
            case MARKSMAN: return "Tiro Calculado";
            case MAGE: return "Ruptura Mística";
            default: return "Sobrecarga";
        }
    }

    @Override
    public String toString() {
        return displayName;
    }
}
