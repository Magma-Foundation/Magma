package com.meti;

import java.util.Optional;

public record StaticMember(String value) implements Member {
    @Override
    public Optional<String> staticValue() {
        return Optional.of(value);
    }
}
