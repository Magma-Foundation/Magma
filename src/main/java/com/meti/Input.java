package com.meti;

public record Input(String value) {
    public boolean endsWithChar(char c) {
        return !value.isEmpty() && value.charAt(value.length() - 1) == c;
    }

    public Option<Integer> firstChar(char c) {
        var index = value.indexOf(c);
        if (index == -1) return new None<>();
        else return new Some<>(index);
    }

    Option<Integer> firstSlice() {
        return new Some<>(value.indexOf("=>"));
    }

    public int length() {
        return value.length();
    }

    String slice(int start, int end) {
        return value.substring(start, end).trim();
    }

    public boolean startsWithChar(char c) {
        return !value.isEmpty() && value.charAt(0) == c;
    }

    boolean startsWithSlice(String slice) {
        return value.startsWith(slice);
    }
}
