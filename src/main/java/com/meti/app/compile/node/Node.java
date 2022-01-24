package com.meti.app.compile.node;

import com.meti.api.collect.stream.EmptyStream;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;

import java.util.stream.Stream;

public interface Node {
    default Attribute apply(Attribute.Type type) throws AttributeException {
        throw new AttributeException("Node had no attributes.");
    }

    @Deprecated
    default Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return Stream.empty();
    }

    default com.meti.api.collect.stream.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return new EmptyStream<>();
    }

    boolean is(Type type);

    default Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        throw new AttributeException("Node does not have attribute to replace of: " + type);
    }

    enum Type {
        Input, Block, Implementation, Declaration, Integer, Structure, Primitive, Import, Extern, Variable, Boolean, Abstraction, Unary, Empty, If, String, Invocation, Line, Implicit, Reference, Initialization, Function, Else, Binary, Cache, Output, Return
    }

    interface Builder<T extends Builder<T>> {
        Node build();

        T merge(T other);
    }
}
