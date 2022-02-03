package com.meti.api.option;

import com.meti.api.core.C1;
import com.meti.api.core.F1;
import com.meti.api.core.Runnable;

public record Some<T>(T value) implements Option<T> {
    @Override
    public <E extends Exception> Option<T> filter(F1<T, Boolean, E> predicate) throws E {
        if(predicate.apply(value)) return this;
        return new None<>();
    }

    @Override
    public <R, E extends Exception> Option<R> flatMap(F1<T, Option<R>, E> mapper) throws E {
        return mapper.apply(value);
    }

    @Override
    public <E extends Exception> void ifPresent(C1<T, E> consumer) throws E {
        consumer.consume(value);
    }

    @Override
    public <E extends Exception> void ifPresentOrElse(C1<T, E> consumer, Runnable<E> runnable) throws E {
        consumer.consume(value);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isPresent() {
        return true;
    }

    @Override
    public <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) throws E {
        return new Some<>(mapper.apply(value));
    }

    @Override
    public Option<T> or(Option<T> option) {
        return this;
    }

    @Override
    public <E extends Exception> Option<T> or(Supplier<Option<T>, E> supplier) {
        return this;
    }

    @Override
    public T orElse(T other) {
        return value;
    }

    @Override
    public <E extends Exception> T orElseGet(Supplier<T, E> supplier) {
        return value;
    }

    @Override
    public <E extends Exception> T orElseThrow(Supplier<E, E> supplier) {
        return value;
    }
}
