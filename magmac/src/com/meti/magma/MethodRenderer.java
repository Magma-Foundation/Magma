package com.meti.magma;

import com.meti.node.Attribute;
import com.meti.node.Content;
import com.meti.node.Node;
import com.meti.stage.Renderer;

import java.util.Optional;

public record MethodRenderer(Node root) implements Renderer {
    @Override
    public Optional<String> render() {
        if (!root.is("method")) {
            return Optional.empty();
        }

        var actualBody = root().apply("body");
        var indent = root().apply("indent").flatMap(Attribute::asInt).orElseThrow();
        var name = root().apply("name").flatMap(Attribute::asString).orElseThrow();
        var type = root().apply("type").flatMap(Attribute::asString).orElseThrow();

        String wrappedBody;
        if (actualBody.isEmpty()) {
            wrappedBody = "{}";
        } else {
            var bodyContent = actualBody.get().asNode()
                    .flatMap(node -> node.apply(Content.VALUE))
                    .flatMap(Attribute::asString)
                    .orElseThrow();

            wrappedBody = "{" + bodyContent + "\n" + "\t".repeat(indent) + "}";
        }


        return Optional.of("\n" + "\t".repeat(indent) + "def " + name + "() : " + type + " => " + wrappedBody);
    }
}