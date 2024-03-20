package com.meti.compile.magma;

import com.meti.collect.option.None;
import com.meti.collect.option.Option;
import com.meti.compile.java.IntegerLexer;
import com.meti.compile.node.Attribute;
import com.meti.compile.node.Node;
import com.meti.compile.node.Renderer;

public class IntegerRenderer implements Renderer {
    private final Node node;

    public IntegerRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Option<String> render() {
        if (node.is(IntegerLexer.Id)) {
            return node.apply("value")
                    .flatMap(Attribute::asInteger)
                    .map(String::valueOf);
        } else {
            return None.None();
        }
    }
}
