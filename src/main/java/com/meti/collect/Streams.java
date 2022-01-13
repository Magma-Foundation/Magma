package com.meti.collect;

public class Streams {
    public static <T> Stream<T> apply(T... strings) {
        return JavaList.apply(strings).stream();
    }
}
