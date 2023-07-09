package com.meti.iterate;

import com.meti.collect.Index;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;

public abstract class IndexIterator<T> extends AbstractIterator<T> {
    private final Index initial = new Index(0, length().unwrap());
    private Index counter = initial;

    @Override
    public Option<T> head() {
        var next = counter.nextExclusive().toTuple(initial);
        if (next.a()) {
            var value = apply(counter);
            counter = next.b();
            return new Some<>(value);
        }

        return new None<>();
    }

    protected abstract T apply(Index index);

    protected abstract Index length();
}
