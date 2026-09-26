package com.hexinteractive.wunderwelt.utils;

import com.hexinteractive.wunderwelt.model.game.AttributeType;
import com.hexinteractive.wunderwelt.model.game.Attributes;
import com.hexinteractive.wunderwelt.model.game.BattleType;
import com.hexinteractive.wunderwelt.model.game.ClassType;
import com.hexinteractive.wunderwelt.model.game.Enemy;
import com.hexinteractive.wunderwelt.model.game.Player;
import com.hexinteractive.wunderwelt.model.game.Race;
import com.hexinteractive.wunderwelt.model.game.RaceVariant;
import com.hexinteractive.wunderwelt.model.game.Region;
import com.hexinteractive.wunderwelt.model.game.Weapon;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DamageCalculatorTest {
    @Test
    public void confirmedTypeAdvantageIsApplied() {
        Player player = warrior();
        Enemy enemy = new Enemy("Alvo", "", BattleType.BALLISTIC, 30, 5);
        DamageCalculator.DamageResult result = new DamageCalculator(new FixedRandom(99))
                .calculatePlayerAttack(player, enemy, false);
        assertEquals(23, result.getDamage());
        assertTrue(result.hasAdvantage());
    }

    @Test
    public void defendReducesEnemyDamage() {
        Enemy enemy = new Enemy("Alvo", "", BattleType.MARTIAL, 30, 8);
        DamageCalculator calculator = new DamageCalculator(new FixedRandom(0));
        assertEquals(4, calculator.calculateEnemyAttack(enemy, true));
    }

    private Player warrior() {
        Attributes attributes = new Attributes();
        for (int i = 0; i < 10; i++) attributes.increase(AttributeType.STRENGTH);
        return new Player("Viajante", Race.HUMAN, RaceVariant.NORMAL, ClassType.WARRIOR,
                Weapon.SWORD, Region.VALLEY_OF_DOOM, attributes);
    }

    private static final class FixedRandom extends Random {
        private final int value;
        FixedRandom(int value) { this.value = value; }
        @Override public int nextInt(int bound) { return Math.min(value, bound - 1); }
    }
}
