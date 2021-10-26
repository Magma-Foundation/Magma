package com.meti;

public record Input(String value) {
    public String compute() {
        return value;
    }

    public boolean endsWithChar(char suffix) {
        return value.length() > 0 && value.charAt(value.length() - 1) == suffix;
    }

    public int length() {
        return value.length();
    }

    public String sliceWithPrefix(String offset) {
        return slice(offset.length(), value.length());
    }

    public String slice(int start, int end) {
        return value.substring(start, end).trim();
    }

    public boolean startsWithChar(char prefix) {
        return value.length() > 0 && value.charAt(0) == prefix;
    }

    public boolean startsWithString(String prefix) {
        return value.startsWith(prefix);
    }
}
