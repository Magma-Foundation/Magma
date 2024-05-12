package com.meti;

import java.util.Optional;

public record CompoundResult(String inner, String outer) implements StackResult {
    public CompoundResult() {
        this("", "");
    }

    @Override
    public Optional<String> findInner() {
        return Optional.of(inner);
    }

    @Override
    public Optional<String> findOuter() {
        return Optional.of(outer);
    }

    @Override
    public StackResult withOuter(String outer) {
        return new CompoundResult(inner, this.outer + outer);
    }

    @Override
    public StackResult withInner(String inner) {
        return new CompoundResult(this.inner + inner, outer);
    }
}

