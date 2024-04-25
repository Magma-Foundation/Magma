package com.meti.collect;

public record Range(int start, int end, int length) {
    Index startIndex() {
        return new Index(start, length);
    }

    public Index endIndex() {
        return new Index(end, length);
    }
}
