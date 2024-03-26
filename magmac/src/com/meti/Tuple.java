package com.meti;

public record Tuple<A, B>(A a, B b) {
    public Tuple<A, B> replaceLeft(A other) {
        return new Tuple<>(other, b);
    }
}
