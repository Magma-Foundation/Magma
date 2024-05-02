package com.meti;

import java.util.Optional;

public final class MagmaDefinition implements Node {
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

    @Override
    public Optional<String> findMutabilityModifier() {
        return Optional.of(mutabilityModifier);
    }

    @Override
    public Optional<String> findName() {
        return Optional.of(name);
    }

    @Override
    public Optional<String> findType() {
        return Optional.of(type);
    }

    @Override
    public Optional<String> findValue() {
        return Optional.of(value);
    }
}