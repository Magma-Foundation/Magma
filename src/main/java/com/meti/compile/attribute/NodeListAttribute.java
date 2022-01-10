package com.meti.compile.attribute;

import com.meti.compile.node.Node;

import java.util.List;
import java.util.stream.Stream;

public record NodeListAttribute(List<Node> values) implements Attribute {
    @Override
    public Stream<Node> asStreamOfNodes() {
        return values.stream();
    }
}
