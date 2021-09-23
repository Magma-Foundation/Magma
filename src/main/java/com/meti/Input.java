package com.meti;

public class Input {
    private final String value;

    public Input(String value) {
        this.value = value.trim();
    }

    public boolean contains(String target) {
        return value.equals(target);
    }

    int firstIndexOfChar(char c) {
        return value.indexOf(c);
    }

    boolean isEmpty() {
        return value.isBlank();
    }

    int length() {
        return value.length();
    }

    String slice(int start) {
        return slice(start, value.length());
    }

    String slice(int start, int end) {
        return value.substring(start, end).trim();
    }

    boolean startsWithString(String slice) {
        return value.startsWith(slice);
    }
}
