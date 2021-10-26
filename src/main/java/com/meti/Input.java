package com.meti;

public record Input(String value) {
    public String compute() {
        return value;
    }

    public int length() {
        return value.length();
    }

    public String truncate(String prefix) {
        var prefixLength = prefix.length();
        return slice(prefixLength, value.length());
    }

    public boolean startsWithString(String prefix) {
        return value.startsWith(prefix);
    }

    public boolean startsWithChar(char prefix) {
        return value.length() != 0 && value.charAt(0) == prefix;
    }

    public boolean endsWithChar(char suffix) {
        var length = value.length();
        return length != 0 && value.charAt(length - 1) == suffix;
    }

    public String slice(int start, int end) {
        return value.substring(start, end).trim();
    }
}
