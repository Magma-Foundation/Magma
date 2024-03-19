package com.meti.collect.stream;

import com.meti.collect.option.Option;

import java.util.ArrayList;
import java.util.Collections;
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

    public static <T> Stream<T> fromNativeList(List<T> elements) {
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
        return fromNativeList(new ArrayList<>(set));
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

    public static Option<Stream<Integer>> rangeReverse(int start, int end) {
        if (!(start <= end)) {
            return None();
        } else {
            return Some(new AbstractStream<>() {
                private int counter = end - 1;

                @Override
                public Option<Integer> next() {
                    if (counter >= start) {
                        var value = counter;
                        counter--;
                        return Some(value);
                    } else {
                        return None();
                    }
                }
            });
        }
    }

    public static <T> Stream<T> reverse(Stream<T> apply) {
        /*
        TODO: figure out a better algorithm for this

        - SirMathhman, 3/18/2024
        */
        var list = new ArrayList<>(apply.collect(Collectors.toNativeList()));
        Collections.reverse(list);
        return Streams.fromNativeList(list);
    }

    public static Option<Stream<Integer>> range(int start, int end) {
        if (start <= end) return None();

        return Some(new AbstractStream<Integer>() {
            private int counter = start;

            @Override
            public Option<Integer> next() {
                if (counter < end) {
                    var value = counter;
                    counter++;
                    return Some(value);
                } else {
                    return None();
                }
            }
        });
    }
}
