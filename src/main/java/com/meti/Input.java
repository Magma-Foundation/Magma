package com.meti;

public class Input {
    private final String input;

    public Input(String input) {
        this.input = input.trim();
    }

    public boolean contains(String target) {
        return input.equals(target);
    }

    int firstIndexOfChar(char c) {
        return input.indexOf(c);
    }

    boolean isEmpty() {
        return input.isBlank();
    }

    int length() {
        return input.length();
    }

    String slice(int start, int end) {
        return input.substring(start, end).trim();
    }

    boolean startsWithString(String slice) {
        return input.startsWith(slice);
    }
}
