package com.meti.clang;

import com.meti.Input;
import com.meti.feature.Node;

public abstract class AbstractLexer extends AbstractProcessor<Node> {
    protected final Input input;

    public AbstractLexer(Input input) {
        this.input = input;
    }
}
