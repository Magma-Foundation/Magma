package com.meti.api.collect;

import com.meti.api.option.None;
import com.meti.api.option.Option;
import com.meti.api.option.Some;

import java.util.ArrayList;

public record ImmutableList<T>(java.util.List<T> nativeList) implements List<T> {
    @Override
    public Iterator<T> iter() {
        return new AbstractIterator<T>() {
            private int counter = 0;

            @Override
            public Option<T> head() {
                if (counter < nativeList.size()) {
                    var element = nativeList.get(counter);
                    counter++;
                    return Some.apply(element);
                } else {
                    return None.apply();
                }
            }
        };
    }

    @Override
    public List<T> addLast(T element) {
        var copy = new ArrayList<>(nativeList);
        copy.add(element);
        return new ImmutableList<>(copy);
    }
}