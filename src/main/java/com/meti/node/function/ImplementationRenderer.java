package com.meti.node.function;

import com.meti.node.Attribute;
import com.meti.node.Content;
import com.meti.node.Node;
import com.meti.node.Renderer;

import java.util.Optional;

public class ImplementationRenderer implements Renderer {
    private final Node node;

    public ImplementationRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Optional<String> render() {
        if (!node.is("implementation")) return Optional.empty();

        var name = node.applyUnsafe("name").asString();
        var body = node.applyUnsafe("body")
                .asNodeUnsafe()
                .applyUnsafe(Content.Key.Value)
                .asStringUnsafe();

        return Optional.of("def " + name + "() => " + body);
    }
}
