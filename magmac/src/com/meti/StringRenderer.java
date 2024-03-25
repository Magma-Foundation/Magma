package com.meti;

import com.meti.lex.StringLexer;
import com.meti.node.Attribute;
import com.meti.node.Node;
import com.meti.stage.Rendererer;

import java.util.Optional;

public class StringRenderer implements Rendererer {
    private final Node node;

    public StringRenderer(Node node) {
        this.node = node;
    }

    @Override
    public Optional<String> render() {
        if(node.is(StringLexer.ID)) {
            return node.apply(StringLexer.VALUE)
                    .flatMap(Attribute::asString)
                    .map(value -> "\"" + value + "\"");
        } else {
            return Optional.empty();
        }
    }
}
