package com.meti;

public record Index(int value) {
    public int unwrap() {
        return value;
    }
}