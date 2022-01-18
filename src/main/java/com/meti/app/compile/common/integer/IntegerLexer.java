package com.meti.app.compile.common.integer;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Text;

public record IntegerLexer(Text text) implements Lexer {
    @Override
    public Option<Node> lex() {
        try {
            var value = Integer.parseInt(text.computeTrimmed());
            return new Some<>(new IntegerNode(value));
        } catch (NumberFormatException e) {
            return new None<>();
        }
    }
}
