package com.meti.magma;

import com.meti.node.Renderable;

import java.util.Optional;

public record MagmaDefinition(String flagString, String mutabilityString, String name, String type,
                              Optional<String> value) implements Renderable {
    @Override
    public String render() {
        var valueString = value.map(inner -> " = " + inner).orElse("");
        return new MagmaDeclaration(flagString, mutabilityString, name, type).render() + valueString;
    }
}