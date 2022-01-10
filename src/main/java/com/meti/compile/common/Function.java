package com.meti.compile.common;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.NodesAttribute;
import com.meti.compile.node.Node;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Function(Node identity, Set<Node> parameters, Node body) implements Node {
    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return switch (group) {
            case Declarations -> Stream.of(Attribute.Type.Parameters);
            case Declaration -> Stream.of(Attribute.Type.Identity);
            case Node -> Stream.of(Attribute.Type.Value);
            default -> Stream.empty();
        };
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Identity -> new NodeAttribute(identity);
            case Parameters -> new NodesAttribute(parameters);
            case Value -> new NodeAttribute(body);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Function;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return switch (type) {
            case Identity -> new Function(attribute.asNode(), parameters, body);
            case Parameters -> new Function(identity, attribute.asStreamOfNodes().collect(Collectors.toSet()), body);
            case Value -> new Function(identity, parameters, attribute.asNode());
            default -> throw new AttributeException(type);
        };
    }
}
