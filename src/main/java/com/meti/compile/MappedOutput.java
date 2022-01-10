package com.meti.compile;

import com.meti.compile.clang.CFormat;

import java.util.Map;
import java.util.stream.Stream;

public record MappedOutput(Map<CFormat, String> map) implements Output {
    @Override
    public Stream<CFormat> streamFormats() {
        return map.keySet().stream();
    }

    @Override
    public String apply(CFormat format, String other) {
        if (map.containsKey(format)) return map.get(format);
        return other;
    }
}
