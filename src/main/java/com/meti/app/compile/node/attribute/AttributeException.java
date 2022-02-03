package com.meti.app.compile.node.attribute;

import com.meti.app.compile.node.Node;
import com.meti.app.compile.stage.CompileException;

public class AttributeException extends CompileException {
    public AttributeException(Node node, Attribute.Category category) {
        this("Type '%s' was not present in node:\n-----\n%s\n-----\n".formatted(category, node));
    }

    public AttributeException(String message) {
        super(message);
    }

    public AttributeException(Attribute.Category category) {
        this("Unknown attribute: " + category);
    }

    public AttributeException(Attribute.Group group) {
        this("Unknown attribute group: " + group);
    }

    public AttributeException(Exception cause) {
        super(cause);
    }
}
