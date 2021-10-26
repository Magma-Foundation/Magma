package com.meti.app.compile.node.output;

import com.meti.api.option.Option;
import com.meti.api.option.Some;
import com.meti.app.compile.node.Node;

public record NodeOutput(Node value) implements Output {
    @Override
    public Option<Node> asNode() {
        return new Some<>(value);
    }
}
