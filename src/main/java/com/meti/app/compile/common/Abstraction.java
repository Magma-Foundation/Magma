package com.meti.app.compile.common;

import com.meti.api.collect.java.JavaList;
import com.meti.app.compile.node.Node;

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
