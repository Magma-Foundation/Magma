package com.meti;

import java.util.Optional;

public interface Attribute {
    default Optional<String> asString(){
        return Optional.empty();
    }

    default Optional<Integer> asInteger() {
        return Optional.empty();
    }
}
