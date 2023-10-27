package com.meti.api.iterator;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

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

    public static Iterator<Integer> to(int length) {
        return new AbstractIterator<Integer>() {
            private int counter = 0;

            @Override
            public Option<Integer> head() {
                if (counter < length) {
                    var temp = counter;
                    counter++;
                    return Some.apply(temp);
                } else {
                    return None.apply();
                }
            }
        };
    }
}
