package com.meti;

import java.util.Optional;

public interface Node {
    default Optional<String> apply(String key) {
        return Optional.empty();
    }
}
