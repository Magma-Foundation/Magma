package com.meti.compile.common;

import com.meti.collect.JavaList;
import com.meti.compile.attribute.*;
import com.meti.compile.node.Node;
import com.meti.compile.node.Text;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Field implements Node {
    protected final Set<Flag> flags;
    protected final Text name;
    protected final Node type;

    @Override
    public String toString() {
        var joinedFlags = flags.stream()
                .map(Flag::toString)
                .collect(Collectors.joining(",", "[", "]"));
        return "{" +
               "\n\t\"flags\":" + joinedFlags +
               ",\n\t\"name\":" + name +
               ",\n\t\"type\":" + type +
               '}';
    }

    public Field(Set<Flag> flags, Text name, Node type) {
        this.flags = flags;
        this.name = name;
        this.type = type;
    }

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
    @Deprecated
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return Stream.empty();
    }

    @Override
    public com.meti.collect.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return new JavaList<>(apply(group).collect(Collectors.toList())).stream();
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        if (type == Attribute.Type.Type) {
            return complete(attribute.asNode());
        }
        return this;
    }

    protected abstract Field complete(Node type) throws AttributeException;

    public enum Flag {
        Extern,
        Operator,
        Let, Const, Def
    }
}
