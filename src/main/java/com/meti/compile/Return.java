package com.meti.compile;

record Return(Node value) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if(type == Attribute.Type.Value) return new NodeAttribute(value);
        throw new AttributeException(type);
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return new Return(attribute.asNode());
    }

    @Override
    public boolean is(Type type) {
        return type == Node.Type.Return;
    }
}
