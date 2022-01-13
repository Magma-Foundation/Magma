package com.meti.compile.attribute;

import com.meti.collect.JavaList;
import com.meti.collect.StreamException;
import com.meti.compile.node.Node;

import java.util.stream.Stream;

public record IntegerAttribute(int value) implements Attribute {

    @Override
    public int asInteger() {
        return value;
    }

    @Override
    public com.meti.collect.Stream<Node> asStreamOfNodes() throws StreamException {
        return asStreamOfNodes1()
                .reduce(new JavaList<Node>(), JavaList::add, (previous, next) -> next)
                .stream();
    }

    private Stream<Node> asStreamOfNodes1() {
        throw new UnsupportedOperationException();
    }
}
