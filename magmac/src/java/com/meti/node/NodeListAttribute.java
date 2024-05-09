package com.meti.node;

import com.meti.util.Option;
import com.meti.util.Options;

import java.util.List;
import java.util.Optional;

public record NodeListAttribute(List<MapNode> nodes) implements Attribute {
    public static final AttributeFactory<List<MapNode>> NodeListFactory = new AttributeFactory<>() {
        @Override
        public Attribute toAttribute(List<MapNode> value) {
            return new NodeListAttribute(value);
        }

        @Override
        public Option<List<MapNode>> fromAttribute(Attribute attribute) {
            return attribute.asListOfNodes();
        }
    };

    private Optional<List<MapNode>> asListOfNodes1() {
        return Optional.of(nodes);
    }

    @Override
    public Option<List<MapNode>> asListOfNodes() {
        return Options.fromNative(asListOfNodes1());
    }

    private Optional<MapNode> asNode1() {
        return Optional.empty();
    }

    @Override
    public Option<MapNode> asNode() {
        return Options.fromNative(asNode1());
    }
}
