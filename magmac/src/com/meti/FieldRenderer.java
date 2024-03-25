package com.meti;

import com.meti.java.FieldLexer;
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
        if (node.is(FieldLexer.ID)) {
            var parentString = node.apply(FieldLexer.PARENT)
                    .flatMap(Attribute::asNode)
                    .flatMap(parent -> parent.apply(Content.VALUE))
                    .flatMap(Attribute::asString)
                    .orElseThrow();

            var memberString = node.apply(FieldLexer.MEMBER)
                    .flatMap(Attribute::asString)
                    .orElseThrow();

            return Optional.of(parentString + "." + memberString);
        } else {
            return Optional.empty();
        }
    }
}
