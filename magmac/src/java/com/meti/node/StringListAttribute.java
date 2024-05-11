package com.meti.node;

import com.meti.api.option.Option;
import com.meti.api.option.Options;

import java.util.List;
import java.util.Optional;

public record StringListAttribute(List<String> values) implements Attribute {
    private Optional<List<String>> asListOfStrings0() {
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

    @Override
    public Option<List<String>> asListOfStrings() {
        return Options.fromNative(asListOfStrings0());
    }
}
