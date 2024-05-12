package com.meti;

import java.util.Optional;

record OuterResult(String outer) implements StackResult {
    @Override
    public Optional<String> findInner() {
        return Optional.empty();
    }

    @Override
    public Optional<String> findOuter() {
        return Optional.of(outer);
    }

    @Override
    public StackResult withOuter(String outer) {
        return new OuterResult(this.outer + outer);
    }

    @Override
    public StackResult withInner(String inner) {
        return new CompoundResult(inner, outer);
    }
}
