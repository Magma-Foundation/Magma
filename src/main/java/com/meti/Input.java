package com.meti;

public class Input {
    private final String value;

    public Input(String value) {
        this.value = value;
    }

    String slice(int start, int end) {
        return value.substring(start, end).trim();
    }

    public String slice(int extent) {
        return slice(extent, value.length());
    }

    boolean startsWith(String slice) {
        return value.startsWith(slice);
    }

    int firstIndexOfString(String target) {
        return value.indexOf(target);
    }

    int firstIndexOfChar(char target) {
        return value.indexOf(target);
    }

    public String compute() {
        return value;
    }
}
