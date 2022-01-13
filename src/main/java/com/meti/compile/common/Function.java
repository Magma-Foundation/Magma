package com.meti.compile.common;

import com.meti.compile.attribute.Attribute;
import com.meti.compile.attribute.AttributeException;
import com.meti.compile.attribute.NodeAttribute;
import com.meti.compile.attribute.NodesAttribute;
import com.meti.compile.node.Node;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Function implements Node {
    protected final Node identity;
    protected final Set<Node> parameters;

    public Function(Node identity, Set<Node> parameters) {
        this.identity = identity;
        this.parameters = parameters;
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return switch (group) {
            case Declarations -> Stream.of(Attribute.Type.Parameters);
            case Declaration -> Stream.of(Attribute.Type.Identity);
            default -> Stream.empty();
        };
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Identity -> new NodeAttribute(identity);
            case Parameters -> new NodesAttribute(parameters);
            default -> throw new AttributeException(type);
        };
    }

    protected abstract Node complete(Node node, Set<Node> parameters);

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        return switch (type) {
            case Identity -> complete(attribute.asNode(), parameters);
            case Parameters -> Stream<Node> result;Attribute attribute1 = attribute;
                    result = attribute1.asStreamOfNodes()
                            .foldRight(Stream.<Node>builder(), Stream.Builder::add)
                            .build();
                    complete(identity, result.collect(Collectors.toSet()));
            default -> throw new AttributeException(type);
        };
    }
}
