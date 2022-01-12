package com.meti.compile.node;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;

import java.util.stream.Stream;

public interface Node {
    default Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
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
        Block, Implementation, Field, Integer, Structure, Primitive, Import, Extern, Variable, Boolean, Abstraction, Unary, Empty, Condition, If, String, Invocation, Line, Implicit, Reference, Return
    }
}
