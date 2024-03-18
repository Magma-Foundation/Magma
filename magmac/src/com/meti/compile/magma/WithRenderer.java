package com.meti.compile.magma;

import com.meti.collect.option.Option;
import com.meti.compile.node.Attribute;
import com.meti.compile.node.Node;
import com.meti.compile.node.Renderer;

import static com.meti.collect.option.None.None;

public class WithRenderer implements Renderer {
    private final Node node;

    public WithRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Option<String> render() {
        if(!node.is("with")) return None();

        return node.apply("type")
                .flatMap(Attribute::asString)
                .map(type -> "with " + type.inner());
    }
}
