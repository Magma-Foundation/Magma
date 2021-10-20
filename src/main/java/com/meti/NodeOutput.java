package com.meti;

import java.util.List;

public record NodeOutput(Node node) implements Output {
    @Override
    public String compute() {
        return node.value();
    }

    @Override
    public Output concat(Output other) {
        return new CompoundOutput(List.of(this, other));
    }
}
