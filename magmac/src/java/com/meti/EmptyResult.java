package com.meti;

import java.util.Optional;

public class EmptyResult implements StackResult {
    @Override
    public Optional<String> findInner() {
        return Optional.empty();
    }

    @Override
    public Optional<String> findOuter() {
        return Optional.empty();
    }

    @Override
    public StackResult withOuter(String outer) {
        return new OuterResult(outer);
    }

    @Override
    public StackResult withInner(String inner) {
        return new InnerResult(inner);
    }
}
