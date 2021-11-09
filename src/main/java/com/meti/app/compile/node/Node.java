package com.meti.app.compile.node;

import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;

import java.util.stream.Stream;

public interface Node {
    default Attribute apply(Attribute.Type type) throws AttributeException {
        throw new AttributeException("Unknown type:" + type);
    }

    boolean is(Node.Type type);

    default Stream<Attribute.Type> stream(Attribute.Group group) {
        return Stream.empty();
    }

    default Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return this;
    }

    enum Type {
        Integer, Return, Block, Content,
        Function, Field, Import
    }
}
