package com.meti.compile.attribute;

import com.meti.collect.JavaList;
import com.meti.collect.StreamException;
import com.meti.compile.node.Node;

public record NodesAttribute(JavaList<Node> values) implements Attribute {
    @Override
    public com.meti.collect.Stream<Node> asStreamOfNodes() throws StreamException {
        return values.stream();
    }
}
