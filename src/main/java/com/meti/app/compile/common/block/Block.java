package com.meti.app.compile.common.block;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.json.ArrayNode;
import com.meti.api.json.EmptyNode;
import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodesAttribute1;

import java.util.Objects;

public record Block(List<Node> children) implements Node {
    public Block() {
        this(List.createList());
    }

    public Block(Node... children) {
        this(List.apply(children));
    }

    @Override
    public JSONNode toJSON() {
        try {
            var jsonChildren = children.stream()
                    .map(Node::toJSON)
                    .foldRight(new ArrayNode.Builder(), ArrayNode.Builder::addObject)
                    .build();
            return new ObjectNode().add("children", jsonChildren);
        } catch (StreamException e) {
            return new EmptyNode();
        }
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Children) return new NodesAttribute1(children);
        throw new AttributeException(type);
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Nodes
                ? Streams.apply(Attribute.Type.Children)
                : Streams.empty();
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Block;
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        try {
            return attribute.asStreamOfNodes1()
                    .foldRight(new Builder(), Builder::add)
                    .build();
        } catch (StreamException e) {
            throw new AttributeException(e);
        }
    }

    @Override
    public String toString() {
        try {
            var childrenAsString = children.stream()
                    .map(Objects::toString)
                    .foldRight((current, next) -> current + "," + next)
                    .orElse("");
            return "{\"buffer\":[" + childrenAsString + "]}";
        } catch (StreamException e) {
            return "";
        }
    }

    public record Builder(List<Node> children) {
        public Builder() {
            this(List.createList());
        }

        public Builder add(Node child) {
            return new Builder(children.add(child));
        }

        public Node build() {
            return new Block(children);
        }
    }
}
