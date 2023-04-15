package com.meti;

import java.util.List;
import java.util.Optional;

public interface Attribute {
    default Optional<List<String>> asTextList() {
        return Optional.empty();
    }

    default Optional<String> asText() {
        return Optional.empty();
    }
}
