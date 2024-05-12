package com.meti;

import java.util.List;
import java.util.Optional;

public class EmptyResult implements StackResult {
    private Optional<String> findInner0() {
        return Optional.empty();
    }

    private Optional<String> findOuter0() {
        return Optional.empty();
    }

    private StackResult withOuter0(String outer) {
        return new OuterResult(outer);
    }

    @Override
    public StackResult withInner(String inner) {
        return new InnerResult(inner);
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
}
