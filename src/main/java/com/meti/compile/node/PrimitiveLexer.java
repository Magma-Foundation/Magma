package com.meti.compile.node;

import com.meti.compile.lex.Lexer;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record PrimitiveLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        var values = Primitive.values();
        for (Primitive value : values) {
            if (text.computeTrimmed().equals(value.name())) {
                return new Some<>(value);
            }
        }
        return new None<>();
    }
}
