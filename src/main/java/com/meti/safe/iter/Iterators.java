package com.meti.safe.iter;

import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

public class Iterators {
    @SafeVarargs
    public static <T> Iterator<T> of(T... values) {
        return new IndexedIterator<>() {
            @Override
            protected int size() {
                return values.length;
            }

            @Override
            protected Option<T> apply(int counter) {
                return Some.apply(values[counter]);
            }
        };
    }

    public static Iterator<Integer> fromRange(int start, int end) {
        return new IndexedIterator<>() {
            @Override
            protected int size() {
                return end - start;
            }

            @Override
            protected Option<Integer> apply(int counter) {
                return Some.apply(start + counter);
            }
        };
    }

    public static <T> Iterator<T> fromOption(Option<T> option) {
        return option.match(Iterators::of, Iterators::empty);
    }

    private static <T> Iterator<T> empty() {
        return new AbstractIterator<>() {
            @Override
            public Option<T> head() {
                return None.apply();
            }
        };
    }
}
