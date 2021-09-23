package com.meti.node;

import com.meti.Input;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public class IfLexer implements Lexer {
    private final Input input;

    public IfLexer(Input input) {
        this.input = input;
    }

    @Override
    public Option<Node> lex() {
        if (input.startsWithString("if(")) {
            var paramStart = input.firstIndexOfChar('(');
            var paramEnd = input.firstIndexOfChar(')');
            var condition = new Content(input.slice(paramStart + 1, paramEnd));
            return new Some<>(new IfNode(condition));
        } else {
            return new None<>();
        }
    }
}
