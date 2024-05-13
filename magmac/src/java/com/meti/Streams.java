package com.meti;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Streams {
    public static <T> Stream<T> from(List<T> items) {
        return new AbstractStream<T>() {
        };
    }

    private static abstract class AbstractStream<T> implements Stream<T> {
        @Override
        public Stream<T> filter(Predicate<T> predicate) {
            return null;
        }

        @Override
        public <R> Stream<R> flatMap(Function<T, Stream<R>> mapper) {
            return null;
        }
    }
}
