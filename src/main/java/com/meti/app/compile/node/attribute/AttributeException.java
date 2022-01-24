package com.meti.app.compile.node.attribute;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.stage.CompileException;

public class AttributeException extends CompileException {
    public AttributeException(Node node, Attribute.Type type) {
        this("Type '" + type + "' was not present in node: '" + node + "'.");
    }

    public AttributeException(Attribute.Type type) {
        this("Unknown attribute: " + type);
    }

    public AttributeException(String message) {
        super(message);
    }

    public AttributeException(Attribute.Group group) {
        this("Unknown attribute group: " + group);
    }

    public AttributeException(Exception cause) {
        super(cause);
    }
}
