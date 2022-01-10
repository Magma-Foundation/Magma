package com.meti.compile.common;

import com.meti.compile.node.Node;

import java.util.Set;

public class Abstraction extends Function {
    public Abstraction(Node identity, Set<Node> parameters) {
        super(identity, parameters);
    }

    @Override
    protected Node complete(Node node, Set<Node> parameters) {
        return new Abstraction(node, parameters);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Abstraction;
    }
}
