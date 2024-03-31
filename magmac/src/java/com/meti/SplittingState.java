package com.meti;

import java.util.ArrayList;
import java.util.List;

final class SplittingState {
    private final List<String> list;
    private StringBuilder builder;
    private int depth;
    boolean isInSingleQuotes = false;

    SplittingState(List<String> list, StringBuilder builder, int depth) {
        this.list = list;
        this.builder = builder;
        this.depth = depth;
    }

    public SplittingState() {
        this(new ArrayList<>(), new StringBuilder(), 0);
    }

    boolean isLevel() {
        return this.depth == 0;
    }

    boolean isShallow() {
        return this.depth == 1;
    }

    void append(char c) {
        this.builder.append(c);
    }

    void clean() {
        this.list.removeIf(String::isBlank);
    }

    void descend() {
        this.depth = this.depth - 1;
    }

    void advance() {
        this.list.add(this.builder.toString());
        this.builder = new StringBuilder();
    }

    void ascend() {
        this.depth = this.depth + 1;
    }

    public List<String> unwrap() {
        return list;
    }

    public void toggleSingleQuotes() {
        this.isInSingleQuotes = !isInSingleQuotes;
    }
}
