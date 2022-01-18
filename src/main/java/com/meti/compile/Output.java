package com.meti.compile;

import com.meti.api.core.F2;
import com.meti.compile.clang.CFormat;

import java.util.stream.Stream;

public interface Output<T> {
    Output<T> append(CFormat key, T value);

    T apply(CFormat types, T other);

    <R, E extends Exception> Output<R> map(F2<CFormat, T, R, E> mapper) throws E;

    Stream<CFormat> streamFormats();
}
