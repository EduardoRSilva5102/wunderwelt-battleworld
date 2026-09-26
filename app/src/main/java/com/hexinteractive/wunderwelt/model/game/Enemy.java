package com.hexinteractive.wunderwelt.model.game;

public final class Enemy {
    private final String name;
    private final String description;
    private final BattleType battleType;
    private final int maxHp;
    private final int attackPower;

    public Enemy(String name, String description, BattleType battleType, int maxHp, int attackPower) {
        this.name = name;
        this.description = description;
        this.battleType = battleType;
        this.maxHp = maxHp;
        this.attackPower = attackPower;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public BattleType getBattleType() { return battleType; }
    public int getMaxHp() { return maxHp; }
    public int getAttackPower() { return attackPower; }

    public static Enemy forRegion(Region region) {
        switch (region) {
            case VALLEY_OF_DOOM:
                return new Enemy("Xerife de Ferro", "Um executor mascarado da lei imperial.", BattleType.BALLISTIC, 34, 6);
            case MARVEL_1602:
                return new Enemy("Inquisidor de Doom", "Sua armadura mistura aço antigo e runas.", BattleType.MYSTICAL, 32, 6);
            case KUN_LUN:
                return new Enemy("Punho Juramentado", "Um guardião que não questiona a ordem imperial.", BattleType.MARTIAL, 36, 6);
            case KOWLOON:
                return new Enemy("Drone Cobrador", "Uma máquina de vigilância reconstruída muitas vezes.", BattleType.TECHNOLOGICAL, 31, 5);
            case NUEVA_YORK_2099:
                return new Enemy("Agente Alchemax", "Um soldado corporativo conectado à rede de vigilância.", BattleType.BALLISTIC, 33, 6);
            case TECHNOPOLIS:
            default:
                return new Enemy("Sentinela de Quarentena", "Uma armadura autônoma marcada com o selo de Doom.", BattleType.TECHNOLOGICAL, 36, 6);
        }
    }
}
