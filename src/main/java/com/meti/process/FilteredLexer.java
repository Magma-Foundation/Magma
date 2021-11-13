package com.meti.process;

import com.meti.Input;
import com.meti.node.Node;

public abstract class FilteredLexer extends FilteredProcessor<Node> implements Lexer {
    protected final Input input;

    public FilteredLexer(Input input) {
        this.input = input;
    }
}
