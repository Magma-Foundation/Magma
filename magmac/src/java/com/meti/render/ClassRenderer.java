package com.meti.render;

import com.meti.node.Attribute;
import com.meti.node.MapNode;

import java.util.Optional;

public record ClassRenderer(MapNode node) implements Renderer {
    @Override
    public Optional<String> render() {
        if (!node().type().equals("class")) return Optional.empty();

        var name = node.apply("name").flatMap(Attribute::asString).orElse("");
        var renderedContent = node.apply("content").flatMap(Attribute::asNode)
                .flatMap(content -> content.apply("lines"))
                .flatMap(Attribute::asString)
                .orElse("");

        return Optional.of("class def " + name + "() => {" + renderedContent + "}");
    }
}