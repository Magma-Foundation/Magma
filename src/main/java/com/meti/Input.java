package com.meti;

public record Input(String input) {
    public Input(String input) {
        this.input = input.trim();
    }

    char apply(int i) {
        return input.charAt(i);
    }

    public String compute() {
        return input;
    }

    boolean endsWith() {
        return input.endsWith("}");
    }

    int firstIndexOfChar(char c) {
        return input.indexOf(c);
    }

    public int firstIndexOfCharFrom(char c, int offset) {
        return input.indexOf(c, offset);
    }

    int firstIndexOfSlice() {
        return input.indexOf("=>");
    }

    boolean isEmpty() {
        return input.isBlank();
    }

    int lastIndexOfString() {
        return input.lastIndexOf("=>");
    }

    int length() {
        return input.length();
    }

    Input sliceToEnd(int start) {
        return slice(start, input.length());
    }

    Input slice(int start, int end) {
        return new Input(input.substring(start, end));
    }

    boolean startsWith(String s) {
        return input.startsWith(s);
    }
}
