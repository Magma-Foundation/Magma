package com.meti.node;

import java.util.Optional;

public class JavaClassRenderer implements Renderer {
    private final Node node;

    public JavaClassRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Optional<String> render() {
        if (!node.is("class")) return Optional.empty();

        var name = node.apply("name")
                .flatMap(Attribute::asString)
                .orElseThrow();

        var body = node.apply("body")
                .flatMap(Attribute::asNode)
                .flatMap(node -> node.apply("value"))
                .flatMap(Attribute::asString)
                .orElseThrow();

        return Optional.of("class " + name + " " + body);
    }
}
