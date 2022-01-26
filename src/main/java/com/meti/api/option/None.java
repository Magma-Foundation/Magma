package com.meti.api.option;

import com.meti.api.core.C1;
import com.meti.api.core.F1;
import com.meti.api.core.Runnable;

public class None<T> implements Option<T> {
    @Override
    public <E extends Exception> Option<T> filter(F1<T, Boolean, E> predicate) {
        return new None<>();
    }

    @Override
    public <R, E extends Exception> Option<R> flatMap(F1<T, Option<R>, E> mapper) {
        return new None<>();
    }

    @Override
    public <E extends Exception> void ifPresent(C1<T, E> consumer) {
    }

    @Override
    public <E extends Exception> void ifPresentOrElse(C1<T, E> consumer, Runnable<E> runnable) throws E {
        runnable.run();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public <R, E extends Exception> Option<R> map(F1<T, R, E> mapper) {
        return new None<>();
    }

    @Override
    public Option<T> or(Option<T> option) {
        return option;
    }

    @Override
    public <E extends Exception> Option<T> or(Supplier<Option<T>, E> supplier) throws E {
        return supplier.get();
    }

    @Override
    public T orElse(T other) {
        return other;
    }

    @Override
    public <E extends Exception> T orElseGet(Supplier<T, E> supplier) throws E {
        return supplier.get();
    }

    @Override
    public <E extends Exception> T orElseThrow(Supplier<E, E> supplier) throws E {
        throw supplier.get();
    }
}
