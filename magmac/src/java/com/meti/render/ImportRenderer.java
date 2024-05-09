package com.meti.render;

import com.meti.node.Attribute;
import com.meti.node.MapNode;

import java.util.Optional;

public record ImportRenderer(MapNode node) implements Renderer {
    @Override
    public Optional<String> render() {
        if (!node().type().equals("import")) return Optional.empty();

        var child1 = node.apply("child").flatMap(Attribute::asString).orElse("");
        var parent1 = node.apply("parent").flatMap(Attribute::asString).orElse("");

        return Optional.of("import { " + child1 + " } from " + parent1 + ";\n");
    }
}