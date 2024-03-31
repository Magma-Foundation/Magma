package com.meti.magma;

import com.meti.node.Renderable;

import java.util.Optional;

public record DeclarationNode(String flagString, String name, String type,
                              Optional<String> value) implements Renderable {
    @Override
    public String render() {
        var renderedValue = value.map(inner -> " = " + inner).orElse("");
        return flagString + type + " " + name + renderedValue + ";";
    }
}