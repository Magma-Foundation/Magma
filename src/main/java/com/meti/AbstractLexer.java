package com.meti;

public abstract class AbstractLexer implements Processor<Node, LexException> {
    protected final Input input;

    public AbstractLexer(Input input) {
        this.input = input;
    }
}
