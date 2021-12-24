package com.meti.attribute;

import com.meti.Input;
import com.meti.Node;

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
