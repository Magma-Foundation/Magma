package com.meti.compile.common;

import com.meti.collect.JavaList;
import com.meti.compile.node.Node;

public class Abstraction extends Function {
    public Abstraction(Node identity, JavaList<Node> parameters) {
        super(identity, parameters);
    }

    @Override
    protected Node complete(Node node, JavaList<Node> parameters) {
        return new Abstraction(node, parameters);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Abstraction;
    }
}
