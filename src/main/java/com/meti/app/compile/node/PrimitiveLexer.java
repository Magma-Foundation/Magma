package com.meti.app.compile.node;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.text.Text;

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
