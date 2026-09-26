package com.hexinteractive.wunderwelt.model.game;

import com.hexinteractive.wunderwelt.utils.DamageCalculator;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.*;

public class BattlePresentationTest {
    @Test public void eachClassHasDistinctAbilityName() {
        Set<String> names = new HashSet<>();
        for (ClassType type : ClassType.values()) assertTrue(names.add(type.getAbilityName()));
    }
    @Test public void previewUsesSameTypeRulesAsDamage() {
        for (BattleType attacker : BattleType.values()) {
            for (BattleType defender : BattleType.values()) {
                float multiplier = DamageCalculator.getTypeMultiplier(attacker, defender);
                assertEquals(attacker.hasAdvantageOver(defender), multiplier > 1);
                assertEquals(defender.hasAdvantageOver(attacker), multiplier < 1);
            }
        }
    }
    @Test public void enemiesHaveStableDistinctDeliveryKeys() {
        Set<String> keys = new HashSet<>();
        for (Region region : Region.values()) {
            String key = Enemy.forRegion(region).getPortraitPublicId();
            assertTrue(key.matches("enemies/enemy_[a-z_0-9]+_default"));
            assertTrue(keys.add(key));
        }
        assertTrue(keys.add(Enemy.forManhattan(true).getPortraitPublicId()));
    }
}
