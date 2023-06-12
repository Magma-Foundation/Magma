package com.meti;

public class ArrayIterable<T> extends IndexedIterable<T> {
    private final Object[] elements;

    public ArrayIterable(Object[] elements) {
        this.elements = elements;
    }

    @Override
    protected int size() {
        return elements.length;
    }

    @Override
    protected T getElement(int index) {
        //noinspection unchecked
        return (T) elements[index];
    }
}
