package com.meti.app;

import com.meti.app.attribute.*;
import com.meti.app.node.Node;

import java.util.stream.Stream;

public enum Primitive {
    U16(false, 16),
    I16(true, 16);

    private final boolean signed;
    private final int bits;

    Primitive(boolean signed, int bits) {
        this.signed = signed;
        this.bits = bits;
    }

    public Node asField(String name, Node preEquality) {
        return new PrimitiveField(name, preEquality);
    }

    private class PrimitiveField implements Node {
        private final String name;
        private final Node preEquality;

        public PrimitiveField(String name, Node preEquality) {
            this.name = name;
            this.preEquality = preEquality;
        }

        @Override
        public Attribute apply(Attribute.Type type) throws AttributeException {
            return switch (type) {
                case Signed -> new BooleanAttribute(signed);
                case Bits -> new IntAttribute(bits);
                case Name -> new StringAttribute(name);
                case PreEquality -> new NodeAttribute(preEquality);
                default -> throw new AttributeException(type);
            };
        }

        @Override
        public boolean is(Type type) {
            return type == Type.Primitive;
        }

        @Override
        public Stream<Attribute.Type> stream(Attribute.Group group) {
            return group == Attribute.Group.Node
                    ? Stream.of(Attribute.Type.PreEquality)
                    : Stream.empty();
        }

        @Override
        public Node with(Attribute.Type type, Attribute value) throws AttributeException {
            return type == Attribute.Type.PreEquality
                    ? new PrimitiveField(name, value.asNode())
                    : this;
        }
    }
}
