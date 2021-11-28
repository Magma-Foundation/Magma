package com.meti.app.node;

import java.util.List;

public final class Block extends Container {
    public Block(List<? extends Node> children) {
        super(children);
    }

    @Override
    protected Node complete(List<Node> nodes) {
        return new Block(nodes);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Block;
    }
}
