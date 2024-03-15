package com.meti.compile.procedure;

import com.meti.compile.node.Content;
import com.meti.compile.node.Node;
import com.meti.java.JavaString;

import java.util.List;

public final class ConstructionLexer extends InvocationLexer {
    public ConstructionLexer(JavaString javaString) {
        super(javaString);
    }

    @Override
    protected Node create(Content caller, List<Content> list) {
        return new ConstructionNode(caller, list);
    }

    @Override
    protected String prefix() {
        return "new ";
    }
}