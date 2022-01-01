package com.meti;

public class Input {
    private final String input;

    public Input(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    String slice(int start, int end) {
        return getInput().substring(start, end).trim();
    }
}
