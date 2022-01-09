package com.meti.compile;

import com.meti.Attribute;

record Function(Input name, Content returnType, Content value) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Identity -> new NodeAttribute(new Field(name, returnType));
            case Value -> new NodeAttribute(value);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Function;
    }

    @Override
    public Node withValue(Node child) {
        return new Function(name, returnType, value);
    }
}
