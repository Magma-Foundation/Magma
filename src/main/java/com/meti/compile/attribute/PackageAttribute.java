package com.meti.compile.attribute;

import com.meti.collect.JavaList;
import com.meti.compile.node.Node;
import com.meti.source.Packaging;

import java.util.stream.Collectors;

public record PackageAttribute(Packaging packaging_) implements Attribute {
    @Override
    public Packaging asPackaging() {
        return packaging_;
    }

    @Override
    public com.meti.collect.Stream<Node> asStreamOfNodes1() throws AttributeException {
        return new JavaList<>(asStreamOfNodes().collect(Collectors.toList())).stream();
    }
}
