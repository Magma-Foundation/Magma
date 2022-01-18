package com.meti.app.compile.common;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.StreamException;
import com.meti.app.compile.attribute.*;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.Text;
import com.meti.app.compile.node.Text;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Field implements Node {
    protected final List<Flag> flags;
    protected final Text name;
    protected final Node type;

    public Field(List<Flag> flags, Text name, Node type) {
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
    public com.meti.api.collect.stream.Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return List.createList(apply(group).collect(Collectors.toList())).stream();
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        if (type == Attribute.Type.Name) {
            return complete(attribute.asText(), this.type);
        }
        if (type == Attribute.Type.Type) {
            return complete(name, attribute.asNode());
        }
        return this;
    }

    protected abstract Field complete(Text name, Node type) throws AttributeException;

    @Override
    public int hashCode() {
        return Objects.hash(flags, name, type);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Field field)) return false;
        return Objects.equals(flags, field.flags) && Objects.equals(name, field.name) && Objects.equals(type, field.type);
    }

    @Override
    public String toString() {
        try {
            var joinedFlags = flags.stream()
                    .map(Flag::toString)
                    .map(value -> "\"" + value + "\"")
                    .foldRight((current, next) -> current + "," + next)
                    .map(value -> "[" + value + "]")
                    .orElse("[]");
            return "{" +
                   "\n\t\"flags\":" + joinedFlags +
                   ",\n\t\"name\":" + name +
                   ",\n\t\"type\":" + type +
                   '}';
        } catch (StreamException e) {
            return "";
        }
    }

    public enum Flag {
        Extern,
        Operator,
        Let, Const, Def
    }
}
