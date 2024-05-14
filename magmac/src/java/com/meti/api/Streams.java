package com.meti.api;

import com.meti.compile.None;
import com.meti.compile.Option;
import com.meti.compile.Some;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class Streams {
    public static <T> Stream<T> fromNativeList(List<T> items) {
        return new AbstractStream<>() {
            private int counter = 0;

            @Override
            public Option<T> head() {
                if (counter < items.size()) {
                    var element = items.get(counter);
                    counter++;
                    return new Some<>(element);
                } else {
                    return new None<>();
                }
            }
        };
    }

    public static <T> Stream<T> empty() {
        return new AbstractStream<T>() {
            @Override
            public Option<T> head() {
                return new None<>();
            }
        };
    }

    private static abstract class AbstractStream<T> implements Stream<T> {
        @Override
        public Stream<T> filter(Predicate<T> predicate) {
            return flatMap(value -> predicate.test(value) ? Streams.from(value) : Streams.empty());
        }

        @Override
        public <R> Stream<R> map(Function<T, R> mapper) {
            return new AbstractStream<R>() {
                @Override
                public Option<R> head() {
                    return AbstractStream.this.head().map(mapper);
                }
            };
        }

        @Override
        public <C> C collect(Collector<T, C> collector) {
            return foldRight(collector.initial(), collector::fold);
        }

        private <C> C foldRight(C initial, BiFunction<C, T, C> fold) {
            var current = initial;
            while (true) {
                var finalCurrent = current;
                var tuple = head()
                        .map(head -> fold.apply(finalCurrent, head))
                        .toTuple(current);

                if (tuple.a()) {
                    current = tuple.b();
                } else {
                    return current;
                }
            }
        }

        @Override
        public <R> Stream<R> flatMap(Function<T, Stream<R>> mapper) {
            return head().map(mapper).<Stream<R>>map(head -> new AbstractStream<R>() {
                private Stream<R> parent = head;

                @Override
                public Option<R> head() {
                    while (true) {
                        var next = parent.head();
                        if (next.isPresent()) return next;

                        var tuple = AbstractStream.this.head()
                                .map(mapper)
                                .toTuple(parent);

                        if (tuple.a()) {
                            parent = tuple.b();
                        } else {
                            return new None<>();
                        }
                    }
                }
            }).orElse(empty());
        }
    }

    private static <T> Stream<T> from(T... values) {
        return fromNativeList(List.of(values));
    }
}
