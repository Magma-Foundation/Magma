package com.meti.node;

import java.util.Optional;

public interface Attribute {

    default Optional<String> asString() {
        return Optional.empty();
    }

    default Optional<Integer> asInt() {
        return Optional.empty();
    }
}
