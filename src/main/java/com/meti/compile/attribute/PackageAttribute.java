package com.meti.compile.attribute;

import com.meti.collect.JavaList;
import com.meti.collect.StreamException;
import com.meti.compile.node.Node;
import com.meti.source.Packaging;

import java.util.stream.Stream;

public record PackageAttribute(Packaging packaging_) implements Attribute {
    @Override
    public Packaging asPackaging() {
        return packaging_;
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
