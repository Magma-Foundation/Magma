package com.meti.render;

import com.meti.node.Attribute;
import com.meti.node.MapNode;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public record ClassRenderer(MapNode node) implements Renderer {
    @Override
    public Optional<String> render() {
        if (!node().type().equals("class")) return Optional.empty();

        var name = node.apply("name").flatMap(Attribute::asString).orElse("");
        var renderedContent = node.apply("content").flatMap(Attribute::asNode)
                .flatMap(content -> content.apply("children"))
                .flatMap(Attribute::asListOfNodes)
                .orElse(Collections.emptyList())
                .stream()
                .map(node -> node.apply("value")).flatMap(Optional::stream)
                .map(Attribute::asString)
                .flatMap(Optional::stream)
                .collect(Collectors.joining());

        return Optional.of("class def " + name + "() => {" + renderedContent + "}");
    }
}