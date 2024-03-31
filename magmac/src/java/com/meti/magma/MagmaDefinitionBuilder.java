package com.meti.magma;

public class MagmaDefinitionBuilder {
    private String flagString;
    private String mutabilityString;
    private String name;
    private String type;
    private String value;

    public MagmaDefinitionBuilder withFlags(String flagString) {
        this.flagString = flagString;
        return this;
    }

    public MagmaDefinitionBuilder withMutability(String mutabilityString) {
        this.mutabilityString = mutabilityString;
        return this;
    }

    public MagmaDefinitionBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public MagmaDefinitionBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public MagmaDefinitionBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public MagmaDefinition build() {
        return new MagmaDefinition(flagString, mutabilityString, name, type, value);
    }
}