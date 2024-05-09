package com.meti;

import java.util.function.Function;

public record Tuple<L, R>(L left, R right) {
    public Tuple<L, R> replaceRight(R other) {
        return new Tuple<>(left, other);
    }

    public <T> Tuple<T, R> mapLeft(Function<L, T> mapper) {
        return new Tuple<>(mapper.apply(left), right);
    }
}
