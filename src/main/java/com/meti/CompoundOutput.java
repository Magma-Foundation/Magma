package com.meti;

import java.util.ArrayList;
import java.util.List;

public record CompoundOutput(List<Output> children) implements Output {
    @Override
    public String compute() throws AttributeException {
        StringBuilder sb = new StringBuilder();
        for (Output child : children) {
            String compute = child.compute();
            sb.append(compute);
        }
        return sb.toString();
    }

    @Override
    public Output concat(Output other) {
        var copy = new ArrayList<>(children);
        copy.add(other);
        return new CompoundOutput(copy);
    }
}
