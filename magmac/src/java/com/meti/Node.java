package com.meti;

import java.util.Optional;

public interface Node {
    default Optional<String> findMutabilityModifier() {
        return Optional.empty();
    }

    default Optional<String> findModifierString() {
        return Optional.empty();
    }

    Optional<String> findName();

    Optional<String> findType();

    Optional<String> findValue();
}
