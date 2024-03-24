package com.meti;

import java.util.Optional;

public record StringCompiler(String value) {
    Optional<String> compileString() {
        if (value().startsWith("\"") && value().endsWith("\"")) {
            return Optional.of("\"" + value().substring(1, value().length() - 1) + "\"");
        } else {
            return Optional.empty();
        }
    }
}