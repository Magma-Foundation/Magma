package com.meti.compile.iterator;

import com.meti.compile.option.None;
import com.meti.compile.option.Option;
import com.meti.compile.option.Some;

public class ArrayIterator extends AbstractIterator<String> {
    private final String[] args;
    private int counter = 0;

    public ArrayIterator(String[] args) {
        this.args = args;
    }

    @Override
    public Option<String> head() {
        if (counter < args.length) {
            var current = args[counter];
            counter++;
            return Some.apply(current);
        } else {
            return None.apply();
        }
    }
}
