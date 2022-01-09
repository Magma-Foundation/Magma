package com.meti.compile;

public record Content(Input input) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if(type == Attribute.Type.Value) return new InputAttribute(getValueAsString());
        throw new AttributeException("No attribute exists of name: " + type);
    }

    private Input getValueAsString() {
        return input;
    }

    @Override
    public boolean is(Type type) {
        return type == Node.Type.Content;
    }
}
