package com.meti.app.compile.feature;

import com.meti.app.compile.node.Input;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.InputAttribute;
import com.meti.app.compile.node.attribute.NodeAttribute;

public record EmptyField(Input name, Node type) implements Node {
    @Override
    public boolean is(Type type) {
        return type == Type.Field;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Name -> new InputAttribute(name);
            case Type -> new NodeAttribute(this.type);
            default -> throw new AttributeException(type.name());
        };
    }
}
