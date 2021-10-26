package com.meti;

import com.meti.feature.Node;

public record NodeOutput(Node value) implements Output {
    @Override
    public Option<Node> asNode() {
        return new Some<>(value);
    }
}
