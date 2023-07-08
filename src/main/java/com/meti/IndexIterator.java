package com.meti;

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
