package com.meti.app.compile.node;

public interface Type extends Node {
    default boolean isAssignableTo(com.meti.app.compile.node.Type other) {
        return this.equals(other);
    }
}
