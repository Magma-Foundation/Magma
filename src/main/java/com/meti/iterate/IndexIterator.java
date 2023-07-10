package com.meti.iterate;

import com.meti.collect.Index;
import com.meti.core.None;
import com.meti.core.Option;
import com.meti.core.Some;

public abstract class IndexIterator<T> extends AbstractIterator<T> {
    private Index counter = new Index(0);

    @Override
    public Option<T> head() {
        if (counter.unwrap() < length().unwrap()) {
            var apply = apply(counter);
            counter = counter.next();
            return Some.apply(apply);
        }

        return new None<>();
    }

    protected abstract T apply(Index index);

    protected abstract Index length();
}
