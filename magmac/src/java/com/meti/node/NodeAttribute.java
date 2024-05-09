package com.meti.node;

import com.meti.util.Option;
import com.meti.util.Options;

import java.util.List;
import java.util.Optional;

public record NodeAttribute(MapNode value) implements Attribute{
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
