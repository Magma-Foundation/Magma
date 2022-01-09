package com.meti.compile;

public class AttributeException extends CompileException {
    public AttributeException(Attribute.Type type) {
        this("Unknown attribute: " + type);
    }

    public AttributeException(String message) {
        super(message);
    }
}
