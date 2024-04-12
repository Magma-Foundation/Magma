package com.meti;

import java.util.Optional;

public interface Member {
    default Optional<String> staticValue() {
        return Optional.empty();
    }

    default Optional<String> instanceValue() {
        return Optional.empty();
    }
}
