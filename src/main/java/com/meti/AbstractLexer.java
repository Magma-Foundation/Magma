package com.meti;

public abstract class AbstractLexer implements Lexer {
    protected final Input input;

    public AbstractLexer(Input input) {
        this.input = input;
    }
}
