package com.meti.compile.node;

public record Text(String value) {
    public Text(String value) {
        this.value = value.trim();
    }

    public boolean isEmpty() {
        return value.length() == 0;
    }

    public Text slice(int start, int end) {
        return new Text(compute().substring(start, end));
    }

    public String compute() {
        return value;
    }

    public Text slice(int offset) {
        return new Text(value.substring(offset));
    }

    public boolean startsWithChar(char c) {
        return hasContent() && compute().indexOf(c) == 0;
    }

    private boolean hasContent() {
        return compute().length() != 0;
    }
}
