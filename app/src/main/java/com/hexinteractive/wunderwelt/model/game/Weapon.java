package com.hexinteractive.wunderwelt.model.game;

import java.util.ArrayList;
import java.util.List;

public enum Weapon {
    SWORD(ClassType.WARRIOR, "Espada", BattleType.MARTIAL, 5),
    GREATSWORD(ClassType.WARRIOR, "Espada grande", BattleType.MARTIAL, 7),
    DAGGER(ClassType.WARRIOR, "Adaga", BattleType.MARTIAL, 4),
    MARTIAL_ARTS(ClassType.BRAWLER, "Artes marciais", BattleType.MARTIAL, 5),
    BRUTE_FORCE(ClassType.BRAWLER, "Força bruta", BattleType.MARTIAL, 7),
    BOXING(ClassType.BRAWLER, "Boxing", BattleType.MARTIAL, 5),
    BOW(ClassType.MARKSMAN, "Arco e flecha", BattleType.BALLISTIC, 5),
    PISTOL(ClassType.MARKSMAN, "Pistola", BattleType.BALLISTIC, 6),
    RIFLE(ClassType.MARKSMAN, "Rifle", BattleType.BALLISTIC, 7),
    MYSTIC_ARTS(ClassType.MAGE, "Artes místicas", BattleType.MYSTICAL, 6),
    CHAOS_ART(ClassType.MAGE, "Arte do caos", BattleType.MYSTICAL, 7),
    NECROMANCY(ClassType.MAGE, "Necromancia", BattleType.MYSTICAL, 6),
    DRONE(ClassType.TECHNOLOGIST, "Drone", BattleType.TECHNOLOGICAL, 5),
    ELECTRIC_GLOVE(ClassType.TECHNOLOGIST, "Luva elétrica", BattleType.TECHNOLOGICAL, 6),
    ELECTRIC_SWORD(ClassType.TECHNOLOGIST, "Espada elétrica", BattleType.TECHNOLOGICAL, 7);

    private final ClassType classType;
    private final String displayName;
    private final BattleType battleType;
    private final int power;

    Weapon(ClassType classType, String displayName, BattleType battleType, int power) {
        this.classType = classType;
        this.displayName = displayName;
        this.battleType = battleType;
        this.power = power;
    }

    public ClassType getClassType() {
        return classType;
    }

    public BattleType getBattleType() {
        return battleType;
    }

    public int getPower() {
        return power;
    }

    public static List<Weapon> forClass(ClassType classType) {
        List<Weapon> weapons = new ArrayList<>();
        for (Weapon weapon : values()) {
            if (weapon.classType == classType) {
                weapons.add(weapon);
            }
        }
        return weapons;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
