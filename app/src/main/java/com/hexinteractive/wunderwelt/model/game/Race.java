package com.hexinteractive.wunderwelt.model.game;

public enum Race {
    HUMAN("Humano", "Versátil e resiliente diante de um mundo impossível."),
    MUTANT("Mutante", "Carrega uma habilidade extraordinária despertada pelo gene mutante."),
    ASGARDIAN("Asgardiano", "Descendente de uma linhagem de força e energia ancestrais."),
    SYMBIOTE("Simbionte", "Uma criatura viva que sobrevive sem hospedeiro em Battleworld.");

    private final String displayName;
    private final String description;

    Race(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
