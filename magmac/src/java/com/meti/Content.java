package com.meti;

import java.util.Optional;

public record Content(String value) implements Node {
    @Override
    public Optional<String> findValue() {
        return Optional.of(value);
    }

    @Override
    public Optional<String> findName() {
        return Optional.empty();
    }
}
