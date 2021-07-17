package com.meti;

public class Input {
    private final String content;

    public Input(String content) {
        this.content = content;
    }

    int firstIndexOfSlice() {
        return getContent().indexOf("=>");
    }

    int firstIndexOfChar(char c) {
        return getContent().indexOf(c);
    }

    String slice(int start, int end) {
        return getContent().substring(start, end).trim();
    }

    public String getContent() {
        return content;
    }
}
