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

    private Optional<String> findMutabilityModifier() {
        return Optional.of(mutabilityModifier);
    }

    private Optional<String> findName() {
        return Optional.of(name);
    }

    private Optional<String> findType() {
        return Optional.of(type);
    }

    private Optional<String> findValue() {
        return Optional.of(value);
    }

    @Override
    public Optional<String> apply(String key) {
        return switch (key) {
            case "mutability-modifier" -> findMutabilityModifier();
            case "modifier-string" -> findModifierString();
            case "name" -> findName();
            case "type" -> findType();
            case "value" -> findValue();
            default -> Optional.empty();
        };
    }

    private Optional<String> findModifierString() {
        throw new UnsupportedOperationException();
    }
}