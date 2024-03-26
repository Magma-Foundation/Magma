package com.meti.magma;

import com.meti.node.Attribute;
import com.meti.node.Node;
import com.meti.stage.Renderer;

import java.util.Optional;

public class StatementRenderer implements Renderer {
    private final Node node;

    public StatementRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Optional<String> render() {
        if(node.is("statement")) {
            var indent = node.apply("indent").flatMap(Attribute::asInt).orElseThrow();
            var value = node.apply("value").flatMap(Attribute::asNode)
                    .flatMap(node -> node.apply("value"))
                    .flatMap(Attribute::asString)
                    .orElseThrow();

            return Optional.of("\n" + "\t".repeat(indent) + value);
        } else {
            return Optional.empty();
        }
    }
}
