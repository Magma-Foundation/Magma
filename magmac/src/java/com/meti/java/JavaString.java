package com.meti.java;

import java.util.Optional;

public record JavaString(String input) {
    public Optional<Integer> lastIndexOfChar(char c) {
        return wrapIndex(this.input().lastIndexOf(c));
    }

    private static Optional<Integer> wrapIndex(int index) {
        return index == -1 ? Optional.empty() : Optional.of(index);
    }

    public Optional<Integer> firstIndexOfChar(char c) {
        return wrapIndex(this.input().indexOf(c));
    }
}