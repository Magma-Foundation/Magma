package com.meti.compile;

import com.meti.compile.clang.CFormat;
import com.meti.core.F2;

import java.util.stream.Stream;

public class EmptyOutput<T> implements Output<T> {
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
