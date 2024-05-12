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

    private StackResult withOuter0(String outer) {
        return new OuterResult(this.outer + outer);
    }

    private StackResult withInner0(String inner) {
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

    @Override
    public StackResult withOuter(Node outer) {
        return outer.findValue().map(this::withOuter0).orElse(this);
    }

    @Override
    public StackResult withInner(Node inner) {
        return inner.findValue().map(this::withInner0).orElse(this);
    }
}
