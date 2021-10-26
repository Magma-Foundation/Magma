package com.meti.compile.node.output;

import com.meti.compile.node.Node;
import com.meti.option.Option;
import com.meti.option.Some;

public record NodeOutput(Node value) implements Output {
    @Override
    public Option<Node> asNode() {
        return new Some<>(value);
    }
}
