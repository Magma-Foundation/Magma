package com.meti.collect;

import com.meti.collect.option.Option;

import java.util.function.Function;

public record Pair<L, R>(L left, R right) {
    public <T> Pair<L, T> mapRight(Function<R, T> mapper) {
        return new Pair<>(left, mapper.apply(right));
    }

    public <T> T into(Function<Pair<L, R>, T> mapper) {
        return mapper.apply(this);
    }

    public static <L, R> Option<Pair<L, R>> requireRight(Pair<L, Option<R>> pair) {
        return pair.right.map(rValue -> new Pair<>(pair.left, rValue));
    }

    public <T> T applyRight(Function<R, T> mapper) {
        return mapper.apply(right);
    }
}
