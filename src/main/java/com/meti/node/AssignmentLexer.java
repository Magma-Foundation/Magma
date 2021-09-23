package com.meti.node;

import com.meti.Input;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public class AssignmentLexer implements Lexer {
    private final Input input;

    public AssignmentLexer(Input input) {
        this.input = input;
    }

    @Override
    public Option<Node> lex() {
        var separator = input.firstIndexOfChar('=');
        if (separator == -1) {
            return new None<>();
        } else {
            var name = input.slice(0, separator);
            var value = input.slice(separator + 1);
            return new Some<>(new Assignment(name, value));
        }
    }
}
