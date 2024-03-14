package com.meti;

import static com.meti.None.None;
import static com.meti.Some.Some;

public class Streams {
    @SafeVarargs
    public static <T> Stream<T> from(T... values) {
        return new AbstractStream<>() {
            private int counter = 0;

            @Override
            public Option<T> next() {
                if (counter < values.length) {
                    var value = values[counter];
                    counter++;
                    return Some(value);
                } else {
                    return None();
                }
            }
        };
    }

    public static <T> Stream<T> fromOption(Option<T> option) {
        //noinspection unchecked
        return option.map(Streams::from).orElseGet(Streams::from);
    }

    public static <T> Stream<T> empty() {
        return new AbstractStream<T>() {
            @Override
            public Option<T> next() {
                return None();
            }
        };
    }
}
