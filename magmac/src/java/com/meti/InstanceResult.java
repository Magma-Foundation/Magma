package com.meti;

import java.util.Optional;

public record InstanceResult(String value) implements Result {
    @Override
    public Optional<String> findInstanceValue() {
        return Optional.of(value);
    }

    @Override
    public Optional<String> findStaticValue() {
        return Optional.empty();
    }
}
