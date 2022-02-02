package com.meti.app.compile.node;

import com.meti.app.compile.node.attribute.AttributeException;

public interface Type extends Node {
    default boolean isAssignableTo(Type other) throws AttributeException {
        return this.equals(other);
    }

    default boolean isInstanceOf(Type other) {
        return this.equals(other);
    }

    default Type reduce() throws AttributeException {
        return this;
    }
}
