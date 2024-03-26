package com.meti.magma;

import com.meti.node.Attribute;
import com.meti.node.Content;
import com.meti.node.Node;
import com.meti.stage.Renderer;

import java.util.Optional;
import java.util.stream.Collectors;

public record MethodRenderer(Node root) implements Renderer {
    @Override
    public Optional<String> render() {
        if (!root.is("method")) {
            return Optional.empty();
        }

        var actualBody = root().apply("body");

        var indent = root().apply("indent").flatMap(Attribute::asInt).orElse(0);

        var name = root().apply("name").flatMap(Attribute::asString).orElseThrow();
        var type = root.apply("type")
                .flatMap(Attribute::asNode)
                .flatMap(node -> node.apply("value"))
                .flatMap(Attribute::asString)
                .orElseThrow();

        String wrappedBody;
        if (actualBody.isEmpty()) {
            wrappedBody = "{}";
        } else {
            var actualList = actualBody.get().asListOfNodes().orElseThrow();
            if(actualList.isEmpty()) {
                wrappedBody = "{}";
            } else {
                var bodyContent = actualList.stream()
                        .map(element -> element.apply(Content.VALUE))
                        .map(option -> option.flatMap(Attribute::asString))
                        .flatMap(Optional::stream)
                        .collect(Collectors.joining());

                wrappedBody = "{" + bodyContent + "\n" + "\t".repeat(indent) + "}";
            }
        }


        return Optional.of("\n" + "\t".repeat(indent) + "def " + name + "() : " + type + " => " + wrappedBody);
    }
}