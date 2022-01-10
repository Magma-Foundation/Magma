package com.meti.compile.common;

import com.meti.compile.attribute.*;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;

import java.util.Set;

public record Field(Set<Flag> flags, Text name, Node type) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Name -> new TextAttribute(name);
            case Flags -> new FlagsAttribute(flags);
            case Type -> new NodeAttribute(this.type);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Field;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        if (type == Attribute.Type.Type) {
            return new Field(flags, name, attribute.asNode());
        }
        return this;
    }

    public enum Flag {
        Extern,
        Operator,
        Def
    }
}
