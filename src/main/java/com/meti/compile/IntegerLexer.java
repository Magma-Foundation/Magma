package com.meti.compile;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record IntegerLexer(Input input) implements Lexer {
    @Override
    public Option<Node> lex() {
        try {
            Integer.parseInt(input.getInput());
            return new Some<>(new Content(input.getInput()));
        } catch (NumberFormatException e) {
            return new None<>();
        }
    }
}
