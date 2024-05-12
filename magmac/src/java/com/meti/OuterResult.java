package com.meti;

import java.util.List;
import java.util.Optional;

record OuterResult(String outer) implements StackResult {
    private Optional<String> findInner0() {
        return Optional.empty();
    }

    private Optional<String> findOuter0() {
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

    @Override
    public Optional<List<Node>> findInner() {
        return findInner0().map(value -> List.of(new Content(value)));
    }

    @Override
    public Optional<List<Node>> findOuter() {
        return findOuter0().map(value -> List.of(new Content(value)));
    }
}
