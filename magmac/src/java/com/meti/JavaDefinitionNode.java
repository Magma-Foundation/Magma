package com.meti;

import java.util.Optional;

public final class JavaDefinitionNode implements Node {
    private final String modifierString;
    private final String name;
    private final String type;
    private final String value;

    public JavaDefinitionNode(String modifierString, String name, String type, String value) {
        this.modifierString = modifierString;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    private Optional<String> findModifierString() {
        return Optional.of(modifierString);
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
    public Optional<String> find(String key) {
        return switch (key) {
            case "mutability-modifier" -> findMutabilityModifier();
            case "modifier-string" -> findModifierString();
            case "name" -> findName();
            case "type" -> findType();
            case "value" -> findValue();
            default -> Optional.empty();
        };
    }

    private Optional<String> findMutabilityModifier() {
        throw new UnsupportedOperationException();
    }
}