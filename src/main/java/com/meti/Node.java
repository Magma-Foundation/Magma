package com.meti;

import java.util.List;
import java.util.stream.Stream;

public interface Node {
    default Node getValueAsNode() throws AttributeException {
        throw new AttributeException("No value is present of type Node.");
    }

    default String getValueAsString() throws AttributeException {
        throw new AttributeException("Node does not have value as string.");
    }

    default Stream<Node> getLinesAsStream() throws AttributeException {
        throw new AttributeException("No values are present.");
    }

    boolean is(Type type);

    default Node withLines(List<Node> values) {
        return this;
    }

    default Node withValue(Node child) {
        return this;
    }

    enum Type {
        Content,
        Block, Return
    }
}
