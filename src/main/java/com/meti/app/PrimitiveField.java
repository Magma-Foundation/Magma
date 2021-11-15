package com.meti.app;

import com.meti.app.attribute.*;
import com.meti.app.node.Node;

public class PrimitiveField implements Node {
    protected final String name;
    protected final int bits;
    protected final boolean signed;

    public PrimitiveField(String name, int bits, boolean signed) {
        this.name = name;
        this.bits = bits;
        this.signed = signed;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Signed -> new BooleanAttribute(signed);
            case Bits -> new IntAttribute(bits);
            case Name -> new StringAttribute(name);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public boolean is(Node.Type type) {
        return type == Node.Type.Primitive;
    }
}
