package com.meti.compile;

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
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return new Field(name, attribute.asNode());
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Field;
    }
}
