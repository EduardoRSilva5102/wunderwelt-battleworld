package com.hexinteractive.wunderwelt.model.game;

import java.util.Objects;

import com.hexinteractive.wunderwelt.utils.Constants;

public final class Player {
    private final String name;
    private final Race race;
    private final RaceVariant raceVariant;
    private final ClassType classType;
    private final Weapon weapon;
    private final Region region;
    private final Attributes attributes;

    public Player(String name, Race race, RaceVariant raceVariant, ClassType classType,
                  Weapon weapon, Region region, Attributes attributes) {
        this.name = Objects.requireNonNull(name).trim();
        this.race = Objects.requireNonNull(race);
        this.classType = Objects.requireNonNull(classType);
        this.weapon = Objects.requireNonNull(weapon);
        this.region = Objects.requireNonNull(region);
        this.attributes = new Attributes(Objects.requireNonNull(attributes));
        if (race == Race.MUTANT && raceVariant == null) {
            throw new IllegalArgumentException("Mutantes precisam de um poder sorteado.");
        }
        if (this.name.isEmpty()) {
            throw new IllegalArgumentException("O nome não pode estar vazio.");
        }
        if (raceVariant != null && raceVariant.getRace() != race) {
            throw new IllegalArgumentException("A variante não pertence à raça escolhida.");
        }
        if (weapon.getClassType() != classType) {
            throw new IllegalArgumentException("A arma não pertence à classe escolhida.");
        }
        if (!region.isStartingRegion()) {
            throw new IllegalArgumentException("Escolha uma das seis regiões de origem.");
        }
        if (!attributes.isComplete()) {
            throw new IllegalArgumentException("Distribua todos os pontos de atributo.");
        }
        this.raceVariant = raceVariant;
    }

    public String getName() { return name; }
    public Race getRace() { return race; }
    public RaceVariant getRaceVariant() { return raceVariant; }
    public ClassType getClassType() { return classType; }
    public Weapon getWeapon() { return weapon; }
    public Region getRegion() { return region; }
    public Attributes getAttributes() { return new Attributes(attributes); }

    public int getMaxHp() {
        return Constants.BASE_PLAYER_HP
                + attributes.get(AttributeType.VITALITY) * Constants.HP_PER_VITALITY
                + (raceVariant == RaceVariant.HEALING_FACTOR ? Constants.HP_PER_VITALITY : 0);
    }
}
