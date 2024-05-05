package com.meti;

import java.util.List;
import java.util.Optional;

public interface Attribute {
    default Optional<String> asString() {
        return Optional.empty();
    }

    default Optional<List<String>> asListOfStrings() {
        return Optional.empty();
    }

    default Optional<Integer> asInteger() {
        return Optional.empty();
    }
}
