package com.meti.render;

import com.meti.node.Attribute;
import com.meti.node.MapNode;
import com.meti.util.Options;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public record ClassRenderer(MapNode node) implements Renderer {
    @Override
    public Optional<String> render() {
        if (!node().type().equals("class")) return Optional.empty();

        var name = Options.toNative(node.apply("name")).flatMap(Attribute::asString).orElse("");
        var renderedContent = Options.toNative(node.apply("content")).flatMap(attribute1 -> Options.toNative(attribute1.asNode()))
                .flatMap(content -> Options.toNative(content.apply("children")))
                .flatMap(attribute -> Options.toNative(attribute.asListOfNodes()))
                .orElse(Collections.emptyList())
                .stream()
                .map(node -> Options.toNative(node.apply("value"))).flatMap(Optional::stream)
                .map(Attribute::asString)
                .flatMap(Optional::stream)
                .collect(Collectors.joining());

        return Optional.of("class def " + name + "() => {" + renderedContent + "}");
    }
}