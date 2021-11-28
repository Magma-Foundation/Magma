package com.meti.app.node;

import java.util.List;

public class Sequence extends Container {
    public Sequence(List<Node> children) {
        super(children);
    }

    @Override
    protected Node complete(List<Node> nodes) {
        return new Sequence(nodes);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Sequence;
    }
}
