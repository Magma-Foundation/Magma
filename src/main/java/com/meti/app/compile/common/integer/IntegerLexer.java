package com.meti.app.compile.common.integer;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.lex.Lexer;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.text.Input;

public record IntegerLexer(Input text) implements Lexer {
    @Override
    public Option<Node> lex() {
        return text.map(this::toInteger).map(IntegerNode::new);
    }

    private Option<Integer> toInteger(String s) {
        try {
            return new Some<>(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return new None<>();
        }
    }
}
