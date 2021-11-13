package com.meti;

public abstract class FilteredLexer extends FilteredProcessor<Node> implements Lexer {
    protected final Input input;

    public FilteredLexer(Input input) {
        this.input = input;
    }
}
