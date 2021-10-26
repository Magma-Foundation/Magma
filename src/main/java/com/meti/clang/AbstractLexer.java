package com.meti.clang;

import com.meti.compile.Input;
import com.meti.compile.Node;

public abstract class AbstractLexer extends AbstractProcessor<Node> {
    protected final Input input;

    public AbstractLexer(Input input) {
        this.input = input;
    }
}
