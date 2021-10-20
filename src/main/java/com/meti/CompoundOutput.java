package com.meti;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record CompoundOutput(List<Output> children) implements Output {
    @Override
    public String compute() {
        return children.stream()
                .map(Output::compute)
                .collect(Collectors.joining());
    }

    @Override
    public Output concat(Output other) {
        var copy = new ArrayList<>(children);
        copy.add(other);
        return new CompoundOutput(copy);
    }
}
