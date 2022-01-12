package com.meti.compile.common.invoke;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.NodesAttribute;
import com.meti.compile.node.Node;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Invocation(Node caller, List<Node> arguments) implements Node {
    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return switch (group) {
            case Node -> Stream.of(Attribute.Type.Caller);
            case Nodes -> Stream.of(Attribute.Type.Arguments);
            default -> Stream.empty();
        };
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Caller -> new NodeAttribute(caller);
            case Arguments -> new NodesAttribute(arguments);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Invocation;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return switch (type) {
            case Caller -> new Invocation(attribute.asNode(), arguments);
            case Arguments -> new Invocation(caller, attribute.asStreamOfNodes().collect(Collectors.toList()));
            default -> this;
        };
    }
}
