package com.meti.safe.iter;

import com.meti.safe.SafeList;

public class Collectors {
    public static <T> Collector<SafeList<T>, T> toList() {
        return new Collector<>() {
            @Override
            public SafeList<T> initial() {
                return SafeList.empty();
            }

            @Override
            public SafeList<T> folder(SafeList<T> previous, T next) {
                return previous.add(next);
            }
        };
    }
}
