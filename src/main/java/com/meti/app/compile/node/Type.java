package com.meti.app.compile.node;

import com.meti.app.compile.node.attribute.AttributeException;

public interface Type extends Node {
    default boolean isAssignableTo(Type other) throws AttributeException {
        return other.is(Role.Primitive) || this.equals(other);
    }

    default Node reduce() throws AttributeException {
        return this;
    }
}
