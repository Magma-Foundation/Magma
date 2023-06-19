package com.meti;

import java.util.function.BiFunction;

public abstract class AbstracIterator<T> implements Iterator<T> {
    @Override
    public <R> R foldLeft(R state, BiFunction<R, T, R> folder) {
        var current = state;
        while (true) {
            var head = head();
            if (head.isPresent()) {
                current = folder.apply(current, head.unwrapOrPanic());
            } else {
                return current;
            }
        }
    }

    @Override
    public <R, E extends Throwable> Result<R, E> foldLeftResult(R state, BiFunction<R, T, Result<R, E>> folder) {
        return foldLeft(Ok.of(state), (accumulated, t) -> accumulated.mapValueToResult(r -> folder.apply(r, t)));
    }
}
