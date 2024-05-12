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
}
