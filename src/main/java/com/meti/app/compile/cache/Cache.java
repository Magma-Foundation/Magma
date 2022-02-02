package com.meti.app.compile.cache;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.json.*;
import com.meti.app.compile.node.AbstractNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodeAttribute;
import com.meti.app.compile.node.attribute.NodesAttribute;

import java.util.Objects;

public final class Cache extends AbstractNode {
    private final Node value;
    private final List<Node> children;

    public Cache(Node value, Node... children) {
        this(value, List.apply(children));
    }

    public Cache(Node value, List<Node> children) {
        this.value = value;
        this.children = children;
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
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
    public Attribute apply(Attribute.Type type) throws AttributeException {
        return switch (type) {
            case Value -> new NodeAttribute(value);
            case Children -> new NodesAttribute(children);
            default -> throw new AttributeException(type);
        };
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        try {
            return switch (type) {
                case Value -> new Cache(attribute.asNode(), children);
                case Children -> new Cache(value, attribute.asStreamOfNodes()
                        .foldRight(List.createList(), List::add));
                default -> this;
            };
        } catch (StreamException e) {
            throw new AttributeException(e);
        }
    }

    @Override
    public JSONNode toJSON() throws JSONException {
        try {
            var jsonChildren = children.stream()
                    .map(Node::toJSON)
                    .foldRight(new ArrayNode.Builder(), ArrayNode.Builder::addObject)
                    .build();

            return new ObjectNode()
                    .addObject("value", value.toJSON())
                    .addObject("children", jsonChildren);
        } catch (StreamException e) {
            return new EmptyNode();
        }
    }

    public List<Node> children() {
        return children;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, children);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Cache) obj;
        return Objects.equals(this.value, that.value) &&
               Objects.equals(this.children, that.children);
    }

    public Node value() {
        return value;
    }

}
