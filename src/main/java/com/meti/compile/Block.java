package com.meti.compile;

import com.meti.Attribute;
import com.meti.InputAttribute;

import java.util.List;
import java.util.stream.Stream;

class Block implements Node {
    private final List<Node> values;

    public Block(List<Node> values) {
        this.values = values;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if(type == Attribute.Type.Value) return new InputAttribute(getValueAsString());
        throw new AttributeException("No attribute exists of name: " + type);
    }

    @Override
    public Stream<Node> getLinesAsStream() {
        return values.stream();
    }

    private Input getValueAsString() throws AttributeException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean is(Type type) {
        return type == Node.Type.Block;
    }

    @Override
    public Node withLines(List<Node> values) {
        return new Block(values);
    }
}
