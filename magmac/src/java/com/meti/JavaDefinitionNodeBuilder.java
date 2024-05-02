package com.meti;

public class JavaDefinitionNodeBuilder {
    private String modifierString;
    private String name;
    private String type;
    private String value;

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

    public JavaDefinitionNode complete() {
        return new JavaDefinitionNode(modifierString, name, type, value);
    }
}