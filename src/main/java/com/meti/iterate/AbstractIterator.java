package com.meti.iterate;

import com.meti.core.None;
import com.meti.core.Ok;
import com.meti.core.Option;
import com.meti.core.Result;
import com.meti.java.JavaMap;
import com.meti.java.Map;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractIterator<T> implements Iterator<T> {
    @Override
    public <P, R> Unzip<T, P, R> unzip(Function<T, P> mapper) {
        return new UnzipImpl<>(this, mapper);
    }

    @Override
    public boolean anyMatch(Predicate<T> predicate) {
        return foldLeft(false, (aBoolean, t) -> aBoolean || predicate.test(t));
    }

    @Override
    public Iterator<T> then(Iterator<T> other) {
        return new AbstractIterator<T>() {
            @Override
            public Option<T> head() {
                return AbstractIterator.this.head().or(other.head());
            }
        };
    }

    @Override
    public <C> C collect(Collector<T, C> collector) {
        return foldLeft(collector.initial(), collector::foldLeft);
    }

    @Override
    public <C, E> Result<C, E> foldLeftToResult(C initial, BiFunction<C, T, Result<C, E>> folder) {
        return foldLeft(Ok.apply(initial), (result, t) -> result.mapValueToResult(s -> folder.apply(s, t)));
    }

    @Override
    public void forEach(Consumer<T> consumer) {
        while (true) {
            var head = head();
            if (head.isPresent()) {
                consumer.accept(head.unwrap());
            } else {
                break;
            }
        }
    }

    @Override
    public Iterator<T> take(int count) {
        return new AbstractIterator<>() {
            private int counter = 0;

            @Override
            public Option<T> head() {
                if (counter < count) {
                    counter++;
                    return AbstractIterator.this.head();
                } else {
                    return None.apply();
                }
            }
        };
    }

    @Override
    public <R> Iterator<R> flatMap(Function<T, Iterator<R>> mapper) {
        return head().map(mapper).<Iterator<R>>map(first -> new AbstractIterator<>() {
            private Iterator<R> current = first;

            @Override
            public Option<R> head() {
                while (true) {
                    var head = current.head();
                    if (head.isPresent()) {
                        return head;
                    } else {
                        var head1 = AbstractIterator.this.head()
                                .map(mapper)
                                .toTuple(Iterators.empty());

                        if (head1.a()) {
                            current = head1.b();
                        } else {
                            return None.apply();
                        }
                    }
                }
            }
        }).unwrapOrElse(Iterators.empty());
    }

    @Override
    public Iterator<T> filter(Predicate<T> predicate) {
        return flatMap(element -> {
            if (predicate.test(element)) return Iterators.of(element);
            else return Iterators.empty();
        });
    }

    @Override
    public <C> C foldLeft(C initial, BiFunction<C, T, C> folder) {
        var current = initial;
        while (true) {
            var head = head();
            if (head.isPresent()) {
                current = folder.apply(current, head.unwrap());
            } else {
                break;
            }
        }
        return current;
    }

    @Override
    public <R> Iterator<R> map(Function<T, R> mapper) {
        return new AbstractIterator<>() {
            @Override
            public Option<R> head() {
                return AbstractIterator.this.head().map(mapper);
            }
        };
    }

    @Override
    public <R> R into(Function<Iterator<T>, R> mapper) {
        return mapper.apply(this);
    }

    private static class UnzipImpl<T, P, R> implements Unzip<T, P, R> {
        private final Iterator<T> parent;
        private final Function<T, P> keyMapper;
        private final Map<Predicate<P>, Function<Iterator<T>, Iterator<R>>> valueHandlers;

        public UnzipImpl(Iterator<T> parent, Function<T, P> keyMapper) {
            this(parent, keyMapper, JavaMap.empty());
        }

        public UnzipImpl(Iterator<T> parent, Function<T, P> keyMapper, Map<Predicate<P>, Function<Iterator<T>, Iterator<R>>> valueHandlers) {
            this.parent = parent;
            this.keyMapper = keyMapper;
            this.valueHandlers = valueHandlers;
        }

        @Override
        public Unzip<T, P, R> on(Predicate<P> predicate, Function<Iterator<T>, Iterator<R>> handler) {
            return new UnzipImpl<>(parent, keyMapper, valueHandlers.insert(predicate, handler));
        }

        @Override
        public Unzipped<R> onDefault(Function<Iterator<T>, Iterator<R>> handler) {
            return folder -> new AbstractIterator<Iterator<R>>() {
                @Override
                public Option<Iterator<R>> head() {
                    return parent.head().map(head -> {
                        var key = keyMapper.apply(head);
                        return valueHandlers.keys()
                                .filter(presentKey -> presentKey.peek(value -> value.test(key)))
                                .map(valueHandlers::apply)
                                .head()
                                .unwrapOrElse(handler)
                                .apply(Iterators.of(head));
                    });
                }
            }.foldLeft(Iterators.empty(), folder);
        }
    }
}
