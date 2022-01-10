package com.meti.compile;

import com.meti.compile.clang.CFormat;

import java.util.Map;
import java.util.stream.Stream;

public record MappedOutput<T>(Map<CFormat, T> map) implements Output<T> {
    @Override
    public Stream<CFormat> streamFormats() {
        return map.keySet().stream();
    }

    @Override
    public T apply(CFormat format, T other) {
        if (map.containsKey(format)) return map.get(format);
        return other;
    }
}
