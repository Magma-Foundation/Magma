package com.meti.app.magma;

import com.meti.app.clang.AbstractProcessor;
import com.meti.app.compile.CompileException;
import com.meti.app.compile.node.Input;
import com.meti.app.compile.node.Node;

public class FunctionLexer extends AbstractProcessor<Node> {
    private final Input input;

    public FunctionLexer(Input input) {
        this.input = input;
    }

    @Override
    protected boolean validate() {
        return input.startsWithString("def ");
    }

    @Override
    protected Node processDefined() throws CompileException {
        return new Node() {
            @Override
            public boolean is(Type type) {
                return type == Type.Function;
            }
        };
    }
}
