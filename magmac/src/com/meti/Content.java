package com.meti;

import java.util.Optional;

public record Content(String value, int indent) implements Node {
    private String findValue1() {
        return value;
    }

    private int findIndent1() {
        return indent;
    }

    @Override
    public Optional<String> findValue() {
        try {
            return Optional.of(findValue1());
        } catch (UnsupportedOperationException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Integer> findIndent() {
        try {
            return Optional.of(findIndent1());
        } catch (UnsupportedOperationException e) {
            return Optional.empty();
        }
    }
}