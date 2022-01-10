package com.meti.compile;

import com.meti.compile.clang.CFormat;

import java.util.stream.Stream;

public class EmptyOutput implements Output {
    @Override
    public String apply(CFormat types, String other) {
        return other;
    }

    @Override
    public Stream<CFormat> streamFormats() {
        return Stream.empty();
    }
}
