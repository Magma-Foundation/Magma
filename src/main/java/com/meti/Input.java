package com.meti;

public class Input {
    private final String input;

    public Input(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    Input slice(int start, int end) {
        return new Input(getInput().substring(start, end).trim());
    }
}
