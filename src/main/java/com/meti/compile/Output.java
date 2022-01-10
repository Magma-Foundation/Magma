package com.meti.compile;

import com.meti.compile.clang.CFormat;

import java.util.stream.Stream;

public interface Output {
    Stream<CFormat> streamFormats();

    String apply(CFormat types, String other);
}
