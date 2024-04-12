package com.meti;

import java.util.Optional;

public record InstanceMember(String value) implements Member {
    @Override
    public Optional<String> instanceValue() {
        return Optional.of(value);
    }
}
