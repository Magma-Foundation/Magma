package com.meti.app;

import com.meti.app.attribute.Attribute;
import com.meti.app.attribute.AttributeException;
import com.meti.app.attribute.NodeAttribute;
import com.meti.app.node.Node;

import java.util.stream.Stream;

class CodaPrimitiveField extends PrimitiveField implements Node {
    private final Node coda;

    public CodaPrimitiveField(boolean signed, int bits, String name, Node coda) {
        super(signed, bits, name);
        this.coda = coda;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return type == Attribute.Type.Coda
                ? new NodeAttribute(coda)
                : super.apply(type);
    }

    @Override
    public Stream<Attribute.Type> stream(Attribute.Group group) {
        return group == Attribute.Group.Node
                ? Stream.of(Attribute.Type.Coda)
                : Stream.empty();
    }

    @Override
    public Node with(Attribute.Type type, Attribute value) throws AttributeException {
        return type == Attribute.Type.Coda
                ? new CodaPrimitiveField(signed, bits, name, value.asNode())
                : this;
    }
}
