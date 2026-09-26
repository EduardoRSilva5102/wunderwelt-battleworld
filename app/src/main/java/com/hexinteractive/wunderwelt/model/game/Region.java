package com.hexinteractive.wunderwelt.model.game;

public enum Region {
    VALLEY_OF_DOOM("Vale do Destino", "Fronteira de areia, ferrugem e pistoleiros sob a lei de Doom."),
    MARVEL_1602("Marvel 1602", "Um domínio renascentista de superstição, madeira e aço."),
    KUN_LUN("K’un-Lun", "Mosteiros e escolas marciais guardam segredos sobre a energia do mundo."),
    KOWLOON("Cidade Murada de Kowloon", "Uma cidade vertical onde becos e facções disputam cada andar."),
    NUEVA_YORK_2099("Nueva York 2099", "Megacorporações, neon e vigilância dominam o horizonte."),
    TECHNOPOLIS("Tecnópolis", "Laboratórios e armaduras mantêm uma quarentena autoritária."),
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
        return this != DOOMSTADT;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
