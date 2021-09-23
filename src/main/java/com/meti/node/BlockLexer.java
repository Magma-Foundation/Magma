package com.meti.node;

import com.meti.Input;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public class BlockLexer implements Lexer {
    private final Input input;

    public BlockLexer(Input input) {
        this.input = input;
    }

    @Override
    public Option<Node> lex() {
        return input.contains("{}") ? new Some<>(new Block()) : new None<>();
    }
}
