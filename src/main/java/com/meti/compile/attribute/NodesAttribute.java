package com.meti.compile.attribute;

import com.meti.compile.node.Node;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public record NodesAttribute(Collection<Node> values) implements Attribute {
    @Override
    public Stream<Node> asStreamOfNodes() {
        return values.stream();
    }
}
