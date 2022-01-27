package com.meti.app.compile.stage;

import com.meti.api.collect.java.JavaMap;
import com.meti.api.core.F2;
import com.meti.app.compile.clang.CFormat;

import java.util.stream.Stream;

public final class MappedTarget<T> implements Target<T> {
    private final JavaMap<CFormat, T> map;

    public MappedTarget() {
        this(new JavaMap<>());
    }

    public MappedTarget(JavaMap<CFormat, T> map) {
        this.map = map;
    }

    @Override
    public Target<T> append(CFormat key, T value) {
        return new MappedTarget<>(map.put(key, value));
    }

    @Override
    public T apply(CFormat format, T other) {
        return map.orElse(format, other);
    }

    @Override
    public <R, E extends Exception> Target<R> map(F2<CFormat, T, R, E> mapper) throws E {
        return new MappedTarget<>(map.mapValues(mapper));
    }

    @Override
    public Stream<CFormat> streamFormats1() {
        return map.keys().stream();
    }
}
