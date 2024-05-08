package com.meti;

public record Tuple<A, B>(A a, B b) {
    public Tuple<A, B> replaceRight(B other) {
        return new Tuple<>(a, other);
    }
}
