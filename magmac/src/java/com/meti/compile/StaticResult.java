package com.meti.compile;

import com.meti.collect.JavaString;

import java.util.Optional;

public record StaticResult(JavaString value) implements StateResult {
    @Override
    public Optional<JavaString> findInstanceValue() {
        return Optional.empty();
    }

    @Override
    public Optional<JavaString> findStaticValue() {
        return Optional.of(value);
    }
}
