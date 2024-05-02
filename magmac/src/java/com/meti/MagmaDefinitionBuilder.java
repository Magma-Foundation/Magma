package com.meti;

public class MagmaDefinitionBuilder {
    private String mutabilityModifier;
    private String name;
    private String type;
    private String value;

    public MagmaDefinitionBuilder() {
        this("", "", "", "");
    }

    public MagmaDefinitionBuilder(String mutabilityModifier, String name, String type, String value) {
        this.mutabilityModifier = mutabilityModifier;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public MagmaDefinitionBuilder withMutabilityModifier(String mutabilityModifier) {
        return new MagmaDefinitionBuilder(mutabilityModifier, name, type, value);
    }

    public MagmaDefinitionBuilder withName(String name) {
        return new MagmaDefinitionBuilder(mutabilityModifier, name, type, value);
    }

    public MagmaDefinitionBuilder withType(String type) {
        return new MagmaDefinitionBuilder(mutabilityModifier, name, type, value);
    }

    public MagmaDefinitionBuilder withValue(String value) {
        return new MagmaDefinitionBuilder(mutabilityModifier, name, type, value);
    }

    public MagmaDefinition build() {
        return new MagmaDefinition(mutabilityModifier, name, type, value);
    }
}