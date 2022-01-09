package com.meti.compile.common.integer;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.BooleanAttribute;
import com.meti.compile.attribute.IntegerAttribute;
import com.meti.compile.node.Node;

public record IntegerType(boolean signed, int bits) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Sign -> new BooleanAttribute(signed);
            case Bits -> new IntegerAttribute(bits);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Integer;
    }
}
