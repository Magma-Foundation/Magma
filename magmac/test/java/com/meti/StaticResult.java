package com.meti;

import java.util.Optional;

public record StaticResult(String value) implements Result {
    @Override
    public Optional<String> findInstanceValue() {
        return Optional.empty();
    }

    @Override
    public Optional<String> findStaticValue() {
        return Optional.of(value);
    }
}
