package com.meti.compile;

import java.util.function.Function;

public record Tuple<A, B>(A a, B b) {
    public <C> Tuple<A, C> mapRight(Function<B, C> mapper) {
        return new Tuple<>(a, mapper.apply(b));
    }
}
