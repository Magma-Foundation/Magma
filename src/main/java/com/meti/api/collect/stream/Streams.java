package com.meti.api.collect.stream;

import com.meti.api.collect.java.JavaList;
import com.meti.api.option.Option;

public class Streams {
    public static <T> Stream<T> optionally(Option<T> option) {
        return option.map(Streams::apply).orElse(new EmptyStream<>());
    }

    @SafeVarargs
    public static <T> Stream<T> apply(T... strings) {
        return JavaList.apply(strings).stream();
    }
}
