package com.meti;

import java.util.Map;

public class JavaDefinitionNodeBuilder {
    private final String modifierString;
    private final String name;
    private final String type;
    private final String value;

    public JavaDefinitionNodeBuilder() {
        this("", "", "", "");
    }

    public JavaDefinitionNodeBuilder(String modifierString, String name, String type, String value) {
        this.modifierString = modifierString;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public JavaDefinitionNodeBuilder withModifierString(String modifierString) {
        return new JavaDefinitionNodeBuilder(modifierString, name, type, value);
    }

    public JavaDefinitionNodeBuilder withName(String name) {
        return new JavaDefinitionNodeBuilder(modifierString, name, type, value);
    }

    public JavaDefinitionNodeBuilder withType(String type) {
        return new JavaDefinitionNodeBuilder(modifierString, name, type, value);
    }

    public JavaDefinitionNodeBuilder withValue(String value) {
        return new JavaDefinitionNodeBuilder(modifierString, name, type, value);
    }

    public Node complete() {
        return new MapNode(Map.of(
                "modifier-string", modifierString,
                "name", name,
                "type", type,
                "value", value
        ));
    }
}