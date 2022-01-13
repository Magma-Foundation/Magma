package com.meti.compile;

import com.meti.compile.clang.CFormat;
import com.meti.core.F2;

import java.util.stream.Stream;

public record MappedOutput<T>(JavaMap<CFormat, T> map) implements Output<T> {
    @Override
    public T apply(CFormat format, T other) {
        if (map.hasKey(format)) return map.apply(format);
        return other;
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
