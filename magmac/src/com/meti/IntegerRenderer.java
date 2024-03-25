package com.meti;

import com.meti.lex.IntegerLexer;
import com.meti.node.Attribute;
import com.meti.node.Node;

import java.util.Optional;

public record IntegerRenderer(Node node1) implements Rendererer {
    @Override
    public Optional<String> render() {
        if(node1().is(IntegerLexer.Id)) {
            return node1().apply(IntegerLexer.VALUE)
                    .flatMap(Attribute::asInt)
                    .map(String::valueOf);
        }
        return Optional.empty();
    }
}