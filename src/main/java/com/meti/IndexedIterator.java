package com.meti;

public abstract class IndexedIterator<T> extends AbstractIterator<T> {
    protected int counter;

    public IndexedIterator() {
        counter = 0;
    }

    @Override
    public Option<T> head() {
        if (counter < size()) {
            var value = apply(counter);
            counter++;
            return value;
        } else {
            return new None<>();
        }
    }

    protected abstract int size();

    protected abstract Option<T> apply(int counter);
}
