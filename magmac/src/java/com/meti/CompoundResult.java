package com.meti;

import java.util.List;
import java.util.Optional;

public record CompoundResult(String inner, String outer) implements StackResult {
    public CompoundResult() {
        this("", "");
    }

    private Optional<String> findInner0() {
        return Optional.of(inner);
    }

    private Optional<String> findOuter0() {
        return Optional.of(outer);
    }

    private StackResult withOuter0(String outer) {
        return new CompoundResult(inner, this.outer + outer);
    }

    private StackResult withInner0(String inner) {
        return new CompoundResult(this.inner + inner, outer);
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

