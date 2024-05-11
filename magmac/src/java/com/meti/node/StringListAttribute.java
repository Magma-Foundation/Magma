package com.meti.node;

import com.meti.api.Option;
import com.meti.api.Options;

import java.util.List;
import java.util.Optional;

public record StringListAttribute(List<String> values) implements Attribute {
    @Override
    public Optional<List<String>> asListOfStrings() {
        return Optional.of(values);
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
