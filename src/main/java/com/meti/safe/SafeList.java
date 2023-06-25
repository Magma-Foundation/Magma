package com.meti.safe;

import com.meti.safe.iter.IndexedIterator;
import com.meti.safe.iter.Iterator;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

import java.util.ArrayList;
import java.util.List;

public class SafeList<T> {
    private final List<T> list;

    public SafeList(List<T> list) {
        this.list = list;
    }

    public static <T> SafeList<T> empty() {
        return new SafeList<T>(new ArrayList<>());
    }

    public SafeList<T> add(T other) {
        list.add(other);
        return this;
    }

    public Iterator<T> iter() {
        return new IndexedIterator<T>() {
            @Override
            protected int size() {
                return list.size();
            }

            @Override
            protected Option<T> apply(int counter) {
                return new Some<>(list.get(counter));
            }
        };
    }
}
