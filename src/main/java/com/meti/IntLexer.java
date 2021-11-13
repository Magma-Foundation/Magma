package com.meti;

import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record IntLexer(Input input) implements Lexer {
    @Override
    public Option<Node> process() throws CompileException {
        try {
            return new Some<>(new IntegerNode(Integer.parseInt(input.value())));
        } catch (NumberFormatException e) {
            return new None<>();
        }
    }
}
