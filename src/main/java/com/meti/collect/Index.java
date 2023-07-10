package com.meti.collect;

public record Index(int value) {
    public int unwrap() {
        return value;
    }

    public Index next() {
        return new Index(value + 1);
    }
}