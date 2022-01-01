package com.meti;

public class Input {
    private final String input;

    public Input(String input) {
        this.input = input;
    }

    int firstIndexOfChar(char c) {
        return getInput().indexOf(c);
    }

    public String getInput() {
        return input;
    }

    int firstIndexOfSlice() {
        return getInput().indexOf("=>");
    }

    boolean isEmpty() {
        return getInput().isBlank();
    }

    Input sliceToEnd(int start) {
        return slice(start, getInput().length());
    }

    Input slice(int start, int end) {
        return new Input(input.substring(start, end).trim());
    }
}
