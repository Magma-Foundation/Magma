package com.meti;

import java.util.Optional;

public interface Node {
    default Optional<String> findValue() {
        return Optional.empty();
    }

    default Optional<Integer> findIndent() {
        return Optional.empty();
    }
}
