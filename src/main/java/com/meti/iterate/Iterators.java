package com.meti.iterate;

import com.meti.collect.Index;
import com.meti.core.None;
import com.meti.core.Option;

public class Iterators {
    public static <T> Iterator<T> fromOption(Option<T> option) {
        return option.map(Iterators::of).unwrapOrElse(empty());
    }

    @SafeVarargs
    public static <T> Iterator<T> of(T... values) {
        return new IndexIterator<>() {
            @Override
            protected T apply(Index index) {
                return values[index.value()];
            }

            @Override
            protected Index length() {
                return new Index(values.length, values.length);
            }
        };
    }

    public static <T> Iterator<T> empty() {
        return new AbstractIterator<>() {
            @Override
            public Option<T> head() {
                return new None<>();
            }
        };
    }
}
