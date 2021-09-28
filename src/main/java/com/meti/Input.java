package com.meti;

public record Input(String value) {
    public boolean endsWith(String slice) {
        return value.endsWith(slice);
    }

    int firstIndexOfChar(char c) {
        return value.indexOf(c);
    }

    int firstIndexOfSlice() {
        return value().indexOf("=>");
    }

    public boolean isBlank() {
        return value().isBlank();
    }

    int lastIndexOfChar() {
        return value().lastIndexOf(':');
    }

    public int length() {
        return value.length();
    }

    String slice(int start) {
        return slice(start, value.length());
    }

    String slice(int start, int end) {
        return value.substring(start, end).trim();
    }

    public boolean startsWith(String slice) {
        return value.startsWith(slice);
    }
}
