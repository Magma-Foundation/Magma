package com.meti.core;

import java.util.function.Function;

public record Tuple<A, B>(A a, B b) {
    public <C> Tuple<C, B> mapLeft(Function<A, C> mapper) {
        return new Tuple<>(mapper.apply(a), b);
    }
}
