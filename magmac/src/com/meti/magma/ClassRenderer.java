package com.meti.magma;

import com.meti.node.Attribute;
import com.meti.node.Content;
import com.meti.node.Node;
import com.meti.stage.Renderer;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClassRenderer implements Renderer {
    private final Node node;

    public ClassRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Optional<String> render() {
        if (!node.is("class")) return Optional.empty();

        var flags = node.apply("flags")
                .flatMap(Attribute::asListOfStrings)
                .orElse(Collections.emptyList());

        var body = node.apply("body").flatMap(Attribute::asListOfNodes)
                .map(node -> node.stream().map(element -> element.apply(Content.VALUE)
                                .flatMap(Attribute::asString))
                        .flatMap(Optional::stream)
                        .collect(Collectors.toList()));

        var name = node.apply("name").flatMap(Attribute::asString).orElseThrow();

        var flagString = flags.contains("public") ? "export " : "";
        String bodyString;
        if (body.isEmpty()) {
            bodyString = "{}";
        } else {
            var bodyArray = body.orElseThrow();
            if (bodyArray.isEmpty()) {
                bodyString = "{}";
            } else {
                bodyString = "{" + String.join("", bodyArray) + "\n" + "}";
            }
        }
        return Optional.of( flagString + "class def " + name + "() => " + bodyString);
    }
}
