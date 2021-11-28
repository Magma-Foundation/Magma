package com.meti.app.process;

import com.meti.app.Input;
import com.meti.app.node.Node;

public abstract class FilteredLexer extends FilteredProcessor<Node> implements Lexer {
    protected final Input input;

    public FilteredLexer(Input input) {
        this.input = input;
    }
}
