package com.meti.app.compile.cache;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.json.JSONFormatter;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.NodesAttribute1;

public record Cache(Node value, List<Node> children) implements Node {
    public Cache(Node value, Node... children) {
        this(value, List.apply(children));
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Value -> new NodeAttribute(value);
            case Children -> new NodesAttribute1(children);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public Stream<Attribute.Type> apply1(Attribute.Group group) throws AttributeException {
        return switch (group) {
            case Node -> Streams.apply(Attribute.Type.Value);
            case Nodes -> Streams.apply(Attribute.Type.Children);
            default -> Streams.empty();
        };
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Cache;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        try {
            return switch (type) {
                case Value -> new Cache(attribute.asNode(), children);
                case Children -> new Cache(value, attribute.asStreamOfNodes1()
                        .foldRight(List.createList(), List::add));
                default -> this;
            };
        } catch (StreamException e) {
            throw new AttributeException(e);
        }
    }

    @Override
    public String toString() {
        return new JSONFormatter(new ObjectNode()
                .add("value", value)
                .add("children", children))
                .toString();
    }
}
