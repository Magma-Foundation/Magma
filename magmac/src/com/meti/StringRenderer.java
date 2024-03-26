package com.meti;

import com.meti.node.Attribute;
import com.meti.node.Node;
import com.meti.stage.Renderer;

import java.util.Optional;

public class StringRenderer implements Renderer {
    private final Node node;

    public StringRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Optional<String> render() {
        if(node.is("string")) {
            return node.apply("value")
                    .flatMap(Attribute::asString)
                    .map(value -> "\"" + value + "\"");
        } else {
            return Optional.empty();
        }
    }
}
