package com.meti.app.compile.common.block;

import com.meti.api.collect.java.List;
import com.meti.api.collect.stream.Stream;
import com.meti.api.collect.stream.StreamException;
import com.meti.api.collect.stream.Streams;
import com.meti.api.json.ArrayNode;
import com.meti.api.json.JSONException;
import com.meti.api.json.JSONNode;
import com.meti.api.json.ObjectNode;
import com.meti.app.compile.node.AbstractNode;
import com.meti.app.compile.node.Node;
import com.meti.app.compile.node.attribute.Attribute;
import com.meti.app.compile.node.attribute.AttributeException;
import com.meti.app.compile.node.attribute.NodesAttribute;

import java.util.Objects;

public final class Block extends AbstractNode {
    private final List<Node> children;

    public Block() {
        this(List.createList());
    }

    public Block(List<Node> children) {
        this.children = children;
    }

    public Block(Node... children) {
        this(List.apply(children));
    }

    @Override
    public Stream<Attribute.Type> apply(Attribute.Group group) throws AttributeException {
        return group == Attribute.Group.Nodes
                ? Streams.apply(Attribute.Type.Children)
                : Streams.empty();
    }

    @Override
    public boolean is(Category category) {
        return category == Category.Block;
    }

    @Override
    public Attribute apply(Attribute.Type type) throws AttributeException {
        if (type == Attribute.Type.Children) return new NodesAttribute(children);
        throw new AttributeException(type);
    }

    @Override
    public Node with(Attribute.Type type, Attribute attribute) throws AttributeException {
        try {
            return attribute.asStreamOfNodes()
                    .foldRight(new Builder(), Builder::add)
                    .build();
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
            return new ObjectNode().addObject("children", jsonChildren);
        } catch (StreamException e) {
            throw new JSONException("Failed to convert node to JSON.", e);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(children);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Block block)) return false;
        return Objects.equals(children, block.children);
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
