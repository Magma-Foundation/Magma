package com.meti;

public class Input {
    private final String content;

    public Input(String content) {
        this.content = content;
    }

    boolean contains(String i16) {
        return getContent().equals(i16);
    }

    int firstIndexOfChar(char c) {
        return getContent().indexOf(c);
    }

    int firstIndexOfSlice() {
        return getContent().indexOf("=>");
    }

    Input sliceToInput(int typeSeparator, int returnSeparator) {
        return new Input(sliceToString(typeSeparator + 1, returnSeparator));
    }

    String sliceToString(int start, int end) {
        return getContent().substring(start, end).trim();
    }

    public String getContent() {
        return content;
    }
}
