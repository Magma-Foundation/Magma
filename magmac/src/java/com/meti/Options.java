package com.meti;

import java.util.stream.Stream;

public class Options {
    public static <T> Stream<T> stream(Option<T> definitionOption) {
        return definitionOption.map(Stream::of).orElse(Stream.empty());
    }
}
