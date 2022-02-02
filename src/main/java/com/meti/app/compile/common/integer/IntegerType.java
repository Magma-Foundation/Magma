package com.meti.app.compile.common.integer;

import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.AbstractNode;
import com.meti.app.compile.node.Type;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.BooleanAttribute;
import com.meti.app.compile.node.attribute.IntegerAttribute;

import java.util.Objects;

public final class IntegerType extends AbstractNode implements Type {
    private final boolean signed;
    private final int bits;

    public IntegerType(boolean signed, int bits) {
        this.signed = signed;
        this.bits = bits;
    }

    @Override
    public int hashCode() {
        return Objects.hash(signed, bits);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntegerType that)) return false;
        return signed == that.signed && bits == that.bits;
    }

    @Override
    public boolean is(Role role) {
        return role == Role.Integer;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Sign -> new BooleanAttribute(signed);
            case Bits -> new IntegerAttribute(bits);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public JSONNode toJSON() {
        return new ObjectNode()
                .addObject("signed", signed)
                .addObject("bits", bits);
    }
}
