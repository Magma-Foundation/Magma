package com.meti.compile.common;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.TextAttribute;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;

import java.util.Collections;
import java.util.Set;

public record Field(Set<Flag> flags, Text name, Node type) implements Node {
    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Name -> new TextAttribute(name);
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
        return new Field(Collections.emptySet(), name, attribute.asNode());
    }

    public enum Flag {
        Extern,
        Operator,
        Def
    }
}
