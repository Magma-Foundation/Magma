package com.meti.api.iterate;

import com.meti.api.collect.AbstractIterator;
import com.meti.api.collect.Iterator;
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

    public static <T> Iterator<T> empty() {
        return new AbstractIterator<T>() {
            @Override
            public Option<T> head() {
                return None.apply();
            }
        };
    }

    public static <T> Iterator<T> fromOption(Option<T> option) {
        return option.map(Iterators::from).unwrapOrElse(Iterators.empty());
    }
}
