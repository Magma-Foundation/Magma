package com.meti.app.compile.stage;

import com.meti.api.core.F2;
import com.meti.app.compile.clang.CFormat;

import java.util.stream.Stream;

public class EmptyTarget<T> implements Target<T> {
    @Override
    public Target<T> append(CFormat key, T value) {
        return new MappedTarget<T>().append(key, value);
    }

    @Override
    public T apply(CFormat types, T other) {
        return other;
    }

    @Override
    public <R, E extends Exception> Target<R> map(F2<CFormat, T, R, E> mapper) throws E {
        return new EmptyTarget<>();
    }

    @Override
    public Stream<CFormat> streamFormats() {
        return Stream.empty();
    }
}
