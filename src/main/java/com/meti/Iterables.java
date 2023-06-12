package com.meti;

import static com.meti.Options.None;

public class Iterables {
    public static <T> Iterable.Collector<NativeSet<T>, T> toSet() {
        return new Iterable.Collector<>() {
            @Override
            public NativeSet<T> initial() {
                return NativeSet.empty();
            }

            @Override
            public NativeSet<T> fold(NativeSet<T> current, T element) {
                return current.add(element);
            }
        };
    }

    public static Option<Iterable<Integer>> range(int start, int end) {
        if (start < 0) return None();
        if (end < 0) return None();
        if ((end - start) >= 0) {
            return Options.Some(new AbstractIterable<>() {
                private int counter = start;

                @Override
                public Option<Integer> head() {
                    if (counter < end) {
                        var value = counter;
                        counter++;
                        return Options.Some(value);
                    } else {
                        return None();
                    }
                }
            });
        }

        return None();
    }

    public static <T> Iterable<T> empty() {
        return new AbstractIterable<>() {
            @Override
            public Option<T> head() {
                return None();
            }
        };
    }

    @SafeVarargs
    public static <T> Iterable<T> of(T... values) {
        return new ArrayIterable<>(values);
    }

    public static Iterable.Collector<Integer, Integer> toIntSum() {
        return new Iterable.Collector<>() {
            @Override
            public Integer initial() {
                return 0;
            }

            @Override
            public Integer fold(Integer current, Integer element) {
                return current + element;
            }
        };
    }
}
