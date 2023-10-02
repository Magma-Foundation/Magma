package com.meti.api.collect;

import java.util.ArrayList;

public class ImmutableLists {
    private ImmutableLists(){}

    public static <T> Collector< T, List<T>> into() {
        return new Collector<>() {
            @Override
            public List<T> initial() {
                return ImmutableLists.empty();
            }

            @Override
            public List<T> fold(List<T> accumulated, T element) {
                return accumulated.addLast(element);
            }
        };
    }

    public static <T> List<T> empty() {
        return new ImmutableList<>(new ArrayList<>());
    }
}
