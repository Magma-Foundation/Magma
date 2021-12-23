package com.meti;

import com.meti.option.Option;
import com.meti.option.Some;

public record Return(Node node) implements Node {
    @Override
    public Option<Node> getValueAsNode() {
        return new Some<>(node);
    }

    @Override
    public boolean is(Type type) {
        return type == Type.Return;
    }
}
