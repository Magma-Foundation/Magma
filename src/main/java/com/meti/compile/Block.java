package com.meti.compile;

import java.util.List;
import java.util.stream.Stream;

class Block implements Node {
    private final List<Node> values;

    public Block(List<Node> values) {
        this.values = values;
    }

    @Override
    public Stream<Node> getLinesAsStream() {
        return values.stream();
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Block;
    }

    @Override
    public Node withLines(List<Node> values) {
        return new Block(values);
    }
}
