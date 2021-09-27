package com.meti;

public record Input(String value) {
    int firstIndexOfChar(char c) {
        return value.indexOf(c);
    }

    int firstIndexOfSlice() {
        return value().indexOf("=>");
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
}
