package com.meti.app.compile.stage;

import com.meti.api.core.F2;
import com.meti.app.compile.clang.CFormat;

import java.util.stream.Stream;

public interface Target<T> {
    Target<T> append(CFormat key, T value);

    T apply(CFormat types, T other);

    <R, E extends Exception> Target<R> map(F2<CFormat, T, R, E> mapper) throws E;

    Stream<CFormat> streamFormats();
}
