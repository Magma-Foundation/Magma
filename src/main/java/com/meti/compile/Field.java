package com.meti.compile;

import com.meti.Attribute;
import com.meti.InputAttribute;

public record Field(Input name, Node type) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Name -> new InputAttribute(name);
            case Type -> new NodeAttribute(this.type);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Field;
    }
}
