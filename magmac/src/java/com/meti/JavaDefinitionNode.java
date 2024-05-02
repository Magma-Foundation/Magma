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

    @Override
    public Optional<String> findModifierString() {
        return Optional.of(modifierString);
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