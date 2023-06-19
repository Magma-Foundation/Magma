package com.meti;

import java.util.List;

public class NativeIterators {
    public static <T> Iterator<T> fromList(List<T> lines) {
        return new AbstracIterator<>() {
            private int counter = 0;

            @Override
            public Option<T> head() {
                if (counter < lines.size()) {
                    var value = lines.get(counter);
                    counter++;
                    return new Some<>(value);
                } else {
                    return new None<>();
                }
            }
        };
    }
}
