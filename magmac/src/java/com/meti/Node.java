package com.meti;

import java.util.Optional;

public interface Node {
    default Optional<String> find(String key) {
        return Optional.empty();
    }
}
