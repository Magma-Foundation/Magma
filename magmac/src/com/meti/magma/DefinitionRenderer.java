package com.meti.magma;

import com.meti.node.Attribute;
import com.meti.node.Content;
import com.meti.node.Node;
import com.meti.stage.Renderer;

import java.util.Optional;

public class DefinitionRenderer implements Renderer {
    private final Node node;

    public DefinitionRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Optional<String> render() {
        if (!node.is("definition")) return Optional.empty();

        var indent = node.apply("indent").flatMap(Attribute::asInt).orElseThrow();
        var flags = node.apply("flags").flatMap(Attribute::asListOfStrings).orElseThrow();
        var name = node.apply("name").flatMap(Attribute::asString).orElseThrow();
        var type = node.apply("type").flatMap(Attribute::asString).orElseThrow();
        var value = node.apply("value").flatMap(Attribute::asNode)
                .flatMap(valueNode -> valueNode.apply(Content.VALUE))
                .flatMap(Attribute::asString)
                .orElseThrow()
                .strip();

        return Optional.of("\n" + "\t".repeat(indent) + String.join(" ", flags) + " " + name + " : " + type + " = " + value + ";");
    }
}
