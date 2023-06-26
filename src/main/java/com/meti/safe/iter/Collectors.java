package com.meti.safe.iter;

import com.meti.safe.SafeList;
import com.meti.safe.SafeMap;
import com.meti.safe.Tuple2;

public class Collectors {
    public static <A, B> Collector<SafeMap<A, B>, Tuple2<A, B>> toMap() {
        return new Collector<SafeMap<A, B>, Tuple2<A, B>>() {
            @Override
            public SafeMap<A, B> initial() {
                return SafeMap.empty();
            }

            @Override
            public SafeMap<A, B> folder(SafeMap<A, B> previous, Tuple2<A, B> next) {
                return null;
            }
        };
    }

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
