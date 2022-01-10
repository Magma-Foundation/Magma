package com.meti.compile;

import com.meti.compile.clang.CFormat;
import com.meti.core.F2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public record MappedOutput<T>(Map<CFormat, T> map) implements Output<T> {
    @Override
    public T apply(CFormat format, T other) {
        if (map.containsKey(format)) return map.get(format);
        return other;
    }

    @Override
    public <R, E extends Exception> Output<R> map(F2<CFormat, T, R, E> mapper) throws E {
        var copy = new HashMap<CFormat, R>();
        for (CFormat cFormat : new ArrayList<>(map.keySet())) {
            copy.put(cFormat, mapper.apply(cFormat, map.get(cFormat)));
        }
        return new MappedOutput<>(copy);
    }

    @Override
    public Stream<CFormat> streamFormats() {
        return map.keySet().stream();
    }
}
