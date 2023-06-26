package com.meti.safe;

import com.meti.safe.iter.IndexedIterator;
import com.meti.safe.iter.Iterator;
import com.meti.safe.iter.Iterators;
import com.meti.safe.option.None;
import com.meti.safe.option.Option;
import com.meti.safe.option.Some;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SafeList<T> {
    private final List<T> list;

    public SafeList(List<T> list) {
        this.list = list;
    }

    public static <T> SafeList<T> empty() {
        return new SafeList<>(new ArrayList<>());
    }

    public SafeList<T> add(T other) {
        list.add(other);
        return this;
    }

    public Iterator<T> iter() {
        return new IndexedIterator<>() {
            @Override
            protected int size() {
                return list.size();
            }

            @Override
            protected Option<T> apply(int counter) {
                return Some.apply(list.get(counter));
            }
        };
    }

    public Option<Index> last() {
        if (list.isEmpty()) {
            return None.apply();
        } else {
            return Some.apply(new Index(list.size() - 1, list.size()));
        }
    }

    public SafeList<T> map(Index index, Function<T, T> mapper) {
        list.set(index.value(), mapper.apply(list.get(index.value())));
        return this;
    }

    public SafeList<T> removeLast() {
        list.remove(list.size() - 1);
        return this;
    }

    public int size() {
        return list.size();
    }

    public Iterator<Index> indices() {
        return Iterators.fromRange(0, list.size()).map(i -> new Index(i, list.size()));
    }

    public SafeList<T> set(Index index, T element) {
        list.set(index.value(), element);
        return this;
    }
}
