package com.hexinteractive.wunderwelt.utils;

import com.hexinteractive.wunderwelt.model.game.AttributeType;
import com.hexinteractive.wunderwelt.model.game.BattleType;
import com.hexinteractive.wunderwelt.model.game.Enemy;
import com.hexinteractive.wunderwelt.model.game.Player;

import java.util.Random;

public final class DamageCalculator {
    private final Random random;

    public DamageCalculator() {
        this(new Random());
    }

    public DamageCalculator(Random random) {
        this.random = random;
    }

    public DamageResult calculatePlayerAttack(Player player, Enemy enemy, boolean ability) {
        BattleType attackType = getAttackType(player, ability);
        int relevantAttribute = getRelevantAttribute(player, attackType);
        int damage = Constants.BASE_ATTACK_DAMAGE + player.getWeapon().getPower() + relevantAttribute;
        if (ability) {
            damage += Constants.ABILITY_BONUS_DAMAGE;
        }

        float typeMultiplier = getTypeMultiplier(attackType, enemy.getBattleType());
        int criticalChance = Math.min(
                Constants.MAX_CRITICAL_CHANCE,
                Constants.BASE_CRITICAL_CHANCE
                        + player.getAttributes().get(AttributeType.AGILITY)
                        * Constants.CRITICAL_CHANCE_PER_AGILITY
        );
        boolean critical = random.nextInt(100) < criticalChance;
        float criticalMultiplier = critical ? Constants.CRITICAL_MULTIPLIER : 1f;
        int finalDamage = Math.max(1, Math.round(damage * typeMultiplier * criticalMultiplier));
        return new DamageResult(finalDamage, critical, typeMultiplier);
    }

    public int calculateEnemyAttack(Enemy enemy, boolean defending) {
        int damage = enemy.getAttackPower() + random.nextInt(3);
        if (defending) {
            damage = Math.round(damage * Constants.DEFEND_MULTIPLIER);
        }
        return Math.max(1, damage);
    }

    public int calculateEnemyAttack(Enemy enemy, Player player, boolean defending) {
        int damage = calculateEnemyAttack(enemy, defending);
        return Math.max(1, damage - (player.getRaceVariant()
                == com.hexinteractive.wunderwelt.model.game.RaceVariant.KINETIC_ABSORPTION ? 1 : 0));
    }

    public static BattleType getAttackType(Player player, boolean ability) {
        if (ability && player.getRaceVariant() != null && player.getRaceVariant().getPowerType() != null) {
            return player.getRaceVariant().getPowerType();
        }
        return player.getWeapon().getBattleType();
    }

    private int getRelevantAttribute(Player player, BattleType type) {
        switch (type) {
            case MARTIAL:
                return player.getAttributes().get(AttributeType.STRENGTH);
            case BALLISTIC:
                return player.getAttributes().get(AttributeType.AGILITY);
            case TECHNOLOGICAL:
                return player.getAttributes().get(AttributeType.INTELLIGENCE);
            case MYSTICAL:
            default:
                return player.getAttributes().get(AttributeType.ENERGY);
        }
    }

    private float getTypeMultiplier(BattleType attacker, BattleType defender) {
        if (attacker.hasAdvantageOver(defender)) {
            return Constants.TYPE_ADVANTAGE_MULTIPLIER;
        }
        if (defender.hasAdvantageOver(attacker)) {
            return Constants.TYPE_DISADVANTAGE_MULTIPLIER;
        }
        return 1f;
    }

    public static final class DamageResult {
        private final int damage;
        private final boolean critical;
        private final float typeMultiplier;

        DamageResult(int damage, boolean critical, float typeMultiplier) {
            this.damage = damage;
            this.critical = critical;
            this.typeMultiplier = typeMultiplier;
        }

        public int getDamage() { return damage; }
        public boolean isCritical() { return critical; }
        public boolean hasAdvantage() { return typeMultiplier > 1f; }
        public boolean hasDisadvantage() { return typeMultiplier < 1f; }
    }
}
