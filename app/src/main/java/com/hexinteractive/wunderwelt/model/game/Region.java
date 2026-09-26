package com.hexinteractive.wunderwelt.model.game;

public enum Region {
    VALLEY_OF_DOOM("Vale do Destino", "Fronteira de areia, ferrugem e pistoleiros sob a lei de Doom."),
    KING_JAMES_ENGLAND("King James' England", "Inglaterra histórica de caçadores, superstição e aço."),
    KUN_LUN("K’un-Lun", "Mosteiros e escolas marciais guardam segredos sobre a energia do mundo."),
    KILLVILLE("Killville", "Uma cidade de assassinos sob a presença de M.O.D.O.K."),
    NUEVA_YORK_2099("Nueva York 2099", "Megacorporações, neon e vigilância dominam o horizonte."),
    TECHNOPOLIS("Tecnópolis", "Laboratórios e armaduras mantêm uma quarentena autoritária."),
    EGYPTIA("Egyptia", "Pirâmides e trabalho forçado sob a sombra de Khonshu."),
    UTOPOLIS("Utopolis", "Um domínio subjugado pelo Esquadrão Sinistro."),
    THE_REGENCY("The Regency", "Um território onde Regent caça os poderes dos heróis."),
    MONARCHY_OF_M("The Monarchy of M", "A monarquia mutante da Casa de Magnus."),
    WASTELANDS("The Wastelands", "Ruínas e estradas de um mundo devastado."),
    MANHATTAN("Manhattan", "Realidades sobrepostas e túneis de Monster Metropolis."),
    DOOMSTADT("Doomstadt", "A fortaleza imperial no centro de Battleworld.");

    private final String displayName;
    private final String description;

    Region(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isStartingRegion() {
        switch (this) {
            case VALLEY_OF_DOOM: case KING_JAMES_ENGLAND: case KUN_LUN:
            case KILLVILLE: case NUEVA_YORK_2099: case TECHNOPOLIS: return true;
            default: return false;
        }
    }

    public Region getPassage() {
        switch (this) {
            case VALLEY_OF_DOOM: return EGYPTIA;
            case KING_JAMES_ENGLAND: return UTOPOLIS;
            case KUN_LUN:
            case TECHNOPOLIS: return THE_REGENCY;
            case KILLVILLE: return MONARCHY_OF_M;
            case NUEVA_YORK_2099: return WASTELANDS;
            default: throw new IllegalStateException("Esta região não é uma origem.");
        }
    }

    @Override
    public String toString() {
        return displayName;
    }
}
