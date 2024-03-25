package com.meti.node;

import java.util.Optional;

public record Content(String value, int indent) implements Node {
    public static final String VALUE = "value";

    private String findValue1() {
        return value;
    }

    private int findIndent1() {
        return indent;
    }

    private Optional<String> findValue() {
        try {
            return Optional.of(findValue1());
        } catch (UnsupportedOperationException e) {
            return Optional.empty();
        }
    }

    private Optional<Integer> findIndent() {
        try {
            return Optional.of(findIndent1());
        } catch (UnsupportedOperationException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Attribute> apply(String name) {
        if (name.equals("value")) return findValue().map(StringAttribute::new);
        if (name.equals("indent")) return findIndent().map(IntAttribute::new);
        return Optional.empty();
    }
}