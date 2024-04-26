package com.meti.compile;

import com.meti.node.Node;
import com.meti.option.None;
import com.meti.option.Option;
import com.meti.option.Some;

public record InstanceResult(com.meti.node.Node value) implements StateResult {
    @Override
    public Option<Node> findInstanceValue() {
        return new Some<>(value);
    }

    @Override
    public Option<Node> findStaticValue() {
        return new None<>();
    }
}
