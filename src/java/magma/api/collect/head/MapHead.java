package magma.api.collect.head;

import magma.api.option.Option;

import java.util.function.Function;

public record MapHead<T, R>(Head<T> head, Function<T, R> mapper) implements Head<R> {
    @Override
    public Option<R> next() {
        return this.head.next().map(this.mapper);
    }
}
