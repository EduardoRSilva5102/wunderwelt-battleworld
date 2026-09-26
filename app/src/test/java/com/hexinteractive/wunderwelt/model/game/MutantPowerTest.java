package com.hexinteractive.wunderwelt.model.game;

import com.hexinteractive.wunderwelt.ui.character.CharacterViewModel;
import com.hexinteractive.wunderwelt.utils.DamageCalculator;
import org.junit.Test;
import java.util.Random;
import static org.junit.Assert.*;

public class MutantPowerTest {
    @Test public void rollAlwaysReturnsAMutantPower() {
        for (int seed = 0; seed < 100; seed++)
            assertEquals(Race.MUTANT, RaceVariant.rollMutantPower(new Random(seed)).getRace());
    }
    @Test public void leavingAndReturningDoesNotReroll() {
        CharacterViewModel vm = new CharacterViewModel();
        vm.setRace(Race.MUTANT);
        RaceVariant power = vm.getRaceVariant();
        vm.setRace(Race.HUMAN);
        vm.setRace(Race.MUTANT);
        assertSame(power, vm.getRaceVariant());
    }
    @Test public void mutantAbilityChangesTypeButNotWeaponAttack() {
        Player player = player(RaceVariant.OPTIC_BLAST);
        assertEquals(BattleType.BALLISTIC, DamageCalculator.getAttackType(player, true));
        assertEquals(BattleType.MARTIAL, DamageCalculator.getAttackType(player, false));
    }
    @Test public void healingIsABonusAndDoesNotSpendAttributeBudget() {
        Player player = player(RaceVariant.HEALING_FACTOR);
        assertEquals(55, player.getMaxHp());
        assertEquals(10, player.getAttributes().getSpentPoints());
    }
    private Player player(RaceVariant power) {
        Attributes a = new Attributes();
        for (int i = 0; i < 10; i++) a.increase(AttributeType.STRENGTH);
        return new Player("Teste", Race.MUTANT, power, ClassType.WARRIOR,
                Weapon.SWORD, Region.VALLEY_OF_DOOM, a);
    }
}
