package com.meti.compile.common.integer;

import com.meti.compile.lex.Lexer;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

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
