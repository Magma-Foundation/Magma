package com.meti.node;

import com.meti.Input;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public class BooleanLexer implements Lexer {
    private final Input input;

    public BooleanLexer(Input input) {
        this.input = input;
    }

    @Override
    public Option<Node> lex() {
        if (input.contains("true")) {
            return new Some<>(Boolean.True);
        } else if (input.contains("false")) {
            return new Some<>(Boolean.False);
        } else {
            return new None<>();
        }
    }
}
