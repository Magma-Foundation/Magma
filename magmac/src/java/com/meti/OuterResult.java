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
}
