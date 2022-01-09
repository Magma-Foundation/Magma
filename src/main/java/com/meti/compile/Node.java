package com.meti.compile;

import com.meti.Attribute;

import java.util.List;
import java.util.stream.Stream;

public interface Node {
    default Attribute apply(Attribute.Type type) throws AttributeException {
        throw new AttributeException("Node had no attributes.");
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
        Block, Function, Field, Return
    }
}
