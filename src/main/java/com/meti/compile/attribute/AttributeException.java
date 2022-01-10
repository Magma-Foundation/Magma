package com.meti.compile.attribute;

import com.meti.compile.CompileException;

public class AttributeException extends CompileException {
    public AttributeException(Attribute.Type type) {
        this("Unknown attribute: " + type);
    }

    public AttributeException(String message) {
        super(message);
    }

    public AttributeException(Attribute.Group group) {
        this("Unknown attribute group: " + group);
    }
}
