package com.meti;

import java.util.Optional;

record InnerResult(String inner) implements StackResult {
    @Override
    public Optional<String> findInner() {
        return Optional.of(inner);
    }

    @Override
    public Optional<String> findOuter() {
        return Optional.empty();
    }

    @Override
    public StackResult withOuter(String outer) {
        return new CompoundResult(inner, outer);
    }

    @Override
    public StackResult withInner(String inner) {
        return new InnerResult(this.inner + inner);
    }
}
