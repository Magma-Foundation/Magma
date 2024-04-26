package com.meti.collect;

public record Range(int start, int end, int length) {
    public Index startIndex() {
        return new Index(start, length);
    }

    public Index endIndex() {
        return new Index(end, length);
    }
}
