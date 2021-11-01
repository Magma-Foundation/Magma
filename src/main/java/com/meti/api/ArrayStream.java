package com.meti.api;

public class ArrayStream<T> implements AbstractStream<T> {
    private final T[] elements;
    private int counter = 0;

    @SafeVarargs
    public ArrayStream(T... elements) {
        this.elements = elements;
    }

    @Override
    public T head() throws StreamException {
        if (counter < elements.length) {
            return elements[counter++];
        } else {
            throw new EndOfStreamException("No more elements left.");
        }
    }
}
