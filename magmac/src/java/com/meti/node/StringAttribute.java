package com.meti.node;

import com.meti.api.option.Option;
import com.meti.api.option.Options;

import java.util.List;
import java.util.Optional;

public record StringAttribute(String value) implements Attribute {
    @Override
    public Optional<String> asString() {
        return Optional.of(value);
    }

    private Optional<List<MapNode>> asListOfNodes1() {
        return Optional.empty();
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
