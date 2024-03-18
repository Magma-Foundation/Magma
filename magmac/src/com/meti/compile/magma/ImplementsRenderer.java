package com.meti.compile.magma;

import com.meti.collect.option.Option;
import com.meti.compile.node.Attribute;
import com.meti.compile.node.Node;
import com.meti.compile.node.Renderer;

public class ImplementsRenderer implements Renderer {
    private final Node node;

    public ImplementsRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Option<String> render() {
        return node.apply("type")
                .flatMap(Attribute::asString)
                .map(type -> "implements " + type.inner());
    }
}
