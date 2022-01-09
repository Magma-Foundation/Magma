package com.meti.compile;

import com.meti.compile.node.Content;
import com.meti.compile.node.Input;
import com.meti.compile.node.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record IntegerLexer(Input input) implements Lexer {
    @Override
    public Option<Node> lex() {
        try {
            Integer.parseInt(input.getInput());
            return new Some<>(new Content(new Input(input.getInput())));
        } catch (NumberFormatException e) {
            return new None<>();
        }
    }
}
