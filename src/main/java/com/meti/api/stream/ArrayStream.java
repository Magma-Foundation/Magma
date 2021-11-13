package com.meti.api.stream;

public class ArrayStream<T> implements Stream<T> {
    private final T[] items;
    private int counter = 0;

    @SafeVarargs
    public ArrayStream(T... items) {
        this.items = items;
    }

    @Override
    public T head() throws EndOfStreamException {
        if (counter < items.length) {
            return items[counter++];
        }
        throw new EndOfStreamException("No more items left in array.");
    }
}
