package com.meti;

public record Input(String value) {
    int firstIndexOfChar() {
        return value.indexOf(':');
    }

    String slice(int start) {
        return slice(start, value.length());
    }

    String slice(int start, int end) {
        return value.substring(start, end).trim();
    }
}
