package com.meti.collect.stream;

import com.meti.collect.option.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

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

    public static <T> Stream<List<T>> crossToList(Stream<T> first, Supplier<Stream<T>> other) {
        return first.cross(other).map(tuple -> {
            return List.of(tuple.a(), tuple.b());
        });
    }

    public static <T> Stream<List<T>> crossListToList(Stream<List<T>> stream, Supplier<Stream<T>> other) {
        return stream.cross(other).map(tuple -> {
            var list = new ArrayList<>(tuple.a());
            list.add(tuple.b());
            return list;
        });
    }
}
