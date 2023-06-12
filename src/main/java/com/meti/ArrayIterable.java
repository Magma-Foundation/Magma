package com.meti;

public class ArrayIterable<T> extends AbstractIterable<T> {
    private final Object[] elements;
    private int counter = 0;

    public ArrayIterable(Object[] elements) {
        this.elements = elements;
    }

    @Override
    public Option<T> head() {
        if (counter < elements.length) {
            var value = elements[counter];
            counter++;
            return Options.Some((T) value);
        }

        return Options.None();
    }
}
