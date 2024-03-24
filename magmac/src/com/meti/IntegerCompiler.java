package com.meti;

import java.util.Optional;

public record IntegerCompiler(String value) {
    Optional<String> compileInteger() {
        try {
            return Optional.of(String.valueOf(Integer.parseInt(value())));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}