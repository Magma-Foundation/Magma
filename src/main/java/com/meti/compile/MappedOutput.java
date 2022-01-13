package com.meti.compile;

import com.meti.collect.JavaMap;
import com.meti.compile.clang.CFormat;
import com.meti.core.F2;

import java.util.stream.Stream;

public final class MappedOutput<T> implements Output<T> {
    private final JavaMap<CFormat, T> map;

    public MappedOutput() {
        this(new JavaMap<>());
    }

    public MappedOutput(JavaMap<CFormat, T> map) {
        this.map = map;
    }

    @Override
    public Output<T> append(CFormat key, T value) {
        return new MappedOutput<>(map.put(key, value));
    }

    @Override
    public T apply(CFormat format, T other) {
        return map.orElse(format, other);
    }

    @Override
    public <R, E extends Exception> Output<R> map(F2<CFormat, T, R, E> mapper) throws E {
        return new MappedOutput<>(map.mapValues(mapper));
    }

    @Override
    public Stream<CFormat> streamFormats() {
        return map.keys().stream();
    }
}
