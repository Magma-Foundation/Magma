package com.meti;

public record Input(String value) {
    public String compute() {
        return value;
    }

    public int length() {
        return value.length();
    }

    public String slice(String prefix, int end) {
        var prefixLength = prefix.length();
        var slice = value.substring(prefixLength, end);
        return slice.trim();
    }

    public boolean startsWithString(String prefix) {
        return value.startsWith(prefix);
    }
}
