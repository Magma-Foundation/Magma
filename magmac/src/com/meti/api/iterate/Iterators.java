package com.meti.api.iterate;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

public class Iterators {
    @SafeVarargs
    public static <T> Iterator<T> from(T... values) {
        return new AbstractIterator<T>() {
            private int counter = 0;

            @Override
            public Option<T> head() {
                if (counter < values.length) {
                    var element = values[counter];
                    counter++;
                    return Some.apply(element);
                } else {
                    return None.apply();
                }
            }
        };
    }
}
