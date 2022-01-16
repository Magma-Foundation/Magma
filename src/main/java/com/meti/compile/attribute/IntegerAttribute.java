package com.meti.compile.attribute;

import com.meti.collect.JavaList;
import com.meti.compile.node.Node;

import java.util.stream.Collectors;

public record IntegerAttribute(int value) implements Attribute {

    @Override
    public int asInteger() {
        return value;
    }

    @Override
    public com.meti.collect.Stream<Node> asStreamOfNodes1() throws AttributeException {
        return new JavaList<>(asStreamOfNodes().collect(Collectors.toList())).stream();
    }
}
