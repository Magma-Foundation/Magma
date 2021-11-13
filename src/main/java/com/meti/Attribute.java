package com.meti;

public interface Attribute {
    default int asInt() throws AttributeException {
        throw new AttributeException("Not an int.");
    }
}
