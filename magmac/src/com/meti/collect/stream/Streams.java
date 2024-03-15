package com.meti.collect.stream;

import com.meti.collect.option.Option;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.meti.collect.option.None.None;
import static com.meti.collect.option.Some.Some;

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

    public static <T> Stream<T> fromList(List<T> elements) {
        return new AbstractStream<T>() {
            private int counter = 0;

            @Override
            public Option<T> next() {
                if (counter < elements.size()) {
                    var value = elements.get(counter);
                    counter++;
                    return Some(value);
                } else {
                    return None();
                }
            }
        };
    }

    public static <T> Stream<T> fromSet(Set<T> set) {
        return fromList(new ArrayList<>(set));
    }
}
