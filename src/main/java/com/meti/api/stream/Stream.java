package com.meti.api.stream;

import com.meti.api.core.F1;
import com.meti.api.core.F2;
import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public interface Stream<T> {
    default Stream<T> filter(F1<T, Boolean, ?> predicate) {
        return () -> {
            try {
                T head;
                do {
                    head = Stream.this.head();
                } while (!predicate.apply(head));
                return head;
            } catch (StreamException e) {
                throw e;
            } catch (Exception e) {
                throw new StreamException(e);
            }
        };
    }

    default Option<T> first() throws StreamException {
        try {
            return new Some<>(head());
        } catch (EndOfStreamException e) {
            return new None<>();
        }
    }

    T head() throws StreamException;

    default <R> Stream<R> flatMap(F1<T, Stream<R>, ?> mapper) {
        return new Stream<>() {
            private Stream<R> current = null;

            @Override
            public R head() throws StreamException {
                if (current == null) advance();

                while (true) {
                    try {
                        return current.head();
                    } catch (EndOfStreamException e) {
                        advance();
                    }
                }
            }

            private void advance() throws StreamException {
                try {
                    current = mapper.apply(Stream.this.head());
                } catch (StreamException e) {
                    throw e;
                } catch (Exception e) {
                    throw new StreamException(e);
                }
            }
        };
    }

    default <E extends Exception> Option<T> foldRight(F2<T, T, T, E> folder) throws StreamException, E {
        try {
            var head = head();
            var folded = foldRight(head, folder);
            return new Some<>(folded);
        } catch (EndOfStreamException e) {
            return new None<>();
        }
    }

    default <R, E extends Exception> R foldRight(R identity, F2<R, T, R, E> folder) throws StreamException, E {
        var current = identity;
        while (true) {
            try {
                current = folder.apply(current, head());
            } catch (EndOfStreamException e) {
                break;
            }
        }
        return current;
    }

    default <R> Stream<R> map(F1<T, R, ?> mapper) {
        return () -> {
            try {
                return mapper.apply(Stream.this.head());
            } catch (StreamException e) {
                throw e;
            } catch (Exception e) {
                throw new StreamException(e);
            }
        };
    }
}
