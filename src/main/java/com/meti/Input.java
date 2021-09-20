package com.meti;

public class Input {
    private final String content;

    public Input(String content) {
        this.content = content;
    }

    String slice(int start, int end) {
        return content.substring(start, end).trim();
    }

    public String slice(int extent) {
        return slice(extent, content.length());
    }

    boolean startsWith(String slice) {
        return content.startsWith(slice);
    }

    int firstIndexOfString(String target) {
        return content.indexOf(target);
    }

    int firstIndexOfChar(char target) {
        return content.indexOf(target);
    }

    public String compute() {
        return content;
    }

    public boolean contains(String content) {
        return this.content.equals(content);
    }
}
