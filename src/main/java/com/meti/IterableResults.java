package com.meti;

import java.util.function.BiFunction;

public class IterableResults {
    public static <T, E extends Exception> IterableResult<T, E> apply(Iterable<Result<T, E>> iterable) {
        return new AbstractIterableResult<>(iterable);
    }

    private static class AbstractIterableResult<T, E extends Exception> extends AbstractIterable<Result<T, E>> implements IterableResult<T, E> {
        private final Iterable<Result<T, E>> iterable;

        public AbstractIterableResult(Iterable<Result<T, E>> iterable) {
            this.iterable = iterable;
        }

        @Override
        public Option<Result<T, E>> head() {
            return iterable.head();
        }

        @Override
        public <R> Result<R, E> foldLeftByValue(R initial, BiFunction<R, T, R> merger) {
            return foldLeft(Results.Ok(initial), (accumulated, element) -> accumulated.merge(element, merger));
        }
    }
}
