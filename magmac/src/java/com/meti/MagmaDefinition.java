package com.meti;

import java.util.Objects;

public final class MagmaDefinition {
    private final String mutabilityModifier;
    private final String name;
    private final String type;
    private final String value;

    public MagmaDefinition(String mutabilityModifier, String name, String type, String value) {
        this.mutabilityModifier = mutabilityModifier;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String mutabilityModifier() {
        return mutabilityModifier;
    }

    public String name() {
        return name;
    }

    public String type() {
        return type;
    }

    public String value() {
        return value;
    }
}