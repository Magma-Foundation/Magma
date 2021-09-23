package com.meti;

public interface Attribute {
    default Node asNode() throws ApplicationException {
        throw new ApplicationException("Not a node.");
    }

    default String asString() throws ApplicationException {
        throw new ApplicationException("Not a string.");
    }

    enum Type {
        Name,
        Type,
        Value
    }
}
