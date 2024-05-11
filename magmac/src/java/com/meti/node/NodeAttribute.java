package com.meti.node;

import com.meti.api.Option;
import com.meti.api.Options;

import java.util.List;
import java.util.Optional;

public record NodeAttribute(MapNode value) implements Attribute {
    public static final String KEY = "node";
    public static final AttributeFactory<MapNode> NodeFactory = new AttributeFactory<>() {
        @Override
        public Option<MapNode> fromAttribute(Attribute attribute) {
            return attribute.asNode();
        }

        @Override
        public boolean accepts(Attribute attribute) {
            return attribute.is(KEY);
        }

        @Override
        public Attribute toAttribute(MapNode value) {
            return new NodeAttribute(value);
        }
    };

    @Override
    public boolean is(String key) {
        return key.equals(KEY);
    }

    private Optional<MapNode> asNode1() {
        return Optional.of(value);
    }

    private Optional<List<MapNode>> asListOfNodes1() {
        return Optional.empty();
    }

    @Override
    public Option<List<MapNode>> asListOfNodes() {
        return Options.fromNative(asListOfNodes1());
    }

    @Override
    public Option<MapNode> asNode() {
        return Options.fromNative(asNode1());
    }
}
