package com.meti.compile;

import com.meti.api.core.F2;
import com.meti.compile.clang.CFormat;

import java.util.stream.Stream;

public class EmptyOutput<T> implements Output<T> {
    @Override
    public Output<T> append(CFormat key, T value) {
        return new MappedOutput<T>().append(key, value);
    }

    @Override
    public T apply(CFormat types, T other) {
        return other;
    }

    @Override
    public <R, E extends Exception> Output<R> map(F2<CFormat, T, R, E> mapper) throws E {
        return new EmptyOutput<>();
    }

    @Override
    public Stream<CFormat> streamFormats() {
        return Stream.empty();
    }
}
