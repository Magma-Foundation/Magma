package com.meti.api.collect.stream;

import com.meti.api.collect.java.List;
import com.meti.api.option.Option;

public class Streams {
    public static <T> Stream<T> optionally(Option<T> option) {
        return option.map(Streams::apply).orElse(empty());
    }

    @SafeVarargs
    public static <T> Stream<T> apply(T... strings) {
        return List.apply(strings).stream();
    }

    public static <T> Stream<T> empty() {
        return new EmptyStream<>();
    }

    private static class EmptyStream<T> extends AbstractStream<T> {
        private EmptyStream() {
        }

        @Override
        public T head() throws StreamException {
            throw new EndOfStreamException("Stream is empty.");
        }
    }
}
