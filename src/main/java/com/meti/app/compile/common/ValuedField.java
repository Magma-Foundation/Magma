package com.meti.app.compile.common;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.EmptyStream;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.Streams;
import com.meti.app.compile.attribute.Attribute;
import com.meti.app.compile.attribute.AttributeException;
import com.meti.app.compile.attribute.NodeAttribute;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Text;

public class ValuedField extends Field {
    private final Node value;

    public ValuedField(List<Flag> flags, Text name, Node type, Node value) {
        super(flags, name, type);
        this.value = value;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return type == Attribute.Type.Value ? new NodeAttribute(value) : super.apply(type);
    }

    @Override
    public Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Node ? Streams.apply(Attribute.Type.Value) : new EmptyStream<>();
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return type == Attribute.Type.Value ? new ValuedField(flags, name, this.type, attribute.asNode()) : super.with(type, attribute);
    }

    @Override
    protected Field complete(Text name, Node type) {
        return new ValuedField(flags, this.name, type, value);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Initialization;
    }
}
