package com.meti.compile;

import com.meti.compile.clang.CFormat;

import java.util.stream.Stream;

public interface Output<T> {
    Stream<CFormat> streamFormats();

    T apply(CFormat types, T other);
}
