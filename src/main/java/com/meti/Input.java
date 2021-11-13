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

    public boolean endsWithChar(char c) {
        return !value.isEmpty() && value.charAt(value.length() - 1) == c;
    }

    public boolean startsWithChar(char c) {
        return !value.isEmpty() && value.charAt(0) == c;
    }

    boolean startsWithSlice(String slice) {
        return value.startsWith(slice);
    }
}
