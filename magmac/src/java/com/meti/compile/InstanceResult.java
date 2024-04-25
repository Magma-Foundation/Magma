package com.meti.compile;

import com.meti.collect.JavaString;

import java.util.Optional;

public record InstanceResult(JavaString value) implements StateResult {
    @Override
    public Optional<JavaString> findInstanceValue() {
        return Optional.of(value);
    }

    @Override
    public Optional<JavaString> findStaticValue() {
        return Optional.empty();
    }
}
