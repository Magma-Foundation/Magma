package com.meti.compile.node;

public record Input(String value) {
    public Input(String value) {
        this.value = value.trim();
    }

    public boolean isEmpty() {
        return value.length() == 0;
    }

    public Input slice(int start, int end) {
        return new Input(getInput().substring(start, end));
    }

    public String getInput() {
        return value;
    }

    public Input slice(int offset) {
        return new Input(value.substring(offset));
    }

    public boolean startsWithChar(char c) {
        return hasContent() && getInput().indexOf(c) == 0;
    }

    private boolean hasContent() {
        return getInput().length() != 0;
    }
}
