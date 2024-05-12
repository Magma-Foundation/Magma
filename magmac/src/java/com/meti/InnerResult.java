package com.meti;

import java.util.List;
import java.util.Optional;

record InnerResult(String inner) implements StackResult {
    private Optional<String> findInner0() {
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

    @Override
    public Optional<List<Node>> findInner() {
        return findInner0().map(value -> List.of(new Content(value)));
    }
}
