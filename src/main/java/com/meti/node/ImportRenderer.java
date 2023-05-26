package com.meti.node;

import com.meti.node.Attribute;
import com.meti.node.Node;
import com.meti.node.Renderer;

import java.util.Optional;

public class ImportRenderer implements Renderer {
    private final Node node;

    public ImportRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Optional<String> render() {
        if(node.is("import")) {
            return Optional.of("import " + node.apply("value")
                    .flatMap(Attribute::asString)
                    .orElseThrow());
        } else {
            return Optional.empty();
        }
    }
}
