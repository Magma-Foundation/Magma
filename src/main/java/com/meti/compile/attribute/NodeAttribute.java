package com.meti.compile.attribute;

import com.meti.collect.JavaList;
import com.meti.compile.node.Node;

import java.util.stream.Collectors;

public record NodeAttribute(Node node) implements Attribute {
    @Override
    public Node asNode() {
        return node;
    }

    @Override
    public com.meti.collect.Stream<Node> asStreamOfNodes1() throws AttributeException {
        return new JavaList<>(asStreamOfNodes().collect(Collectors.toList())).stream();
    }
}
