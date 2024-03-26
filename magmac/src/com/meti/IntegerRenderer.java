package com.meti;

import com.meti.java.IntegerLexer;
import com.meti.node.Attribute;
import com.meti.node.Node;
import com.meti.stage.Renderer;

import java.util.Optional;

public record IntegerRenderer(Node node1) implements Renderer {
    @Override
    public Optional<String> render() {
        if(node1().is(IntegerLexer.Id)) {
            return node1().apply(IntegerLexer.VALUE)
                    .flatMap(Attribute::asString)
                    .map(String::valueOf);
        }
        return Optional.empty();
    }
}