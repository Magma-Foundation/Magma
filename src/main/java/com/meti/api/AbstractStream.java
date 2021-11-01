package com.meti.api;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public interface AbstractStream<T> extends Stream<T> {
    @Override
    default <E extends Exception> Option<T> foldRight(F2<T, T, T, E> folder) throws E, StreamException {
        try {
            var first = head();
            return new Some<>(foldRight(first, folder));
        } catch (EndOfStreamException e) {
            return new None<>();
        }
    }

    @Override
    default <R, E extends Exception> R foldRight(R identity, F2<R, T, R, E> folder) throws E, StreamException {
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

    @Override
    default Stream<T> filter(F1<T, Boolean, ?> predicate) {
        return (AbstractStream<T>) () -> {
            while (true) {
                try {
                    var current = AbstractStream.this.head();
                    if (predicate.apply(current)) {
                        return current;
                    }
                } catch (EndOfStreamException e) {
                    throw e;
                } catch (Exception e) {
                    throw new StreamException(e);
                }
            }
        };
    }

    @Override
    default <R> Stream<R> flatMap(F1<T, Stream<R>, ?> mapper) {
        return new AbstractStream<>() {
            private Stream<R> current = new EmptyStream<>();
            private boolean hasCurrent = false;

            @Override
            public R head() throws StreamException {
                if (!hasCurrent) {
                    try {
                        current = mapper.apply(AbstractStream.this.head());
                    } catch (EndOfStreamException e) {
                        throw e;
                    } catch (Exception e) {
                        throw new StreamException(e);
                    }
                    hasCurrent = true;
                }

                while (hasCurrent) {
                    try {
                        return current.head();
                    } catch (EndOfStreamException e) {
                        try {
                            current = mapper.apply(AbstractStream.this.head());
                        } catch (EndOfStreamException e2) {
                            hasCurrent = false;
                        } catch (Exception ex) {
                            throw new StreamException(e);
                        }
                    }
                }

                throw new EndOfStreamException("No more streams left.");
            }
        };
    }

    @Override
    default <R> Stream<R> map(F1<T, R, ?> mapper) {
        return (AbstractStream<R>) () -> {
            try {
                return mapper.apply(AbstractStream.this.head());
            } catch (EndOfStreamException e) {
                throw e;
            } catch (Exception e) {
                throw new StreamException(e);
            }
        };
    }

    @Override
    default Option<T> first() throws StreamException {
        try {
            return new Some<>(head());
        } catch (EndOfStreamException e) {
            return new None<>();
        }
    }
}
