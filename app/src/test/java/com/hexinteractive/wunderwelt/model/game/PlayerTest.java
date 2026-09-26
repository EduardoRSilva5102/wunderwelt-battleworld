package com.hexinteractive.wunderwelt.model.game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerTest {
    @Test(expected = IllegalArgumentException.class)
    public void rejectsWeaponFromAnotherClass() {
        new Player("Viajante", Race.HUMAN, RaceVariant.NORMAL, ClassType.WARRIOR,
                Weapon.DRONE, Region.VALLEY_OF_DOOM, completeAttributes());
    }

    @Test
    public void vitalityIncreasesMaximumHp() {
        Attributes attributes = new Attributes();
        for (int i = 0; i < 10; i++) attributes.increase(AttributeType.VITALITY);
        Player player = new Player("Viajante", Race.HUMAN, RaceVariant.NORMAL, ClassType.WARRIOR,
                Weapon.SWORD, Region.VALLEY_OF_DOOM, attributes);
        assertEquals(100, player.getMaxHp());
    }

    private Attributes completeAttributes() {
        Attributes attributes = new Attributes();
        for (int i = 0; i < 10; i++) attributes.increase(AttributeType.STRENGTH);
        return attributes;
    }
}
