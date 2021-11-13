package com.meti;

import java.util.stream.Stream;

public interface Node {
    default Attribute apply(Attribute.Type type) throws AttributeException {
        throw new AttributeException("No attribute exists for type: " + type);
    }

    boolean is(Type type);

    default Stream<Attribute.Type> stream(Attribute.Group group) {
        return Stream.empty();
    }

    default Node with(Attribute.Type type, Attribute value) throws AttributeException {
        return this;
    }

    enum Type {
        Block,
        Content,
        Function,
        Int,
        Primitive,
        Return,
        Sequence,
    }
}
