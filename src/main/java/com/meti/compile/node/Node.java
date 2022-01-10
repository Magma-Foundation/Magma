package com.meti.compile.node;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;

import java.util.stream.Stream;

public interface Node {
    default Stream<Attribute.Type> apply(Attribute.Group group) {
        return Stream.empty();
    }

    default Attribute apply(Attribute.Type type) throws AttributeException {
        throw new AttributeException("Node had no attributes.");
    }

    boolean is(Type type);

    default Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        throw new AttributeException("Node does not have attribute to replace of: " + type);
    }

    enum Type {
        Content,
        Block, Function, Field, Integer, Structure, Primitive, Import, Extern, Return
    }
}
