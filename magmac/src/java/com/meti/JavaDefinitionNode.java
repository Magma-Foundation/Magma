package com.meti;

import java.util.Objects;

public final class JavaDefinitionNode {
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

    public String modifierString() {
        return modifierString;
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