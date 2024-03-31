package com.meti.magma;

import com.meti.java.RenderableBuilder;

import java.util.Optional;

public class MagmaDefinitionBuilder implements RenderableBuilder {
    private String flagString;
    private String mutabilityString;
    private String name;
    private String type;
    private Optional<String> value;

    public MagmaDefinitionBuilder() {
        this("", "", "", "", Optional.empty());
    }

    public MagmaDefinitionBuilder(String flagString, String mutabilityString, String name, String type, Optional<String> value) {
        this.flagString = flagString;
        this.mutabilityString = mutabilityString;
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public MagmaDefinitionBuilder withFlags(String flagString) {
        return new MagmaDefinitionBuilder(flagString, mutabilityString, name, type, value);
    }

    public MagmaDefinitionBuilder withMutability(String mutabilityString) {
        return new MagmaDefinitionBuilder(flagString, mutabilityString, name, type, value);
    }

    public MagmaDefinitionBuilder withName(String name) {
        return new MagmaDefinitionBuilder(flagString, mutabilityString, name, type, value);
    }

    public MagmaDefinitionBuilder withType(String type) {
        return new MagmaDefinitionBuilder(flagString, mutabilityString, name, type, value);
    }

    public MagmaDefinitionBuilder withValue(String value) {
        return new MagmaDefinitionBuilder(flagString, mutabilityString, name, type, Optional.of(value));
    }

    @Override
    public MagmaDefinition build() {
        return new MagmaDefinition(flagString, mutabilityString, name, type, value);
    }
}