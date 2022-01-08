package com.meti.compile;

record Return(Node value) implements Node {
    @Override
    public Node getValueAsNode() {
        return value;
    }

    @Override
    public String getValueAsString() throws AttributeException {
        throw new AttributeException("Node has no value as string.");
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Return;
    }

    @Override
    public Node withValue(Node child) {
        return new Return(child);
    }
}
