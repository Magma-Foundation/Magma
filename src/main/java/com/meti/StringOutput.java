package com.meti;

import java.util.List;

public record StringOutput(String value) implements Output {
    @Override
    public String compute() {
        return value;
    }

    @Override
    public Output concat(Output other) {
        return new CompoundOutput(List.of(this, other));
    }
}
