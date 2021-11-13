package com.meti;

public record Input(String value) {
    Option<Integer> firstIndexOfSlice() {
        return new Some<>(value.indexOf("=>"));
    }

    public int length() {
        return value.length();
    }

    String slice(int start, int end) {
        return value.substring(start, end).trim();
    }

    boolean startsWithSlice() {
        return value.startsWith("def ");
    }
}
