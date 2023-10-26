package com.meti.compile.iterator;

import com.meti.compile.option.None;
import com.meti.compile.option.Option;

public class Iterators {
    @SafeVarargs
    public static <T> Iterator<T> of(T... values) {
        return new ArrayIterator<>(values);
    }

    public static <T> Iterator<T> empty() {
        return new AbstractIterator<T>() {
            @Override
            public Option<T> head() {
                return None.apply();
            }
        };
    }
}
