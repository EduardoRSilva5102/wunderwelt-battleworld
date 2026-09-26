package com.hexinteractive.wunderwelt.model.game;

public final class Enemy {
    private final String name;
    private final String description;
    private final BattleType battleType;
    private final int maxHp;
    private final int attackPower;
    private String portraitPublicId = "enemies/enemy_guard_default";

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
    /** Reserved delivery key; no Cloudinary call or upload is performed in the MVP. */
    public String getPortraitPublicId() { return portraitPublicId; }

    public static Enemy forRegion(Region region) {
        Enemy enemy = createForRegion(region);
        enemy.portraitPublicId = "enemies/enemy_" + region.name().toLowerCase(java.util.Locale.ROOT) + "_default";
        return enemy;
    }

    private static Enemy createForRegion(Region region) {
        switch (region) {
            case EGYPTIA:
                return new Enemy("Cavaleiro da Lua", "Um dos guerreiros licantropos de Khonshu bloqueia a passagem.", BattleType.MYSTICAL, 36, 6);
            case UTOPOLIS:
                return new Enemy("Nighthawk", "O membro do Esquadrão Sinistro testa quem cruza Utopolis.", BattleType.TECHNOLOGICAL, 36, 6);
            case THE_REGENCY:
                return new Enemy("Regent", "Você precisa romper o cerco de sua armadura coletora.", BattleType.TECHNOLOGICAL, 38, 6);
            case MONARCHY_OF_M:
                return new Enemy("Mercúrio", "A velocidade de Pietro fecha o caminho até a saída.", BattleType.MARTIAL, 35, 6);
            case WASTELANDS:
                return new Enemy("Bando Hulk", "A travessia é interrompida por um dos brutais Hulks dos ermos.", BattleType.MARTIAL, 38, 6);
            case MANHATTAN:
                return new Enemy("Thor da patrulha", "A lei de Destino exige que você entregue o fragmento.", BattleType.MYSTICAL, 38, 6);
            case DOOMSTADT:
                return new Enemy("Deus Imperador Destino", "Resista à prova de Destino. Vencer este encontro não apaga seu poder divino.", BattleType.MYSTICAL, 40, 6);
            case VALLEY_OF_DOOM:
                return new Enemy("Xerife de Ferro", "Um executor mascarado da lei imperial.", BattleType.BALLISTIC, 34, 6);
            case KING_JAMES_ENGLAND:
                return new Enemy("Inquisidor de Doom", "Sua armadura mistura aço antigo e runas.", BattleType.MYSTICAL, 32, 6);
            case KUN_LUN:
                return new Enemy("Punho Juramentado", "Um guardião que não questiona a ordem imperial.", BattleType.MARTIAL, 36, 6);
            case KILLVILLE:
                return new Enemy("Drone Cobrador", "Uma máquina de vigilância reconstruída muitas vezes.", BattleType.TECHNOLOGICAL, 31, 5);
            case NUEVA_YORK_2099:
                return new Enemy("Agente Alchemax", "Um soldado corporativo conectado à rede de vigilância.", BattleType.BALLISTIC, 33, 6);
            case TECHNOPOLIS:
            default:
                return new Enemy("Sentinela de Quarentena", "Uma armadura autônoma marcada com o selo de Doom.", BattleType.TECHNOLOGICAL, 36, 6);
        }
    }

    public static Enemy forManhattan(boolean underground) {
        if (!underground) return forRegion(Region.MANHATTAN);
        Enemy enemy = new Enemy("Drácula", "O senhor dos vampiros exige o fragmento como tributo.",
                BattleType.MYSTICAL, 38, 6);
        enemy.portraitPublicId = "enemies/enemy_dracula_default";
        return enemy;
    }
}
