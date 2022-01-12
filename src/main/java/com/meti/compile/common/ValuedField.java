package com.meti.compile.common;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;

import java.util.Set;
import java.util.stream.Stream;

public class ValuedField extends Field {
    private final Node value;

    public ValuedField(Set<Flag> flags, Text name, Node type, Node value) {
        super(flags, name, type);
        this.value = value;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return type == Attribute.Type.Value ? new NodeAttribute(value) : super.apply(type);
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return type == Attribute.Type.Value ? new ValuedField(flags, name, this.type, attribute.asNode()) : this;
    }

    @Override
    protected Field complete(Node type) {
        return new ValuedField(flags, name, type, value);
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Node ? Stream.of(Attribute.Type.Value) : Stream.empty();
    }

    @Override
    public boolean is(Type type) {
        return type == Type.ValuedField;
    }
}
