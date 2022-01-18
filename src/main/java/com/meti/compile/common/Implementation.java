package com.meti.compile.common;

import com.meti.collect.JavaList;
import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.node.Node;

import java.util.Objects;
import java.util.stream.Stream;

public final class Implementation extends Function {
    private final Node body;

    public Implementation(Node identity, Node body, Node... parameters) {
        this(identity, body, JavaList.apply(parameters));
    }

    public Implementation(Node identity, Node body, JavaList<Node> parameters) {
        super(identity, parameters);
        this.body = body;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return type == Attribute.Type.Value
                ? new NodeAttribute(body)
                : super.apply(type);
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Node
                ? Stream.of(Attribute.Type.Value)
                : super.apply(group);
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return type == Attribute.Type.Value
                ? new Implementation(identity, attribute.asNode(), parameters)
                : super.with(type, attribute);
    }

    @Override
    protected Node complete(Node node, JavaList<Node> parameters) {
        return new Implementation(node, body, parameters);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Implementation that = (Implementation) o;
        return super.equals(o) && Objects.equals(body, that.body);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Implementation;
    }

    @Override
    public String toString() {
        return "{" +
               "\n\t\"identity\":" + identity +
               ",\n\t\"parameters\":" + parameters +
               ",\n\t\"body\":" + body +
               '}';
    }
}
