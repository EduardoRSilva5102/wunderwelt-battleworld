package com.hexinteractive.wunderwelt.model.game;

import java.util.EnumMap;
import java.util.Map;

public final class Attributes {
    public static final int POINT_BUDGET = 10;

    private final EnumMap<AttributeType, Integer> values = new EnumMap<>(AttributeType.class);

    public Attributes() {
        for (AttributeType type : AttributeType.values()) {
            values.put(type, 0);
        }
    }

    public Attributes(Attributes source) {
        values.putAll(source.values);
    }

    public int get(AttributeType type) {
        Integer value = values.get(type);
        return value == null ? 0 : value;
    }

    public boolean increase(AttributeType type) {
        if (getRemainingPoints() == 0) {
            return false;
        }
        values.put(type, get(type) + 1);
        return true;
    }

    public boolean decrease(AttributeType type) {
        if (get(type) == 0) {
            return false;
        }
        values.put(type, get(type) - 1);
        return true;
    }

    public int getSpentPoints() {
        int total = 0;
        for (Map.Entry<AttributeType, Integer> entry : values.entrySet()) {
            total += entry.getValue();
        }
        return total;
    }

    public int getRemainingPoints() {
        return POINT_BUDGET - getSpentPoints();
    }

    public boolean isComplete() {
        return getRemainingPoints() == 0;
    }
}
