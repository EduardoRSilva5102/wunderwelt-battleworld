package com.hexinteractive.wunderwelt.model.game;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AttributesTest {
    @Test
    public void budgetCannotBeExceeded() {
        Attributes attributes = new Attributes();
        for (int i = 0; i < Attributes.POINT_BUDGET; i++) {
            assertTrue(attributes.increase(AttributeType.STRENGTH));
        }
        assertFalse(attributes.increase(AttributeType.VITALITY));
        assertTrue(attributes.isComplete());
        assertEquals(0, attributes.getRemainingPoints());
    }

    @Test
    public void valuesCannotBecomeNegative() {
        Attributes attributes = new Attributes();
        assertFalse(attributes.decrease(AttributeType.ENERGY));
        assertEquals(0, attributes.get(AttributeType.ENERGY));
    }
}
