package com.meti;

public class MagmaDefinitionBuilder {
    private String flagString;
    private String mutabilityString;
    private String name;
    private String type;
    private String value;

    public MagmaDefinitionBuilder setFlagString(String flagString) {
        this.flagString = flagString;
        return this;
    }

    public MagmaDefinitionBuilder setMutabilityString(String mutabilityString) {
        this.mutabilityString = mutabilityString;
        return this;
    }

    public MagmaDefinitionBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public MagmaDefinitionBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public MagmaDefinitionBuilder setValue(String value) {
        this.value = value;
        return this;
    }

    public MagmaDefinition createMagmaDefinition() {
        return new MagmaDefinition(flagString, mutabilityString, name, type, value);
    }
}