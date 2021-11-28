package com.meti.app;

import com.meti.app.attribute.Attribute;
import com.meti.app.attribute.AttributeException;
import com.meti.app.attribute.NodeAttribute;
import com.meti.app.node.Node;

import java.util.stream.Stream;

class OnsetPrimitiveField extends PrimitiveField implements Node {
    private final Node onset;

    public OnsetPrimitiveField(boolean signed, int bits, String name, Node onset) {
        super(signed, bits, name);
        this.onset = onset;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return type == Attribute.Type.Onset
                ? new NodeAttribute(onset)
                : super.apply(type);
    }

    @Override
    public Stream<Attribute.Type> stream(Attribute.Group group) {
        return group == Attribute.Group.Node
                ? Stream.of(Attribute.Type.Onset)
                : Stream.empty();
    }

    @Override
    public Node with(Attribute.Type type, Attribute value) throws AttributeException {
        return type == Attribute.Type.Onset
                ? new OnsetPrimitiveField(signed, bits, name, value.asNode())
                : this;
    }
}
