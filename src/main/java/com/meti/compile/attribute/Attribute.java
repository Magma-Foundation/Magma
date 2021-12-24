package com.meti.compile.attribute;

import com.meti.compile.Input;
import com.meti.compile.Node;

public interface Attribute {
    default Input asInput() throws AttributeException {
        throw new AttributeException("Not input.");
    }

    default int asInteger() throws AttributeException {
        throw new AttributeException("Not an integer.");
    }

    default Node asNode() throws AttributeException {
        throw new AttributeException("Not a node.");
    }
}
