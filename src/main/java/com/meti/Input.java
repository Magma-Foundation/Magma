package com.meti;

public record Input(String value) {
    public String compute() {
        return value;
    }

    public boolean endsWithChar(char c) {
        return value.length() != 0 && value.charAt(value.length() - 1) == c;
    }

    int firstChar(char c) {
        return value.indexOf(c);
    }

    int firstSlice() {
        return value.indexOf("=>");
    }

    public int size() {
        return value.length();
    }

    public Input sliceToEnd(int offset) {
        return new Input(slice(offset, value.length()));
    }

    String slice(int start, int end) {
        return value.substring(start, end).trim();
    }

    public boolean startsWithChar(char c) {
        return value.length() != 0 && value.charAt(0) == c;
    }


    boolean startsWithSlice(String s) {
        return value.startsWith(s);
    }
}
