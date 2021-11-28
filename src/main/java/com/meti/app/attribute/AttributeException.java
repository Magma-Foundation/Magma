package com.meti.app.attribute;

import com.meti.app.CompileException;

public class AttributeException extends CompileException {
    public AttributeException(String message) {
        super(message);
    }

    public AttributeException(Attribute.Type type) {
        super("Unknown type: " + type);
    }

    public AttributeException(String message, Throwable cause) {
        super(message, cause);
    }
}
