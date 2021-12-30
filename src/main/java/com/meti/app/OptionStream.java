package com.meti.app;

import com.meti.api.func.F1;
import com.meti.api.option.Option;

public record OptionStream<T>(Option<T> existing) implements Stream<T> {
    @Override
    public <R, E extends Exception> Stream<R> map(F1<T, R, E> compile) throws E {
        return new OptionStream<>(existing.map(compile));
    }
}
