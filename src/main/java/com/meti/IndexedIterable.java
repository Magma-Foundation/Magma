package com.meti;

public abstract class IndexedIterable<T> extends AbstractIterable<T> {
    private int counter = 0;

    @Override
    public Option<T> head() {
        if (counter < size()) {
            var value = getElement(counter);
            counter++;
            return Options.Some(value);
        }

        return Options.None();
    }

    protected abstract int size();

    protected abstract T getElement(int index);
}
