package com.meti;

import java.util.List;

public interface Attribute {
    default String asString() throws AttributeException {
        throw new AttributeException("Not a string.");
    }

    default List<? extends Node> asNodeList() throws AttributeException {
        throw new AttributeException("Not a list of nodes.");
    }

    default Node asNode() throws AttributeException {
        throw new AttributeException("Not a node.");
    }

    enum Group {
        Name,
        Value,
        Members,
        Type,
        Body,
        Identity
    }
}
