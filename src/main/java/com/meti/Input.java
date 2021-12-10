package com.meti;

public class Input {
    private final String input;

    public Input(String input) {
        this.input = input;
    }

    int firstChar(char c) {
        return getInput().indexOf(c);
    }

    public String getInput() {
        return input;
    }

    String slice(int start, int end) {
        return getInput().substring(start, end).trim();
    }

    boolean startsWithSlice(String s) {
        return getInput().startsWith(s);
    }
}
