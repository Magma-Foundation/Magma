package com.meti.app.compile.common;

import com.meti.api.collect.java.List;
import com.meti.app.compile.node.Node;

public class Abstraction extends Function {
    public Abstraction(Node identity, List<Node> parameters) {
        super(identity, parameters);
    }

    @Override
    protected Node complete(Node node, List<Node> parameters) {
        return new Abstraction(node, parameters);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Abstraction;
    }
}
