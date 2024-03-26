package com.meti;

import com.meti.node.Attribute;
import com.meti.node.Content;
import com.meti.node.Node;
import com.meti.stage.Renderer;

import java.util.Optional;

public class FieldRenderer implements Renderer {
    private final Node node;

    public FieldRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Optional<String> render() {
        if (node.is("field")) {
            var parentString = node.apply("parent")
                    .flatMap(Attribute::asNode)
                    .flatMap(parent -> parent.apply(Content.VALUE))
                    .flatMap(Attribute::asString)
                    .orElseThrow()
                    .strip();

            var memberString = node.apply("member")
                    .flatMap(Attribute::asString)
                    .orElseThrow();

            return Optional.of(parentString + "." + memberString);
        } else {
            return Optional.empty();
        }
    }
}
