package com.meti.magma;

import com.meti.java.RenderableBuilder;
import com.meti.node.Renderable;

import java.util.Optional;

public record DeclarationNodeBuilder(String flagString, String name, String type,
                                     Optional<String> value) implements RenderableBuilder {
    public DeclarationNodeBuilder() {
        this("", "", "", Optional.empty());
    }

    public DeclarationNodeBuilder withFlagString(String flagString) {
        return new DeclarationNodeBuilder(flagString, name, type, value);
    }

    public DeclarationNodeBuilder withName(String name) {
        return new DeclarationNodeBuilder(flagString, name, type, value);
    }

    public DeclarationNodeBuilder withType(String type) {
        return new DeclarationNodeBuilder(flagString, name, type, value);
    }

    public DeclarationNodeBuilder withValue(String value) {
        return new DeclarationNodeBuilder(flagString, name, type, Optional.of(value));
    }

    @Override
    public Renderable build() {
        return new DeclarationNode(flagString, name, type, value);
    }
}