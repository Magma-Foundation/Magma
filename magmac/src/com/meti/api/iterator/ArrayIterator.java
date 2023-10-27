package com.meti.api.iterator;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public class ArrayIterator<T> extends AbstractIterator<T> {
    private final T[] args;
    private int counter = 0;

    public ArrayIterator(T[] args) {
        this.args = args;
    }

    @Override
    public Option<T> head() {
        if (counter < args.length) {
            var current = args[counter];
            counter++;
            return Some.apply(current);
        } else {
            return None.apply();
        }
    }
}
