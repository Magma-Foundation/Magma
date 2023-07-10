package com.meti.iterate;

import com.meti.collect.Index;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;

public abstract class IndexIterator<T> extends AbstractIterator<T> {
    private final Index initial = new Index(0, length().unwrap());
    private Option<Index> counter = new None<>();

    @Override
    public Option<T> head() {
        if (length().unwrap() == 0) {
            return new None<>();
        }

        if (counter.isEmpty()) {
            counter = new Some<>(initial);
            return new Some<>(apply(initial));
        } else {
            var unwrapped = counter.unwrap();
            var next = unwrapped.nextExclusive().toTuple(unwrapped);
            if (next.a()) {
                var value = apply(next.b());
                counter = new Some<>(next.b());
                return new Some<>(value);
            }

            return new None<>();
        }
    }

    protected abstract T apply(Index index);

    protected abstract Index length();
}
