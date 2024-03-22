package com.meti.compile.magma;

import com.meti.collect.option.Option;
import com.meti.compile.node.Attribute;
import com.meti.compile.node.Node;
import com.meti.compile.node.Renderer;
import com.meti.compile.scope.ReturnLexer;

import static com.meti.collect.option.None.None;

public class ReturnRenderer implements Renderer {
    private final Node node;

    public ReturnRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Option<String> render() {
        if(!node.is(ReturnLexer.Id.inner())) return None();

        return node.apply("value")
                .flatMap(Attribute::asNode)
                .flatMap(content -> content.apply("value"))
                .flatMap(Attribute::asString)
                .map(value -> "return " + value);
    }
}
