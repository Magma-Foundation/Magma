package com.meti;

import com.meti.compile.AttributeException;
import com.meti.compile.Input;
import com.meti.compile.Node;

public interface Attribute {
    default Input asInput() throws AttributeException {
        throw new AttributeException("Not input.");
    }

    default Node asNode() throws AttributeException {
        throw new AttributeException("Not a node.");
    }

    enum Type {
        Value,
        Identity,
        Type,
        Name
    }
}
